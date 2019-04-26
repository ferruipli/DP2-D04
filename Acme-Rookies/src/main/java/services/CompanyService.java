
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Company;
import domain.CreditCard;
import forms.RegistrationForm;

@Service
@Transactional
public class CompanyService {

	// Managed repository --------------------------

	@Autowired
	private CompanyRepository	companyRepository;

	// Other supporting services -------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private Validator			validator;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -------------------------------

	public CompanyService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Company create() {
		Company result;

		result = new Company();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.COMPANY));

		return result;
	}

	public Company findOne(final int companyId) {
		Company result;

		result = this.companyRepository.findOne(companyId);
		Assert.notNull(result);

		return result;
	}

	public Company findOneToDisplayEdit(final int companyId) {
		Assert.isTrue(companyId != 0);

		Company result, principal;

		principal = this.findByPrincipal();
		result = this.companyRepository.findOne(companyId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == companyId);

		return result;
	}

	public Company save(final Company company) {
		Company result;

		result = (Company) this.actorService.save(company);

		return result;
	}

	public void delete(final Company company) {
		Assert.notNull(company);
		Assert.isTrue(company.getId() != 0);
		Assert.isTrue(this.findByPrincipal().equals(company));

		// Delete positions's answers
		this.answerService.deleteAnswerByCompany(company);

		// Delete applications
		this.applicationService.deleteApplicationByCompany(company);

		// Delete positions
		this.positionService.deleteByCompany(company);

		// Delete problems
		this.problemService.deleteByCompany(company);

		this.actorService.delete(company);
	}

	public Collection<Company> findAll() {
		Collection<Company> result;

		result = this.companyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// Other business methods ---------------------

	public Company findByPrincipal() {
		Company result;
		int userAccountId;

		userAccountId = LoginService.getPrincipal().getId();

		result = this.findCompanyByUserAccount(userAccountId);
		Assert.notNull(result);

		return result;
	}

	private Company findCompanyByUserAccount(final int id) {
		Company result;

		result = this.companyRepository.findCompanyByUserAccount(id);

		return result;
	}

	public Collection<Company> findCompaniesOfferedMorePositions() {
		Collection<Company> result;

		result = this.companyRepository.findCompaniesOfferedMorePositions();

		return result;
	}

	public Company reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Company result, companyStored;
		UserAccount userAccount;
		CreditCard creditCard;

		if (registrationForm.getId() == 0) {
			result = this.create();
			creditCard = new CreditCard();

			result.setName(registrationForm.getName());
			result.setSurname(registrationForm.getSurname());
			result.setVATnumber(registrationForm.getVATnumber());
			result.setEmail(registrationForm.getEmail());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setCommercialName(registrationForm.getCommercialName());
			result.setIsSpammer(registrationForm.getIsSpammer());

			creditCard.setHolder(registrationForm.getCreditCard().getHolder());
			creditCard.setMake(registrationForm.getCreditCard().getMake());
			creditCard.setNumber(registrationForm.getCreditCard().getNumber());
			creditCard.setExpirationMonth(registrationForm.getCreditCard().getExpirationMonth());
			creditCard.setExpirationYear(registrationForm.getCreditCard().getExpirationYear());
			creditCard.setCvvCode(registrationForm.getCreditCard().getCvvCode());

			result.setCreditCard(creditCard);

			userAccount = result.getUserAccount();
			userAccount.setUsername(registrationForm.getUserAccount().getUsername());
			userAccount.setPassword(registrationForm.getUserAccount().getPassword());

			this.validateRegistration(result, registrationForm, binding);
		} else {
			result = new Company();
			companyStored = this.findOneToDisplayEdit(registrationForm.getId());

			result.setName(registrationForm.getName());
			result.setSurname(registrationForm.getSurname());
			result.setEmail(registrationForm.getEmail());
			result.setVATnumber(registrationForm.getVATnumber());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setCommercialName(registrationForm.getCommercialName());
			result.setIsSpammer(companyStored.getIsSpammer());
			result.setId(companyStored.getId());
			result.setVersion(companyStored.getVersion());
			result.setCreditCard(companyStored.getCreditCard());

			this.utilityService.validateEmail(registrationForm.getEmail(), binding);

			if (registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) // No ha actualizado ningun atributo de user account
				result.setUserAccount(companyStored.getUserAccount());
			else if (!registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) {// Modifica el username
				this.utilityService.validateUsernameEdition(registrationForm.getUserAccount().getUsername(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = companyStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					result.setUserAccount(userAccount);
				}
			} else if (registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica la password
				this.utilityService.validatePasswordEdition(registrationForm.getUserAccount().getPassword(), registrationForm.getUserAccount().getConfirmPassword(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = companyStored.getUserAccount();
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			} else if (!registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica el username y la password
				this.utilityService.validateUsernamePasswordEdition(registrationForm, binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = companyStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			}

		}
		this.validator.validate(result, binding);

		return result;
	}
	private void validateRegistration(final Company company, final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword, username;
		boolean checkBox, checkBoxData;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getUserAccount().getConfirmPassword();
		username = registrationForm.getUserAccount().getUsername();
		checkBox = registrationForm.getCheckBoxAccepted();
		checkBoxData = registrationForm.getCheckBoxDataProcessesAccepted();

		this.utilityService.validateEmail(company.getEmail(), binding);
		if (username.trim().equals(""))
			binding.rejectValue("userAccount.username", "actor.username.blank", "Must entry a username.");
		if (password.trim().equals("") && confirmPassword.trim().equals("")) {
			binding.rejectValue("userAccount.password", "password.empty", "Must entry a password");
			binding.rejectValue("userAccount.confirmPassword", "confirmPassword.empty", "Must entry a confirm password");
		}
		if (!password.equals(confirmPassword))
			binding.rejectValue("userAccount.confirmPassword", "user.missmatch.password", "Does not match with password");
		if (checkBox == false)
			binding.rejectValue("checkBoxAccepted", "actor.checkBox.agree", "Must agree terms and conditions");
		if (checkBoxData == false)
			binding.rejectValue("checkBoxDataProcessesAccepted", "actor.checkBoxData.agree", "Must agree data processes");
		if (this.userAccountService.existUsername(username))
			binding.rejectValue("userAccount.username", "actor.username.used", "Username already in use");
		if (this.actorService.existEmail(company.getEmail()))
			binding.rejectValue("email", "actor.email.used", "Email already in use");

	}

	public RegistrationForm createForm(final Company company) {
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();

		registrationForm.setName(company.getName());
		registrationForm.setSurname(company.getSurname());
		registrationForm.setVATnumber(company.getVATnumber());
		registrationForm.setEmail(company.getEmail());
		registrationForm.setId(company.getId());
		registrationForm.setPhoto(company.getPhoto());
		registrationForm.setPhoneNumber(company.getPhoneNumber());
		registrationForm.setAddress(company.getAddress());
		registrationForm.setCommercialName(company.getCommercialName());
		registrationForm.setCheckBoxAccepted(false);
		registrationForm.setCheckBoxDataProcessesAccepted(false);

		return registrationForm;
	}

}

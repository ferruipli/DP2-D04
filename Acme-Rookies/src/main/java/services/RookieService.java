
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.CreditCard;
import domain.Rookie;
import forms.RegistrationForm;

@Service
@Transactional
public class RookieService {

	// Managed repository --------------------------

	@Autowired
	private RookieRepository	rookieRepository;

	// Other supporting services -------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -------------------------------

	public RookieService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Rookie create() {
		Rookie result;

		result = new Rookie();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.ROOKIE));

		return result;
	}

	public Rookie findOne(final int rookieId) {
		Rookie result;

		result = this.rookieRepository.findOne(rookieId);
		Assert.notNull(result);

		return result;
	}

	public Rookie findOneToDisplayEdit(final int rookieId) {
		Assert.isTrue(rookieId != 0);

		Rookie result, principal;

		principal = this.findByPrincipal();
		result = this.rookieRepository.findOne(rookieId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == rookieId);

		return result;
	}

	public Rookie save(final Rookie rookie) {
		Rookie result;

		result = (Rookie) this.actorService.save(rookie);

		if (rookie.getId() == 0)
			this.finderService.assignNewFinder(result);

		return result;
	}

	public void delete(final Rookie rookie) {
		Assert.notNull(rookie);
		Assert.isTrue(rookie.getId() != 0);
		Assert.isTrue(this.findByPrincipal().equals(rookie));

		// Delete answer
		this.answerService.deleteAnswerByRookie(rookie);

		// Delete application
		this.applicationService.deleteApplicationByRookie(rookie);

		// Delete finder
		this.finderService.deleteFinder(rookie);

		// Delete curriculums
		this.curriculumService.deleteCurriculums(rookie);

		this.actorService.delete(rookie);
	}

	public Collection<Rookie> findAll() {
		Collection<Rookie> result;

		result = this.rookieRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	// Other business methods ---------------------

	public Collection<Rookie> findRookiesWithMoreApplications() {
		Collection<Rookie> result;

		result = this.rookieRepository.findRookiesWithMoreApplications();
		Assert.notNull(result);

		return result;
	}

	public Rookie findByPrincipal() {
		Rookie result;
		int userAccountId;

		userAccountId = LoginService.getPrincipal().getId();

		result = this.findRookieByUserAccount(userAccountId);
		Assert.notNull(result);

		return result;
	}

	private Rookie findRookieByUserAccount(final int id) {
		Rookie result;

		result = this.rookieRepository.findRookieByUserAccount(id);

		return result;
	}

	public Rookie reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Rookie result, rookieStored;
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
			result = new Rookie();
			rookieStored = this.findOne(registrationForm.getId());

			result.setName(registrationForm.getName());
			result.setSurname(registrationForm.getSurname());
			result.setEmail(registrationForm.getEmail());
			result.setVATnumber(registrationForm.getVATnumber());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setIsSpammer(rookieStored.getIsSpammer());
			result.setId(rookieStored.getId());
			result.setVersion(rookieStored.getVersion());
			result.setCreditCard(rookieStored.getCreditCard());

			this.utilityService.validateEmail(registrationForm.getEmail(), binding);

			if (registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) // No ha actualizado ningun atributo de user account
				result.setUserAccount(rookieStored.getUserAccount());
			else if (!registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) {// Modifica el username
				this.utilityService.validateUsernameEdition(registrationForm.getUserAccount().getUsername(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = rookieStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					result.setUserAccount(userAccount);
				}
			} else if (registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica la password
				this.utilityService.validatePasswordEdition(registrationForm.getUserAccount().getPassword(), registrationForm.getUserAccount().getConfirmPassword(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = rookieStored.getUserAccount();
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			} else if (!registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica el username y la password
				this.utilityService.validateUsernamePasswordEdition(registrationForm, binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = rookieStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			}

		}
		this.validator.validate(result, binding);

		return result;
	}
	private void validateRegistration(final Rookie rookie, final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword, username;
		boolean checkBox, checkBoxData;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getUserAccount().getConfirmPassword();
		username = registrationForm.getUserAccount().getUsername();
		checkBox = registrationForm.getCheckBoxAccepted();
		checkBoxData = registrationForm.getCheckBoxDataProcessesAccepted();

		this.utilityService.validateEmail(rookie.getEmail(), binding);
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
		if (this.actorService.existEmail(rookie.getEmail()))
			binding.rejectValue("email", "actor.email.used", "Email already in use");

	}

	public RegistrationForm createForm(final Rookie rookie) {
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();

		registrationForm.setName(rookie.getName());
		registrationForm.setSurname(rookie.getSurname());
		registrationForm.setVATnumber(rookie.getVATnumber());
		registrationForm.setEmail(rookie.getEmail());
		registrationForm.setId(rookie.getId());
		registrationForm.setPhoto(rookie.getPhoto());
		registrationForm.setPhoneNumber(rookie.getPhoneNumber());
		registrationForm.setAddress(rookie.getAddress());
		registrationForm.setCheckBoxAccepted(false);
		registrationForm.setCheckBoxDataProcessesAccepted(false);

		return registrationForm;
	}

	protected Rookie findByPersonalDataId(final int personalDataId) {
		Rookie result;

		result = this.rookieRepository.findByPersonalDataId(personalDataId);
		Assert.notNull(result);

		return result;
	}

	protected Rookie findByPositionDataId(final int positionDataId) {
		Rookie result;

		result = this.rookieRepository.findByPositionDataId(positionDataId);
		Assert.notNull(result);

		return result;
	}

	protected Rookie findByEducationDataId(final int educationDataId) {
		Rookie result;

		result = this.rookieRepository.findByEducationDataId(educationDataId);
		Assert.notNull(result);

		return result;
	}

	protected Rookie findByMiscellaneousDataId(final int miscellaneousDataId) {
		Rookie result;

		result = this.rookieRepository.findByMiscellaneousDataId(miscellaneousDataId);
		Assert.notNull(result);

		return result;
	}
}

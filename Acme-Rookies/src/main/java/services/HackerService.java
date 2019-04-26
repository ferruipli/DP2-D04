
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.CreditCard;
import domain.Hacker;
import forms.RegistrationForm;

@Service
@Transactional
public class HackerService {

	// Managed repository --------------------------

	@Autowired
	private HackerRepository	hackerRepository;

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

	public HackerService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Hacker create() {
		Hacker result;

		result = new Hacker();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.HACKER));

		return result;
	}

	public Hacker findOne(final int hackerId) {
		Hacker result;

		result = this.hackerRepository.findOne(hackerId);
		Assert.notNull(result);

		return result;
	}

	public Hacker findOneToDisplayEdit(final int hackerId) {
		Assert.isTrue(hackerId != 0);

		Hacker result, principal;

		principal = this.findByPrincipal();
		result = this.hackerRepository.findOne(hackerId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == hackerId);

		return result;
	}

	public Hacker save(final Hacker hacker) {
		Hacker result;

		result = (Hacker) this.actorService.save(hacker);

		if (hacker.getId() == 0)
			this.finderService.assignNewFinder(result);

		return result;
	}

	public void delete(final Hacker hacker) {
		Assert.notNull(hacker);
		Assert.isTrue(hacker.getId() != 0);
		Assert.isTrue(this.findByPrincipal().equals(hacker));

		// Delete answer
		this.answerService.deleteAnswerByHacker(hacker);

		// Delete application
		this.applicationService.deleteApplicationByHacker(hacker);

		// Delete finder
		this.finderService.deleteFinder(hacker);

		// Delete curriculums
		this.curriculumService.deleteCurriculums(hacker);

		this.actorService.delete(hacker);
	}

	public Collection<Hacker> findAll() {
		Collection<Hacker> result;

		result = this.hackerRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	// Other business methods ---------------------

	public Collection<Hacker> findHackersWithMoreApplications() {
		Collection<Hacker> result;

		result = this.hackerRepository.findHackersWithMoreApplications();
		Assert.notNull(result);

		return result;
	}

	public Hacker findByPrincipal() {
		Hacker result;
		int userAccountId;

		userAccountId = LoginService.getPrincipal().getId();

		result = this.findHackerByUserAccount(userAccountId);
		Assert.notNull(result);

		return result;
	}

	private Hacker findHackerByUserAccount(final int id) {
		Hacker result;

		result = this.hackerRepository.findHackerByUserAccount(id);

		return result;
	}

	public Hacker reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Hacker result, hackerStored;
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
			result = new Hacker();
			hackerStored = this.findOneToDisplayEdit(registrationForm.getId());

			result.setName(registrationForm.getName());
			result.setSurname(registrationForm.getSurname());
			result.setEmail(registrationForm.getEmail());
			result.setVATnumber(registrationForm.getVATnumber());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setIsSpammer(hackerStored.getIsSpammer());
			result.setId(hackerStored.getId());
			result.setVersion(hackerStored.getVersion());
			result.setCreditCard(hackerStored.getCreditCard());

			this.utilityService.validateEmail(registrationForm.getEmail(), binding);

			if (registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) // No ha actualizado ningun atributo de user account
				result.setUserAccount(hackerStored.getUserAccount());
			else if (!registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) {// Modifica el username
				this.utilityService.validateUsernameEdition(registrationForm.getUserAccount().getUsername(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = hackerStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					result.setUserAccount(userAccount);
				}
			} else if (registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica la password
				this.utilityService.validatePasswordEdition(registrationForm.getUserAccount().getPassword(), registrationForm.getUserAccount().getConfirmPassword(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = hackerStored.getUserAccount();
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			} else if (!registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica el username y la password
				this.utilityService.validateUsernamePasswordEdition(registrationForm, binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = hackerStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			}

		}
		this.validator.validate(result, binding);

		return result;
	}
	private void validateRegistration(final Hacker hacker, final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword, username;
		boolean checkBox, checkBoxData;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getUserAccount().getConfirmPassword();
		username = registrationForm.getUserAccount().getUsername();
		checkBox = registrationForm.getCheckBoxAccepted();
		checkBoxData = registrationForm.getCheckBoxDataProcessesAccepted();

		this.utilityService.validateEmail(hacker.getEmail(), binding);
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
		if (this.actorService.existEmail(hacker.getEmail()))
			binding.rejectValue("email", "actor.email.used", "Email already in use");

	}

	public RegistrationForm createForm(final Hacker hacker) {
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();

		registrationForm.setName(hacker.getName());
		registrationForm.setSurname(hacker.getSurname());
		registrationForm.setVATnumber(hacker.getVATnumber());
		registrationForm.setEmail(hacker.getEmail());
		registrationForm.setId(hacker.getId());
		registrationForm.setPhoto(hacker.getPhoto());
		registrationForm.setPhoneNumber(hacker.getPhoneNumber());
		registrationForm.setAddress(hacker.getAddress());
		registrationForm.setCheckBoxAccepted(false);
		registrationForm.setCheckBoxDataProcessesAccepted(false);

		return registrationForm;
	}

	protected Hacker findByPersonalDataId(final int personalDataId) {
		Hacker result;

		result = this.hackerRepository.findByPersonalDataId(personalDataId);
		Assert.notNull(result);

		return result;
	}

	protected Hacker findByPositionDataId(final int positionDataId) {
		Hacker result;

		result = this.hackerRepository.findByPositionDataId(positionDataId);
		Assert.notNull(result);

		return result;
	}

	protected Hacker findByEducationDataId(final int educationDataId) {
		Hacker result;

		result = this.hackerRepository.findByEducationDataId(educationDataId);
		Assert.notNull(result);

		return result;
	}

	protected Hacker findByMiscellaneousDataId(final int miscellaneousDataId) {
		Hacker result;

		result = this.hackerRepository.findByMiscellaneousDataId(miscellaneousDataId);
		Assert.notNull(result);

		return result;
	}
}

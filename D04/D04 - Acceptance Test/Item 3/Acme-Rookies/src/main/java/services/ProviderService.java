
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.CreditCard;
import domain.Provider;
import forms.RegistrationForm;

@Service
@Transactional
public class ProviderService {

	// Managed repository ---------------------------------------------

	@Autowired
	private ProviderRepository	providerRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ItemService			itemService;


	//Constructor ----------------------------------------------------

	public ProviderService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------

	public Provider create() {
		Provider result;

		result = new Provider();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.PROVIDER));

		return result;
	}

	public Provider findOne(final int providerId) {
		Provider result;

		result = this.providerRepository.findOne(providerId);
		Assert.notNull(result);

		return result;
	}

	public Provider findOneToDisplayEdit(final int providerId) {
		Assert.isTrue(providerId != 0);

		Provider result, principal;

		principal = this.findByPrincipal();
		result = this.providerRepository.findOne(providerId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == providerId);

		return result;
	}

	public Provider save(final Provider provider) {
		Provider result;

		result = (Provider) this.actorService.save(provider);

		return result;
	}

	public Collection<Provider> findAll() {
		Collection<Provider> result;

		result = this.providerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Provider provider) {
		Assert.notNull(provider);
		Assert.isTrue(provider.getId() != 0);
		Assert.isTrue(this.findByPrincipal().equals(provider));

		// Delete items
		this.itemService.deleteItemsByProvider(provider);

		// Delete sponsorships
		this.sponsorshipService.deleteSponsorships(provider);

		this.actorService.delete(provider);

	}

	// Other business methods ----------------------------------------

	public Collection<Provider> findProvidersWithMoreSponsorships() {
		Collection<Provider> result;

		result = this.providerRepository.findProvidersWithMoreSponsorships();
		Assert.notNull(result);

		return result;
	}

	public Collection<Provider> topFiveProviders() {
		Collection<Provider> results;
		Pageable page;
		Page<Provider> providers;

		page = new PageRequest(0, 5);
		providers = this.providerRepository.topFiveProviders(page);

		results = providers.getContent();

		return results;
	}

	public Provider findByPrincipal() {
		Provider result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	private Provider findByUserAccount(final int userAccountId) {
		Provider result;

		result = this.providerRepository.findByUserAccount(userAccountId);

		return result;
	}

	public RegistrationForm createForm(final Provider provider) {
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();

		registrationForm.setName(provider.getName());
		registrationForm.setSurname(provider.getSurname());
		registrationForm.setVATnumber(provider.getVATnumber());
		registrationForm.setEmail(provider.getEmail());
		registrationForm.setId(provider.getId());
		registrationForm.setPhoto(provider.getPhoto());
		registrationForm.setPhoneNumber(provider.getPhoneNumber());
		registrationForm.setAddress(provider.getAddress());
		registrationForm.setMake(provider.getMake());
		registrationForm.setCheckBoxAccepted(false);
		registrationForm.setCheckBoxDataProcessesAccepted(false);

		return registrationForm;
	}

	public Provider reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Provider result;
		final Provider providerStored;
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
			result.setMake(registrationForm.getMake());

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
			result = new Provider();
			providerStored = this.findOneToDisplayEdit(registrationForm.getId());

			result.setName(registrationForm.getName());
			result.setSurname(registrationForm.getSurname());
			result.setEmail(registrationForm.getEmail());
			result.setVATnumber(registrationForm.getVATnumber());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setMake(registrationForm.getMake());
			result.setIsSpammer(providerStored.getIsSpammer());
			result.setId(providerStored.getId());
			result.setVersion(providerStored.getVersion());
			result.setCreditCard(providerStored.getCreditCard());

			this.utilityService.validateEmail(registrationForm.getEmail(), binding);

			if (registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) // No ha actualizado ningun atributo de user account
				result.setUserAccount(providerStored.getUserAccount());
			else if (!registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) {// Modifica el username
				this.utilityService.validateUsernameEdition(registrationForm.getUserAccount().getUsername(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = providerStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					result.setUserAccount(userAccount);
				}
			} else if (registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica la password
				this.utilityService.validatePasswordEdition(registrationForm.getUserAccount().getPassword(), registrationForm.getUserAccount().getConfirmPassword(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = providerStored.getUserAccount();
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			} else if (!registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica el username y la password
				this.utilityService.validateUsernamePasswordEdition(registrationForm, binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = providerStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			}

		}
		this.validator.validate(result, binding);

		return result;
	}

	private void validateRegistration(final Provider provider, final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword, username;
		boolean checkBox, checkBoxData;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getUserAccount().getConfirmPassword();
		username = registrationForm.getUserAccount().getUsername();
		checkBox = registrationForm.getCheckBoxAccepted();
		checkBoxData = registrationForm.getCheckBoxDataProcessesAccepted();

		this.utilityService.validateEmail(provider.getEmail(), binding);
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
		if (this.actorService.existEmail(provider.getEmail()))
			binding.rejectValue("email", "actor.email.used", "Email already in use");

	}

	protected void flush() {
		this.providerRepository.flush();
	}

}

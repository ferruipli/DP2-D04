
package services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Position;
import forms.RegistrationForm;

@Service
@Transactional
public class UtilityService {

	// Managed repository ------------------------------------------------------

	// Supporting services -----------------------------------------------------

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private CompanyService			companyService;


	// Constructors ------------------------
	public UtilityService() {
		super();
	}

	// Methods ------------------------------
	public Date current_moment() {
		Date result;

		result = new Date(System.currentTimeMillis() - 1);

		return result;
	}

	public void checkEmailActors(final Actor actor) {
		if (actor instanceof Administrator)
			Assert.isTrue(actor.getEmail().matches("[A-Za-z0-9]+@[a-zA-Z0-9.-]+|[\\w]+[\\s]*[\\<][A-Za-z0-9]+@[a-zA-Z0-9.-]+[\\>]|[A-Za-z0-9]+@|[\\w\\s]+[\\<][A-Za-z0-9]+@+[\\>]"));
		else
			Assert.isTrue(actor.getEmail().matches("[A-Za-z0-9]+@[a-zA-Z0-9.-]+|[\\w]+[\\s]*[\\<][A-Za-z0-9]+@[a-zA-Z0-9.-]+[\\>]"));
	}

	public String getValidPhone(String phone) {
		String countryCode, result;

		if (phone != null && !phone.equals("")) {
			phone = phone.trim();

			if (phone.matches("(([0-9]{1,3}\\ )?([0-9]+))")) {
				countryCode = this.customisationService.find().getCountryCode();
				result = countryCode + " " + phone;
			} else
				result = phone;
		} else
			result = null;

		return result;
	}

	public void checkURLS(final String urls) {
		final List<String> urlList;

		Assert.notNull(urls);
		urlList = this.getSplittedString(urls);

		for (final String at : urlList)
			try {
				new URL(at);
			} catch (final MalformedURLException e) {
				throw new IllegalArgumentException("Invalid URL");
			}
	}

	public List<String> getSplittedString(final String string) {
		List<String> result;
		String[] stringsArray;

		result = new ArrayList<>();
		stringsArray = string.split("\r");

		for (String at : stringsArray) {
			at = at.trim();
			if (!at.isEmpty())
				result.add(at);
		}

		return result;
	}

	public List<String> ListByString(final String cadena) {
		List<String> result;
		String[] campos;
		String word;

		campos = cadena.split(",");

		result = new ArrayList<String>();
		for (final String s : campos) {
			word = s.trim();
			result.add(word);
		}

		return result;
	}

	public String generateValidTicker(final Position position) {
		final String letters;
		String result;
		Integer counter;
		Integer size;
		final String commercialNameWithoutSpace;
		String commercialNameWithoutCharacter, commercialNameWithoutNumber;
		String commercialName;

		counter = 0;

		commercialName = this.companyService.findByPrincipal().getCommercialName();

		commercialNameWithoutSpace = commercialName.replaceAll("\\s", "");
		commercialNameWithoutCharacter = commercialNameWithoutSpace.replaceAll("\\W", "");
		commercialNameWithoutNumber = commercialNameWithoutCharacter.replaceAll("\\d", "");

		size = commercialNameWithoutNumber.length();

		if (size == 0)
			letters = "XXXX-";
		else if (size == 1)
			letters = commercialNameWithoutNumber + "XXX-";
		else if (size == 2)
			letters = commercialNameWithoutNumber + "XX-";
		else if (size == 3)
			letters = commercialNameWithoutNumber + "X-";
		else
			letters = commercialNameWithoutNumber.substring(0, 4) + "-";

		do {
			result = letters + this.createRandomNumbers();
			counter++;
		} while (!(this.positionService.existTicker(result) == null) && counter < 650000);

		return result;
	}

	private String createRandomNumbers() {
		String result, characters;
		Random randomNumber;

		result = "";
		randomNumber = new Random();
		characters = "0123456789";

		for (int i = 0; i <= 3; i++)
			result += characters.charAt(randomNumber.nextInt(characters.length()));

		return result;
	}

	protected void validateUsernamePasswordEdition(final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword, username;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getUserAccount().getConfirmPassword();
		username = registrationForm.getUserAccount().getUsername();

		if (password.trim().equals("") && confirmPassword.trim().equals("")) {
			binding.rejectValue("userAccount.password", "password.empty", "Must entry a password");
			binding.rejectValue("userAccount.confirmPassword", "confirmPassword.empty", "Must entry a confirm password");
		}
		if (username.trim().equals(""))
			binding.rejectValue("userAccount.username", "actor.username.blank", "Must entry a username.");
		if (!password.equals(confirmPassword))
			binding.rejectValue("userAccount.confirmPassword", "user.missmatch.password", "Does not match with password");
		if (this.userAccountService.existUsername(username))
			binding.rejectValue("userAccount.username", "actor.username.used", "Username already in use");
		if (password.length() < 5 || password.length() > 32)
			binding.rejectValue("userAccount.password", "actor.password.size", "Password must have between 5 and 32 characters");
		if (username.length() < 5 || username.length() > 32)
			binding.rejectValue("userAccount.username", "actor.username.size", "Username must have between 5 and 32 characters.");

	}

	protected void validateUsernameEdition(final String username, final BindingResult binding) {

		if (username.trim().equals(""))
			binding.rejectValue("userAccount.username", "actor.username.blank", "Must entry a username.");
		if (this.userAccountService.existUsername(username))
			binding.rejectValue("userAccount.username", "actor.username.used", "Username already in use");
		if (username.length() < 5 || username.length() > 32)
			binding.rejectValue("userAccount.username", "actor.username.size", "Username must have between 5 and 32 characters.");

	}

	protected void validatePasswordEdition(final String password, final String confirmPassword, final BindingResult binding) {

		if (password.trim().equals("") && confirmPassword.trim().equals("")) {
			binding.rejectValue("userAccount.password", "password.empty", "Must entry a password");
			binding.rejectValue("userAccount.confirmPassword", "confirmPassword.empty", "Must entry a confirm password");
		}
		if (!password.equals(confirmPassword))
			binding.rejectValue("userAccount.confirmPassword", "user.missmatch.password", "Does not match with password");
		if (password.length() < 5 || password.length() > 32)
			binding.rejectValue("userAccount.password", "actor.password.size", "Password must have between 5 and 32 characters");

	}

	protected void validateEmailAdministrator(final String email, final BindingResult binding) {

		if (!email.matches("[A-Za-z0-9]+@[a-zA-Z0-9.-]+|[\\w]+[\\s]*[\\<][A-Za-z0-9]+@[a-zA-Z0-9.-]+[\\>]|[A-Za-z0-9]+@|[\\w\\s]+[\\<][A-Za-z0-9]+@+[\\>]"))
			binding.rejectValue("email", "actor.email.error", "Invalid email pattern");

	}

	protected void validateEmail(final String email, final BindingResult binding) {

		if (!email.matches("[A-Za-z0-9]+@[a-zA-Z0-9.-]+|[\\w]+[\\s]*[\\<][A-Za-z0-9]+@[a-zA-Z0-9.-]+[\\>]"))
			binding.rejectValue("email", "actor.email.error", "Invalid email pattern");

	}

	public void checkAttachments(final String attachments) {
		final List<String> attachmentsList;

		Assert.notNull(attachments);
		attachmentsList = this.getSplittedString(attachments);

		for (final String at : attachmentsList)
			try {
				new URL(at);
			} catch (final MalformedURLException e) {
				throw new DataIntegrityViolationException("Invalid URL");
			}
	}

}

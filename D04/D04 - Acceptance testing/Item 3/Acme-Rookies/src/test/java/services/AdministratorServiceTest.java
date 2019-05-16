
package services;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Company;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// Services under testing ---------------------------
	@Autowired
	private AdministratorService	administratorService;

	// Supporting test ---------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ActorService			actorService;


	// Test

	/*
	 * A: An actor who is authenticated as administrator must be able to:
	 * Create user accounts for new administrators
	 */
	@Test
	public void registerAdministratorDriver() {
		final Object testingData[][] = {
			/*
			 * B: Positive test
			 * 
			 * C: Approximately 100% of sentence coverage, since it has been
			 * covered 18 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTEST", "AdministratorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", null
			},
			/*
			 * B: Administrator::name is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "", "AdministratorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Administrator::surname is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTEST", "", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Administrator::email is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTEST", "AdministratorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Administrator::creditCard::holder is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTEST", "AdministratorTEST", 14, "", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Administrator::creditCard::make is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTEST", "AdministratorTEST", 14, "holderTEST", "", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Administrator::creditCard::number is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTEST", "AdministratorTEST", 14, "holderTEST", "makeTEST", "", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Administrator::creditCard::expirationMonth is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTEST", "AdministratorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Administrator::creditCard::expirationYear is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTEST", "AdministratorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Administrator::creditCard is expired
			 * 
			 * C: Approximately 39% of sentence coverage, since it has been
			 * covered 7 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTEST", "AdministratorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "18", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Actor unauthenticated tries to register an administrator account
			 * 
			 * C: Approximately 11% of sentence coverage, since it has been
			 * covered 2 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				null, "AdministratorTEST", "AdministratorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: A company tries to register an administrator
			 * 
			 * C: Approximately 17% of sentence coverage, since it has been
			 * covered 3 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"company1", "AdministratorTEST", "AdministratorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerAdministratorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (Class<?>) testingData[i][14]);
	}

	protected void registerAdministratorTemplate(final String username, final String name, final String surname, final int VATnumber, final String holder, final String make, final String number, final String month, final String year, final int cvvCode,
		final String photo, final String email, final String phoneNumber, final String address, final Class<?> expected) {
		Class<?> caught;
		Administrator administrator, saved;
		UserAccount userAccount;
		Authority auth;
		CreditCard creditCard;

		super.startTransaction();

		caught = null;

		try {
			super.authenticate(username);

			auth = new Authority();
			auth.setAuthority("ADMIN");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			administrator = this.administratorService.create();
			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setAddress(address);
			administrator.setEmail(email);
			administrator.setPhoneNumber(phoneNumber);
			administrator.setVATnumber(VATnumber);
			administrator.setPhoto(photo);

			creditCard = new CreditCard();
			creditCard.setHolder(holder);
			creditCard.setMake(make);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(month);
			creditCard.setExpirationYear(year);
			creditCard.setCvvCode(cvvCode);

			administrator.setCreditCard(creditCard);

			saved = this.administratorService.save(administrator);
			this.administratorRepository.flush();

			administrator = this.administratorService.findOne(saved.getId());
			Assert.isTrue(saved.equals(administrator));

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been
	 * covered 18 lines of code of 18 possible.
	 * 
	 * D: Approximately 8% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test
	public void save_positive_test() {
		Administrator administrator, saved;
		String oldName;

		super.authenticate("admin1");

		this.startTransaction();

		administrator = this.administratorService.findOneToDisplayEdit(super.getEntityId("administrator1"));

		oldName = administrator.getName();

		administrator.setName("TEST");

		saved = this.administratorService.save(administrator);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: Actor::name is blank
	 * 
	 * C: Approximately 67% of sentence coverage, since it has been
	 * covered 10 lines of code of 18 possible.
	 * 
	 * D: Approximately 8% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void save_negative_test() {
		Administrator administrator, saved;
		String oldName;

		super.authenticate("admin1");

		this.startTransaction();

		administrator = this.administratorService.findOneToDisplayEdit(super.getEntityId("administrator1"));

		oldName = administrator.getName();

		administrator.setName("");

		saved = this.administratorService.save(administrator);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Ban an actor with the spammer flag
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been
	 * covered 6 lines of code of 6 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void banActor_positive_test() {
		Company company;

		super.authenticate("admin1");

		company = this.companyService.findOne(super.getEntityId("company1"));

		company.setIsSpammer(true);

		this.actorService.ban(company);

		Assert.isTrue(company.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Ban an actor with the spammer flag
	 * 
	 * B: Ban an actor that doesn't have spammer flag
	 * 
	 * C: Approximately 33% of sentence coverage, since it has been
	 * covered 2 lines of code of 6 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void banActor_negative_test() {
		Company company;

		super.authenticate("admin1");

		company = this.companyService.findOne(super.getEntityId("company1"));

		company.setIsSpammer(false);

		this.actorService.ban(company);

		Assert.isTrue(company.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Unban an actor who was banned previously
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been
	 * covered 6 lines of code of 6 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void unBanActor_positive_test() {
		Company company;

		super.authenticate("admin1");

		company = this.companyService.findOne(super.getEntityId("company1"));

		company.setIsSpammer(true);
		company.getUserAccount().setIsBanned(true);

		this.actorService.unBan(company);

		Assert.isTrue(!company.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Unban an actor who was banned previously
	 * 
	 * B: Unban an actor who wasn't banned previously
	 * 
	 * C: Approximately 33% of sentence coverage, since it has been
	 * covered 2 lines of code of 6 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void unBanActor_negative_test() {
		Company company;

		super.authenticate("admin1");

		company = this.companyService.findOne(super.getEntityId("company1"));

		company.setIsSpammer(true);
		company.getUserAccount().setIsBanned(false);

		this.actorService.unBan(company);

		Assert.isTrue(!company.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

}


package services;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.HackerRepository;
import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Hacker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HackerServiceTest extends AbstractTest {

	// The SUT ----------------------------------------------------------------

	@Autowired
	private HackerService		hackerService;

	// Other services ---------------------------------------------------------

	@Autowired
	private HackerRepository	hackerRepository;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The average, the minimum, the maximum and the standard deviation of the
	 * number of application per hacker.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testFindHackerWithMoreApplications() {
		Collection<Hacker> hackers;
		Hacker hacker2, hacker4;
		int hacker2Id, hacker4Id;

		hacker2Id = super.getEntityId("hacker2");
		hacker4Id = super.getEntityId("hacker4");
		hacker2 = this.hackerRepository.findOne(hacker2Id);
		hacker4 = this.hackerRepository.findOne(hacker4Id);

		hackers = this.hackerService.findHackersWithMoreApplications();

		Assert.isTrue(hackers.size() == 2);
		Assert.isTrue(hackers.contains(hacker2) && hackers.contains(hacker4));
	}

	/*
	 * A: An actor who is not authenticated must be able to:
	 * Register to the system as hacker
	 */
	@Test
	public void registerHackerDriver() {
		final Object testingData[][] = {
			/*
			 * B: Positive test
			 * 
			 * C: Approximately 100% of sentence coverage, since it has been
			 * covered 16 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "HackerTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", null
			},
			/*
			 * B: Hacker::name is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"", "HackerTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Hacker::surname is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Hacker::email is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "HackerTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Hacker::creditCard::holder is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "HackerTEST", 14, "", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Hacker::creditCard::make is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "HackerTEST", 14, "holderTEST", "", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Hacker::creditCard::number is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "HackerTEST", 14, "holderTEST", "makeTEST", "", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Hacker::creditCard::expirationMonth is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "HackerTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Hacker::creditCard::expirationYear is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "HackerTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Hacker::creditCard is expired
			 * 
			 * C: Approximately 31% of sentence coverage, since it has been
			 * covered 5 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "HackerTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "18", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Hacker::email does not match with the pattern
			 * 
			 * C: Approximately 31% of sentence coverage, since it has been
			 * covered 5 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"HackerTEST", "HackerTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@", "63214578", "calle test", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerHackerTemplate((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (Class<?>) testingData[i][13]);
	}

	protected void registerHackerTemplate(final String name, final String surname, final int VATnumber, final String holder, final String make, final String number, final String month, final String year, final int cvvCode, final String photo,
		final String email, final String phoneNumber, final String address, final Class<?> expected) {
		Class<?> caught;
		Hacker hacker, saved;
		UserAccount userAccount;
		Authority auth;
		CreditCard creditCard;

		super.startTransaction();

		caught = null;

		try {
			auth = new Authority();
			auth.setAuthority("HACKER");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			hacker = this.hackerService.create();
			hacker.setName(name);
			hacker.setSurname(surname);
			hacker.setAddress(address);
			hacker.setEmail(email);
			hacker.setPhoneNumber(phoneNumber);
			hacker.setVATnumber(VATnumber);
			hacker.setPhoto(photo);

			creditCard = new CreditCard();
			creditCard.setHolder(holder);
			creditCard.setMake(make);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(month);
			creditCard.setExpirationYear(year);
			creditCard.setCvvCode(cvvCode);

			hacker.setCreditCard(creditCard);

			saved = this.hackerService.save(hacker);
			this.hackerRepository.flush();

			hacker = this.hackerService.findOne(saved.getId());
			Assert.isTrue(saved.equals(hacker));
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
	 * covered 16 lines of code of 16 possible.
	 * 
	 * D: Approximately 8% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test
	public void save_positive_test() {
		Hacker hacker, saved;
		String oldName;

		super.authenticate("hacker1");

		this.startTransaction();

		hacker = this.hackerService.findOneToDisplayEdit(super.getEntityId("hacker1"));

		oldName = hacker.getName();

		hacker.setName("TEST");

		saved = this.hackerService.save(hacker);

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
	 * C: Approximately 63% of sentence coverage, since it has been
	 * covered 10 lines of code of 16 possible.
	 * 
	 * D: Approximately 8% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void save_negative_test() {
		Hacker hacker, saved;
		String oldName;

		super.authenticate("hacker1");

		this.startTransaction();

		hacker = this.hackerService.findOneToDisplayEdit(super.getEntityId("hacker1"));

		oldName = hacker.getName();

		hacker.setName("");

		saved = this.hackerService.save(hacker);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

}

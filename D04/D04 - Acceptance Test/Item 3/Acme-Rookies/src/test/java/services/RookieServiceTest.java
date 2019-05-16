
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

import repositories.RookieRepository;
import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Rookie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RookieServiceTest extends AbstractTest {

	// The SUT ----------------------------------------------------------------

	@Autowired
	private RookieService		rookieService;

	// Other services ---------------------------------------------------------

	@Autowired
	private RookieRepository	rookieRepository;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The average, the minimum, the maximum and the standard deviation of the
	 * number of application per rookie.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testFindRookieWithMoreApplications() {
		Collection<Rookie> rookies;
		Rookie rookie2, rookie4;
		int rookie2Id, rookie4Id;

		rookie2Id = super.getEntityId("rookie2");
		rookie4Id = super.getEntityId("rookie4");
		rookie2 = this.rookieRepository.findOne(rookie2Id);
		rookie4 = this.rookieRepository.findOne(rookie4Id);

		rookies = this.rookieService.findRookiesWithMoreApplications();

		Assert.isTrue(rookies.size() == 2);
		Assert.isTrue(rookies.contains(rookie2) && rookies.contains(rookie4));
	}

	/*
	 * A: An actor who is not authenticated must be able to:
	 * Register to the system as rookie
	 */
	@Test
	public void registerRookieDriver() {
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
				"RookieTEST", "RookieTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", null
			},
			/*
			 * B: Rookie::name is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"", "RookieTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Rookie::surname is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"RookieTEST", "", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Rookie::email is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"RookieTEST", "RookieTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Rookie::creditCard::holder is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"RookieTEST", "RookieTEST", 14, "", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Rookie::creditCard::make is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"RookieTEST", "RookieTEST", 14, "holderTEST", "", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Rookie::creditCard::number is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"RookieTEST", "RookieTEST", 14, "holderTEST", "makeTEST", "", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Rookie::creditCard::expirationMonth is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"RookieTEST", "RookieTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Rookie::creditCard::expirationYear is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"RookieTEST", "RookieTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Rookie::creditCard is expired
			 * 
			 * C: Approximately 31% of sentence coverage, since it has been
			 * covered 5 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"RookieTEST", "RookieTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "18", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Rookie::email does not match with the pattern
			 * 
			 * C: Approximately 31% of sentence coverage, since it has been
			 * covered 5 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"RookieTEST", "RookieTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@", "63214578", "calle test", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerRookieTemplate((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (Class<?>) testingData[i][13]);
	}

	protected void registerRookieTemplate(final String name, final String surname, final int VATnumber, final String holder, final String make, final String number, final String month, final String year, final int cvvCode, final String photo,
		final String email, final String phoneNumber, final String address, final Class<?> expected) {
		Class<?> caught;
		Rookie rookie, saved;
		UserAccount userAccount;
		Authority auth;
		CreditCard creditCard;

		super.startTransaction();

		caught = null;

		try {
			auth = new Authority();
			auth.setAuthority("ROOKIE");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			rookie = this.rookieService.create();
			rookie.setName(name);
			rookie.setSurname(surname);
			rookie.setAddress(address);
			rookie.setEmail(email);
			rookie.setPhoneNumber(phoneNumber);
			rookie.setVATnumber(VATnumber);
			rookie.setPhoto(photo);

			creditCard = new CreditCard();
			creditCard.setHolder(holder);
			creditCard.setMake(make);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(month);
			creditCard.setExpirationYear(year);
			creditCard.setCvvCode(cvvCode);

			rookie.setCreditCard(creditCard);

			saved = this.rookieService.save(rookie);
			this.rookieRepository.flush();

			rookie = this.rookieService.findOne(saved.getId());
			Assert.isTrue(saved.equals(rookie));
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
		Rookie rookie, saved;
		String oldName;

		super.authenticate("rookie1");

		this.startTransaction();

		rookie = this.rookieService.findOneToDisplayEdit(super.getEntityId("rookie1"));

		oldName = rookie.getName();

		rookie.setName("TEST");

		saved = this.rookieService.save(rookie);

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
		Rookie rookie, saved;
		String oldName;

		super.authenticate("rookie1");

		this.startTransaction();

		rookie = this.rookieService.findOneToDisplayEdit(super.getEntityId("rookie1"));

		oldName = rookie.getName();

		rookie.setName("");

		saved = this.rookieService.save(rookie);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

}


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

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Auditor;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	// Services under testing ---------------------------
	@Autowired
	private AuditorService	auditorService;


	// Supporting test ---------------------------------------

	// Test

	/*
	 * A: An actor who is authenticated as administrator must be able to:
	 * Create user accounts for new auditors
	 */
	@Test
	public void registerAuditorDriver() {
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
				"admin1", "auditorTEST", "auditorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", null
			},
			/*
			 * B: Auditor::name is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "", "auditorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Auditor::surname is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "auditorTEST", "", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Auditor::email is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "auditorTEST", "auditorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Auditor::creditCard::holder is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "auditorTEST", "auditorTEST", 14, "", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Auditor::creditCard::make is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "auditorTEST", "auditorTEST", 14, "holderTEST", "", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Auditor::creditCard::number is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "auditorTEST", "auditorTEST", 14, "holderTEST", "makeTEST", "", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", ConstraintViolationException.class
			},
			/*
			 * B: Auditor::creditCard::expirationMonth is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "auditorTEST", "auditorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Auditor::creditCard::expirationYear is blank
			 * 
			 * C: Approximately 67% of sentence coverage, since it has been
			 * covered 12 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "auditorTEST", "auditorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Auditor::creditCard is expired
			 * 
			 * C: Approximately 31% of sentence coverage, since it has been
			 * covered 7 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "auditorTEST", "auditorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "18", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: Actor unauthenticated tries to register an auditor account
			 * 
			 * C: Approximately 17% of sentence coverage, since it has been
			 * covered 3 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				null, "auditorTEST", "auditorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
			/*
			 * B: A company tries to register an auditor
			 * 
			 * C: Approximately 17% of sentence coverage, since it has been
			 * covered 3 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"company1", "auditorTEST", "auditorTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerAuditorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (Class<?>) testingData[i][14]);
	}

	protected void registerAuditorTemplate(final String username, final String name, final String surname, final int VATnumber, final String holder, final String make, final String number, final String month, final String year, final int cvvCode,
		final String photo, final String email, final String phoneNumber, final String address, final Class<?> expected) {
		Class<?> caught;
		Auditor auditor, saved;
		UserAccount userAccount;
		Authority auth;
		CreditCard creditCard;

		super.startTransaction();

		caught = null;

		try {
			super.authenticate(username);

			auth = new Authority();
			auth.setAuthority("AUDITOR");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			auditor = this.auditorService.create();
			auditor.setName(name);
			auditor.setSurname(surname);
			auditor.setAddress(address);
			auditor.setEmail(email);
			auditor.setPhoneNumber(phoneNumber);
			auditor.setVATnumber(VATnumber);
			auditor.setPhoto(photo);

			creditCard = new CreditCard();
			creditCard.setHolder(holder);
			creditCard.setMake(make);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(month);
			creditCard.setExpirationYear(year);
			creditCard.setCvvCode(cvvCode);

			auditor.setCreditCard(creditCard);

			saved = this.auditorService.save(auditor);
			this.auditorService.flush();

			auditor = this.auditorService.findOne(saved.getId());
			Assert.isTrue(saved.equals(auditor));

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
		Auditor auditor, saved;
		String oldName;

		super.authenticate("auditor1");

		this.startTransaction();

		auditor = this.auditorService.findOneToDisplayEdit(super.getEntityId("auditor1"));

		oldName = auditor.getName();

		auditor.setName("TEST");

		saved = this.auditorService.save(auditor);

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
		Auditor auditor, saved;
		String oldName;

		super.authenticate("auditor1");

		this.startTransaction();

		auditor = this.auditorService.findOneToDisplayEdit(super.getEntityId("auditor1"));

		oldName = auditor.getName();

		auditor.setName("");

		saved = this.auditorService.save(auditor);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

}

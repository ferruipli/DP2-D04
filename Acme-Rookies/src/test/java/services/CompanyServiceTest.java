
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CompanyRepository;
import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Company;
import domain.CreditCard;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CompanyServiceTest extends AbstractTest {

	// The SUT ----------------------------------------------------------------

	@Autowired
	private CompanyService		companyService;

	// Other services ---------------------------------------------------------

	@Autowired
	private CompanyRepository	companyRepository;

	@Autowired
	private PositionService		positionService;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is not authenticated must be able to:
	 * Register to the system as company
	 */
	@Test
	public void registerCompanyDriver() {
		final Object testingData[][] = {
			/*
			 * B: Positive test
			 * 
			 * C: Approximately 100% of sentence coverage, since it has been
			 * covered 16 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "commercialNameTest", null
			},
			/*
			 * B: Company::name is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"", "CompanyTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "commercialNameTest", ConstraintViolationException.class
			},
			/*
			 * B: Company::surname is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "commercialNameTest", ConstraintViolationException.class
			},
			/*
			 * B: Company::email is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "", "63214578", "calle test", "commercialNameTest", IllegalArgumentException.class
			},
			/*
			 * B: Company::creditCard::holder is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "commercialNameTest", ConstraintViolationException.class
			},
			/*
			 * B: Company::creditCard::make is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "holderTEST", "", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "commercialNameTest", ConstraintViolationException.class
			},
			/*
			 * B: Company::creditCard::number is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "holderTEST", "makeTEST", "", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "commercialNameTest", ConstraintViolationException.class
			},
			/*
			 * B: Company::creditCard::expirationMonth is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "commercialNameTest", IllegalArgumentException.class
			},
			/*
			 * B: Company::creditCard::expirationYear is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "commercialNameTest", IllegalArgumentException.class
			},
			/*
			 * B: Company::commercialName is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "", ConstraintViolationException.class
			},
			/*
			 * B: Company::creditCard is expired
			 * 
			 * C: Approximately 31% of sentence coverage, since it has been
			 * covered 5 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "18", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "commercialNameTest", IllegalArgumentException.class
			},
			/*
			 * B: Company::email does not match with the pattern
			 * 
			 * C: Approximately 31% of sentence coverage, since it has been
			 * covered 5 lines of code of 16 possible.
			 * 
			 * D: Approximately 75% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CompanyTEST", "CompanyTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@", "63214578", "calle test", "commercialNameTest", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerCompanyTemplate((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (Class<?>) testingData[i][14]);
	}

	protected void registerCompanyTemplate(final String name, final String surname, final int VATnumber, final String holder, final String make, final String number, final String month, final String year, final int cvvCode, final String photo,
		final String email, final String phoneNumber, final String address, final String commercialName, final Class<?> expected) {
		Class<?> caught;
		Company company, saved;
		UserAccount userAccount;
		Authority auth;
		CreditCard creditCard;

		super.startTransaction();

		caught = null;

		try {
			auth = new Authority();
			auth.setAuthority("COMPANY");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			company = this.companyService.create();
			company.setName(name);
			company.setSurname(surname);
			company.setAddress(address);
			company.setCommercialName(commercialName);
			company.setEmail(email);
			company.setPhoneNumber(phoneNumber);
			company.setVATnumber(VATnumber);
			company.setPhoto(photo);

			creditCard = new CreditCard();
			creditCard.setHolder(holder);
			creditCard.setMake(make);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(month);
			creditCard.setExpirationYear(year);
			creditCard.setCvvCode(cvvCode);

			company.setCreditCard(creditCard);

			saved = this.companyService.save(company);
			this.companyRepository.flush();

			company = this.companyService.findOne(saved.getId());
			Assert.isTrue(saved.equals(company));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * A: An actor who is not authenticated must be able to:
	 * List the companies available and navigate to the corresponding positions
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been
	 * covered 7 lines of code of 7 possible.
	 * 
	 * D: Approximately 100% of data coverage
	 */
	@Test
	public void listCompaniesAvailablesNavigatePositions_positive_test() {
		List<Company> companies;
		List<Position> positions;
		Company company;

		companies = new ArrayList<>(this.companyService.findAll());
		Assert.notNull(companies);

		company = companies.get(0);
		positions = new ArrayList<>(this.positionService.findFinalModePositionsByCompany(company.getId()));
		Assert.notNull(positions);
	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * List the companies available and navigate to the corresponding positions
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been
	 * covered 7 lines of code of 7 possible.
	 * 
	 * D: Approximately 100% of data coverage
	 */
	@Test
	public void AuthenticatedlistCompaniesAvailablesNavigatePositions_positive_test() {

		super.authenticate("company1");

		List<Company> companies;
		List<Position> positions;
		Company company;

		companies = new ArrayList<>(this.companyService.findAll());
		Assert.notNull(companies);

		company = companies.get(0);
		positions = new ArrayList<>(this.positionService.findFinalModePositionsByCompany(company.getId()));
		Assert.notNull(positions);

		super.unauthenticate();
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
		Company company, saved;
		String oldCommercialName;

		super.authenticate("company1");

		this.startTransaction();

		company = this.companyService.findOneToDisplayEdit(super.getEntityId("company1"));

		oldCommercialName = company.getCommercialName();

		company.setCommercialName("TEST");

		saved = this.companyService.save(company);

		Assert.isTrue(!saved.getCommercialName().equals(oldCommercialName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: Actor::commercialName is blank
	 * 
	 * C: Approximately 63% of sentence coverage, since it has been
	 * covered 10 lines of code of 16 possible.
	 * 
	 * D: Approximately 8% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void save_negative_test() {
		Company company, saved;
		String oldCommercialName;

		super.authenticate("company1");

		this.startTransaction();

		company = this.companyService.findOneToDisplayEdit(super.getEntityId("company1"));

		oldCommercialName = company.getCommercialName();

		company.setCommercialName("");

		saved = this.companyService.save(company);

		Assert.isTrue(!saved.getCommercialName().equals(oldCommercialName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated as an administrator muest be able to:
	 * Display a dashboard with the following information:
	 * The companies that have offered more positions.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void findCompaniesOfferedMorePositions() {
		Collection<Company> data;

		data = this.companyService.findCompaniesOfferedMorePositions();

		Assert.isTrue(data.size() == 3.0);

	}

}

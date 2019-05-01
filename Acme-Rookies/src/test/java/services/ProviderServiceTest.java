
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

import repositories.ProviderRepository;
import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Item;
import domain.Provider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProviderServiceTest extends AbstractTest {

	// Service under testing ---------------------------------------------

	@Autowired
	private ProviderService		providerService;

	// Other supporting services and repositories ------------------------

	@Autowired
	private ProviderRepository	providerRepository;

	@Autowired
	private ItemService			itemService;


	// Tests -------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The providers who have a number of sponsorships that is at least 10%
	 * above the average number of sponsorships per provider.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testFindProvidersWithMoreSponsorships() {
		int numberOfProviders, provider1Id, provider5Id;
		Provider provider1, provider5;
		Collection<Provider> providers;

		provider1Id = super.getEntityId("provider1");
		provider5Id = super.getEntityId("provider5");
		provider1 = this.providerRepository.findOne(provider1Id);
		provider5 = this.providerRepository.findOne(provider5Id);
		numberOfProviders = 2;

		providers = this.providerService.findProvidersWithMoreSponsorships();

		Assert.isTrue(providers.size() == numberOfProviders);
		Assert.isTrue(providers.contains(provider1) && providers.contains(provider5));
	}

	/*
	 * A: Acme Rookies - Requirement 11.1.b (The top 5 providers in terms of total number of items provided).
	 * C: Analysis of sentence coverage: 7/7 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: Intentionally blank.
	 */
	@Test
	public void topFiveProviders_positive() {
		super.authenticate("admin1");

		Collection<Provider> providers;

		providers = this.providerService.topFiveProviders();

		Assert.notNull(providers);
		Assert.isTrue(providers.size() == 5);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is not authenticated must be able to:
	 * Register to the system as provider
	 */
	@Test
	public void registerProviderDriver() {
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
				"ProviderTEST", "ProviderTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "makeTEST", null
			},
			/*
			 * B: Provider::name is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"", "ProviderTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "makeTEST", ConstraintViolationException.class
			},
			/*
			 * B: Provider::surname is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"ProviderTEST", "", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "makeTEST", ConstraintViolationException.class
			},
			/*
			 * B: Provider::email is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"ProviderTEST", "ProviderTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "", "63214578", "calle test", "makeTEST", IllegalArgumentException.class
			},
			/*
			 * B: Provider::creditCard::holder is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"ProviderTEST", "ProviderTEST", 14, "", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "makeTEST", ConstraintViolationException.class
			},
			/*
			 * B: Provider::creditCard::make is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"ProviderTEST", "ProviderTEST", 14, "holderTEST", "", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "makeTEST", ConstraintViolationException.class
			},
			/*
			 * B: Provider::creditCard::number is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"ProviderTEST", "ProviderTEST", 14, "holderTEST", "makeTEST", "", "04", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "makeTEST", ConstraintViolationException.class
			},
			/*
			 * B: Provider::creditCard::expirationMonth is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"ProviderTEST", "ProviderTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "", "22", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "makeTEST", IllegalArgumentException.class
			},
			/*
			 * B: Provider::creditCard::expirationYear is blank
			 * 
			 * C: Approximately 63% of sentence coverage, since it has been
			 * covered 10 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"ProviderTEST", "ProviderTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "makeTEST", IllegalArgumentException.class
			},
			/*
			 * B: Provider::creditCard is expired
			 * 
			 * C: Approximately 31% of sentence coverage, since it has been
			 * covered 5 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"ProviderTEST", "ProviderTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "18", 123, "http://www.instagram.com", "test@us.es", "63214578", "calle test", "makeTEST", IllegalArgumentException.class
			},
			/*
			 * B: Provider::email does not match with the pattern
			 * 
			 * C: Approximately 31% of sentence coverage, since it has been
			 * covered 5 lines of code of 16 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"ProviderTEST", "ProviderTEST", 14, "holderTEST", "makeTEST", "1111222233334444", "04", "22", 123, "http://www.instagram.com", "test@", "63214578", "calle test", "makeTEST", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerProviderTemplate((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (Class<?>) testingData[i][14]);
	}

	protected void registerProviderTemplate(final String name, final String surname, final int VATnumber, final String holder, final String make, final String number, final String month, final String year, final int cvvCode, final String photo,
		final String email, final String phoneNumber, final String address, final String providerMake, final Class<?> expected) {
		Class<?> caught;
		Provider provider, saved;
		UserAccount userAccount;
		Authority auth;
		CreditCard creditCard;

		super.startTransaction();

		caught = null;

		try {
			auth = new Authority();
			auth.setAuthority("PROVIDER");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			provider = this.providerService.create();
			provider.setName(name);
			provider.setSurname(surname);
			provider.setAddress(address);
			provider.setEmail(email);
			provider.setPhoneNumber(phoneNumber);
			provider.setVATnumber(VATnumber);
			provider.setPhoto(photo);
			provider.setMake(providerMake);

			creditCard = new CreditCard();
			creditCard.setHolder(holder);
			creditCard.setMake(make);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(month);
			creditCard.setExpirationYear(year);
			creditCard.setCvvCode(cvvCode);

			provider.setCreditCard(creditCard);

			saved = this.providerService.save(provider);
			this.providerService.flush();

			provider = this.providerService.findOne(saved.getId());
			Assert.isTrue(saved.equals(provider));
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
		Provider provider, saved;
		String oldName;

		super.authenticate("provider1");

		this.startTransaction();

		provider = this.providerService.findOneToDisplayEdit(super.getEntityId("provider1"));

		oldName = provider.getName();

		provider.setName("TEST");

		saved = this.providerService.save(provider);

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
		Provider provider, saved;
		String oldName;

		super.authenticate("provider1");

		this.startTransaction();

		provider = this.providerService.findOneToDisplayEdit(super.getEntityId("provider1"));

		oldName = provider.getName();

		provider.setName("");

		saved = this.providerService.save(provider);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is not authenticated must be able to:
	 * Browse the list of providers and navigate to their items
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been
	 * covered 7 lines of code of 7 possible.
	 * 
	 * D: Approximately 100% of data coverage
	 */
	@Test
	public void listProvidersAndNavigateItems() {
		List<Provider> providers;
		List<Item> items;
		Provider provider;

		providers = new ArrayList<>(this.providerService.findAll());
		Assert.notNull(providers);

		provider = providers.get(0);
		items = new ArrayList<>(this.itemService.f);
		Assert.notNull(positions);
	}

}

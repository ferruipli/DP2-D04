
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.ProviderRepository;
import utilities.AbstractTest;
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

}

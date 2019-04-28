
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProviderServiceTest extends AbstractTest {

	// Service under testing ---------------------------------------------

	//	@Autowired
	//	private ProviderService	providerService;

	// Other supporting services and repositories ------------------------

	//	@Autowired
	//	private ProviderRepository providerRepository;

	// Tests -------------------------------------------------------------

	//	TODO: Test funcional
	//	/*
	//	 * A: An actor who is authenticated as an administrator must be able to
	//	 * display a dashboard with the following information:
	//	 * The providers who have a number of sponsorships that is at least 10%
	//	 * above the average number of sponsorships per provider.
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: 100% of sentence coverage.
	//	 * 
	//	 * D: 100% of data coverage.
	//	 */
	//	@Test
	//	public void testFindProvidersWithMoreSponsorships() {
	//		int numberOfProviders, provider1Id, provider2Id;
	//		Collection<E>rovider1, provider2;
	//		Collection<Provider> providers;
	//	
	//		provider1Id = super.getEntityId("provider1");
	//		provider2Id = super.getEntityId("provider2");
	//		provider1 = this.providerRepository.findOne(provider1Id);
	//		provider2 = this.providerRepository.findOne(provider2Id);
	//		numberOfProviders = 2;
	//		
	//		providers = this.providerService.findProvidersWithMoreSponsorships();
	//	
	//		Assert.isTrue(providers.size() == numberOfProviders);
	//		Assert.isTrue(providers.contains(provider1) && providers.contains(provider2));
	//	}

}


package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// Service under testing ---------------------------------------------
	@Autowired
	private SponsorshipService		sponsorshipService;

	// Other supporting services and repositories ------------------------

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	// Tests -------------------------------------------------------------

	//	TODO: Test funcional
	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test
	public void testPositiveListSponsorship() {
		Collection<Sponsorship> sponsorships;
		int sponsorshipId, numberSponsorships;
		Sponsorship sponsorship1, sponsorship2;

		super.authenticate("provider1");

		sponsorshipId = super.getEntityId("sponsorship1");
		sponsorship1 = this.sponsorshipRepository.findOne(sponsorshipId);
		sponsorshipId = super.getEntityId("sponsorship2");
		sponsorship2 = this.sponsorshipRepository.findOne(sponsorshipId);
		numberSponsorships = 2;

		sponsorships = this.sponsorshipService.findAllByPrincipal();

		super.unauthenticate();

		Assert.isTrue(sponsorships.contains(sponsorship1) && sponsorships.contains(sponsorship2));
		Assert.isTrue(sponsorships.size() == numberSponsorships);
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: The actor principal must be authenticated as Provider
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeListSponsorship() {
		Collection<Sponsorship> sponsorships;
		int sponsorshipId, numberSponsorships;
		Sponsorship sponsorship1, sponsorship2;

		super.authenticate("company1");

		sponsorshipId = super.getEntityId("sponsorship1");
		sponsorship1 = this.sponsorshipRepository.findOne(sponsorshipId);
		sponsorshipId = super.getEntityId("sponsorship2");
		sponsorship2 = this.sponsorshipRepository.findOne(sponsorshipId);
		numberSponsorships = 2;

		sponsorships = this.sponsorshipService.findAllByPrincipal();

		super.unauthenticate();

		Assert.isTrue(sponsorships.contains(sponsorship1) && sponsorships.contains(sponsorship2));
		Assert.isTrue(sponsorships.size() == numberSponsorships);
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes listing, SHOWING,
	 * creating, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test
	public void testPositiveDisplaySponsorship() {
		int sponsorshipId;
		Sponsorship sponsorship, stored;

		super.authenticate("provider1");

		sponsorshipId = super.getEntityId("sponsorship1");
		stored = this.sponsorshipRepository.findOne(sponsorshipId);
		sponsorship = this.sponsorshipService.findOneToEditDisplay(sponsorshipId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(sponsorship));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes listing, SHOWING,
	 * creating, updating and deleting them.
	 * 
	 * B: The sponsorship to display must belong to the provider principal.
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDisplaySponsorship() {
		int sponsorshipId;
		Sponsorship sponsorship, stored;

		super.authenticate("provider2");

		sponsorshipId = super.getEntityId("sponsorship1");
		stored = this.sponsorshipRepository.findOne(sponsorshipId);
		sponsorship = this.sponsorshipService.findOneToEditDisplay(sponsorshipId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(sponsorship));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes listing, showing,
	 * CREATING, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test
	public void testPositiveCreateSponsorship() {
		int positionId;
		Sponsorship sponsorship, saved, stored;

		super.authenticate("provider1");

		positionId = super.getEntityId("position1");
		sponsorship = this.sponsorshipService.create(positionId);
		sponsorship.setBanner("https://bannertest.com");
		sponsorship.setTargetPage("http://www.targetPageTest.es");
		saved = this.sponsorshipService.save(sponsorship);
		stored = this.sponsorshipRepository.findOne(saved.getId());

		super.unauthenticate();

		Assert.isTrue(stored.equals(saved));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes listing, showing,
	 * CREATING, updating and deleting them.
	 * 
	 * B: For every sponsorship, the system must store a valid credit
	 * card.
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testNegativeCreateSponsorship() {
		int positionId;
		Sponsorship sponsorship, saved, stored;
		CreditCard creditCard;

		super.authenticate("provider1");

		positionId = super.getEntityId("position1");
		sponsorship = this.sponsorshipService.create(positionId);
		creditCard = sponsorship.getCreditCard();
		creditCard.setNumber("Invalid number");
		sponsorship.setBanner("https://bannertest.com");
		sponsorship.setTargetPage("http://www.targetPageTest.es");
		sponsorship.setCreditCard(creditCard);
		saved = this.sponsorshipService.save(sponsorship);
		stored = this.sponsorshipRepository.findOne(saved.getId());

		super.unauthenticate();

		Assert.isTrue(stored.equals(saved));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes listing, showing,
	 * creating, UPDATING and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test
	public void testPositiveUpdateSponsorship() {
		int sponsorshipId;
		Sponsorship sponsorship, saved;
		String targetPage, banner;

		targetPage = "http://www.tagetPage.com";
		banner = "https://www.banner.com";

		super.authenticate("provider1");

		sponsorshipId = super.getEntityId("sponsorship1");
		sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);
		sponsorship = this.copy(sponsorship);
		sponsorship.setBanner(banner);
		sponsorship.setTargetPage(targetPage);
		saved = this.sponsorshipService.save(sponsorship);
		this.sponsorshipRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getBanner().equals(banner));
		Assert.isTrue(saved.getTargetPage().equals(targetPage));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes listing, showing,
	 * creating, UPDATING and deleting them.
	 * 
	 * B: The target page must be a valid URL.
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testNegativeUpdateSponsorship() {
		int sponsorshipId;
		Sponsorship sponsorship, saved;
		String targetPage, banner;

		targetPage = "Target page test";
		banner = "https://www.banner.com";

		super.authenticate("provider1");

		sponsorshipId = super.getEntityId("sponsorship1");
		sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);
		sponsorship = this.copy(sponsorship);
		sponsorship.setBanner(banner);
		sponsorship.setTargetPage(targetPage);
		saved = this.sponsorshipService.save(sponsorship);
		this.sponsorshipRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getBanner().equals(banner));
		Assert.isTrue(saved.getTargetPage().equals(targetPage));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes listing, showing,
	 * creating, updating and DELETING them.
	 * 
	 * B: Positive test
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test
	public void testPositiveDeleteSponsorship() {
		int sponsorshipId;
		Sponsorship sponsorship;

		super.authenticate("provider1");

		sponsorshipId = super.getEntityId("sponsorship1");
		sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);
		this.sponsorshipService.delete(sponsorship);

		super.unauthenticate();

		sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.isTrue(sponsorship == null);
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her sponsorships, which includes listing, showing,
	 * creating, updating and DELETING them.
	 * 
	 * B: The sponsorship to delete must belong to the provider principal.
	 * 
	 * C: x% of sentence coverage.
	 * 
	 * D: x% of data coverage.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDeleteSponsorship() {
		int sponsorshipId;
		Sponsorship sponsorship;

		super.authenticate("provider2");

		sponsorshipId = super.getEntityId("sponsorship1");
		sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);
		this.sponsorshipService.delete(sponsorship);

		super.unauthenticate();

		sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.isTrue(sponsorship == null);
	}

	// Ancillary methods -------------------------------------------------

	private Sponsorship copy(final Sponsorship sponsorship) {
		Sponsorship result;

		result = new Sponsorship();

		result.setBanner(sponsorship.getBanner());
		result.setCreditCard(sponsorship.getCreditCard());
		result.setId(sponsorship.getId());
		result.setPosition(sponsorship.getPosition());
		result.setProvider(sponsorship.getProvider());
		result.setTargetPage(sponsorship.getTargetPage());
		result.setVersion(sponsorship.getVersion());

		return result;
	}

	/*
	 * A: Acme Rookies - Requirement 14.1.a (The average, the minimum,
	 * the maximum and the average deviation of the number of
	 * sponsorships per provider).
	 * C: Analysis of sentence coverage: 3/3 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: Intentionally blank.
	 */
	@Test
	public void dataOfSponsorshipPerProvider_positiveTest() {
		super.authenticate("admin1");

		Double[] data;

		data = this.sponsorshipService.dataOfSponsorshipPerProvider();

		Assert.isTrue(data[0] == 1.14286);
		Assert.isTrue(data[1] == 0.0);
		Assert.isTrue(data[2] == 2.0);
		Assert.isTrue(data[3] == 0.63888);

		super.unauthenticate();
	}

}

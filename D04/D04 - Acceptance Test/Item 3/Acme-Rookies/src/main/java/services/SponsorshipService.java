
package services;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository ------------------------------------------------

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private Validator				validator;


	// Constructors ------------------------------------------------------

	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	private Sponsorship create(final Position position) {
		Sponsorship result;
		Provider provider;

		provider = this.providerService.findByPrincipal();
		result = new Sponsorship();

		result.setProvider(provider);
		result.setCreditCard(provider.getCreditCard());
		result.setPosition(position);

		return result;
	}

	public Sponsorship create(final int positionId) {
		Sponsorship result;
		Position position;

		position = this.positionService.findOne(positionId);
		result = this.create(position);

		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getPosition().getIsFinalMode());
		Assert.isTrue(!sponsorship.getPosition().getIsCancelled());
		Assert.isTrue(!this.utilityService.checkIsExpired(sponsorship.getCreditCard()), "Expired credit card");
		this.checkOwner(sponsorship);

		Sponsorship saved;

		saved = this.sponsorshipRepository.save(sponsorship);

		return saved;
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));
		this.checkOwnerStored(sponsorship);

		this.sponsorshipRepository.delete(sponsorship.getId());
	}

	public Sponsorship findOneToEditDisplay(final int sponsorshipId) {
		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);
		this.checkOwner(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public Sponsorship getRandomSponsorship(final int positionId) {
		List<Sponsorship> activeSponsorships;
		Sponsorship result;
		int index, numberSponsorships;
		Random random;

		activeSponsorships = this.findAllByPositionId(positionId);
		random = new Random();
		numberSponsorships = activeSponsorships.size();

		if (numberSponsorships > 0) {
			index = random.nextInt(numberSponsorships);
			result = activeSponsorships.get(index);
			this.messageService.notificationFlatRate(result);
		} else
			result = null;

		return result;
	}

	public Collection<Sponsorship> findAllByPrincipal() {
		Collection<Sponsorship> result;
		Provider provider;

		provider = this.providerService.findByPrincipal();
		result = this.sponsorshipRepository.findAllByProviderId(provider.getId());
		Assert.notNull(result);

		return result;
	}

	public Double[] dataOfSponsorshipPerProvider() {
		Double[] results;

		results = this.sponsorshipRepository.dataOfSponsorshipPerProvider();

		return results;
	}

	public Double[] dataOfSponsorshipPerPosition() {
		Double[] results;

		results = this.sponsorshipRepository.dataOfSponsorshipPerPosition();

		return results;
	}

	// Ancillary methods ----------------------------------

	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {
		Sponsorship result, sponsorshipStored;

		if (sponsorship.getId() == 0)
			result = this.create(sponsorship.getPosition());
		else {
			result = new Sponsorship();
			sponsorshipStored = this.sponsorshipRepository.findOne(sponsorship.getId());

			result.setId(sponsorship.getId());
			result.setProvider(sponsorshipStored.getProvider());
			result.setVersion(sponsorshipStored.getVersion());
			result.setPosition(sponsorshipStored.getPosition());
		}

		result.setBanner(sponsorship.getBanner().trim());
		result.setCreditCard(sponsorship.getCreditCard());
		result.setTargetPage(sponsorship.getTargetPage().trim());

		this.validator.validate(result, binding);

		return result;
	}

	protected void deleteSponsorships(final Position position) {
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipRepository.findAllByPositionId(position.getId());

		this.sponsorshipRepository.deleteInBatch(sponsorships);
	}

	protected void deleteSponsorships(final Provider provider) {
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipRepository.findAllByProviderId(provider.getId());

		this.sponsorshipRepository.deleteInBatch(sponsorships);
	}

	private List<Sponsorship> findAllByPositionId(final int positionId) {
		List<Sponsorship> result;

		result = this.sponsorshipRepository.findAllByPositionId(positionId);
		Assert.notNull(result);

		return result;
	}

	private void checkOwner(final Sponsorship sponsorship) {
		Provider principal;

		principal = this.providerService.findByPrincipal();
		Assert.isTrue(sponsorship.getProvider().equals(principal));
	}

	private void checkOwnerStored(final Sponsorship sponsorship) {
		Sponsorship stored;

		stored = this.sponsorshipRepository.findOne(sponsorship.getId());
		this.checkOwner(stored);
	}
}

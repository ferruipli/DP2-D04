
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import domain.Customisation;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class FinderService {

	// Managed repository ------------------------------------------------

	@Autowired
	private FinderRepository		finderRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private Validator				validator;


	// Constructors ------------------------------------------------------

	public FinderService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	private Finder create() {
		Finder result;

		result = new Finder();
		result.setKeyword("");
		result.setUpdatedMoment(new Date(Integer.MIN_VALUE));
		result.setPositions(Collections.<Position> emptySet());

		return result;
	}

	public void save(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(this.validDeadlines(finder));
		this.checkOwner(finder);

		Finder saved;
		Pageable pageable;
		Customisation customisation;

		customisation = this.customisationService.find();
		pageable = new PageRequest(0, customisation.getMaxNumberResults());

		saved = this.finderRepository.save(finder);
		this.positionService.searchPositionFinder(saved, pageable);
	}

	// Other business methods --------------------------------------------

	public Finder evaluateSearch(final Finder finder) {
		Pageable pageable;
		Customisation customisation;

		customisation = this.customisationService.find();
		pageable = new PageRequest(0, customisation.getMaxNumberResults());

		if (this.isFinderOutdated(finder.getUpdatedMoment(), customisation.getTimeCachedResults()))
			this.positionService.searchPositionFinder(finder, pageable);

		return finder;
	}

	public void clear(final Finder finder) {
		this.checkOwner(finder);

		finder.setKeyword("");
		finder.setDeadline(null);
		finder.setMaximumDeadline(null);
		finder.setMinimumSalary(null);
		finder.setUpdatedMoment(new Date(Integer.MIN_VALUE));
	}

	public Finder findByHackerPrincipal() {
		Finder result;
		Hacker hacker;

		hacker = this.hackerService.findByPrincipal();
		result = this.finderRepository.findByHackerId(hacker.getId());
		Assert.notNull(result);

		return result;
	}

	public Double[] findDataNumberResultsFinder() {
		Double[] result;

		result = this.finderRepository.findDataNumberResultsFinder();
		Assert.notNull(result);

		return result;
	}

	public Double findRatioEmptyVsNonEmpty() {
		Double result;

		result = this.finderRepository.findRatioEmptyVsNonEmpty();

		return result;
	}

	// Ancillary methods -------------------------------------------------

	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		Finder result, finderStored;

		result = new Finder();
		finderStored = this.finderRepository.findOne(finder.getId());

		result.setId(finder.getId());
		result.setKeyword(finder.getKeyword().trim());
		result.setDeadline(finder.getDeadline());
		result.setMaximumDeadline(finder.getMaximumDeadline());
		result.setMinimumSalary(finder.getMinimumSalary());
		result.setHacker(finderStored.getHacker());
		result.setPositions(finderStored.getPositions());
		result.setUpdatedMoment(finderStored.getUpdatedMoment());
		result.setVersion(finderStored.getVersion());

		this.validator.validate(result, binding);
		if (!this.validDeadlines(finder))
			binding.rejectValue("maximumDeadline", null, "Maximum deadline cannot be erlier than Deadline.");

		return result;
	}

	private boolean validDeadlines(final Finder finder) {
		boolean result;

		if (finder.getDeadline() != null && finder.getMaximumDeadline() != null)
			result = finder.getDeadline().before(finder.getMaximumDeadline());
		else
			result = true;

		return result;
	}

	private boolean isFinderOutdated(final Date updatedUpdate, final int timeCache) {
		Long diff, milisTimeCache;

		diff = this.utilityService.current_moment().getTime() - updatedUpdate.getTime();
		milisTimeCache = TimeUnit.HOURS.toMillis(timeCache);

		return diff >= milisTimeCache;
	}

	private void checkOwner(final Finder finder) {
		Hacker principal;

		if (finder.getId() != 0) {
			principal = this.hackerService.findByPrincipal();
			Assert.isTrue(finder.getHacker().equals(principal));
		}
	}

	protected void assignNewFinder(final Hacker hacker) {
		Finder finder;

		finder = this.create();
		finder.setHacker(hacker);
		this.save(finder);
	}

	protected Finder findByHacker(final int hackerId) {
		Finder result;

		result = this.finderRepository.findByHackerId(hackerId);

		return result;
	}

	protected void deleteFinder(final Hacker hacker) {
		Finder finder;

		finder = this.finderRepository.findByHackerId(hacker.getId());
		this.finderRepository.delete(finder);
	}

	protected void deleteFromFinders(final Position position) {
		Collection<Finder> finders;

		finders = this.finderRepository.findAllByPosition(position.getId());

		for (final Finder f : finders)
			f.getPositions().remove(position);
	}
}

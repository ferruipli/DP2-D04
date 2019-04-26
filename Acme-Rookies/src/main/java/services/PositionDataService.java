
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import domain.Curriculum;
import domain.Hacker;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	// Managed repository ------------------------------------------------

	@Autowired
	private PositionDataRepository	positionDataRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculumService		curriculumService;


	// Constructors ------------------------------------------------------

	public PositionDataService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public PositionData create() {
		PositionData result;

		result = new PositionData();

		return result;
	}

	private PositionData saveInternal(final PositionData positionData) {
		this.checkDates(positionData);

		PositionData saved;

		saved = this.positionDataRepository.save(positionData);

		return saved;
	}

	// Editing an existing PositionData
	public PositionData save(final PositionData positionData) {
		Assert.notNull(positionData);
		Assert.isTrue(this.positionDataRepository.exists(positionData.getId()));
		this.checkOwner(positionData.getId());
		this.checkCurriculumIsOriginal(positionData);

		final PositionData saved = this.saveInternal(positionData);

		return saved;
	}

	// Creating new PositionData
	public PositionData save(final PositionData positionData, final int curriculumId) {
		Assert.notNull(positionData);
		Assert.isTrue(!this.positionDataRepository.exists(positionData.getId()));

		final PositionData saved = this.saveInternal(positionData);
		final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);

		this.curriculumService.addPositionData(curriculum, saved);

		return saved;
	}

	public void delete(final PositionData positionData) {
		Assert.notNull(positionData);
		Assert.isTrue(this.positionDataRepository.exists(positionData.getId()));
		this.checkOwner(positionData.getId());
		this.checkCurriculumIsOriginal(positionData);

		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByPositionDataId(positionData.getId());
		curriculum = this.curriculumService.findOneToEdit(curriculumId);
		this.curriculumService.removePositionData(curriculum, positionData);
		this.positionDataRepository.delete(positionData);
	}

	public PositionData findOne(final int positionDataId) {
		PositionData result;

		result = this.positionDataRepository.findOne(positionDataId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public PositionData findOneToEdit(final int positionDataId) {
		PositionData result;

		result = this.findOne(positionDataId);
		this.checkOwner(positionDataId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	protected Collection<PositionData> copy(final Collection<PositionData> positionData) {
		final Set<PositionData> result = new HashSet<>();

		for (final PositionData p : positionData) {
			final PositionData copy = new PositionData();

			copy.setDescription(p.getDescription());
			copy.setEndDate(p.getEndDate());
			copy.setStartDate(p.getStartDate());
			copy.setTitle(p.getTitle());

			result.add(copy);
		}

		return result;
	}

	private void checkOwner(final int positionDataId) {
		Hacker principal, owner;

		principal = this.hackerService.findByPrincipal();
		owner = this.hackerService.findByPositionDataId(positionDataId);

		Assert.isTrue(principal.equals(owner));
	}

	private void checkCurriculumIsOriginal(final PositionData positionData) {
		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByPositionDataId(positionData.getId());
		curriculum = this.curriculumService.findOne(curriculumId);

		Assert.isTrue(curriculum.getIsOriginal());
	}

	private void checkDates(final PositionData positionData) {
		Date startDate, endDate;

		startDate = positionData.getStartDate();
		endDate = positionData.getEndDate();
		Assert.isTrue(startDate == null || endDate == null || startDate.before(endDate), "Incorrect dates");
	}
}

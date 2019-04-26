
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationDataRepository;
import domain.Curriculum;
import domain.EducationData;
import domain.Hacker;

@Service
@Transactional
public class EducationDataService {

	// Managed repository ------------------------------------------------

	@Autowired
	private EducationDataRepository	educationDataRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private HackerService			hackerService;


	// Constructors ------------------------------------------------------

	public EducationDataService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public EducationData create() {
		EducationData result;

		result = new EducationData();

		return result;
	}

	private EducationData saveInternal(final EducationData educationData) {
		this.checkDates(educationData);

		EducationData saved;

		saved = this.educationDataRepository.save(educationData);

		return saved;
	}

	// Editing an existing EducationData
	public EducationData save(final EducationData educationData) {
		Assert.notNull(educationData);
		Assert.isTrue(this.educationDataRepository.exists(educationData.getId()));
		this.checkOwner(educationData.getId());
		this.checkCurriculumIsOriginal(educationData);

		final EducationData saved = this.saveInternal(educationData);

		return saved;
	}

	// Creating new EducationData
	public EducationData save(final EducationData educationData, final int curriculumId) {
		Assert.notNull(educationData);
		Assert.isTrue(!this.educationDataRepository.exists(educationData.getId()));

		final EducationData saved = this.saveInternal(educationData);
		final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);

		this.curriculumService.addEducationData(curriculum, saved);

		return saved;
	}

	public void delete(final EducationData educationData) {
		Assert.notNull(educationData);
		Assert.isTrue(this.educationDataRepository.exists(educationData.getId()));
		this.checkOwner(educationData.getId());
		this.checkCurriculumIsOriginal(educationData);

		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByEducationDataId(educationData.getId());
		curriculum = this.curriculumService.findOneToEdit(curriculumId);
		this.curriculumService.removeEducationData(curriculum, educationData);
		this.educationDataRepository.delete(educationData);
	}

	public EducationData findOne(final int educationDataId) {
		EducationData result;

		result = this.educationDataRepository.findOne(educationDataId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public EducationData findOneToEdit(final int educationDataId) {
		EducationData result;

		result = this.findOne(educationDataId);
		this.checkOwner(educationDataId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	protected Collection<EducationData> copy(final Collection<EducationData> educationDatas) {
		final Set<EducationData> result = new HashSet<>();

		for (final EducationData e : educationDatas) {
			final EducationData copy = new EducationData();

			copy.setDegree(e.getDegree());
			copy.setEndDate(e.getEndDate());
			copy.setInstitution(e.getInstitution());
			copy.setMark(e.getMark());
			copy.setStartDate(e.getStartDate());

			result.add(copy);
		}

		return result;
	}

	private void checkOwner(final int educationDataId) {
		Hacker principal, owner;

		principal = this.hackerService.findByPrincipal();
		owner = this.hackerService.findByEducationDataId(educationDataId);

		Assert.isTrue(principal.equals(owner));
	}

	private void checkCurriculumIsOriginal(final EducationData educationData) {
		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByEducationDataId(educationData.getId());
		curriculum = this.curriculumService.findOne(curriculumId);

		Assert.isTrue(curriculum.getIsOriginal());
	}

	private void checkDates(final EducationData educationData) {
		Date startDate, endDate;

		startDate = educationData.getStartDate();
		endDate = educationData.getEndDate();
		Assert.isTrue(startDate == null || endDate == null || startDate.before(endDate), "Incorrect dates");
	}
}

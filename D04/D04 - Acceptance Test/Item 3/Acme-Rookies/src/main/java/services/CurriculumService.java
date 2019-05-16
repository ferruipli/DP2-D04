
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CurriculumRepository;
import domain.Application;
import domain.Curriculum;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;
import domain.Rookie;

@Service
@Transactional
public class CurriculumService {

	// Managed repository ------------------------------------------------

	@Autowired
	private CurriculumRepository		curriculumRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private PositionDataService			positionDataService;

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private RookieService				rookieService;

	@Autowired
	private Validator					validator;


	// Constructors ------------------------------------------------------

	public CurriculumService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public Curriculum create() {
		Curriculum result;
		Rookie principal;
		PersonalData personalData;

		principal = this.rookieService.findByPrincipal();
		personalData = this.personalDataService.create(principal.getFullname());
		result = new Curriculum();

		result.setRookie(principal);
		result.setIsOriginal(true);
		result.setPersonalData(personalData);
		result.setPositionDatas(new HashSet<PositionData>());
		result.setEducationDatas(new HashSet<EducationData>());
		result.setMiscellaneousDatas(new HashSet<MiscellaneousData>());

		return result;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getIsOriginal());

		Rookie principal;

		if (!this.curriculumRepository.exists(curriculum.getId())) {
			this.personalDataService.checkProfileURL(curriculum.getPersonalData());
			principal = this.rookieService.findByPrincipal();
			this.checkOwner(principal, curriculum);
			this.personalDataService.checkFullname(principal, curriculum.getPersonalData());
		} else
			this.checkOwner(curriculum);

		final Curriculum saved = this.curriculumRepository.save(curriculum);

		return saved;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(this.curriculumRepository.exists(curriculum.getId()));
		Assert.isTrue(curriculum.getIsOriginal());
		this.checkOwner(curriculum);

		this.curriculumRepository.delete(curriculum);
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public List<Curriculum> originalCurriculaByPrincipal() {
		List<Curriculum> results;
		Rookie principal;

		principal = this.rookieService.findByPrincipal();

		results = new ArrayList<>(this.curriculumRepository.originalCurricula(principal.getId()));

		return results;
	}

	public Curriculum findOneToEdit(final int curriculumId) {
		Curriculum result;

		result = this.findOne(curriculumId);
		Assert.isTrue(result.getIsOriginal());
		this.checkOwner(result);

		return result;
	}

	public Collection<Curriculum> findOriginalByRookiePrincipal() {
		Collection<Curriculum> result;
		Rookie principal;

		principal = this.rookieService.findByPrincipal();
		result = this.curriculumRepository.originalCurricula(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Double[] findDataNumberCurriculumPerRookie() {
		Double[] result;

		result = this.curriculumRepository.findDataNumberCurriculumPerRookie();
		Assert.notNull(result);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	public Integer findIdByPersonalDataId(final int personalDataId) {
		Integer result;

		result = this.curriculumRepository.findIdByPersonalDataId(personalDataId);
		Assert.notNull(result);

		return result;
	}

	public Integer findIdByPositionDataId(final int positionDataId) {
		Integer result;

		result = this.curriculumRepository.findIdByPositionDataId(positionDataId);
		Assert.notNull(result);

		return result;
	}

	public Integer findIdByEducationDataId(final int educationDataId) {
		Integer result;

		result = this.curriculumRepository.findIdByEducationDataId(educationDataId);
		Assert.notNull(result);

		return result;
	}

	public Integer findIdByMiscellaneousDataId(final int miscellaneousDataId) {
		Integer result;

		result = this.curriculumRepository.findIdByMiscellaneousDataId(miscellaneousDataId);
		Assert.notNull(result);

		return result;
	}

	public boolean checkIsOwner(final Curriculum curriculum) {
		Rookie rookie;

		rookie = this.rookieService.findByPrincipal();

		return curriculum.getRookie().equals(rookie);
	}

	public Curriculum reconstruct(final Curriculum curriculum, final BindingResult binding) {
		Curriculum result, curriculumStored;
		PersonalData personalDataResult, personalDataForm;

		if (curriculum.getId() == 0) {
			result = this.create();
			personalDataResult = result.getPersonalData();
			personalDataForm = curriculum.getPersonalData();

			personalDataResult.setFullname(personalDataForm.getFullname().trim());
			personalDataResult.setGithubProfile(personalDataForm.getGithubProfile().trim());
			personalDataResult.setLinkedInProfile(personalDataForm.getLinkedInProfile().trim());
			personalDataResult.setPhoneNumber(personalDataForm.getPhoneNumber().trim());
			personalDataResult.setStatement(personalDataForm.getStatement().trim());
			result.setTitle(curriculum.getTitle().trim());
		} else {
			result = new Curriculum();
			curriculumStored = this.curriculumRepository.findOne(curriculum.getId());

			result.setEducationDatas(curriculumStored.getEducationDatas());
			result.setRookie(curriculumStored.getRookie());
			result.setId(curriculumStored.getId());
			result.setIsOriginal(curriculumStored.getIsOriginal());
			result.setMiscellaneousDatas(curriculumStored.getMiscellaneousDatas());
			result.setPersonalData(curriculumStored.getPersonalData());
			result.setPositionDatas(curriculumStored.getPositionDatas());
			result.setTitle(curriculum.getTitle().trim());
			result.setVersion(curriculumStored.getVersion());
		}

		this.validator.validate(result, binding);

		return result;
	}

	protected void addPositionData(final Curriculum curriculum, final PositionData positionData) {
		curriculum.getPositionDatas().add(positionData);
	}

	protected void addEducationData(final Curriculum curriculum, final EducationData educationData) {
		curriculum.getEducationDatas().add(educationData);
	}

	protected void addMiscellaneousData(final Curriculum curriculum, final MiscellaneousData miscellaneousData) {
		curriculum.getMiscellaneousDatas().add(miscellaneousData);
	}

	protected void removePositionData(final Curriculum curriculum, final PositionData positionData) {
		curriculum.getPositionDatas().remove(positionData);
	}

	protected void removeEducationData(final Curriculum curriculum, final EducationData educationData) {
		curriculum.getEducationDatas().remove(educationData);
	}

	protected void removeMiscellaneousData(final Curriculum curriculum, final MiscellaneousData miscellaneousData) {
		curriculum.getMiscellaneousDatas().remove(miscellaneousData);
	}

	protected Curriculum saveCopy(final Curriculum curriculum) {
		Curriculum saved, copy;

		copy = this.copy(curriculum);
		saved = this.curriculumRepository.save(copy);

		return saved;
	}

	protected List<Curriculum> originalCurricula(final int rookieId) {
		List<Curriculum> results;

		results = new ArrayList<>(this.curriculumRepository.originalCurricula(rookieId));

		return results;
	}

	protected void deleteCurriculum(final Application application) {
		Curriculum curriculum;

		curriculum = application.getCurriculum();

		this.curriculumRepository.delete(curriculum);
	}

	protected void deleteCurriculums(final Rookie rookie) {
		Collection<Curriculum> curriculums;

		curriculums = this.curriculumRepository.findAllByRookie(rookie.getId());

		this.curriculumRepository.deleteInBatch(curriculums);
	}

	private Curriculum copy(final Curriculum curriculum) {
		Curriculum result;
		PersonalData personalData;
		Collection<EducationData> educationDatas;
		Collection<MiscellaneousData> miscellaneousDatas;
		Collection<PositionData> positionDatas;

		result = new Curriculum();
		personalData = this.personalDataService.copy(curriculum.getPersonalData());
		educationDatas = this.educationDataService.copy(curriculum.getEducationDatas());
		miscellaneousDatas = this.miscellaneousDataService.copy(curriculum.getMiscellaneousDatas());
		positionDatas = this.positionDataService.copy(curriculum.getPositionDatas());

		result.setRookie(curriculum.getRookie());
		result.setIsOriginal(false);
		result.setTitle(curriculum.getTitle());
		result.setEducationDatas(educationDatas);
		result.setMiscellaneousDatas(miscellaneousDatas);
		result.setPersonalData(personalData);
		result.setPositionDatas(positionDatas);

		return result;
	}

	private void checkOwner(final Curriculum curriculum) {
		Assert.isTrue(this.checkIsOwner(curriculum));
	}

	private void checkOwner(final Rookie rookie, final Curriculum curriculum) {
		Assert.isTrue(rookie.equals(curriculum.getRookie()));
	}
}

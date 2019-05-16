
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import domain.Curriculum;
import domain.Rookie;
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService {

	// Managed repository ------------------------------------------------

	@Autowired
	private PersonalDataRepository	personalDataRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CurriculumService		curriculumService;


	// Constructors ------------------------------------------------------

	public PersonalDataService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	protected PersonalData create(final String fullname) {
		PersonalData result;

		result = new PersonalData();
		result.setFullname(fullname);

		return result;
	}

	public PersonalData save(final PersonalData personalData) {
		Assert.notNull(personalData);
		Assert.isTrue(this.personalDataRepository.exists(personalData.getId()));
		this.checkProfileURL(personalData);
		this.checkCurriculumIsOriginal(personalData);

		final Rookie principal = this.rookieService.findByPrincipal();
		PersonalData saved;

		this.checkOwner(principal, personalData.getId());
		this.checkFullname(principal, personalData);

		saved = this.personalDataRepository.save(personalData);

		return saved;
	}

	private PersonalData findOne(final int personalDataId) {
		PersonalData result;

		result = this.personalDataRepository.findOne(personalDataId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public PersonalData findOneToEdit(final int personalDataId) {
		PersonalData result;

		result = this.findOne(personalDataId);
		this.checkOwner(personalDataId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	protected PersonalData copy(final PersonalData personalData) {
		final PersonalData res = new PersonalData();

		res.setFullname(personalData.getFullname());
		res.setGithubProfile(personalData.getGithubProfile());
		res.setLinkedInProfile(personalData.getLinkedInProfile());
		res.setPhoneNumber(personalData.getPhoneNumber());
		res.setStatement(personalData.getStatement());

		return res;
	}

	protected void checkFullname(final Rookie rookie, final PersonalData personalData) {
		Assert.isTrue(rookie.getFullname().equals(personalData.getFullname()), "Fullname does not match");
	}

	protected void checkProfileURL(final PersonalData personalData) {
		Assert.isTrue(personalData.getGithubProfile().startsWith("https://github.com/"), "Not in github");
		Assert.isTrue(personalData.getLinkedInProfile().startsWith("https://www.linkedin.com/"), "Not in linkedin");
	}

	private void checkCurriculumIsOriginal(final PersonalData personalData) {
		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByPersonalDataId(personalData.getId());
		curriculum = this.curriculumService.findOne(curriculumId);

		Assert.isTrue(curriculum.getIsOriginal());
	}

	private void checkOwner(final int personalDataId) {
		Rookie principal;

		principal = this.rookieService.findByPrincipal();
		this.checkOwner(principal, personalDataId);
	}

	private void checkOwner(final Rookie rookie, final int personalDataId) {
		Rookie owner;

		owner = this.rookieService.findByPersonalDataId(personalDataId);

		Assert.isTrue(rookie.equals(owner));
	}
}

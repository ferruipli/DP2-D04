
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import domain.Curriculum;
import domain.Hacker;
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService {

	// Managed repository ------------------------------------------------

	@Autowired
	private PersonalDataRepository	personalDataRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private HackerService			hackerService;

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

		final Hacker principal = this.hackerService.findByPrincipal();
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

	protected void checkFullname(final Hacker hacker, final PersonalData personalData) {
		Assert.isTrue(hacker.getFullname().equals(personalData.getFullname()), "Fullname does not match");
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
		Hacker principal;

		principal = this.hackerService.findByPrincipal();
		this.checkOwner(principal, personalDataId);
	}

	private void checkOwner(final Hacker hacker, final int personalDataId) {
		Hacker owner;

		owner = this.hackerService.findByPersonalDataId(personalDataId);

		Assert.isTrue(hacker.equals(owner));
	}
}

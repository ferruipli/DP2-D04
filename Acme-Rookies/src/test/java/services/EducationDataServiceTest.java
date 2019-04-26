
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import repositories.EducationDataRepository;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EducationDataServiceTest extends AbstractTest {

	// Service under testing --------------------------------------------------

	@Autowired
	private EducationDataService	educationDataService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private EducationDataRepository	educationDataRepository;

	@Autowired
	private CurriculumRepository	curriculumRepository;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, CREATING, updating,
	 * and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been covered
	 * 23 lines of code of 23 possible.
	 * 
	 * D: Approximately 25% of data coverage, since it has been used 5
	 * values in the data of 20 different possible values.
	 */
	@Test
	public void educationDataCreateTest() {
		EducationData educationData, savedEducationData;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String degree, institution;
		double mark;
		Date startDate, endDate;

		// Data
		degree = "Degree test";
		institution = "University of Seville";
		mark = 8.0;
		startDate = LocalDate.now().minusYears(2).toDate();
		endDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("hacker8");

		curriculumId = super.getEntityId("curriculum81");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getEducationDatas().size();
		educationData = this.educationDataService.create();

		educationData.setDegree(degree);
		educationData.setInstitution(institution);
		educationData.setMark(mark);
		educationData.setStartDate(startDate);
		educationData.setEndDate(endDate);

		savedEducationData = this.educationDataService.save(educationData, curriculumId);

		super.unauthenticate();

		Assert.isTrue(curriculum.getEducationDatas().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getEducationDatas().contains(savedEducationData));
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, CREATING, updating,
	 * and deleting them.
	 * 
	 * B: The education data can only be created in one of the curriculum in
	 * which the hacker principal is owner.
	 * 
	 * C: Approximately 87% of sentence coverage, since it has been covered
	 * 20 lines of code of 23 possible.
	 * 
	 * D: Approximately 25% of data coverage, since it has been used 5
	 * values in the data of 20 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void educationDataCreateNegativeTest() {
		EducationData educationData, savedEducationData;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String degree, institution;
		double mark;
		Date startDate, endDate;

		// Data
		degree = "Degree test";
		institution = "University of Seville";
		mark = 8.0;
		startDate = LocalDate.now().minusYears(2).toDate();
		endDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("hacker9");

		curriculumId = super.getEntityId("curriculum81");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getEducationDatas().size();
		educationData = this.educationDataService.create();

		educationData.setDegree(degree);
		educationData.setInstitution(institution);
		educationData.setMark(mark);
		educationData.setStartDate(startDate);
		educationData.setEndDate(endDate);

		savedEducationData = this.educationDataService.save(educationData, curriculumId);

		super.unauthenticate();

		Assert.isTrue(curriculum.getEducationDatas().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getEducationDatas().contains(savedEducationData));
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, creating, UPDATING,
	 * and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been covered
	 * 33 lines of code of 33 possible.
	 * 
	 * D: Approximately 25% of data coverage, since it has been used 5
	 * values in the data of 20 different possible values.
	 */
	@Test
	public void educationDataEditTest() {
		EducationData educationData, saved;
		int educationDataId;
		String degree;

		// Data
		degree = "Degreee Edit test";

		super.authenticate("hacker8");

		educationDataId = super.getEntityId("educationData81");
		educationData = this.educationDataRepository.findOne(educationDataId);
		educationData = this.cloneEducationData(educationData);

		educationData.setDegree(degree);
		saved = this.educationDataService.save(educationData);

		super.unauthenticate();

		Assert.isTrue(saved.getDegree() == degree);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, creating, UPDATING,
	 * and deleting them.
	 * 
	 * B: The Education Data can only be updated by its owner.
	 * 
	 * C: Approximately 18.2% of sentence coverage, since it has been covered
	 * 6 lines of code of 33 possible.
	 * 
	 * D: Approximately 25% of data coverage, since it has been used 5
	 * values in the data of 20 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void educationDataEditNegativeTest() {
		EducationData educationData, saved;
		int educationDataId;
		String degree;

		// Data
		degree = "Degreee Edit test";

		super.authenticate("hacker9");

		educationDataId = super.getEntityId("educationData81");
		educationData = this.educationDataRepository.findOne(educationDataId);
		educationData = this.cloneEducationData(educationData);

		educationData.setDegree(degree);
		saved = this.educationDataService.save(educationData);

		super.unauthenticate();

		Assert.isTrue(saved.getDegree() == degree);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, creating, updating,
	 * and DELETING them.
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been covered
	 * 40 lines of code of 40 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void educationDataDeleteTest() {
		EducationData educationData, saved;
		int educationDataId;

		super.authenticate("hacker8");

		educationDataId = super.getEntityId("educationData81");
		educationData = this.educationDataRepository.findOne(educationDataId);

		this.educationDataService.delete(educationData);

		super.unauthenticate();

		saved = this.educationDataRepository.findOne(educationDataId);
		Assert.isTrue(saved == null);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, creating, updating,
	 * and DELETING them.
	 * 
	 * B: The Education Data can only be deleted by its owner.
	 * 
	 * C: Approximately 15% of sentence coverage, since it has been covered
	 * 6 lines of code of 40 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void educationDataDeleteNegativeTest() {
		EducationData educationData, saved;
		int educationDataId;

		super.authenticate("hacker9");

		educationDataId = super.getEntityId("educationData81");
		educationData = this.educationDataRepository.findOne(educationDataId);

		this.educationDataService.delete(educationData);

		super.unauthenticate();

		saved = this.educationDataRepository.findOne(educationDataId);
		Assert.isTrue(saved == null);
	}

	// Ancillary methods --------------------------------------------------

	private EducationData cloneEducationData(final EducationData educationData) {
		final EducationData res = new EducationData();

		res.setDegree(educationData.getDegree());
		res.setEndDate(educationData.getEndDate());
		res.setId(educationData.getId());
		res.setInstitution(educationData.getInstitution());
		res.setMark(educationData.getMark());
		res.setStartDate(educationData.getStartDate());
		res.setVersion(educationData.getVersion());

		return res;
	}

}

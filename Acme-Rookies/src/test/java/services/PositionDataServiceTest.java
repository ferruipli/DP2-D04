
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
import repositories.PositionDataRepository;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.PositionData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionDataServiceTest extends AbstractTest {

	// Service under testing --------------------------------------------------

	@Autowired
	private PositionDataService		positionDataService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private PositionDataRepository	positionDataRepository;

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
	 * D: Approximately 33% of data coverage, since it has been used 4
	 * values in the data of 12 different possible values.
	 */
	@Test
	public void positionDataCreateTest() {
		PositionData positionData, savedPositionData;
		Curriculum curriculum;
		int curriculumId, numberPosData;
		String title, description;
		Date startDate, endDate;

		// Data
		title = "Title test";
		description = "Description test";
		startDate = LocalDate.now().minusYears(2).toDate();
		endDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("hacker8");

		curriculumId = super.getEntityId("curriculum81");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberPosData = curriculum.getPositionDatas().size();
		positionData = this.positionDataService.create();

		positionData.setTitle(title);
		positionData.setDescription(description);
		positionData.setStartDate(startDate);
		positionData.setEndDate(endDate);

		savedPositionData = this.positionDataService.save(positionData, curriculumId);

		super.unauthenticate();

		Assert.isTrue(curriculum.getPositionDatas().size() == numberPosData + 1);
		Assert.isTrue(curriculum.getPositionDatas().contains(savedPositionData));
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, CREATING, updating,
	 * and deleting them.
	 * 
	 * B: The position data can only be created in one of the curriculum in
	 * which the hacker principal is owner.
	 * 
	 * C: Approximately 87% of sentence coverage, since it has been covered
	 * 20 lines of code of 23 possible.
	 * 
	 * D: Approximately 33% of data coverage, since it has been used 4
	 * values in the data of 12 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void positionDataCreateNegativeTest() {
		PositionData positionData, savedPositionData;
		Curriculum curriculum;
		int curriculumId, numberPosData;
		String title, description;
		Date startDate, endDate;

		// Data
		title = "Title test";
		description = "Description test";
		startDate = LocalDate.now().minusYears(2).toDate();
		endDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("hacker9");

		curriculumId = super.getEntityId("curriculum81");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberPosData = curriculum.getPositionDatas().size();
		positionData = this.positionDataService.create();

		positionData.setTitle(title);
		positionData.setDescription(description);
		positionData.setStartDate(startDate);
		positionData.setEndDate(endDate);

		savedPositionData = this.positionDataService.save(positionData, curriculumId);

		super.unauthenticate();

		Assert.isTrue(curriculum.getPositionDatas().size() == numberPosData + 1);
		Assert.isTrue(curriculum.getPositionDatas().contains(savedPositionData));
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
	 * D: Approximately 33% of data coverage, since it has been used 4
	 * values in the data of 12 different possible values.
	 */
	@Test
	public void positionDataEditTest() {
		PositionData positionData, saved;
		int positionDataId;
		String title;

		// Data
		title = "Edit test";

		super.authenticate("hacker8");

		positionDataId = super.getEntityId("positionData81");
		positionData = this.positionDataRepository.findOne(positionDataId);
		positionData = this.clonePositionData(positionData);

		positionData.setTitle(title);
		saved = this.positionDataService.save(positionData);

		super.unauthenticate();

		Assert.isTrue(saved.getTitle() == title);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, creating, UPDATING,
	 * and deleting them.
	 * 
	 * B: The Position Data can only be updated by its owner.
	 * 
	 * C: Approximately 18.2% of sentence coverage, since it has been covered
	 * 6 lines of code of 33 possible.
	 * 
	 * D: Approximately 33% of data coverage, since it has been used 4
	 * values in the data of 12 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void positionDataEditNegativeTest() {
		PositionData positionData, saved;
		int positionDataId;
		String title;

		// Data
		title = "Edit test";

		super.authenticate("hacker9");

		positionDataId = super.getEntityId("positionData81");
		positionData = this.positionDataRepository.findOne(positionDataId);
		positionData = this.clonePositionData(positionData);

		positionData.setTitle(title);
		saved = this.positionDataService.save(positionData);

		super.unauthenticate();

		Assert.isTrue(saved.getTitle() == title);
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
	public void positionDataDeleteTest() {
		PositionData positionData, saved;
		int positionDataId;

		super.authenticate("hacker8");

		positionDataId = super.getEntityId("positionData81");
		positionData = this.positionDataRepository.findOne(positionDataId);

		this.positionDataService.delete(positionData);

		super.unauthenticate();

		saved = this.positionDataRepository.findOne(positionDataId);
		Assert.isTrue(saved == null);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, creating, updating,
	 * and DELETING them.
	 * 
	 * B: The Position Data can only be deleted by its owner.
	 * 
	 * C: Approximately 15% of sentence coverage, since it has been covered
	 * 6 lines of code of 40 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void positionDataDeleteNegativeTest() {
		PositionData positionData, saved;
		int positionDataId;

		super.authenticate("hacker9");

		positionDataId = super.getEntityId("positionData81");
		positionData = this.positionDataRepository.findOne(positionDataId);

		this.positionDataService.delete(positionData);

		super.unauthenticate();

		saved = this.positionDataRepository.findOne(positionDataId);
		Assert.isTrue(saved == null);
	}

	// Ancillary methods --------------------------------------------------

	private PositionData clonePositionData(final PositionData positionData) {
		final PositionData res = new PositionData();

		res.setDescription(positionData.getDescription());
		res.setEndDate(positionData.getEndDate());
		res.setId(positionData.getId());
		res.setStartDate(positionData.getStartDate());
		res.setTitle(positionData.getTitle());
		res.setVersion(positionData.getVersion());

		return res;
	}

}


package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import repositories.MiscellaneousDataRepository;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.MiscellaneousData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MiscellaneousDataServiceTest extends AbstractTest {

	// Service under testing --------------------------------------------------

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	@Autowired
	private CurriculumRepository		curriculumRepository;


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
	 * D: Approximately 33% of data coverage, since it has been used 2
	 * values in the data of 6 different possible values.
	 */
	@Test
	public void miscellaneousDataCreateTest() {
		MiscellaneousData miscellaneousData, savedMiscellaneousData;
		Curriculum curriculum;
		int curriculumId, numberMiscData;
		String attachments, text;

		// Data
		attachments = "https://www.attachment1.com\rhttps://www.attachments2.com\rhttps://www.attachment3.com";
		text = "Miscellaneous text test";

		super.authenticate("hacker8");

		curriculumId = super.getEntityId("curriculum81");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberMiscData = curriculum.getMiscellaneousDatas().size();
		miscellaneousData = this.miscellaneousDataService.create();

		miscellaneousData.setAttachments(attachments);
		miscellaneousData.setText(text);

		savedMiscellaneousData = this.miscellaneousDataService.save(miscellaneousData, curriculumId);

		super.unauthenticate();

		Assert.isTrue(curriculum.getMiscellaneousDatas().size() == numberMiscData + 1);
		Assert.isTrue(curriculum.getMiscellaneousDatas().contains(savedMiscellaneousData));
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, CREATING, updating,
	 * and deleting them.
	 * 
	 * B: The miscellaneous data can only be created in one of the curriculum in
	 * which the hacker principal is owner.
	 * 
	 * C: Approximately 87% of sentence coverage, since it has been covered
	 * 20 lines of code of 23 possible.
	 * 
	 * D: Approximately 33% of data coverage, since it has been used 2
	 * values in the data of 6 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void miscellaneousDataCreateNegativeTest() {
		MiscellaneousData miscellaneousData, savedMiscellaneousData;
		Curriculum curriculum;
		int curriculumId, numberMiscData;
		String attachments, text;

		// Data
		attachments = "https://www.attachment1.com\rhttps://www.attachments2.com\rhttps://www.attachment3.com";
		text = "Miscellaneous text test";

		super.authenticate("hacker9");

		curriculumId = super.getEntityId("curriculum81");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberMiscData = curriculum.getMiscellaneousDatas().size();
		miscellaneousData = this.miscellaneousDataService.create();

		miscellaneousData.setAttachments(attachments);
		miscellaneousData.setText(text);

		savedMiscellaneousData = this.miscellaneousDataService.save(miscellaneousData, curriculumId);

		super.unauthenticate();

		Assert.isTrue(curriculum.getMiscellaneousDatas().size() == numberMiscData + 1);
		Assert.isTrue(curriculum.getMiscellaneousDatas().contains(savedMiscellaneousData));
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
	 * D: Approximately 33% of data coverage, since it has been used 2
	 * values in the data of 6 different possible values.
	 */
	@Test
	public void miscellaneousDataEditTest() {
		MiscellaneousData miscellaneousData, saved;
		int miscellaneousDataId;
		String text;

		// Data
		text = "Text Edit test";

		super.authenticate("hacker7");

		miscellaneousDataId = super.getEntityId("miscellaneousData71");
		miscellaneousData = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		miscellaneousData = this.cloneMiscellaneousData(miscellaneousData);

		miscellaneousData.setText(text);
		saved = this.miscellaneousDataService.save(miscellaneousData);

		super.unauthenticate();

		Assert.isTrue(saved.getText() == text);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, creating, UPDATING,
	 * and deleting them.
	 * 
	 * B: The Miscellaneous Data can only be updated by its owner.
	 * 
	 * C: Approximately 18.2% of sentence coverage, since it has been covered
	 * 6 lines of code of 33 possible.
	 * 
	 * D: Approximately 33% of data coverage, since it has been used 2
	 * values in the data of 6 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void miscellaneousDataEditNegativeTest() {
		MiscellaneousData miscellaneousData, saved;
		int miscellaneousDataId;
		String text;

		// Data
		text = "Text Edit test";

		super.authenticate("hacker9");

		miscellaneousDataId = super.getEntityId("miscellaneousData71");
		miscellaneousData = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		miscellaneousData = this.cloneMiscellaneousData(miscellaneousData);

		miscellaneousData.setText(text);
		saved = this.miscellaneousDataService.save(miscellaneousData);

		super.unauthenticate();

		Assert.isTrue(saved.getText() == text);
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
	public void miscellaneousDataDeleteTest() {
		MiscellaneousData miscellaneousData, saved;
		int miscellaneousDataId;

		super.authenticate("hacker7");

		miscellaneousDataId = super.getEntityId("miscellaneousData71");
		miscellaneousData = this.miscellaneousDataRepository.findOne(miscellaneousDataId);

		this.miscellaneousDataService.delete(miscellaneousData);

		super.unauthenticate();

		saved = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		Assert.isTrue(saved == null);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, creating, updating,
	 * and DELETING them.
	 * 
	 * B: The Miscellaneous Data can only be deleted by its owner.
	 * 
	 * C: Approximately 15% of sentence coverage, since it has been covered
	 * 6 lines of code of 40 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void miscellaneousDataDeleteNegativeTest() {
		MiscellaneousData miscellaneousData, saved;
		int miscellaneousDataId;

		super.authenticate("hacker9");

		miscellaneousDataId = super.getEntityId("miscellaneousData71");
		miscellaneousData = this.miscellaneousDataRepository.findOne(miscellaneousDataId);

		this.miscellaneousDataService.delete(miscellaneousData);

		super.unauthenticate();

		saved = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		Assert.isTrue(saved == null);
	}

	// Ancillary methods --------------------------------------------------

	private MiscellaneousData cloneMiscellaneousData(final MiscellaneousData miscellaneousData) {
		final MiscellaneousData res = new MiscellaneousData();

		res.setAttachments(miscellaneousData.getAttachments());
		res.setId(miscellaneousData.getId());
		res.setText(miscellaneousData.getText());
		res.setVersion(miscellaneousData.getVersion());

		return res;
	}

}

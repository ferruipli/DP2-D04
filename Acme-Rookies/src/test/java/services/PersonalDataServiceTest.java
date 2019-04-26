
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import utilities.AbstractTest;
import domain.PersonalData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	// Service under testing --------------------------------------------------

	@Autowired
	private PersonalDataService		personalDataService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private PersonalDataRepository	personalDataRepository;


	// Tests ------------------------------------------------------------------

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
	 * D: Approximately 38.5% of data coverage, since it has been used 5
	 * values in the data of 13 different possible values.
	 */
	@Test
	public void personalDataEditTest() {
		PersonalData personalData, saved;
		int personalDataId;
		String fullname;

		// Data
		fullname = "Hacker8 Moreno";

		super.authenticate("hacker8");

		personalDataId = super.getEntityId("personalData81");
		personalData = this.personalDataRepository.findOne(personalDataId);
		personalData = this.clonePersonalData(personalData);

		personalData.setFullname(fullname);
		saved = this.personalDataService.save(personalData);

		super.unauthenticate();

		Assert.isTrue(saved.getFullname() == fullname);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage his
	 * or her curricula, which includes listing, showing, creating, UPDATING,
	 * and deleting them.
	 * 
	 * B: The curriculum can only be updated by its owner.
	 * 
	 * C: Approximately 90.9% of sentence coverage, since it has been covered
	 * 30 lines of code of 33 possible.
	 * 
	 * D: Approximately 38.5% of data coverage, since it has been used 5
	 * values in the data of 13 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void personalDataEditNegativeTest() {
		PersonalData personalData, saved;
		int personalDataId;
		String fullname;

		// Data
		fullname = "Hacker9 Rubio";

		super.authenticate("hacker9");

		personalDataId = super.getEntityId("personalData81");
		personalData = this.personalDataRepository.findOne(personalDataId);
		personalData = this.clonePersonalData(personalData);

		personalData.setFullname(fullname);
		saved = this.personalDataService.save(personalData);

		super.unauthenticate();

		Assert.isTrue(saved.getFullname() == fullname);
	}

	// Ancillary methods --------------------------------------------------

	private PersonalData clonePersonalData(final PersonalData personalData) {
		final PersonalData res = new PersonalData();

		res.setFullname(personalData.getFullname());
		res.setGithubProfile(personalData.getGithubProfile());
		res.setId(personalData.getId());
		res.setLinkedInProfile(personalData.getLinkedInProfile());
		res.setPhoneNumber(personalData.getPhoneNumber());
		res.setStatement(personalData.getStatement());
		res.setVersion(personalData.getVersion());

		return res;
	}

}

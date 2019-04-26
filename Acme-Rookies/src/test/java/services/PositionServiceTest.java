
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import utilities.AbstractTest;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionServiceTest extends AbstractTest {

	// Service under testing -----------------------------------

	@Autowired
	private PositionService		positionService;

	// Other services ------------------------------------------

	@Autowired
	private PositionRepository	positionRepository;


	// Suite test ---------------------------------------------

	/*
	 * A: An actor who is not authenticated must be able to: Search for a
	 * position using a single key word that must be contained in its title,
	 * its description, its profile, its skills, its technologies, or the
	 * name of the corresponding company.
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been covered
	 * 4 lines of code of 4 possible.
	 * 
	 * D: Approximately 33% of data coverage, since it has been used 1
	 * values in the data of 3 different possible values.
	 */
	@Test
	public void searchByKeywordTest() {
		Collection<Position> results;
		Position position;
		int positionId;

		positionId = super.getEntityId("position6");
		position = this.positionRepository.findOne(positionId);
		results = this.positionService.searchByKeyword("company2");

		Assert.isTrue(results.size() == 2);
		Assert.isTrue(results.contains(position));
	}

	/*
	 * A: An actor who is not authenticated must be able to: Search for a
	 * position using a single key word that must be contained in its title,
	 * its description, its profile, its skills, its technologies, or the
	 * name of the corresponding company.
	 * 
	 * B: The positions are not available publicly until they are saved in `
	 * final mode.
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been covered
	 * 4 lines of code of 4 possible.
	 * 
	 * D: Approximately 33% of data coverage, since it has been used 1
	 * values in the data of 3 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void searchByKeywordNegativeTest() {
		Collection<Position> results;
		Position position;
		int positionId;

		positionId = super.getEntityId("position3");
		position = this.positionRepository.findOne(positionId);
		results = this.positionService.searchByKeyword("company2");

		Assert.isTrue(results.size() == 2);
		Assert.isTrue(results.contains(position));
	}

	/*
	 * A: An actor who is authenticated as an administrator muest be able to:
	 * Display a dashboard with the following information:
	 * The average, the minimum, the maximum, and the standard deviation of the number of positions per company.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testfindDataNumberPositionsPerCompany() {
		Double[] data;

		data = this.positionService.findDataNumberPositionsPerCompany();

		Assert.isTrue(data[0] == 3.0);
		Assert.isTrue(data[1] == 2.0);
		Assert.isTrue(data[2] == 4.0);
		Assert.isTrue(data[3] == 0.8165);

	}

	/*
	 * A: An actor who is authenticated as an administrator muest be able to:
	 * Display a dashboard with the following information:
	 * The average, the minimum, the maximum, and the standard deviation of the
	 * salaries offered.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void findDataSalaryOffered() {
		Double[] data;

		data = this.positionService.findDataSalaryOffered();

		Assert.isTrue(data[0] == 2843.3728571428574);
		Assert.isTrue(data[1] == 1586.23);
		Assert.isTrue(data[2] == 3586.23);
		Assert.isTrue(data[3] == 625.3162465211225);

	}

	/*
	 * A: An actor who is authenticated as an administrator muest be able to:
	 * Display a dashboard with the following information:
	 * The average, the minimum, the maximum, and the standard deviation of the
	 * salaries offered.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void findPositionsBestWorstSalary() {
		List<Position> data;

		data = this.positionService.findPositionsBestWorstSalary();

		Assert.isTrue(data.size() == 2.0);

	}

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			/*
			 * A:Req 9.1 Create a position, all datas
			 * C: 36/38 -> 95% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "titleNuevo", "descriptionNuevo", "2020/02/02", "profileNuevo", "skillsNuevo", "technologiesNuevo", 22.3, null
			},

			/*
			 * A:Req 9.1 Create a position,
			 * B: title in blank
			 * C: 35/38 -> 92% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "", "descriptionNuevo", "2020/02/02", "profileNuevo", "skillsNuevo", "technologiesNuevo", 22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position,
			 * B: description in blank
			 * C: 35/38 -> 92% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "titleNuevo", "", "2020/02/02", "profileNuevo", "skillsNuevo", "technologiesNuevo", 22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position
			 * B: profile in blank
			 * C: 35/38 -> 92% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "titleNuevo", "descriptionNuevo", "2020/02/02", "", "skillsNuevo", "technologiesNuevo", 22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position
			 * B: skills in blank
			 * C: 35/38 -> 92% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "titleNuevo", "descriptionNuevo", "2020/02/02", "profileNuevo", "", "technologiesNuevo", 22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position
			 * B: technologies in blank
			 * C: 35/38 -> 92% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "titleNuevo", "descriptionNuevo", "2020/02/02", "profileNuevo", "skillsNuevo", "", 22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position
			 * B: negative salary
			 * C: 35/38 -> 92% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "titleNuevo", "descriptionNuevo", "2020/02/02", "profileNuevo", "skillsNuevo", "technologiesNuevo", -22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position
			 * B: Past deadline
			 * C: 33/38 -> 86% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "titleNuevo", "descriptionNuevo", "2018/02/02", "profileNuevo", "skillsNuevo", "technologiesNuevo", 22.3, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (Double) testingData[i][7],
				(Class<?>) testingData[i][8]);

	}

	protected void templateCreate(final String username, final String title, final String description, final String deadlineString, final String profile, final String skills, final String technologies, final Double salary, final Class<?> expected) {
		Class<?> caught;
		final Position position;
		final Position positionSaved;
		DateFormat formatter;
		java.util.Date deadline;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			position = this.positionService.create();

			formatter = new SimpleDateFormat("yyyy/MM/dd");
			deadline = formatter.parse(deadlineString);

			position.setTitle(title);
			position.setDescription(description);
			position.setDeadline(deadline);
			position.setProfile(profile);
			position.setSkills(skills);
			position.setTechnologies(technologies);
			position.setSalary(salary);

			positionSaved = this.positionService.save(position);

			this.positionService.flush();

			Assert.notNull(positionSaved);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverEdit() {
		final Object testingData[][] = {
			/*
			 * A:Req 9.1 Edit a position, all datas
			 * C: 32/34-> 94% of executed lines codes
			 * D:
			 */
			{
				"company1", "position3", "titleNuevo", "descriptionNuevo", "2020/02/02", "profileNuevo", "skillsNuevo", "technologiesNuevo", 22.3, null
			},

			/*
			 * A:Req 9.1 Edit a position,
			 * B: title in blank
			 * C: 31/34-> 91% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "position3", "", "descriptionNuevo", "2020/02/02", "profileNuevo", "skillsNuevo", "technologiesNuevo", 22.2, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Edit a position,
			 * B: description in blank
			 * C: 31/34-> 91% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "position3", "titleNuevo", "", "2020/02/02", "profileNuevo", "skillsNuevo", "technologiesNuevo", 22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position
			 * B: profile in blank
			 * C: 31/34-> 91% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "position3", "titleNuevo", "descriptionNuevo", "2020/02/02", "", "skillsNuevo", "technologiesNuevo", 22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position
			 * B: skills in blank
			 * C: 31/34-> 91% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "position3", "titleNuevo", "descriptionNuevo", "2020/02/02", "profileNuevo", "", "technologiesNuevo", 22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position
			 * B: technologies in blank
			 * C: 31/34-> 91% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "position3", "titleNuevo", "descriptionNuevo", "2020/02/02", "profileNuevo", "skillsNuevo", "", 22.3, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Create a position
			 * B: negative salary
			 * C: 31/34-> 91% of executed lines codes
			 * D: 1/128 we test 1 of the 128 possible combinations that can take in total.
			 */
			{
				"company1", "position3", "titleNuevo", "descriptionNuevo", "2020/02/02", "profileNuevo", "skillsNuevo", "technologiesNuevo", -22.3, ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Double) testingData[i][8], (Class<?>) testingData[i][9]);

	}

	protected void templateEdit(final String username, final int positionId, final String title, final String description, final String deadlineString, final String profile, final String skills, final String technologies, final Double salary,
		final Class<?> expected) {
		Class<?> caught;
		final Position position;
		final Position positionSaved;
		DateFormat formatter;
		java.util.Date deadline;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			position = this.positionService.findOneToEditDelete(positionId);

			formatter = new SimpleDateFormat("yyyy/MM/dd");
			deadline = formatter.parse(deadlineString);

			position.setTitle(title);
			position.setDescription(description);
			position.setDeadline(deadline);
			position.setProfile(profile);
			position.setSkills(skills);
			position.setTechnologies(technologies);
			position.setSalary(salary);

			positionSaved = this.positionService.save(position);

			this.positionService.flush();

			Assert.notNull(positionSaved);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * A: Req 9.1 Delete a position
	 * C: 33/33 -> 100% of executed lines codes
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void delete_positive_test() {
		super.authenticate("company1");

		int positionId;
		Position position;

		positionId = super.getEntityId("position3");
		position = this.positionService.findOneToEditDelete(positionId);

		this.positionService.delete(position);

		super.unauthenticate();
	}

	/*
	 * A: Req 9.1 Delete a position
	 * B: Delete a position to another user
	 * C: 17/33 -> 51.5% of executed lines codes
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void delete_negative_test() {
		super.authenticate("company1");

		int positionId;
		Position position;

		positionId = super.getEntityId("position8");
		position = this.positionService.findOneToEditDelete(positionId);

		this.positionService.delete(position);

		super.unauthenticate();
	}

	/*
	 * A: Req 9.1 Display a position
	 * C:4/4 -> 100% of executed lines codes
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void display_positive_test() {
		super.authenticate("company1");

		int positionId;

		positionId = super.getEntityId("position4");
		this.positionService.findOneToDisplay(positionId);

		super.unauthenticate();
	}

	/*
	 * A: Req 9.1 Display a position
	 * B: Display a position in draft mode to another user
	 * C: 3/4 -> 75% of executed lines codes
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void display_negative_test() {
		super.authenticate("company1");

		int positionId;

		positionId = super.getEntityId("position5");
		this.positionService.findOneToDisplay(positionId);

		super.unauthenticate();
	}

}


package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Audit;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	// Service under testing -----------------------------------
	@Autowired
	private AuditService	auditService;

	@Autowired
	private PositionService	positionService;


	// Other services ------------------------------------------

	// Suite test ---------------------------------------------

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			/*
			 * A:Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to create an audits
			 * C:
			 * D:
			 */
			{
				"auditor1", "position1", "text", "5", null
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);

	}

	protected void templateCreate(final String username, final Integer positionId, final String text, final Integer score, final Class<?> expected) {
		Class<?> caught;
		final Audit audit;
		final Audit auditSaved;
		Position position;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			position = this.positionService.findOneToEditDelete(positionId);
			audit = this.auditService.create(position);

			audit.setText(text);
			audit.setScore(score);

			auditSaved = this.auditService.save(audit);

			this.auditService.flush();

			Assert.notNull(auditSaved);

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
			 * A:Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to edit his or her audits
			 * C:
			 * D:
			 */
			{
				"auditor1", "audit1", "text", "5", null
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);

	}

	protected void templateEdit(final String username, final Integer auditId, final String text, final Integer score, final Class<?> expected) {
		Class<?> caught;
		final Audit audit;
		final Audit auditSaved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			audit = this.auditService.findOneToEditDelete(auditId);

			audit.setText(text);
			audit.setScore(score);

			auditSaved = this.auditService.save(audit);

			this.auditService.flush();

			Assert.notNull(auditSaved);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	//	/*
	//	 * A: Req 9.2 Delete a problem
	//	 * C: 21/21 -> 100% of executed lines codes
	//	 * D:intentionally blank.there's nothing to check
	//	 */
	//	@Test
	//	public void delete_positive_test() {
	//		super.authenticate("company1");
	//
	//		int problemId;
	//		Problem problem;
	//
	//		problemId = super.getEntityId("problem4");
	//		problem = this.problemService.findOneToEditDelete(problemId);
	//
	//		this.problemService.delete(problem);
	//
	//		super.unauthenticate();
	//	}
	//
	//	/*
	//	 * A: Req 9.2 Delete a problem
	//	 * B: Delete a problem from another user
	//	 * C: 9/21 -> 42% of executed lines codes
	//	 * D:intentionally blank.there's nothing to check
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void delete_negative_test() {
	//		super.authenticate("company2");
	//
	//		int problemId;
	//		Problem problem;
	//
	//		problemId = super.getEntityId("problem4");
	//		problem = this.problemService.findOneToEditDelete(problemId);
	//
	//		this.problemService.delete(problem);
	//
	//		super.unauthenticate();
	//	}
	//
	//	/*
	//	 * A: Req 9.2 Delete a problem
	//	 * B: Delete a problem in final mode
	//	 * C: 10/21 -> 47% of executed lines codes
	//	 * D:intentionally blank.there's nothing to check
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void delete_negative1_test() {
	//		super.authenticate("company1");
	//
	//		int problemId;
	//		Problem problem;
	//
	//		problemId = super.getEntityId("problem1");
	//		problem = this.problemService.findOneToEditDelete(problemId);
	//
	//		this.problemService.delete(problem);
	//
	//		super.unauthenticate();
	//	}
	//
	//	/*
	//	 * A: Req 9.2 Display a problem
	//	 * C: 10/10 -> 100% of executed lines codes
	//	 * D:intentionally blank.there's nothing to check
	//	 */
	//	@Test
	//	public void display_positive_test() {
	//		super.authenticate("company1");
	//
	//		int problemId;
	//
	//		problemId = super.getEntityId("problem1");
	//		this.problemService.findOneToPrincipal(problemId);
	//
	//		super.unauthenticate();
	//	}
	//
	//	/*
	//	 * A: Req 9.2 Display a problem
	//	 * B: Display a from from another user
	//	 * C: 8/10 -> 80% of executed lines codes
	//	 * D:intentionally blank.there's nothing to check
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void display_negative_test() {
	//		super.authenticate("company2");
	//
	//		int problemId;
	//
	//		problemId = super.getEntityId("problem1");
	//		this.problemService.findOneToPrincipal(problemId);
	//
	//		super.unauthenticate();
	//	}
}

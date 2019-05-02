
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Audit;
import domain.Auditor;
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

	@Autowired
	private AuditorService	auditorService;


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
				"auditor1", "position2", "text", "5", null
			},

			/*
			 * A:Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to create an audits
			 * B: Negative Score
			 * C:
			 * D:
			 */
			{
				"auditor1", "position2", "text", "-5", ConstraintViolationException.class
			},
			/*
			 * A:Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to create an audits
			 * B: Blank text
			 * C:
			 * D:
			 */
			{
				"auditor1", "position2", "", "3", ConstraintViolationException.class
			},
			/*
			 * A:Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to create an audits
			 * B: A una position que ya tiene auditoría del principal
			 * C:
			 * D:
			 */
			{
				"auditor1", "position1", "text", "3", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

	}

	protected void templateCreate(final String username, final Integer positionId, final String text, final String score, final Class<?> expected) {
		Class<?> caught;
		final Audit audit;
		final Audit auditSaved;
		Position position;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			position = this.positionService.findOne(positionId);
			audit = this.auditService.create(position);

			audit.setText(text);
			audit.setScore(Integer.parseInt(score));

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
				"auditor3", "audit3", "text", "5", null
			},
			/*
			 * A:Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to edit her/his audits
			 * B: Negative Score
			 * C:
			 * D:
			 */
			{
				"auditor3", "audit3", "text", "-5", ConstraintViolationException.class
			},
			/*
			 * A:Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to edit her/his audits
			 * B: Blank text
			 * C:
			 * D:
			 */
			{
				"auditor3", "audit3", "", "3", ConstraintViolationException.class
			},
			/*
			 * A:Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to edit her/his audits
			 * B: Edit an audit to another auditor
			 * C:
			 * D:
			 */
			{
				"auditor1", "audit3", "text", "3", IllegalArgumentException.class
			},
			/*
			 * A:Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to edit her/his audits
			 * B: Edit an audit in final mode
			 * C:
			 * D:
			 */
			{
				"auditor1", "audit1", "text", "3", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

	}

	protected void templateEdit(final String username, final Integer auditId, final String text, final String score, final Class<?> expected) {
		Class<?> caught;
		final Audit audit;
		final Audit auditSaved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			audit = this.auditService.findOneToEditDelete(auditId);

			audit.setText(text);
			audit.setScore(Integer.parseInt(score));

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

	/*
	 * A: Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to delete her/his audits in draft mode
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void display_positiveAuthenticate_test() {
		super.authenticate("auditor3");

		int auditId;

		auditId = super.getEntityId("audit3");
		this.auditService.findOne(auditId);

		super.unauthenticate();
	}

	/*
	 * A: Req Acme-Rookies
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void display_positiveUnauthenticate_test() {

		int auditId;

		auditId = super.getEntityId("audit1");
		this.auditService.findOneToDisplay(auditId);

	}

	/*
	 * A:
	 * B: Autenticado puede ver uno en modo no final de otro
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void display_negativeAuthenticate_test() {
		super.authenticate("auditor2");

		int auditId;

		auditId = super.getEntityId("audit3");
		this.auditService.findOneToDisplay(auditId);
		super.unauthenticate();
	}

	/*
	 * A:
	 * B: sin Autenticado puede ver uno en modo no final
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void display_negativeUnauthenticate_test() {

		int auditId;

		auditId = super.getEntityId("audit3");
		this.auditService.findOneToDisplay(auditId);

	}

	/*
	 * A: Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to display her/his audits
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void delete_positive_test() {
		super.authenticate("auditor3");

		int auditId;
		Audit audit;

		auditId = super.getEntityId("audit3");
		audit = this.auditService.findOneToEditDelete(auditId);

		this.auditService.delete(audit);

		super.unauthenticate();
	}

	/*
	 * A: Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to delete her/his audits
	 * B: Delete an audit to another auditor
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void delete_negative_test() {
		super.authenticate("auditor2");

		int auditId;
		Audit audit;

		auditId = super.getEntityId("audit3");
		audit = this.auditService.findOneToEditDelete(auditId);

		this.auditService.delete(audit);

		super.unauthenticate();
	}

	/*
	 * A: Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to delete her/his audits
	 * B: Delete an audit in final mode
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void delete_negative1_test() {
		super.authenticate("auditor2");

		int auditId;
		Audit audit;

		auditId = super.getEntityId("audit2");
		audit = this.auditService.findOneToEditDelete(auditId);

		this.auditService.delete(audit);

		super.unauthenticate();
	}

	/*
	 * A: Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to make final her/his audits
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void makeFinal_positive_test() {
		super.authenticate("auditor3");

		int auditId;
		Audit audit;

		auditId = super.getEntityId("audit3");
		audit = this.auditService.findOne(auditId);

		this.auditService.makeFinal(audit);

		super.unauthenticate();
	}

	/*
	 * A: Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to make final her/his audits
	 * B: Make final an audit to another auditor
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void makeFinal_negative_test() {
		super.authenticate("auditor2");

		int auditId;
		Audit audit;

		auditId = super.getEntityId("audit3");
		audit = this.auditService.findOne(auditId);

		this.auditService.makeFinal(audit);

		super.unauthenticate();
	}

	/*
	 * A: Req 3.2 Acme-Rookies An actor who is authenticated as an auditor must be able to list her/his audits
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void list_positive_test() {
		Collection<Audit> audits;
		int auditId;
		int sizeAudits;
		Audit audit1;
		Auditor auditor;

		super.authenticate("auditor1");

		auditor = this.auditorService.findByPrincipal();
		auditId = super.getEntityId("audit1");
		audit1 = this.auditService.findOne(auditId);
		sizeAudits = 1;

		audits = this.auditService.findAuditsByAuditor(auditor);

		super.unauthenticate();

		Assert.isTrue(audits.contains(audit1));
		Assert.isTrue(audits.size() == sizeAudits);
	}

	/*
	 * A:
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void findDataNumberAuditScore_positive_est() {
		super.authenticate("admin1");

		Double[] data;

		data = this.auditService.findDataNumberAuditScore();

		Assert.isTrue(data[0] == 0.45);
		Assert.isTrue(data[1] == 0.4);
		Assert.isTrue(data[2] == 0.55);
		Assert.isTrue(data[3] == 0.07071067811865475);

		super.unauthenticate();
	}

	/*
	 * A:
	 * C:
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void findAvgSalaryByHighestPosition_positive_est() {
		super.authenticate("admin1");

		this.auditService.findAvgSalaryByHighestPosition();

		super.unauthenticate();
	}


	//TODO: cobertura

	/*
	 * A: Acme Rookies - Requirement 4.4.1 (The average, the minimum, the maximum, and the standard deviation of the audit score of the companies that are registered in the system.).
	 * C: Analysis of sentence coverage: 3/3 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: Intentionally blank.
	 */
	@Test
	public void findDataNumberAuditScoreOfCompany_positiveTest() {
		super.authenticate("admin1");

		Double[] data;

		data = this.auditService.findDataNumberAuditScoreOfCompany();

		Assert.isTrue(data[0] == 0.47500000000000003);
		Assert.isTrue(data[1] == 0.4);
		Assert.isTrue(data[2] == 0.55);
		Assert.isTrue(data[3] == 0.07500000000000001);

		super.unauthenticate();
	}

}

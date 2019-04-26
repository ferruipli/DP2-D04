
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// The SUT ---------------------------
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private CurriculumService	curriculumService;


	//Test ------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The average, the minimum, the maximum and the standard deviation of the
	 * number of application per hacker.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testDataNumerApplicationPerHacker() {
		Double[] data;

		data = this.applicationService.findDataNumberApplicationPerHacker();

		Assert.isTrue(data[0] == 1.5556);
		Assert.isTrue(data[1] == 0.0);
		Assert.isTrue(data[2] == 3.0);
		Assert.isTrue(data[3] == 1.0657);
	}

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test positivo
			 * C: 100%. 77/77 Recorre 77 de las 77 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker1", "position6", "curriculum11", null
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: El curriculum no es del hacker
			 * C: 32%. 25/77 Recorre 25 de las 77 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker1", "position6", "curriculum21", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: El curriculum no isOriginal
			 * C: 38.9%. 30/77 Recorre 30 de las 77 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker1", "position6", "curriculum12", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: La position es no final
			 * C: 11.6%. 9/77 Recorre 9 de las 77 líneas totales
			 * D:Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker1", "position5", "curriculum11", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: La position isCancelled
			 * C: 12.9%. 10/77 Recorre 10 de las 77 líneas totales
			 * D:Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker1", "position4", "curriculum11", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: Ya tiene una application creada en esa position
			 * C: 27.2%. 21/77 Recorre 21 de las 77 líneas totales
			 * D:Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker1", "position1", "curriculum11", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: Un company no puede crear un application
			 * C: 29.8%. 23/77 Recorre 19 de las 65 líneas totales
			 * D:Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"company1", "position1", "curriculum11", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);

	}

	protected void templateCreate(final String username, final int positionId, final int curriculumId, final Class<?> expected) {
		Class<?> caught;
		Application application, applicationSaved;
		Position position;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);
			position = this.positionService.findOne(positionId);

			application = this.applicationService.create(position);
			application.setCurriculum(this.curriculumService.findOne(curriculumId));
			applicationSaved = this.applicationService.save(application);
			this.applicationService.flush();

			Assert.notNull(applicationSaved);
			Assert.isTrue(applicationSaved.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * A: Req.9.3 Cambiar estado de application a "ACCEPTED"
	 * C:96.7% 28/28 Recorre 28 de las 28 líneas totales
	 * D: Intencionadamente en blanco. No se comprueban datos
	 */
	@Test
	public void acceptedApplication_test() {
		super.authenticate("company2");

		int applicationId;
		Application application;

		applicationId = super.getEntityId("application7");
		application = this.applicationService.findOneToCompany(applicationId);

		this.applicationService.acceptedApplication(application);

		super.unauthenticate();
	}

	/*
	 * A: Req.9.3 Cambiar estado de application a "REJECTED"
	 * C:96.7% 28/28 Recorre 28 de las 28 líneas totales
	 * D: Intencionadamente en blanco. No se comprueban datos
	 */
	@Test
	public void rejectedApplication_test() {
		super.authenticate("company2");

		int applicationId;
		Application application;

		applicationId = super.getEntityId("application7");
		application = this.applicationService.findOneToCompany(applicationId);

		this.applicationService.rejectedApplication(application);

		super.unauthenticate();
	}

	/*
	 * A: Req.9.3 Cambiar estado de application de otro company a "ACCEPTED"
	 * C:96.7% 18/28 Recorre 18 de las 28 líneas totales
	 * D: Intencionadamente en blanco. No se comprueban datos
	 */
	@Test(expected = IllegalArgumentException.class)
	public void acceptedApplication_negative_test() {
		super.authenticate("company1");

		int applicationId;
		Application application;

		applicationId = super.getEntityId("application7");
		application = this.applicationService.findOneToCompany(applicationId);

		this.applicationService.acceptedApplication(application);

		super.unauthenticate();
	}

}

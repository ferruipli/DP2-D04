
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Answer;
import domain.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnswerServiceTest extends AbstractTest {

	// The SUT ---------------------------
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private AnswerService		answerService;


	//Test ------------------------------------------------

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			/*
			 * A: Req.10.1 Create answer
			 * B: Test positivo
			 * C: 100%. 28/28 Recorre 28 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker4", "application13", "Text", "https:linkCode", null
			},
			/*
			 * A: Req.10.1 Create answer
			 * B: Su estado no es Pending, por lo que ya tiene una answer dicha application
			 * C: 71%. 20/28 Recorre 20 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker4", "application12", "Text", "https:linkCode", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Create answer
			 * B: El hacker que quiere crear la respuesta no es el de la solicitud
			 * C: 67%. 19/28 Recorre 19 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker1", "application13", "Text", "https:linkCode", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Create answer
			 * B: Text no puede está vacío
			 * C: 75%. 21/28 Recorre 21 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker4", "application13", "", "https:linkCode", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Create answer
			 * B: LinkCode debe ser una url
			 * C: 67%. 19/28 Recorre 19 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"hacker4", "application13", "Texto", "linkcode", ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);

	}

	protected void templateCreate(final String username, final int applicationId, final String text, final String codeLink, final Class<?> expected) {
		Class<?> caught;
		Answer answer, answerSaved;
		Application application;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);
			application = this.applicationService.findOne(applicationId);

			answer = this.answerService.create(applicationId);
			answer.setText(text);
			answer.setCodeLink(codeLink);
			answerSaved = this.answerService.save(answer, application);
			this.applicationService.flush();

			Assert.notNull(answerSaved);
			Assert.isTrue(answerSaved.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

}

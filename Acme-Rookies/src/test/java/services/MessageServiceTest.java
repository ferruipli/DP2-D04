
package services;

import java.util.ArrayList;
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

import utilities.AbstractTest;
import domain.Actor;
import domain.Application;
import domain.Message;
import domain.Position;
import domain.SystemTag;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Service under testing -----------------------------------
	@Autowired
	private MessageService		messageService;

	// Other services ------------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private SystemTagService	systemTagService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ApplicationService	applicationService;


	// Suite test ---------------------------------------------

	/*
	 * A: Requirement 23.2 (An authenticated user can display his or her messages).
	 * B: This user try to display a "deleted" message.
	 * C: Analysis of sentence coverage: 33/34 -> 97.05% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void findOneToDisplay_negativeTest_uno() {
		super.authenticate("hacker2");

		int messageId;
		Message message;

		messageId = super.getEntityId("message1");
		message = this.messageService.findOneToDisplay(messageId);

		Assert.isNull(message);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.2 (An authenticated user can display his or her messages).
	 * B: An user try to display a message that he or she hasn't sent or hasn't received.
	 * C: Analysis of sentence coverage: 18/34 -> 52.94% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void findOneToDisplay_negativeTest_dos() {
		super.authenticate("company1");

		int messageId;
		Message message;

		messageId = super.getEntityId("message1");
		message = this.messageService.findOneToDisplay(messageId);

		Assert.isNull(message);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.2 (An authenticated user can display his or her messages).
	 * C: Analysis of sentence coverage: 34/34 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void findOneToDisplay_positiveTest() {
		super.authenticate("hacker1");

		int messageId;
		Message message;

		messageId = super.getEntityId("message1");
		message = this.messageService.findOneToDisplay(messageId);

		Assert.notNull(message);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.2 (An authenticated user can list his or her messages).
	 * C: Analysis of sentence coverage: 6/6 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void findMessageByActor_positiveTest() {
		super.authenticate("hacker1");

		int actorId;
		Collection<Message> sentMessages, receivedMessages;

		actorId = super.getEntityId("hacker1");

		sentMessages = this.messageService.findSentMessagesOrderByTags(actorId);
		receivedMessages = this.messageService.findReceivedMessagesOrderByTags(actorId);

		Assert.notEmpty(sentMessages);
		Assert.notEmpty(receivedMessages);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.2 (An authenticated user can send a message).
	 * C: Analysis of sentence coverage: 22/22 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void create_positiveTest() {
		super.authenticate("hacker1");

		Message message;

		message = this.messageService.create();

		Assert.notNull(message);
		Assert.notNull(message.getSender());
		Assert.notNull(message.getRecipients());
		Assert.notNull(message.getIsSpam());
		Assert.notNull(message.getSentMoment());
		Assert.isNull(message.getBody());
		Assert.isNull(message.getSubject());
		Assert.isNull(message.getTags());

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.2 (An authenticated user can send a message).
	 * B: An user try to edit a message.
	 * C: Analysis of sentence coverage: 16/53 -> 30.18% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void send_negativeTest() {
		super.authenticate("hacker1");

		final int messageId = super.getEntityId("message1");
		Message message = null, sent;

		message = this.messageService.findOne(messageId);
		message.setBody("Body edited");
		message.setSubject("Subject edited");

		sent = this.messageService.send(message);

		Assert.isNull(sent);

		super.unauthenticate();
	}

	@Test
	public void driverSend() {
		final Object testingData[][] = {
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * B: Invalid data in Message::subject.
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Message::subject is null => 1/12 -> 8.33%.
			 */
			{
				null, "¿Que tal?", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * B: Invalid data in Message::subject.
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Message::subject is a empty string => 1/12 -> 8.33%.
			 */
			{
				"", "¿Que tal?", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * B: Invalid data in Message::subject.
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Message::subject is a malicious script => 1/12 -> 8.33%.
			 */
			{
				"<script> Alert('HACKED'); </script>", "¿Que tal?", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * B: Invalid data in Message::body.
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Message::body is null => 1/12 -> 8.33%.
			 */
			{
				"Saludos", null, "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * B: Invalid data in Message::body.
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Message::body is a empty string => 1/12 -> 8.33%.
			 */
			{
				"Saludos", "", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * B: Invalid data in Message::body.
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Message::body is a malicious script => 1/12 -> 8.33%.
			 */
			{
				"saludos", "<script> Alert('HACKED'); </script>", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * B: Invalid data in Message::subject.
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Message::tags is a malicious script => 1/12 -> 8.33%.
			 */
			{
				"saludos", "¿Que tal?", "<script> Alert('HACKED'); </script>", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Every attribute has a valid value => 12/12 -> 100.00%.
			 */
			{
				"Saludos", "¿Que tal?", "", null
			},
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Every attribute has a valid value => 12/12 -> 100.00%.
			 */
			{
				"Saludos", "viagra, Sex, etc", "", null
			},
			/*
			 * A: Requirement 23.2 (An authenticated user can send a message).
			 * C: Analysis of sentence coverage: 52/53 -> 30.18% of executed lines codes .
			 * D: Analysis of data coverage: Every attribute has a valid value => 12/12 -> 100.00%.
			 */
			{
				"Saludos", "¿Que tal?", "PRIMERIZO", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSend((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void templateSend(final String subject, final String body, final String tags, final Class<?> expected) {
		Class<?> caught;
		Message message, saved;
		List<Actor> recipients;
		Actor actor_one, actor_two, actor_three;
		int actor_uno, actor_dos, actor_tres;
		actor_uno = super.getEntityId("company1");
		actor_dos = super.getEntityId("company2");
		actor_tres = super.getEntityId("company3");

		actor_one = this.actorService.findOne(actor_uno);
		actor_two = this.actorService.findOne(actor_dos);
		actor_three = this.actorService.findOne(actor_tres);

		recipients = new ArrayList<Actor>();
		recipients.add(actor_one);
		recipients.add(actor_two);
		recipients.add(actor_three);

		this.startTransaction();

		caught = null;
		try {
			super.authenticate("hacker1");

			message = this.messageService.create();
			message.setSubject(subject);
			message.setBody(body);
			message.setTags(tags);
			message.setRecipients(recipients);

			saved = this.messageService.send(message);
			this.messageService.flush();

			Assert.notNull(saved);
			Assert.isTrue(saved.getId() != 0);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * A: Requirement 23.2 (An authenticated user can delete a message).
	 * B: An user try to delete a message that doesn't belong him/her.
	 * C: Analysis of sentence coverage: 15/68 -> 20.25% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void delete_negativeTest() {
		super.authenticate("admin1");

		Message message, found;
		int messageId;

		messageId = this.getEntityId("message1");
		message = this.messageService.findOne(messageId);

		this.messageService.delete(message);

		found = this.messageService.findOne(messageId);

		Assert.notNull(found);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.2 (An authenticated user can delete a message).
	 * C: Analysis of sentence coverage: 56/68 -> 82.35% of executed lines codes.
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void delete_positiveTest_uno() {
		super.authenticate("admin1");

		Message message;
		int messageId, actorId;
		SystemTag deleted;

		actorId = super.getEntityId("administrator1");
		messageId = super.getEntityId("message3");
		message = this.messageService.findOne(messageId);

		deleted = this.systemTagService.findMessageTaggedAsDELETED(actorId, messageId);
		Assert.isNull(deleted);

		this.messageService.delete(message);

		deleted = this.systemTagService.findMessageTaggedAsDELETED(actorId, messageId);
		Assert.notNull(deleted);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.2 (An authenticated user can delete a message).
	 * C: Analysis of sentence coverage: 43/68 -> 63.23% of executed lines codes.
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void delete_positiveTest_dos() {
		super.authenticate("hacker2");

		Message message;
		int messageId, actorId;
		SystemTag deleted, hardDeleted;

		actorId = super.getEntityId("hacker2");
		messageId = super.getEntityId("message3");
		message = this.messageService.findOne(messageId);

		hardDeleted = this.systemTagService.findMessageTaggedAsHARDDELETED(actorId, messageId);
		deleted = this.systemTagService.findMessageTaggedAsDELETED(actorId, messageId);

		Assert.notNull(deleted);
		Assert.isNull(hardDeleted);

		this.messageService.delete(message);

		hardDeleted = this.systemTagService.findMessageTaggedAsHARDDELETED(actorId, messageId);
		deleted = this.systemTagService.findMessageTaggedAsDELETED(actorId, messageId);

		Assert.isNull(deleted);
		Assert.notNull(hardDeleted);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 24.1 (Broadcast a notification message to the actors of the system).
	 * B: The message has not tag "SYSTEM".
	 * C: Analysis of sentence coverage: 1/60 -> 1.66% of executed lines codes .
	 * D: Analysis of data coverage: Every attribute has a valid value => 12/12 -> 100.00%.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void sendBroadcast_negativeTest() {
		super.authenticate("admin1");

		Message message, broadcast;

		message = this.messageService.createBroadcast();
		message.setSubject("Mensaje de difusion");
		message.setBody("Esto es un mensaje de difusion");
		message.setTags("HELLO WORLD");

		broadcast = this.messageService.sendBroadcast(message);

		Assert.isNull(broadcast);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 24.1 (Broadcast a notification message to the actors of the system).
	 * C: Analysis of sentence coverage: 1/60 -> 1.66% of executed lines codes .
	 * D: Analysis of data coverage: Every attribute has a valid value => 12/12 -> 100.00%.
	 */
	@Test
	public void sendBroadcast_positiveTest() {
		super.authenticate("admin1");

		Message message, broadcast;

		message = this.messageService.createBroadcast();
		message.setSubject("Mensaje de difusion");
		message.setBody("Esto es un mensaje de difusion");
		message.setTags("SYSTEM");

		broadcast = this.messageService.sendBroadcast(message);
		this.messageService.flush();

		Assert.notNull(broadcast);
		Assert.isTrue(broadcast.getId() != 0);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 27 (An application changes its status).
	 * C: Analysis of sentence coverage: 35/35 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: Intentionally blank.
	 */
	@Test
	public void notificationApplication_positiveTest() {
		int applicationId, actorId;
		Application application;
		Message notification;
		Collection<Message> receivedMessages;

		applicationId = super.getEntityId("application1");
		application = this.applicationService.findOne(applicationId);

		notification = this.messageService.notification_applicationStatusChanges(application);

		actorId = super.getEntityId("hacker1");
		receivedMessages = this.messageService.findReceivedMessagesOrderByTags(actorId);

		Assert.isTrue(receivedMessages.contains(notification));
	}

	/*
	 * A: Requirement 27 (A new offer that matches a hacker's finder search criteria is published).
	 * B: The position is not published
	 * C: Analysis of sentence coverage: 2/52 -> 3.85% of executed lines codes .
	 * D: Analysis of data coverage: Intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void notification_newOffer_negativeTest() {
		int positionId;
		Position position;
		Message notification;

		positionId = super.getEntityId("position3");
		position = this.positionService.findOne(positionId);

		notification = this.messageService.notification_newOfferPublished(position);

		Assert.isNull(notification);
	}

	/*
	 * A: Requirement 27 (A new offer that matches a hacker's finder search criteria is published).
	 * C: Analysis of sentence coverage: 49/52 -> 94.23% of executed lines codes .
	 * D: Analysis of data coverage: Intentionally blank.
	 */
	@Test
	public void notification_newOffer_positiveTest() {
		int positionId;
		Position position;
		Message notification;

		positionId = super.getEntityId("position1");
		position = this.positionService.findOne(positionId);

		notification = this.messageService.notification_newOfferPublished(position);

		Assert.notNull(notification);
		Assert.notEmpty(notification.getRecipients());
	}

}

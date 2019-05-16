
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private ActorService	actorService;


	// Tests ----------------------------------------------

	/*
	 * A: Requirement 24.2.
	 * C: Analysis of sentence coverage: 23/23 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void launchProcess_positiveTest() {
		super.authenticate("admin1");

		final Collection<Actor> all = this.actorService.findAll();

		this.actorService.spammerProcess();

		for (final Actor a : all)
			Assert.notNull(a.getIsSpammer());

		super.unauthenticate();
	}

}

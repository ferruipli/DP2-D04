
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customisation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomisationServiceTest extends AbstractTest {

	// Service under test ----------------------------------------
	@Autowired
	private CustomisationService	customisationService;


	// Supporting services --------------------------------------

	// Suite Tests ----------------------------------------------

	/*
	 * A: Requirement 14 (The system must be easy to customise at run time).
	 * B: Customisation is null.
	 * C: Analysis of sentence coverage: 1/5 -> 20.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_saveTest_uno() {
		super.authenticate("admin1");

		final Customisation customisation = null;
		Customisation saved;

		saved = this.customisationService.save(customisation);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 14 (The system must be easy to customise at run time).
	 * B: An object of Customisation is inserted in DB.
	 * C: Analysis of sentence coverage: 2/5 -> 40.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_saveTest_dos() {
		super.authenticate("admin1");

		Customisation customisation, saved;

		customisation = new Customisation();
		customisation.setName("Acme Testing");
		customisation.setBanner("https://tinyurl.com/acme-handy-worker-logo");
		customisation.setWelcomeMessageEn("Hello world!!");
		customisation.setWelcomeMessageEs("Hola mundo!!");
		customisation.setCountryCode("+34");
		customisation.setMaxNumberResults(20);
		customisation.setTimeCachedResults(10);
		customisation.setSpamWords("caparros,monchi,del nido,castro");

		saved = this.customisationService.save(customisation);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	@Test
	public void driverEdit() {
		final Object testingData[][] = {
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::name.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::name is null => 1/31 -> 3.22%.
			 */
			{
				null, "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::name.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::name is empty string => 1/31 -> 3.22%.
			 */
			{
				"", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::name.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::name is a malicious script => 1/31 -> 3.22%.
			 */
			{
				"<script> Alert('Hacked'); </script>", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::banner.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::banner is null => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", null, "Hello world!!", "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::banner.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::banner is empty string => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "", "Hello world!!", "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::banner.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::banner is a malicious script => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "<script> Alert('Hacked'); </script>", "Hello world!!", "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::welcomeMessageEn.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::welcomeMessageEn is null => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", null, "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::welcomeMessageEn.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::welcomeMessageEn is empty string => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "", "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::welcomeMessageEn.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::welcomeMessageEn is a malicious script => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "<script> Alert('Hacked'); </script>", "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::welcomeMessageEs.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::welcomeMessageEs is null => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world", null, "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::welcomeMessageEs.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::welcomeMessageEs is a empty string => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world", "", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::welcomeMessageEs.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::welcomeMessageEs is a malicious script => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "<script> Alert('Hacked'); </script>", "+43", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::countryCode.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::countryCode is null => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", null, 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::countryCode.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::countryCode is a empty string => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::countryCode.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::countryCode is an invalid pattern => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "-f4hy3", 20, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::timeCachedResults.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::timeCachedResults out of range: 0 => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+22", 0, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::timeCachedResults.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::timeCachedResuts out of range: 25 => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 25, 50, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::maxNumberResults.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::maxNumberResults out of range: 0 => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 12, 0, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::maxNumberResults.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::maxNumberResuts out of range: 101 => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 12, 101, "gilipollas,tonto,subnormal", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::spamWords.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::spamWords is null => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 20, 50, null, ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::spamWords.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::spamWords is a empty string => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 12, 50, "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::spamWords.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::spamWords: is a malicious script => 1/31 -> 3.22%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 12, 50, "<script> Alert('Hacked'); </script>", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Every attribute has a valid value => 31/31 -> 100.00%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 1, 1, "gilipollas,tonto,subnormal", null
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Every attribute has a valid value => 1/31 -> 100.00%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 2, 2, "gilipollas,tonto,subnormal", null
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Every attribute has a valid value => 1/31 -> 100.00%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 12, 50, "gilipollas,tonto,subnormal", null
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Every attribute has a valid value => 1/31 -> 100.00%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 23, 99, "gilipollas,tonto,subnormal", null
			},
			/*
			 * A: Requirement 14 (The system must be easy to customise at run time)
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Every attribute has a valid value => 1/31 -> 100.00%.
			 */
			{
				"Acme Retro", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", 24, 100, "gilipollas,tonto,subnormal", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);

	}

	protected void templateEdit(final String name, final String banner, final String welcomeMessageEn, final String welcomeMessageEs, final String countryCode, final int timeCachedResults, final int maxNumberResults, final String spamWords,
		final Class<?> expected) {
		Class<?> caught;
		Customisation found, saved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate("admin1");

			found = this.customisationService.find();
			found.setName(name);
			found.setBanner(banner);
			found.setWelcomeMessageEn(welcomeMessageEn);
			found.setWelcomeMessageEs(welcomeMessageEs);
			found.setCountryCode(countryCode);
			found.setTimeCachedResults(timeCachedResults);
			found.setMaxNumberResults(maxNumberResults);
			found.setSpamWords(spamWords);

			saved = this.customisationService.save(found);
			this.customisationService.flush();

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
}

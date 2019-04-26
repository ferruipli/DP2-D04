
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import utilities.AbstractTest;
import domain.Finder;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// Service under testing --------------------------------------------------

	@Autowired
	private FinderService		finderService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private FinderRepository	finderRepository;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The minimum, the maximum, the average and the standard deviation of the
	 * number of results in the finders.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testDataNumberResultsFinder() {
		Double[] data;

		data = this.finderService.findDataNumberResultsFinder();

		Assert.isTrue(data[0] == 0.0);
		Assert.isTrue(data[1] == 3.0);
		Assert.isTrue(data[2] == 1.2222);
		Assert.isTrue(data[3] == 1.3147);
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The ratio of empty versus non-empty finders.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testRatioEmptyVsNonEmpty() {
		Double data;

		data = this.finderService.findRatioEmptyVsNonEmpty();

		Assert.isTrue(data == 0.28571);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage
	 * his or her finder, which involves UPDATING THE SEARCH CRITERIA,
	 * listing its contents, and clearing it
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 88.2% of sentence coverage, since it has been covered
	 * 30 lines of code of 34 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 4
	 * values in the data of 15 different possible values.
	 */
	@Test()
	public void finderSearchTest() {
		Collection<Position> results;
		Finder finder, saved;
		int finderId;

		super.authenticate("hacker9");

		finderId = super.getEntityId("finder9");

		finder = this.finderRepository.findOne(finderId);
		finder = this.cloneFinder(finder);

		finder.setKeyword("56");
		finder.setMaximumDeadline(LocalDate.parse("2019-11-08").toDate());
		this.finderService.save(finder);
		saved = this.finderService.findByHackerPrincipal();

		results = saved.getPositions();

		super.unauthenticate();

		Assert.isTrue(results.size() == 2);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage
	 * his or her finder, which involves UPDATING THE SEARCH CRITERIA,
	 * listing its contents, and clearing it
	 * 
	 * B: The finder can only be used by its owner.
	 * 
	 * C: Approximately 52.9% of sentence coverage, since it has been covered
	 * 18 lines of code of 34 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 4
	 * values in the data of 15 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void finderSearchNegativeTest() {
		Collection<Position> results;
		Finder finder, saved;
		int finderId;

		super.authenticate("hacker9");

		finderId = super.getEntityId("finder8");

		finder = this.finderRepository.findOne(finderId);
		finder = this.cloneFinder(finder);

		finder.setKeyword("56");
		finder.setMaximumDeadline(LocalDate.parse("2019-11-08").toDate());
		this.finderService.save(finder);
		saved = this.finderService.findByHackerPrincipal();

		results = saved.getPositions();

		super.unauthenticate();

		Assert.isTrue(results.size() == 2);
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage
	 * his or her finder, which involves updating the search criteria,
	 * listing its contents, and CLEARING IT.
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been covered
	 * 16 lines of code of 16 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test()
	public void finderClearTest() {
		Finder finder;
		int finderId;

		super.authenticate("hacker9");

		finderId = super.getEntityId("finder9");

		finder = this.finderRepository.findOne(finderId);

		this.finderService.clear(finder);

		super.unauthenticate();

		Assert.isTrue(finder.getDeadline() == null);
		Assert.isTrue(finder.getKeyword().isEmpty());
		Assert.isTrue(finder.getMaximumDeadline() == null);
		Assert.isTrue(finder.getMinimumSalary() == null);
		Assert.isTrue(finder.getUpdatedMoment().equals(new Date(Integer.MIN_VALUE)));
	}

	/*
	 * A: An actor who is authenticated as a hacker must be able to: Manage
	 * his or her finder, which involves updating the search criteria,
	 * listing its contents, and CLEARING IT.
	 * 
	 * B: The finder can only be used by its owner.
	 * 
	 * C: Approximately 68.8% of sentence coverage, since it has been covered
	 * 11 lines of code of 16 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void finderClearNegativeTest() {
		Finder finder;
		int finderId;

		super.authenticate("hacker9");

		finderId = super.getEntityId("finder8");

		finder = this.finderRepository.findOne(finderId);

		this.finderService.clear(finder);

		super.unauthenticate();

		Assert.isTrue(finder.getDeadline() == null);
		Assert.isTrue(finder.getKeyword().isEmpty());
		Assert.isTrue(finder.getMaximumDeadline() == null);
		Assert.isTrue(finder.getMinimumSalary() == null);
		Assert.isTrue(finder.getUpdatedMoment().equals(new Date(Integer.MIN_VALUE)));
	}

	// Ancillary methods --------------------------------------------------

	private Finder cloneFinder(final Finder finder) {
		Finder result;

		result = new Finder();
		result.setDeadline(finder.getDeadline());
		result.setHacker(finder.getHacker());
		result.setId(finder.getId());
		result.setKeyword(finder.getKeyword());
		result.setMaximumDeadline(finder.getMaximumDeadline());
		result.setMinimumSalary(finder.getMinimumSalary());
		result.setPositions(finder.getPositions());
		result.setUpdatedMoment(finder.getUpdatedMoment());
		result.setVersion(finder.getVersion());

		return result;
	}

}

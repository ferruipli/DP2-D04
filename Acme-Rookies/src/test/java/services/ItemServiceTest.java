
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ItemServiceTest extends AbstractTest {

	// Service under testing ---------------------------------------------

	@Autowired
	private ItemService	itemService;


	// Other supporting services and repositories ------------------------

	// Tests -------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The minimum, the maximum, the average and the standard deviation of
	 * the number of items per provider.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testDataItemPerProvider() {
		Double[] data;

		data = this.itemService.dataItemsPerProvider();

		Assert.isTrue(data[0] == 0);
		Assert.isTrue(data[1] == 4);
		Assert.isTrue(data[2] == 1.85714);
		Assert.isTrue(data[3] == 1.2454);
	}

}

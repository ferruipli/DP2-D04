
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Item;

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

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			/*
			 * A: Acme- Rookies: Req.10.1 Create item
			 * B: Test positivo
			 * C: 100%. 28/28 Recorre 28 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"provider1", null
			},
			/*
			 * A: Acme- Rookies: Req.10.1 Create item
			 * B: Un rookie no puede crear un artículo
			 * C: 71%. 20/28 Recorre 20 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"rookie4", IllegalArgumentException.class
			},
			/*
			 * A: Acme- Rookies: Req.10.1 Create item
			 * B: El admin no puede crear un artículo
			 * C: 71%. 20/28 Recorre 20 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"admin1", IllegalArgumentException.class
			},
			/*
			 * A: Acme- Rookies: Req.10.1 Create item
			 * B: Un auditor no puede crear un artículo
			 * C: 71%. 20/28 Recorre 20 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"auditor1", IllegalArgumentException.class
			},
			/*
			 * A: Acme- Rookies: Req.10.1 Create item
			 * B: Una company no puede crear un artículo
			 * C: 71%. 20/28 Recorre 20 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"company1", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void templateCreate(final String username, final Class<?> expected) {
		Class<?> caught;
		Item item, itemSaved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			item = this.itemService.create();
			itemSaved = this.itemService.save(item);
			this.itemService.flush();

			Assert.notNull(itemSaved);
			Assert.isTrue(itemSaved.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

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

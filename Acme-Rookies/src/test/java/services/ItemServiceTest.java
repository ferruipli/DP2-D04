
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

import repositories.ItemRepository;
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
	private ItemService		itemService;

	@Autowired
	private ProviderService	providerService;

	// Other supporting services and repositories ------------------------

	@Autowired
	private ItemRepository	itemRepository;


	// Tests -------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her catalogue of items, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testPositiveListItem() {
		Collection<Item> items;
		int itemId, numberItems, providerId;
		Item item4, item5;

		super.authenticate("provider2");

		providerId = this.providerService.findByPrincipal().getId();
		itemId = super.getEntityId("item4");
		item4 = this.itemRepository.findOne(itemId);
		itemId = super.getEntityId("item5");
		item5 = this.itemRepository.findOne(itemId);
		numberItems = 2;

		items = this.itemService.findItemsByProvider(providerId);

		super.unauthenticate();

		Assert.isTrue(items.contains(item4) && items.contains(item5));
		Assert.isTrue(items.size() == numberItems);
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her catalogue of items, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: The actor must be provider
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeListItem() {
		Collection<Item> items;
		int itemId, numberItems, providerId;
		Item item4, item5;

		super.authenticate("company1");

		providerId = this.providerService.findByPrincipal().getId();
		itemId = super.getEntityId("item4");
		item4 = this.itemRepository.findOne(itemId);
		itemId = super.getEntityId("item5");
		item5 = this.itemRepository.findOne(itemId);
		numberItems = 2;

		items = this.itemService.findItemsByProvider(providerId);

		super.unauthenticate();

		Assert.isTrue(items.contains(item4) && items.contains(item5));
		Assert.isTrue(items.size() == numberItems);
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her catalogue of items, which includes listing, showing,
	 * CREATING, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 28 lines of code of 28 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 8
	 * values in the data of 30 different possible values.
	 */
	@Test
	public void testPositiveCreateItem() {
		Item item, saved, stored;

		super.authenticate("provider1");

		item = this.itemService.create();
		item.setName("Name item");
		item.setDescription("Description item");
		item.setLink("http://www.targetPageTest.es");
		item.setPicture("http://www.targetPageTest.es");
		saved = this.itemService.save(item);
		this.itemService.flush();
		stored = this.itemRepository.findOne(saved.getId());

		super.unauthenticate();

		Assert.isTrue(stored.equals(saved));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her catalogue of items, which includes listing, showing,
	 * CREATING, updating and deleting them.
	 * 
	 * B: For every item, the system must store a name
	 * 
	 * C: 53.57% of sentence coverage, since it has been covered
	 * 15 lines of code of 28 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 8
	 * values in the data of 30 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testNegativeCreateItem() {
		Item item, saved, stored;

		super.authenticate("provider1");

		item = this.itemService.create();
		item.setDescription("Description item");
		item.setLink("http://www.targetPageTest.es");
		item.setPicture("http://www.targetPageTest.es");
		saved = this.itemService.save(item);
		this.itemService.flush();
		stored = this.itemRepository.findOne(saved.getId());

		super.unauthenticate();

		Assert.isTrue(stored.equals(saved));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her catalogue of items, which includes listing, showing,
	 * creating, UPDATING and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 28 lines of code of 28 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 8
	 * values in the data of 30 different possible values.
	 */
	@Test
	public void testPositiveUpdateItem() {
		int itemId;
		Item item, saved;
		String name, description, link;

		name = "name";
		description = "description";
		link = "https://www.link.com";

		super.authenticate("provider1");

		itemId = super.getEntityId("item1");
		item = this.itemService.findOneToProviderEdit(itemId);
		//item = this.copy(item);
		item.setName(name);
		item.setDescription(description);
		item.setLink(link);
		saved = this.itemService.save(item);
		this.itemRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getName().equals(name));
		Assert.isTrue(saved.getDescription().equals(description));
		Assert.isTrue(saved.getLink().equals(link));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her catalogue of items, which includes listing, showing,
	 * creating, UPDATING and deleting them.
	 * 
	 * B: The link page must be a valid URL.
	 * 
	 * C: 53.57% of sentence coverage, since it has been covered
	 * 15 lines of code of 28 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 8
	 * values in the data of 30 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testNegativeUpdateItem() {
		int itemId;
		Item item, saved;
		String name, description, link;

		name = "name";
		description = "description";
		link = "link";

		super.authenticate("provider1");

		itemId = super.getEntityId("item1");
		item = this.itemService.findOneToProviderEdit(itemId);
		//item = this.copy(item);
		item.setName(name);
		item.setDescription(description);
		item.setLink(link);
		saved = this.itemService.save(item);
		this.itemRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getName().equals(name));
		Assert.isTrue(saved.getDescription().equals(description));
		Assert.isTrue(saved.getLink().equals(link));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her items, which includes listing, showing,
	 * creating, updating and DELETING them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 15 lines of code of 15 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 8
	 * values in the data of 30 different possible values.
	 */
	@Test
	public void testPositiveDeleteItem() {
		int itemId;
		Item item;

		super.authenticate("provider1");

		itemId = super.getEntityId("item1");
		item = this.itemService.findOneToProviderEdit(itemId);
		this.itemService.delete(item);

		super.unauthenticate();

		item = this.itemRepository.findOne(itemId);
		Assert.isTrue(item == null);
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her items, which includes listing, showing,
	 * creating, updating and DELETING them.
	 * 
	 * B: The item to delete must belong to the provider principal.
	 * 
	 * C: Approximately 93.33% of sentence coverage, since it has been covered
	 * 14 lines of code of 15 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 8
	 * values in the data of 30 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDeleteItem() {
		int itemId;
		Item item;

		super.authenticate("provider2");

		itemId = super.getEntityId("item1");
		item = this.itemService.findOneToProviderEdit(itemId);
		this.itemService.delete(item);

		super.unauthenticate();

		item = this.itemRepository.findOne(itemId);
		Assert.isTrue(item == null);
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


package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	// Managed repository ---------------------------------------------
	@Autowired
	private ItemRepository	itemRepository;

	// Supporting services -------------------------------------------
	@Autowired
	private ProviderService	providerService;


	//Constructor ----------------------------------------------------
	public ItemService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Item create() {
		Item result;
		Provider provider;

		provider = this.providerService.findByPrincipal();
		result = new Item();

		result.setProvider(provider);

		return result;
	}

	public Item save(final Item item) {
		Assert.notNull(item);
		Item result;

		result = this.itemRepository.save(item);

		this.checkByPrincipal(result);

		return result;
	}

	public Item findOne(final int itemId) {
		Item result;

		result = this.itemRepository.findOne(itemId);

		Assert.notNull(result);

		return result;
	}

	public Item findOneToProviderEdit(final int itemId) {
		Item result;

		result = this.findOne(itemId);

		this.checkByPrincipal(result);

		return result;
	}

	public Collection<Item> findAll() {
		Collection<Item> results;

		results = this.itemRepository.findAll();

		return results;
	}

	public void delete(final Item item) {
		Assert.notNull(item);
		//Assert.isTrue(this.itemRepository.exists(item.getId()));
		this.checkByPrincipal(item);

		this.itemRepository.delete(item);
	}

	// This method id used when an actor want to delete all his or her data.
	public void deleteItemsByProvider(final Provider provider) {
		Collection<Item> items;

		items = this.itemRepository.findItemsByProvider(provider.getId());
		this.itemRepository.delete(items);
	}

	// Other business methods ---------------------

	public Double[] dataItemsPerProvider() {
		Double[] results;

		results = this.itemRepository.dataItemsPerProvider();

		return results;
	}

	private void checkByPrincipal(final Item item) {
		Provider provider;

		provider = this.providerService.findByPrincipal();

		Assert.isTrue(provider.equals(item.getProvider()));
	}

	public Collection<Item> findItemsByProvider(final int providerId) {
		Collection<Item> results;

		results = this.itemRepository.findItemsByProvider(providerId);

		return results;
	}
}
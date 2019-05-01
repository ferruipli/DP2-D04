
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ItemRepository;

@Service
@Transactional
public class ItemService {

	// Managed repository ------------------------------------------------

	@Autowired
	private ItemRepository	itemRepository;


	// Other supporting services -----------------------------------------

	// Constructors ------------------------------------------------------

	public ItemService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	// Other business methods --------------------------------------------

	public Double[] dataItemsPerProvider() {
		Double[] results;

		results = this.itemRepository.dataItemsPerProvider();

		return results;
	}

	// Ancillary methods ----------------------------------

}

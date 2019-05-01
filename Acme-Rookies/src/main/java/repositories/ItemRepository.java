
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	// Req. 11.1.1
	@Query("select min(1.0 * (select count(i) from Item i where i.provider.id = p.id)), max(1.0 * (select count(i) from Item i where i.provider.id = p.id)), avg(1.0 * (select count(i) from Item i where i.provider.id = p.id)), stddev(1.0 * (select count(i) from Item i where i.provider.id = p.id)) from Provider p)")
	Double[] dataItemsPerProvider();

}

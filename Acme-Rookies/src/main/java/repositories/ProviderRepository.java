
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select p from Provider p where p.userAccount.id=?1")
	Provider findByUserAccount(int userAccountId);

	// Requirement 11.1.b: Top 5 providers in terms of total number of items provided.
	@Query("select i.provider from Item i group by i.provider order by count(i) desc")
	Page<Provider> topFiveProviders(Pageable page);
}

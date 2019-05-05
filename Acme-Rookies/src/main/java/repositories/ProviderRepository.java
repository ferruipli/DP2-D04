
package repositories;

import java.util.Collection;

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

	// Req. 14.1.3
	@Query("select p from Provider p where (select count(s) from Sponsorship s where s.provider.id = p.id) > (select (1.1 * avg(1.0 * (select count(s) from Sponsorship s where s.provider.id = p.id))) from Provider p)")
	Collection<Provider> findProvidersWithMoreSponsorships();
}

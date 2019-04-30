
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select avg(1.0 * (select count(s) from Sponsorship s where s.provider.id = p.id)), min(1.0 * (select count(s) from Sponsorship s where s.provider.id = p.id)), max(1.0 * (select count(s) from Sponsorship s where s.provider.id = p.id)),stddev(1.0 * (select count(s) from Sponsorship s where s.provider.id = p.id)) from Provider p)")
	Double[] dataOfSponsorshipPerProvider();

	@Query("select s from Sponsorship s where s.provider.id = ?1")
	Collection<Sponsorship> findAllByProviderId(int providerId);

	@Query("select s from Sponsorship s where s.position.id = ?1")
	List<Sponsorship> findAllByPositionId(final int positionId);

}

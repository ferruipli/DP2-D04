
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select min(f.positions.size), max(f.positions.size), avg(f.positions.size), stddev(f.positions.size) from Finder f")
	Double[] findDataNumberResultsFinder();

	@Query("select count(f)/(select count(ff) from Finder ff where ff.keyword != '' or ff.deadline is not null or ff.minimumSalary is not null or ff.maximumDeadline is not null)*1.0 from Finder f where f.keyword = '' and f.deadline is null and f.minimumSalary is null and f.maximumDeadline is null")
	Double findRatioEmptyVsNonEmpty();

	@Query("select f from Finder f where f.hacker.id = ?1")
	Finder findByHackerId(int hackerId);

	@Query("select f from Finder f join f.positions p where p.id =?1")
	Collection<Finder> findAllByPosition(int positionId);

}

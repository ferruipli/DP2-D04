
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p.ticker from Position p where p.ticker = ?1")
	String existTicker(String ticker);

	@Query("select p from Position p where p.isFinalMode = true and p.isCancelled = false")
	Collection<Position> findAllPositionAvailable();

	@Query("select p from Position p where p.company.id=?1")
	Collection<Position> findPositionByCompany(int id);

	@Query("select p from Position p where p.company.id = ?1 and p.isFinalMode = true and p.isCancelled = false")
	Collection<Position> findFinalModePositionsByCompany(int companyId);

	@Query("select p from Position p join p.problems pr where pr.id=?1")
	Collection<Position> findPositionsByProblem(int id);

	@Query("select p from Position p where (p.isFinalMode = true) and (p.isCancelled = false) and ((p.title like concat('%', concat(?1, '%'))) or (p.description like concat('%', concat(?1, '%'))) or (p.profile like concat('%', concat(?1, '%'))) or (p.skills like concat('%', concat(?1, '%'))) or (p.technologies like concat('%', concat(?1, '%'))) or (p.company.name like concat('%', concat(?1, '%'))))")
	Collection<Position> findAvailableByKeyword(String keyword);

	@Query("select p from Position p where (p.isFinalMode = true) and (p.isCancelled = false) and ((p.ticker like concat('%', concat(?1, '%'))) or (p.title like concat('%', concat(?1, '%'))) or (p.description like concat('%', concat(?1, '%'))) or (p.profile like concat('%', concat(?1, '%'))) or (p.skills like concat('%', concat(?1, '%'))) or (p.technologies like concat('%', concat(?1, '%')))) and (p.deadline = ?2 or ?2 = NULL) and (p.deadline <= ?3 or ?3 = NULL) and (p.salary >= ?4 or ?4 = NULL)")
	Page<Position> searchPositionFinder(String keyword, Date deadline, Date maximumDeadline, Double minimumSalary, Pageable pageable);

	@Query("select p from Position p where (p.isFinalMode = true) and (p.isCancelled = false) and ((p.ticker like concat('%', concat(?1, '%'))) or (p.title like concat('%', concat(?1, '%'))) or (p.description like concat('%', concat(?1, '%'))) or (p.profile like concat('%', concat(?1, '%'))) or (p.skills like concat('%', concat(?1, '%'))) or (p.technologies like concat('%', concat(?1, '%')))) and (p.deadline = ?2 or ?2 = NULL) and (p.deadline <= ?3 or ?3 = NULL) and (p.salary >= ?4 or ?4 = NULL)")
	Collection<Position> matchCriteria(String keyword, Date deadline, Date maximumDeadline, Double minimumSalary);

	// Query dashboard 11.2.5 The average, the minimum, the maximum, and the standard deviation of the salaries offered.
	@Query("select avg(p.salary),min(p.salary),max(p.salary),stddev(p.salary) from Position p where p.isFinalMode = true")
	Double[] findDataSalaryOffered();

	// Query dashboard 11.2.6 The best and the worst position in terms of salary
	@Query("select p from Position p where p.salary = (select max(p.salary) from Position p) or p.salary = (select min(p.salary) from Position p) order by p.salary DESC")
	Collection<Position> findPositionsBestWorstSalary();

	// Query dashboard 11.2.1 The average, the minimum, the maximum, and the standard deviation of the number of positions per company
	@Query("select avg(1.0 * (select count(p) from Position p where p.company.id = c.id)), min(1.0 * (select count(p) from Position p where p.company.id = c.id)), max(1.0 * (select count(p) from Position p where p.company.id = c.id)),stddev(1.0 * (select count(p) from Position p where p.company.id = c.id)) from Company c)")
	Double[] findDataNumberPositionsPerCompany();

}

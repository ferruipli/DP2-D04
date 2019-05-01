
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select a from Audit a where a.auditor.id =?1")
	Collection<Audit> findAuditsByAuditor(int id);

	@Query("select a from Audit a where a.position.id =?1")
	Collection<Audit> findAuditsByPosition(int id);

	@Query("select a from Audit a where a.position.id =?1 and a.finalMode=true")
	Collection<Audit> findFinalAuditsByPosition(int id);

	@Query("select avg(a.score) from Audit a where a.position.company.id=?1")
	Double avgScoreByCompany(int companyId);

	@Query("select a from Audit a where a.auditor.id =?1 and a.position.id =?2 ")
	Collection<Audit> findAuditsByAuditorPosition(int idAuditor, int idPos);

	@Query("select avg(a.score), min(a.score), max(a.score), stddev(a.score) from Audit a")
	Double[] findDataNumberAuditScore();

	@Query("select avg(1 * (select count(a.score) from Audit a where a.position.id = p.id)), min(1 * (select count(a.score) from Audit a where a.position.id = p.id)), max(1 * (select count(a.score) from Audit a where a.position.id = p.id)), stddev(1 * (select count(a.score) from Audit a where a.position.id = p.id)) from Position p")
	Double[] findDataNumberAuditScorePerPosition();

	@Query("select avg(a.score) from Audit a")
	Collection<Double> findAvgSalaryByHighestPosition();

}

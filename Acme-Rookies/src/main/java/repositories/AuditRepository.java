
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

	@Query("select avg(c.auditScore), min(c.auditScore), max(c.auditScore) ,stddev(c.auditScore)  from Position p join p.company c")
	Double[] findDataNumberAuditScorePerPosition();

	@Query("select avg(a.score) from Audit a")
	Collection<Double> findAvgSalaryByHighestPosition();

}

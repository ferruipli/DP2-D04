
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

	@Query("select avg(a.score) from Audit a where a.finalMode=true and a.position.company.id=?1")
	Double avgScoreByCompany(int companyId);

	@Query("select a from Audit a where a.auditor.id =?1 and a.position.id =?2 ")
	Collection<Audit> findAuditsByAuditorPosition(int idAuditor, int idPos);

	@Query("select avg(1.0*(select avg(a.score) from Audit a where a.position = p)), min(1.0*(select avg(a.score) from Audit a where a.position = p)), max(1.0*(select avg(a.score) from Audit a where a.position = p)) ,stddev(1.0*(select avg(a.score) from Audit a where a.position = p)) from Position p")
	Double[] findDataNumberAuditScorePerPosition();

	@Query("select avg(p.salary) from Position p where (select avg(a.score) from Audit a where a.position = p)>(select avg(1.0*(select avg(a.score)from Audit a where a.position = po))from Position po)")
	Collection<Double> findAvgSalaryByHighestPosition();

	@Query("select avg(1.0* c.auditScore), min(1.0*c.auditScore), max(1.0*c.auditScore), stddev(1.0*c.auditScore)  from Company c")
	Double[] findDataNumberAuditScoreOfCompany();

}

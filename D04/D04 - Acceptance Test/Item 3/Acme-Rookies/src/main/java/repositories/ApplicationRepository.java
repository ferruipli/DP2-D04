
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.position.id=?1 and a.rookie.id=?2")
	Collection<Application> findApplicationsByPositionByRookie(int idPosition, int idRookie);

	// Req 11.2.2
	@Query("select avg(1 * (select count(a) from Application a where a.rookie.id = h.id)), min(1 * (select count(a) from Application a where a.rookie.id = h.id)), max(1 * (select count(a) from Application a where a.rookie.id = h.id)), stddev(1 * (select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
	Double[] findDataNumberApplicationPerRookie();

	@Query("select a from Application a where a.rookie.id = ?1 and a.status='SUBMITTED'")
	Collection<Application> findSubmittedApplicationsByRookie(int id);

	@Query("select a from Application a where a.rookie.id = ?1 and a.status='ACCEPTED'")
	Collection<Application> findAcceptedApplicationsByRookie(int id);

	@Query("select a from Application a where a.rookie.id = ?1 and a.status='REJECTED'")
	Collection<Application> findRejectedApplicationsByRookie(int id);

	@Query("select a from Application a where a.rookie.id = ?1 and a.status='PENDING'")
	Collection<Application> findPendingApplicationsByRookie(int id);

	@Query("select a from Application a where a.position.id = ?1 and a.status='SUBMITTED'")
	Collection<Application> findSubmittedApplicationsByPosition(int positionId);

	@Query("select a from Application a where a.position.id = ?1 and a.status='ACCEPTED'")
	Collection<Application> findAcceptedApplicationsByPosition(int positionId);

	@Query("select a from Application a where a.position.id = ?1 and a.status='REJECTED'")
	Collection<Application> findRejectedApplicationsByPosition(int positionId);

	@Query("select a from Application a where a.answer.id=?1")
	Application findApplicationByAnswer(int answerId);

	@Query("select a from Application a where a.position.company.id=?1")
	Collection<Application> findApplicationByCompany(int id);

	@Query("select a from Application a where a.rookie.id=?1")
	Collection<Application> findApplicationByRookie(int id);

	@Query("select a from Application a where a.position.id=?1 and (a.status='SUBMITTED' or a.status='PENDING')")
	Collection<Application> findSubmittedPendingByPosition(int id);

	@Query("select a from Application a where a.problem.id=?1 and a.rookie.id=?2")
	Collection<Application> findApplicationsByProblemRookie(int idProblem, int idRookie);

}

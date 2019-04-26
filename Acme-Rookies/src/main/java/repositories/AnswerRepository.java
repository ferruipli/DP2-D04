
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	@Query("select a.answer from Application a where a.position.company.id=?1")
	Collection<Answer> findAnswerByCompany(int id);

	@Query("select a.answer from Application a where a.hacker.id=?1")
	Collection<Answer> findAnswerByHacker(int id);

}

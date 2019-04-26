
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SystemTag;

@Repository
public interface SystemTagRepository extends JpaRepository<SystemTag, Integer> {

	@Query("select s from SystemTag s where s.actor.id=?1 and s.message.id=?2 and s.text='DELETED'")
	SystemTag findMessageTaggedAsDELETED(int actorId, int messageId);

	@Query("select s from SystemTag s where s.actor.id=?1 and s.message.id=?2 and s.text='HARDDELETED'")
	SystemTag findMessageTaggedAsHARDDELETED(int actorId, int messageId);

	@Query("select count(s) from SystemTag s where s.message.id=?1 and s.text='HARDDELETED'")
	Integer numberOfTimesTaggedAsHARDDELETED(int messageId);

	@Query("select s from SystemTag s where s.message.id=?1")
	Collection<SystemTag> systemTagByMessage(int messageId);
}


package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;
import domain.Rookie;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {

	@Query("select r from Rookie r where r.userAccount.id = ?1")
	Rookie findRookieByUserAccount(int userAccountId);

	// Req 11.2.4
	@Query("select a.rookie from Application a group by a.rookie.id having count(a) = (select max(1 * (select count(a) from Application a where a.rookie.id = h.id)) from Rookie h)")
	Collection<Rookie> findRookiesWithMoreApplications();

	@Query("select c from Curriculum c join c.rookie h where h.id=?1 and c.isOriginal=true")
	Collection<Curriculum> originalCurricula(int id);

	@Query("select c.rookie from Curriculum c where c.personalData.id = ?1")
	Rookie findByPersonalDataId(int personalDataId);

	@Query("select c.rookie from Curriculum c join c.positionDatas d where d.id = ?1")
	Rookie findByPositionDataId(int positionDataId);

	@Query("select c.rookie from Curriculum c join c.educationDatas d where d.id = ?1")
	Rookie findByEducationDataId(int educationDataId);

	@Query("select c.rookie from Curriculum c join c.miscellaneousDatas d where d.id = ?1")
	Rookie findByMiscellaneousDataId(int miscellaneousDataId);
}

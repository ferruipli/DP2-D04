
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;
import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	@Query("select h from Hacker h where h.userAccount.id = ?1")
	Hacker findHackerByUserAccount(int userAccountId);

	// Req 11.2.4
	@Query("select a.hacker from Application a group by a.hacker.id having count(a) = (select max(1 * (select count(a) from Application a where a.hacker.id = h.id)) from Hacker h)")
	Collection<Hacker> findHackersWithMoreApplications();

	@Query("select c from Curriculum c join c.hacker h where h.id=?1 and c.isOriginal=true")
	Collection<Curriculum> originalCurricula(int id);

	@Query("select c.hacker from Curriculum c where c.personalData.id = ?1")
	Hacker findByPersonalDataId(int personalDataId);

	@Query("select c.hacker from Curriculum c join c.positionDatas d where d.id = ?1")
	Hacker findByPositionDataId(int positionDataId);

	@Query("select c.hacker from Curriculum c join c.educationDatas d where d.id = ?1")
	Hacker findByEducationDataId(int educationDataId);

	@Query("select c.hacker from Curriculum c join c.miscellaneousDatas d where d.id = ?1")
	Hacker findByMiscellaneousDataId(int miscellaneousDataId);
}

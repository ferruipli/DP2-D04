
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select c from Company c where c.userAccount.id = ?1")
	Company findCompanyByUserAccount(int userAccountId);

	// Query dashboard 11.2.3 The companies that have offered more positions.
	@Query("select c from Company c where (select count(p) from Position p where p.company.id = c.id and p.isFinalMode = true and p.isCancelled = false) = (select max(1.0 * (select count(p) from Position p where p.company.id = c.id and p.isFinalMode = true and p.isCancelled = false)) from Company c)")
	Collection<Company> findCompaniesOfferedMorePositions();
}

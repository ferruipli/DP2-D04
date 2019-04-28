
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Auditor;

@Service
@Transactional
public class AuditorService {

	// Managed repository ---------------------------------------------

	@Autowired
	private AuditorRepository	auditorRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;


	//Constructor ----------------------------------------------------

	public AuditorService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------

	public Auditor create() {
		Auditor result;

		result = new Auditor();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.AUDITOR));

		return result;
	}

	public Auditor findOne(final int auditorId) {
		Auditor result;

		result = this.auditorRepository.findOne(auditorId);
		Assert.notNull(result);

		return result;
	}

	public Auditor findOneToDisplayEdit(final int auditorId) {
		Assert.isTrue(auditorId != 0);

		final Auditor result;
		Auditor principal;

		principal = this.findByPrincipal();
		result = this.auditorRepository.findOne(auditorId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == auditorId);

		return result;
	}

	public Auditor save(final Auditor auditor) {
		Auditor result;

		result = (Auditor) this.actorService.save(auditor);

		return result;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> result;

		result = this.auditorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// Other business methods ---------------------

	public Auditor findByPrincipal() {
		Auditor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	private Auditor findByUserAccount(final int userAccountId) {
		Auditor result;

		result = this.auditorRepository.findByUserAccount(userAccountId);

		return result;
	}

}


package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;

@Service
@Transactional
public class AuditService {

	// Managed repository ---------------------------------------------

	@Autowired
	private AuditRepository	auditRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private PositionService	positionService;


	//Constructor ----------------------------------------------------

	public AuditService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------

	public Audit create() {
		Audit result;
		final Auditor auditor;

		//TODO
		result = new Audit();

		return result;
	}
	public Audit save(final Audit audit) {
		Assert.notNull(audit);
		this.checkByPrincipal(audit);
		Assert.isTrue(!audit.getFinalMode());

		//TODO

		final Audit result;

		result = this.auditRepository.save(audit);

		return result;
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit);
		Assert.isTrue(this.auditRepository.exists(audit.getId()));
		this.checkByPrincipal(audit);
		Assert.isTrue(!audit.getFinalMode());

		this.auditRepository.delete(audit);
	}

	public Audit findOneToEditDelete(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);
		this.checkByPrincipal(result);
		Assert.isTrue(!result.getFinalMode());

		Assert.notNull(result);

		return result;
	}

	public Audit findOne(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);
		Assert.notNull(result);

		return result;
	}

	public Audit findOneToDisplay(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);
		Assert.isTrue(result.getFinalMode());

		Assert.notNull(result);

		return result;
	}

	private void checkByPrincipal(final Audit audit) {
		Auditor owner;
		Auditor principal;

		owner = audit.getAuditor();
		principal = this.auditorService.findByPrincipal();

		Assert.isTrue(owner.equals(principal));
	}

}


package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;
import domain.Position;

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
	private UtilityService	utilityService;

	@Autowired
	private Validator		validator;


	//Constructor ----------------------------------------------------

	public AuditService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------

	public Audit create(final Position position) {
		Audit result;
		final Auditor auditor;

		Assert.isTrue(position.getIsFinalMode());
		Assert.isTrue(!position.getIsCancelled());

		result = new Audit();
		auditor = this.auditorService.findByPrincipal();

		result.setWrittenMoment(this.utilityService.current_moment());
		result.setAuditor(auditor);
		result.setPosition(position);

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


	public void makeFinal(final Audit audit) {
		this.checkByPrincipal(audit);

		audit.setFinalMode(true);

	protected Double avgScoreByCompany(final int companyId) {
		Double result;

		result = this.auditRepository.avgScoreByCompany(companyId);

		return result;

	}

	private void checkByPrincipal(final Audit audit) {
		Auditor owner;
		Auditor principal;

		owner = audit.getAuditor();
		principal = this.auditorService.findByPrincipal();

		Assert.isTrue(owner.equals(principal));
	}
	public Collection<Audit> findAuditsByAuditor(final Auditor auditor) {
		Collection<Audit> audits;

		audits = this.auditRepository.findAuditsByAuditor(auditor.getId());

		return audits;
	}
	protected void flush() {
		this.auditRepository.flush();
	}

	public Audit reconstruct(final Audit audit, final Position position, final BindingResult binding) {
		Audit result, auditStored;

		if (audit.getId() != 0) {
			result = new Audit();
			auditStored = this.findOne(audit.getId());
			result.setVersion(auditStored.getVersion());
			result.setAuditor(auditStored.getAuditor());
			result.setFinalMode(auditStored.getFinalMode());
			result.setPosition(auditStored.getPosition());
			result.setWrittenMoment(auditStored.getWrittenMoment());

		} else
			result = this.create(position);

		result.setId(audit.getId());
		result.setScore(audit.getScore());
		result.setText(audit.getText());

		this.validator.validate(result, binding);

		return result;
	}

}

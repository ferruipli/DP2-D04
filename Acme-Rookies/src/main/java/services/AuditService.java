
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

		result = new Audit();
		auditor = this.auditorService.findByPrincipal();

		Assert.isTrue(this.isAuditable(position, auditor));

		result.setWrittenMoment(this.utilityService.current_moment());
		result.setAuditor(auditor);
		result.setPosition(position);

		return result;
	}
	public Audit save(final Audit audit) {
		Assert.notNull(audit);
		this.checkByPrincipal(audit);
		Assert.isTrue(!audit.getFinalMode());

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

	public void deleteByAuditor() {
		Auditor principal;
		Collection<Audit> audits;

		principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);

		audits = this.auditRepository.findAuditsByAuditor(principal.getId());

		this.auditRepository.delete(audits);
	}

	public void deleteByPosition(final Position position) {
		Collection<Audit> audits;

		audits = this.auditRepository.findAuditsByPosition(position.getId());

		this.auditRepository.delete(audits);
	}

	public Audit findOne(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);
		Assert.notNull(result);

		return result;
	}

	public Audit findOneToEditDelete(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);
		this.checkByPrincipal(result);
		Assert.isTrue(!result.getFinalMode());

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

	public Collection<Audit> findAuditsByAuditor(final Auditor auditor) {
		Collection<Audit> audits;

		audits = this.auditRepository.findAuditsByAuditor(auditor.getId());

		return audits;
	}
	public Collection<Audit> findFinalByPosition(final Position position) {
		Collection<Audit> audits;

		audits = this.auditRepository.findFinalAuditsByPosition(position.getId());

		return audits;
	}

	public void makeFinal(final Audit audit) {
		this.checkByPrincipal(audit);

		audit.setFinalMode(true);
	}

	public boolean isAuditable(final Position position, final Auditor auditor) {
		boolean result;
		Collection<Audit> audits;

		Assert.isTrue(position.getIsFinalMode());
		Assert.isTrue(!position.getIsCancelled());

		audits = this.auditRepository.findAuditsByAuditorPosition(auditor.getId(), position.getId());
		result = audits.isEmpty();

		return result;
	}
	// Req 4.4.1 The average, the minimum, the maximum, and the standard deviation of the audit score
	//	of the positions stored in the system
	public Double[] findDataNumberAuditScore() {
		Double[] res;

		res = this.auditRepository.findDataNumberAuditScore();

		return res;
	}

	// Req 4.4.2 The average, the minimum, the maximum, and the standard deviation of the audit score
	//	of the companies that are registered in the system.
	public Double[] findDataNumberAuditScoreOfCompany() {
		Double[] res;

		res = this.auditRepository.findDataNumberAuditScoreOfCompany();

		return res;
	}

	// Req 4.4.4 The average salary offered by the positions that have the highest average audit score
	public Double findAvgSalaryByHighestPosition() {
		Double res;

		res = this.auditRepository.findAvgSalaryByHighestPosition();

		return res;
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
	private void checkByPrincipal(final Audit audit) {
		Auditor owner;
		Auditor principal;

		owner = audit.getAuditor();
		principal = this.auditorService.findByPrincipal();

		Assert.isTrue(owner.equals(principal));
	}
	protected void flush() {
		this.auditRepository.flush();
	}
	protected Double avgScoreByCompany(final int companyId) {
		Double result;

		result = this.auditRepository.avgScoreByCompany(companyId);

		return result;

	}

}

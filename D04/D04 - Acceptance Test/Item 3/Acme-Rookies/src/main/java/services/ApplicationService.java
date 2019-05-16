
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ApplicationRepository;
import domain.Answer;
import domain.Application;
import domain.Company;
import domain.Curriculum;
import domain.Position;
import domain.Problem;
import domain.Rookie;

@Service
@Transactional
public class ApplicationService {

	// Managed repository ---------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ProblemService			problemService;


	//Constructor ----------------------------------------------------
	public ApplicationService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Application create(final Position position) {
		Assert.isTrue(position.getIsFinalMode());
		Assert.isTrue(!(position.getIsCancelled()));

		Application result;
		Rookie rookie;
		Date moment;
		final Curriculum curriculum;
		List<Curriculum> curricula;
		final Problem problem;

		rookie = this.rookieService.findByPrincipal();

		curricula = new ArrayList<>(this.curriculumService.originalCurricula(rookie.getId()));

		Assert.isTrue(!curricula.isEmpty());
		Assert.isTrue(this.isApplied(position, rookie));

		moment = this.utilityService.current_moment();
		Assert.isTrue(position.getDeadline().after(moment));
		curriculum = curricula.get(0);
		problem = this.getRandomProblem(position.getProblems());

		result = new Application();
		result.setRookie(rookie);
		result.setCurriculum(curriculum);
		result.setProblem(problem);
		result.setPosition(position);
		result.setStatus("PENDING");
		result.setApplicationMoment(moment);

		return result;
	}
	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getPosition().getIsFinalMode());
		Assert.isTrue(!(application.getPosition().getIsCancelled()));
		Assert.isTrue(this.rookieService.findByPrincipal().equals(application.getRookie()));
		Assert.isTrue(application.getStatus().equals("PENDING"));

		Application result;
		Application applicationSaved;

		applicationSaved = this.applicationRepository.findOne(application.getId());

		if (application.getId() == 0) {
			Assert.isTrue(this.applicationRepository.findApplicationsByPositionByRookie(application.getPosition().getId(), application.getRookie().getId()).isEmpty());
			Assert.isTrue(!this.problemService.notExistProblemInPosition(application.getProblem().getId(), application.getPosition().getId()));
			Assert.isTrue(!(this.curriculumService.originalCurriculaByPrincipal().isEmpty()));
			Assert.isTrue(application.getCurriculum().getRookie().equals(this.rookieService.findByPrincipal()));
			Assert.isTrue(application.getCurriculum().getIsOriginal());
			Curriculum curriculumCopy;
			curriculumCopy = this.curriculumService.saveCopy(application.getCurriculum());
			application.setCurriculum(curriculumCopy);
			Assert.isNull(application.getSubmittedMoment());
			Assert.isTrue(!(application.getCurriculum().getIsOriginal()));
			Assert.isTrue(!(application.getApplicationMoment().equals(null)));
			Assert.isNull(application.getAnswer());

		} else {
			Assert.isTrue(applicationSaved.getProblem().equals(application.getProblem()));
			Assert.isTrue(applicationSaved.getPosition().equals(application.getPosition()));
			Assert.isTrue(applicationSaved.getRookie().equals(application.getRookie()));
			Assert.isTrue(applicationSaved.getCurriculum().equals(application.getCurriculum()));
			Assert.isTrue(applicationSaved.getApplicationMoment().equals(application.getApplicationMoment()));
			Assert.isNull(application.getSubmittedMoment());
			Assert.isTrue(!(application.getAnswer().equals(null)));

			Date moment;
			moment = this.utilityService.current_moment();

			application.setSubmittedMoment(moment);
			application.setStatus("SUBMITTED");
		}

		result = this.applicationRepository.save(application);

		return result;
	}
	public void acceptedApplication(final Application application) {
		Assert.isTrue(this.companyService.findByPrincipal().equals(application.getPosition().getCompany()));
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		Assert.isTrue(!(application.getAnswer().equals(null)));
		Assert.isTrue(!(application.getSubmittedMoment().equals(null)));
		application.setStatus("ACCEPTED");
		this.messageService.notification_applicationStatusChanges(application);
	}

	public void rejectedApplication(final Application application) {
		Assert.isTrue(this.companyService.findByPrincipal().equals(application.getPosition().getCompany()));
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		Assert.isTrue(!(application.getAnswer().equals(null)));
		Assert.isTrue(!(application.getSubmittedMoment().equals(null)));
		application.setStatus("REJECTED");
		this.messageService.notification_applicationStatusChanges(application);
	}

	protected void addAnswer(final Application application, final Answer answer) {
		application.setAnswer(answer);
		this.save(application);
	}

	protected Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	public Application findOneToRookie(final int applicationId) {
		Application result;

		result = this.findOne(applicationId);

		Assert.notNull(result);
		Assert.isTrue(this.rookieService.findByPrincipal().equals(result.getRookie()));

		return result;
	}

	public Application findOneToRookieEdit(final int applicationId) {
		Application result;

		result = this.findOneToRookie(applicationId);

		Assert.isTrue(result.getCurriculum().equals(null));

		return result;
	}

	public Application findOneToCompany(final int applicationId) {
		Application result;

		result = this.findOne(applicationId);

		Assert.notNull(result);
		Assert.isTrue(this.companyService.findByPrincipal().equals(result.getPosition().getCompany()));

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> results;

		results = this.applicationRepository.findAll();

		return results;
	}

	protected void deleteApplicationByCompany(final Company company) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationByCompany(company.getId());
		for (final Application a : applications)
			this.curriculumService.deleteCurriculum(a);

		this.applicationRepository.deleteInBatch(applications);
	}

	protected void deleteApplicationByRookie(final Rookie rookie) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationByRookie(rookie.getId());
		this.applicationRepository.deleteInBatch(applications);
	}

	// Reconstruct ----------------------------------------------
	public Application reconstruct(final Application application, final BindingResult binding) {
		Application result, applicationStored;

		if (application.getId() != 0) {
			result = new Application();
			applicationStored = this.findOne(application.getId());
			result.setId(applicationStored.getId());
			result.setVersion(applicationStored.getVersion());
			result.setApplicationMoment(applicationStored.getApplicationMoment());
			result.setStatus(applicationStored.getStatus());
			result.setCurriculum(applicationStored.getCurriculum());
			result.setPosition(applicationStored.getPosition());
			result.setProblem(applicationStored.getProblem());

			result.setSubmittedMoment(application.getSubmittedMoment());
			result.setAnswer(application.getAnswer());

		} else {
			result = this.create(application.getPosition());
			result.setCurriculum(application.getCurriculum());
		}

		return result;
	}

	// Other business methods ---------------------

	private Problem getRandomProblem(final Collection<Problem> problems) {
		List<Problem> problemList;
		Problem result;

		problemList = new ArrayList<>(problems);
		result = problemList.get(new Random().nextInt(problems.size()));

		return result;
	}

	public Double[] findDataNumberApplicationPerRookie() {
		Double[] result;

		result = this.applicationRepository.findDataNumberApplicationPerRookie();
		Assert.notNull(result);

		return result;
	}

	protected Collection<Application> findApplicationsByProblemRookie(final int idProblem, final int idRookie) {
		Collection<Application> result;

		result = this.applicationRepository.findApplicationsByProblemRookie(idProblem, idRookie);

		return result;
	}

	public Collection<Application> findPendingApplicationsByRookie() {
		Collection<Application> applications;
		Rookie rookie;

		rookie = this.rookieService.findByPrincipal();
		applications = this.applicationRepository.findPendingApplicationsByRookie(rookie.getId());

		return applications;
	}

	public Collection<Application> findSubmittedApplicationsByRookie() {
		Collection<Application> applications;
		Rookie rookie;

		rookie = this.rookieService.findByPrincipal();
		applications = this.applicationRepository.findSubmittedApplicationsByRookie(rookie.getId());

		return applications;
	}

	public Collection<Application> findAcceptedApplicationsByRookie() {
		Collection<Application> applications;
		Rookie rookie;

		rookie = this.rookieService.findByPrincipal();
		applications = this.applicationRepository.findAcceptedApplicationsByRookie(rookie.getId());

		return applications;
	}

	public Collection<Application> findRejectedApplicationsByRookie() {
		Collection<Application> applications;
		Rookie rookie;

		rookie = this.rookieService.findByPrincipal();
		applications = this.applicationRepository.findRejectedApplicationsByRookie(rookie.getId());

		return applications;
	}

	public Collection<Application> findSubmittedApplicationsByPosition(final int positionId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findSubmittedApplicationsByPosition(positionId);

		return applications;
	}

	public Collection<Application> findAcceptedApplicationsByPosition(final int positionId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findAcceptedApplicationsByPosition(positionId);

		return applications;
	}

	public Collection<Application> findRejectedApplicationsByPosition(final int positionId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findRejectedApplicationsByPosition(positionId);

		return applications;
	}

	public Application findApplicationByAnswer(final int answerId) {
		Application result;

		result = this.applicationRepository.findApplicationByAnswer(answerId);

		return result;
	}

	public boolean isApplied(final Position position, final Rookie rookie) {
		boolean result;
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationsByPositionByRookie(position.getId(), rookie.getId());
		result = applications.isEmpty();

		return result;
	}

	protected Collection<Application> findSubmittedPendingByPosition(final int positionId) {
		Collection<Application> result;

		result = this.applicationRepository.findSubmittedPendingByPosition(positionId);

		return result;
	}

	protected void rejectedCancelPosition(final Application application) {
		Assert.isTrue(this.companyService.findByPrincipal().equals(application.getPosition().getCompany()));
		Assert.isTrue(application.getStatus().equals("SUBMITTED") || application.getStatus().equals("PENDING"));
		application.setStatus("REJECTED");
		this.messageService.notification_applicationStatusChanges(application);
	}

	protected void flush() {
		this.applicationRepository.flush();
	}

}

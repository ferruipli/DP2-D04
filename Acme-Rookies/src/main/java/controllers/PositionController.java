
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.AuditService;
import services.AuditorService;
import services.CompanyService;
import services.CurriculumService;
import services.PositionService;
import services.RookieService;
import services.SponsorshipService;
import services.UtilityService;
import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.Curriculum;
import domain.Position;
import domain.Problem;
import domain.Rookie;
import domain.Sponsorship;

@Controller
@RequestMapping(value = "/position")
public class PositionController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private PositionService		positionService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private UtilityService		utilityService;


	// Constructor ------------------------------------

	public PositionController() {
		super();
	}

	// List------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int companyId) {
		ModelAndView result;
		Collection<Position> positions;
		Company principal;
		Company owner;

		try {
			result = new ModelAndView("position/list");
			positions = this.positionService.findFinalModePositionsByCompany(companyId);
			owner = this.companyService.findOne(companyId);

			try {
				principal = this.companyService.findByPrincipal();
			} catch (final Exception e1) {
				principal = null;
			}

			if (principal != null && principal.getId() == companyId) {
				result.addObject("principal", principal);
				positions = this.positionService.findPositionByPrincipal();
			}
			result.addObject("principal", principal);
			result.addObject("owner", owner);
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/availableList", method = RequestMethod.GET)
	public ModelAndView availableList() {
		ModelAndView result;
		Collection<Position> positions;
		Company principal;

		try {
			result = new ModelAndView("position/list");

			positions = this.positionService.findAllPositionAvailable();
			try {
				principal = this.companyService.findByPrincipal();
			} catch (final Exception e1) {
				principal = null;
			}
			if (principal != null)
				result.addObject("principal", principal);

			result.addObject("owner", null);
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/availableList.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(value = "keyword", defaultValue = "") final String keyword) {
		ModelAndView result;
		Collection<Position> positions;

		positions = this.positionService.searchByKeyword(keyword);

		result = new ModelAndView("position/search");
		result.addObject("keyword", keyword);
		result.addObject("positions", positions);
		result.addObject("isSearch", true);
		result.addObject("requestURI", "position/search.do");

		return result;
	}

	// Display------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;
		Sponsorship sponsorship;
		Company principal;
		Collection<Problem> problemList;

		final Collection<Audit> audits;
		Boolean isApplied, isDeadlineFuture, isAuditable;
		Rookie rookiePrincipal;
		Boolean hasProblem;
		Date moment;
		Auditor auditorPrincipal;

		try {
			result = new ModelAndView("position/display");
			position = this.positionService.findOne(positionId);
			moment = this.utilityService.current_moment();
			isDeadlineFuture = false;
			if (position.getDeadline().after(moment))
				isDeadlineFuture = true;

			result.addObject("isDeadlineFuture", isDeadlineFuture);
			try {
				auditorPrincipal = this.auditorService.findByPrincipal();
			} catch (final Exception e1) {
				auditorPrincipal = null;
			}

			try {
				principal = this.companyService.findByPrincipal();
			} catch (final Exception e1) {
				principal = null;
			}

			try {
				rookiePrincipal = this.rookieService.findByPrincipal();
				result.addObject("noCurriculum", false);
				final Collection<Curriculum> curriculum = this.curriculumService.findOriginalByRookiePrincipal();
				if (curriculum.isEmpty())
					result.addObject("noCurriculum", true);
			} catch (final Exception e1) {
				rookiePrincipal = null;
			}

			if (principal != null && principal.equals(position.getCompany())) {
				position = this.positionService.findOne(positionId);
				problemList = position.getProblems();
				hasProblem = this.positionService.hasProblem(position);

				result.addObject("hasProblem", hasProblem);
				result.addObject("principal", principal);
				result.addObject("problemList", problemList);

			} else if (rookiePrincipal != null) {

				position = this.positionService.findOne(positionId);
				rookiePrincipal = this.rookieService.findByPrincipal();
				isApplied = this.applicationService.isApplied(position, rookiePrincipal);
				result.addObject("isApplied", isApplied);

			} else if (auditorPrincipal != null) {

				isAuditable = this.auditService.isAuditable(position, auditorPrincipal);
				result.addObject("isAuditable", isAuditable);
			}

			else
				position = this.positionService.findOneToDisplay(positionId);

			audits = this.auditService.findByPosition(position);
			sponsorship = this.sponsorshipService.getRandomSponsorship(positionId);

			result.addObject("audits", audits);
			result.addObject("sponsorship", sponsorship);
			result.addObject("position", position);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}
}

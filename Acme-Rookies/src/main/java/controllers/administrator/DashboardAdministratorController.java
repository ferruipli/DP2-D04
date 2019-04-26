
package controllers.administrator;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import services.CurriculumService;
import services.FinderService;
import services.HackerService;
import services.PositionService;
import controllers.AbstractController;
import domain.Company;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping("dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ------------------

	@Autowired
	private PositionService		positionService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private FinderService		finderService;


	// Constructors --------------
	public DashboardAdministratorController() {
		super();
	}

	// methods --------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final Locale locale) {
		final ModelAndView result;

		result = new ModelAndView("dashboard/display");

		// LEVEL C -----------------------------------------

		// Req 11.2.1
		Double[] findDataNumberPositionsPerCompany;
		findDataNumberPositionsPerCompany = this.positionService.findDataNumberPositionsPerCompany();
		result.addObject("findDataNumberPositionsPerCompany", findDataNumberPositionsPerCompany);

		// Req 11.2.2
		Double[] findDataNumberApplicationPerHacker;
		findDataNumberApplicationPerHacker = this.applicationService.findDataNumberApplicationPerHacker();
		result.addObject("findDataNumberApplicationPerHacker", findDataNumberApplicationPerHacker);

		// Req 11.2.3
		Collection<Company> findCompaniesOfferedMorePositions;
		findCompaniesOfferedMorePositions = this.companyService.findCompaniesOfferedMorePositions();
		result.addObject("findCompaniesOfferedMorePositions", findCompaniesOfferedMorePositions);

		// Req 11.2.4
		Collection<Hacker> findHackersWithMoreApplications;
		findHackersWithMoreApplications = this.hackerService.findHackersWithMoreApplications();
		result.addObject("findHackersWithMoreApplications", findHackersWithMoreApplications);

		// Req 11.2.5
		Double[] dataSalaryOffered;
		dataSalaryOffered = this.positionService.findDataSalaryOffered();
		result.addObject("dataSalaryOffered", dataSalaryOffered);

		// Req 11.2.6
		final List<Position> dataPositionsBestWorstSalary;
		dataPositionsBestWorstSalary = this.positionService.findPositionsBestWorstSalary();
		result.addObject("dataPositionsBestWorstSalary", dataPositionsBestWorstSalary);

		// LEVEL B --------------------------------------

		// Req 18.1.1
		Double[] findDataNumberCurriculumPerHacker;
		findDataNumberCurriculumPerHacker = this.curriculumService.findDataNumberCurriculumPerHacker();
		result.addObject("findDataNumberCurriculumPerHacker", findDataNumberCurriculumPerHacker);

		// Req 18.1.2
		Double[] findDataNumberResultsFinder;
		findDataNumberResultsFinder = this.finderService.findDataNumberResultsFinder();
		result.addObject("findDataNumberResultsFinder", findDataNumberResultsFinder);

		// Req 18.1.3
		Double findRatioEmptyVsNonEmpty;
		findRatioEmptyVsNonEmpty = this.finderService.findRatioEmptyVsNonEmpty();
		result.addObject("findRatioEmptyVsNonEmpty", findRatioEmptyVsNonEmpty);

		return result;
	}
}

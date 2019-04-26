
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.HackerService;
import services.PositionService;
import controllers.AbstractController;
import domain.Application;
import domain.Position;

@Controller
@RequestMapping(value = "/application/company")
public class ApplicationCompanyController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private HackerService		hackerService;


	// Constructors -----------------------------------------------------------

	public ApplicationCompanyController() {
		super();
	}

	// Request List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int positionId) {
		ModelAndView result;
		final Collection<Application> submittedApplications;
		Collection<Application> acceptedApplications;
		Collection<Application> rejectedApplications;
		Position checkPosition;

		try {
			checkPosition = this.positionService.findOneFinalByPrincipal(positionId);
			submittedApplications = this.applicationService.findSubmittedApplicationsByPosition(positionId);
			acceptedApplications = this.applicationService.findAcceptedApplicationsByPosition(positionId);
			rejectedApplications = this.applicationService.findRejectedApplicationsByPosition(positionId);

			result = new ModelAndView("application/list");
			result.addObject("submittedApplications", submittedApplications);
			result.addObject("acceptedApplications", acceptedApplications);
			result.addObject("rejectedApplications", rejectedApplications);

			result.addObject("requestURI", "application/company/list.do?positionId=" + checkPosition.getId());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	//Accept
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		try {
			application = this.applicationService.findOneToCompany(applicationId);

			try {
				this.applicationService.acceptedApplication(application);
				result = new ModelAndView("redirect:../../application/company/list.do?positionId=" + application.getPosition().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	//Reject
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		try {
			application = this.applicationService.findOneToCompany(applicationId);

			try {
				this.applicationService.rejectedApplication(application);
				result = new ModelAndView("redirect:../../application/company/list.do?positionId=" + application.getPosition().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;
		int hackerId;

		hackerId = this.hackerService.findByPrincipal().getId();

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("messageCode", messageCode);
		result.addObject("hackerId", hackerId);

		return result;

	}
}

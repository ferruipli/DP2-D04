
package controllers.companyRookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import controllers.AbstractController;
import domain.Application;

@Controller
@RequestMapping(value = "/application/company,rookie")
public class ApplicationCompanyRookieController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------

	public ApplicationCompanyRookieController() {
		super();
	}

	// Application Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		String rolActor;
		boolean existAnswer;
		existAnswer = true;

		try {
			result = new ModelAndView("application/display");
			if (LoginService.getPrincipal().getAuthorities().toString().equals("[ROOKIE]")) {
				application = this.applicationService.findOneToRookie(applicationId);
				rolActor = "rookie";
				if (application.getAnswer() == null)
					existAnswer = false;
			} else {
				application = this.applicationService.findOneToCompany(applicationId);
				rolActor = "company";
				if (application.getAnswer() == null)
					existAnswer = false;
			}
			result.addObject("application", application);
			result.addObject("rolActor", rolActor);
			result.addObject("existAnswer", existAnswer);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

}

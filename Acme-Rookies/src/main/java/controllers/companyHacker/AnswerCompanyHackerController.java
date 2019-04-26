
package controllers.companyHacker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AnswerService;
import services.ApplicationService;
import controllers.AbstractController;
import domain.Answer;
import domain.Application;

@Controller
@RequestMapping(value = "/answer/company,hacker")
public class AnswerCompanyHackerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private AnswerService		answerService;


	// Constructors -----------------------------------------------------------

	public AnswerCompanyHackerController() {
		super();
	}

	// Answer Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int answerId) {
		ModelAndView result;
		final Application application;
		final Answer answer;
		String rolActor;

		try {
			result = new ModelAndView("answer/display");
			application = this.applicationService.findApplicationByAnswer(answerId);
			if (LoginService.getPrincipal().getAuthorities().toString().equals("[HACKER]")) {
				answer = this.answerService.findOneToHackerDisplay(answerId);
				rolActor = "hacker";
			} else {
				answer = this.answerService.findOneToCompanyDisplay(answerId);
				rolActor = "company";
			}
			result.addObject("answer", answer);
			result.addObject("application", application);
			result.addObject("rolActor", rolActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}
}

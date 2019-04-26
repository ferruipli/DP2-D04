
package controllers.companyHacker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.PositionService;
import services.ProblemService;
import services.UtilityService;
import controllers.AbstractController;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping(value = "/problem/company,hacker")
public class ProblemCompanyHackerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private UtilityService	utilityService;


	// Constructor ------------------------------------

	public ProblemCompanyHackerController() {
		super();
	}

	// Display------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int problemId) {
		ModelAndView result;
		Problem problem;
		UserAccount userPrincipal;
		final Collection<Position> positionsList;
		Collection<String> attachments;

		try {

			result = new ModelAndView("problem/display");
			userPrincipal = LoginService.getPrincipal();

			if (userPrincipal.getAuthorities().toString().equals("[COMPANY]")) {
				problem = this.problemService.findOneToPrincipal(problemId);
				positionsList = this.positionService.findPositionsByProblem(problem);
				attachments = this.utilityService.getSplittedString(problem.getAttachments());

				result.addObject("attachments", attachments);
				result.addObject("problem", problem);
				result.addObject("positionsList", positionsList);
			} else if (userPrincipal.getAuthorities().toString().equals("[HACKER]")) {
				problem = this.problemService.findOneToDisplayHacker(problemId);
				positionsList = this.positionService.findPositionsByProblem(problem);
				attachments = this.utilityService.getSplittedString(problem.getAttachments());

				result.addObject("problem", problem);
				result.addObject("positionsList", positionsList);
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}
}

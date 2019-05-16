
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ProblemService;
import controllers.AbstractController;
import domain.Problem;

@Controller
@RequestMapping(value = "/problem/company")
public class ProblemCompanyController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ProblemService	problemService;


	// Constructor ------------------------------------

	public ProblemCompanyController() {
		super();
	}

	// List------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Problem> problems;

		try {
			result = new ModelAndView("problem/list");
			problems = this.problemService.findProblemsByPrincipal();

			result.addObject("problems", problems);
			result.addObject("requestURI", "problem/company/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}
	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Problem problem;

		problem = this.problemService.create();

		result = this.createEditModelAndView(problem);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int problemId) {
		ModelAndView result;
		Problem problem;

		try {
			problem = this.problemService.findOneToEditDelete(problemId);

			result = this.createEditModelAndView(problem);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Problem problem, final BindingResult binding) {
		ModelAndView result;
		Problem problemRec;

		problemRec = this.problemService.reconstruct(problem, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(problem);
		else
			try {

				this.problemService.save(problemRec);
				result = new ModelAndView("redirect:../company/list.do");
			} catch (final DataIntegrityViolationException e1) {
				result = this.createEditModelAndView(problem, "problem.commit.url");
			}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(problem, "problem.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Problem problem, final BindingResult binding) {
		ModelAndView result;
		Problem problemBbdd;

		try {
			problemBbdd = this.problemService.findOneToEditDelete(problem.getId());

			this.problemService.delete(problemBbdd);
			result = new ModelAndView("redirect:../company/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(problem, "problem.delete.error");
		}

		return result;
	}

	@RequestMapping(value = "/makeFinal", method = RequestMethod.GET)
	public ModelAndView makeFinal(@RequestParam final int problemId, final RedirectAttributes redir) {
		ModelAndView result;
		Problem problem;

		problem = this.problemService.findOne(problemId);

		try {
			this.problemService.makeFinal(problem);
		} catch (final Throwable oops) {
			redir.addFlashAttribute("messageCode", "problem.make.final.error");
		}

		result = new ModelAndView("redirect:/problem/company/list.do");

		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final Problem problem) {
		ModelAndView result;

		result = this.createEditModelAndView(problem, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Problem problem, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("problem/edit");
		result.addObject("problem", problem);
		result.addObject("messageCode", messageCode);

		return result;

	}

}

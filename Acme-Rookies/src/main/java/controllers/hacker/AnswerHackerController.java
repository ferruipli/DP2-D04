
package controllers.hacker;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import services.ApplicationService;
import controllers.AbstractController;
import domain.Answer;
import domain.Application;

@Controller
@RequestMapping(value = "/answer/hacker")
public class AnswerHackerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private AnswerService		answerService;


	// Constructors -----------------------------------------------------------

	public AnswerHackerController() {
		super();
	}

	// Request create -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		ModelAndView result;

		try {
			final Answer answer;

			answer = this.answerService.create(applicationId);

			result = this.createEditModelAndView(answer, applicationId);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:../../error.do");
		}
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int answerId) {
		ModelAndView result;
		Answer answer;

		try {

			answer = this.answerService.findOne(answerId);
			result = this.createEditModelAndView(answer);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Answer answer, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		Integer applicationId;
		String paramApplicationId;
		final Application application;

		paramApplicationId = request.getParameter("applicationId");
		applicationId = paramApplicationId.isEmpty() ? null : Integer.parseInt(paramApplicationId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(answer, applicationId);
		else
			try {
				application = this.applicationService.findOneToHacker(applicationId);
				this.answerService.save(answer, application);
				result = new ModelAndView("redirect:../../application/hacker/list.do");
			}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(answer, applicationId, "answer.commit.error");
			}

		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final Answer answer) {
		ModelAndView result;

		result = this.createEditModelAndView(answer, null, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Answer answer, final int applicationId) {
		ModelAndView result;

		result = this.createEditModelAndView(answer, applicationId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Answer answer, final Integer applicationId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("answer/edit");
		result.addObject("messageCode", messageCode);
		result.addObject("answer", answer);
		result.addObject("applicationId", applicationId);
		return result;

	}
}

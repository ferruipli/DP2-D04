
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

import services.CurriculumService;
import services.PositionDataService;
import controllers.AbstractController;
import domain.PositionData;

@Controller
@RequestMapping("positionData/hacker/")
public class PositionDataHackerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public PositionDataHackerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		PositionData positionData;

		positionData = this.positionDataService.create();
		result = this.createEditModelAndView(positionData, curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionDataId) {
		ModelAndView result;
		PositionData positionData;

		try {
			positionData = this.positionDataService.findOneToEdit(positionDataId);
			result = this.createEditModelAndView(positionData);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PositionData positionData, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		PositionData saved;
		String paramCurriculumId;
		Integer curriculumId;
		String messageError;

		paramCurriculumId = request.getParameter("curriculumId");
		curriculumId = paramCurriculumId.isEmpty() ? null : Integer.parseInt(paramCurriculumId);
		if (binding.hasErrors())
			result = this.createEditModelAndView(positionData, curriculumId);
		else
			try {
				if (curriculumId == null)
					saved = this.positionDataService.save(positionData);
				else
					saved = this.positionDataService.save(positionData, curriculumId);
				result = new ModelAndView("redirect:backCurriculum.do?positionDataId=" + saved.getId());
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Incorrect dates"))
					messageError = "positionData.date.error";
				else
					messageError = "positionData.commit.error";
				result = this.createEditModelAndView(positionData, curriculumId, messageError);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PositionData positionData, final BindingResult binding) {
		ModelAndView result;
		int positionDataId;

		positionDataId = positionData.getId();
		try {
			result = this.back(positionDataId);
			this.positionDataService.delete(positionData);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(positionData, null, "positionData.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/backCurriculum", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int positionDataId) {
		ModelAndView result;
		int curriculumId;

		curriculumId = this.curriculumService.findIdByPositionDataId(positionDataId);
		result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PositionData positionData) {
		ModelAndView result;

		result = this.createEditModelAndView(positionData, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PositionData positionData, final Integer curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(positionData, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PositionData positionData, final Integer curriculumId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("positionData/edit");
		result.addObject("positionData", positionData);
		result.addObject("curriculumId", curriculumId);
		result.addObject("messageCode", messageCode);

		return result;
	}

}

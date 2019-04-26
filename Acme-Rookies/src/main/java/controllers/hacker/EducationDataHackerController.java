
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
import services.EducationDataService;
import controllers.AbstractController;
import domain.EducationData;

@Controller
@RequestMapping("educationData/hacker/")
public class EducationDataHackerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private CurriculumService		curriculumService;


	// Constructors -----------------------------------------------------------

	public EducationDataHackerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		EducationData educationData;

		educationData = this.educationDataService.create();
		result = this.createEditModelAndView(educationData, curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationDataId) {
		ModelAndView result;
		EducationData educationData;

		try {
			educationData = this.educationDataService.findOneToEdit(educationDataId);
			result = this.createEditModelAndView(educationData);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationData educationData, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		EducationData saved;
		String paramCurriculumId;
		Integer curriculumId;
		String messageError;

		paramCurriculumId = request.getParameter("curriculumId");
		curriculumId = paramCurriculumId.isEmpty() ? null : Integer.parseInt(paramCurriculumId);
		if (binding.hasErrors())
			result = this.createEditModelAndView(educationData, curriculumId);
		else
			try {
				if (curriculumId == null)
					saved = this.educationDataService.save(educationData);
				else
					saved = this.educationDataService.save(educationData, curriculumId);
				result = new ModelAndView("redirect:backCurriculum.do?educationDataId=" + saved.getId());
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Incorrect dates"))
					messageError = "educationData.date.error";
				else
					messageError = "educationData.commit.error";
				result = this.createEditModelAndView(educationData, curriculumId, messageError);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationData educationData, final BindingResult binding) {
		ModelAndView result;
		int educationDataId;

		educationDataId = educationData.getId();
		try {
			result = this.back(educationDataId);
			this.educationDataService.delete(educationData);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationData, null, "educationData.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/backCurriculum", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int educationDataId) {
		ModelAndView result;
		int curriculumId;

		curriculumId = this.curriculumService.findIdByEducationDataId(educationDataId);
		result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationData educationData) {
		ModelAndView result;

		result = this.createEditModelAndView(educationData, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationData educationData, final Integer curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(educationData, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationData educationData, final Integer curriculumId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("educationData/edit");
		result.addObject("educationData", educationData);
		result.addObject("curriculumId", curriculumId);
		result.addObject("messageCode", messageCode);

		return result;
	}

}

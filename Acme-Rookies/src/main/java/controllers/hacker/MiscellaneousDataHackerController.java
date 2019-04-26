
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
import services.MiscellaneousDataService;
import controllers.AbstractController;
import domain.MiscellaneousData;

@Controller
@RequestMapping("miscellaneousData/hacker/")
public class MiscellaneousDataHackerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private CurriculumService			curriculumService;


	// Constructors -----------------------------------------------------------

	public MiscellaneousDataHackerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		MiscellaneousData miscellaneousData;

		miscellaneousData = this.miscellaneousDataService.create();
		result = this.createEditModelAndView(miscellaneousData, curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousDataId) {
		ModelAndView result;
		MiscellaneousData miscellaneousData;

		try {
			miscellaneousData = this.miscellaneousDataService.findOneToEdit(miscellaneousDataId);
			result = this.createEditModelAndView(miscellaneousData);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousData miscellaneousData, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		MiscellaneousData saved;
		String paramCurriculumId;
		Integer curriculumId;

		paramCurriculumId = request.getParameter("curriculumId");
		curriculumId = paramCurriculumId.isEmpty() ? null : Integer.parseInt(paramCurriculumId);
		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousData, curriculumId);
		else
			try {
				if (curriculumId == null)
					saved = this.miscellaneousDataService.save(miscellaneousData);
				else
					saved = this.miscellaneousDataService.save(miscellaneousData, curriculumId);
				result = new ModelAndView("redirect:backCurriculum.do?miscellaneousDataId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousData, curriculumId, "miscellaneousData.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousData miscellaneousData, final BindingResult binding) {
		ModelAndView result;
		int miscellaneousDataId;

		miscellaneousDataId = miscellaneousData.getId();
		try {
			result = this.back(miscellaneousDataId);
			this.miscellaneousDataService.delete(miscellaneousData);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousData, null, "miscellaneousData.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/backCurriculum", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int miscellaneousDataId) {
		ModelAndView result;
		int curriculumId;

		curriculumId = this.curriculumService.findIdByMiscellaneousDataId(miscellaneousDataId);
		result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousData miscellaneousData) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousData, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousData miscellaneousData, final Integer curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousData, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousData miscellaneousData, final Integer curriculumId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("miscellaneousData/edit");
		result.addObject("miscellaneousData", miscellaneousData);
		result.addObject("curriculumId", curriculumId);
		result.addObject("messageCode", messageCode);

		return result;
	}

}

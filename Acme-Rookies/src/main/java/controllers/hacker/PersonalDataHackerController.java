
package controllers.hacker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.PersonalDataService;
import controllers.AbstractController;
import domain.PersonalData;

@Controller
@RequestMapping("personalData/hacker/")
public class PersonalDataHackerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PersonalDataService	personalDataService;

	@Autowired
	private CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public PersonalDataHackerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView result;
		PersonalData personalData;

		try {
			personalData = this.personalDataService.findOneToEdit(personalDataId);
			result = this.editModelAndView(personalData);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalData personalData, final BindingResult binding) {
		ModelAndView result;
		PersonalData saved;

		if (binding.hasErrors())
			result = this.editModelAndView(personalData);
		else
			try {
				saved = this.personalDataService.save(personalData);
				result = new ModelAndView("redirect:backCurriculum.do?personalDataId=" + saved.getId());
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Fullname does not match"))
					binding.rejectValue("fullname", "personalData.fullname.error", "Must match with the full name of your profile.");
				else if (oops.getMessage().contains("Not in github"))
					binding.rejectValue("githubProfile", "personalData.githubProfile.error", "The url does not belong to the domain of GitHub");
				else if (oops.getMessage().contains("Not in linkedin"))
					binding.rejectValue("linkedInProfile", "personalData.linkedInProfile.error", "The url does not belong to the domain of LinkedIn");

				result = this.editModelAndView(personalData, "personalData.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/backCurriculum", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int personalDataId) {
		ModelAndView result;
		int curriculumId;

		curriculumId = this.curriculumService.findIdByPersonalDataId(personalDataId);
		result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView editModelAndView(final PersonalData personalData) {
		ModelAndView result;

		result = this.editModelAndView(personalData, null);

		return result;
	}

	protected ModelAndView editModelAndView(final PersonalData personalData, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("personalData/edit");
		result.addObject("personalData", personalData);
		result.addObject("messageCode", messageCode);

		return result;
	}

}

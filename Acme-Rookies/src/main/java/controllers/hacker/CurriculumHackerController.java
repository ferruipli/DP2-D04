
package controllers.hacker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import controllers.AbstractController;
import domain.Curriculum;

@Controller
@RequestMapping("curriculum/hacker/")
public class CurriculumHackerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public CurriculumHackerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Curriculum> curriculums;

		curriculums = this.curriculumService.findOriginalByHackerPrincipal();

		result = new ModelAndView("curriculum/list");
		result.addObject("requestURI", "curriculum/hacker/list.do");
		result.addObject("curriculums", curriculums);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.create();
		result = this.createModelAndView(curriculum);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;
		Curriculum curriculumRec, saved;

		curriculumRec = this.curriculumService.reconstruct(curriculum, binding);
		if (binding.hasErrors())
			if (curriculum.getId() == 0)
				result = this.createModelAndView(curriculum);
			else
				result = this.editModelAndView(curriculum);
		else
			try {
				saved = this.curriculumService.save(curriculumRec);
				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + saved.getId());
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Fullname does not match"))
					binding.rejectValue("personalData.fullname", "curriculum.fullname.error", "Must match with the full name of your profile.");
				else if (oops.getMessage().contains("Not in github"))
					binding.rejectValue("personalData.githubProfile", "curriculum.githubProfile.error", "The url does not belong to the domain of GitHub");
				else if (oops.getMessage().contains("Not in linkedin"))
					binding.rejectValue("personalData.linkedInProfile", "curriculum.linkedInProfile.error", "The url does not belong to the domain of LinkedIn");

				if (curriculum.getId() == 0)
					result = this.createModelAndView(curriculum, "curriculum.commit.error");
				else
					result = this.editModelAndView(curriculum, "curriculum.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;
		Curriculum curriculumStored;

		curriculumStored = this.curriculumService.findOne(curriculum.getId());
		try {
			this.curriculumService.delete(curriculumStored);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(curriculum, "curriculum.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculumId) {
		ModelAndView result;
		Curriculum curriculum;

		try {
			curriculum = this.curriculumService.findOneToEdit(curriculumId);
			result = this.editModelAndView(curriculum);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int curriculumId) {
		ModelAndView result;
		Curriculum curriculum;

		try {
			curriculum = this.curriculumService.findOneToEdit(curriculumId);
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createModelAndView(curriculum, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Curriculum curriculum, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/create");
		result.addObject("curriculum", curriculum);
		result.addObject("messageCode", messageCode);

		return result;
	}

	protected ModelAndView editModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.editModelAndView(curriculum, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Curriculum curriculum, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("messageCode", messageCode);

		return result;
	}

}

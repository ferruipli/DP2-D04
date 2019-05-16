
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import domain.Curriculum;

@Controller
@RequestMapping("curriculum/")
public class CurriculumController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public CurriculumController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculumId) {
		ModelAndView result;
		Curriculum curriculum;
		boolean isOwner;

		curriculum = this.curriculumService.findOne(curriculumId);
		isOwner = this.curriculumService.checkIsOwner(curriculum);

		result = new ModelAndView("curriculum/display");
		result.addObject("requestURI", "curriculum/display.do");
		result.addObject("curriculum", curriculum);
		result.addObject("isOwner", isOwner);

		return result;
	}

}

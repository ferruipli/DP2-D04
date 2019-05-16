
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EducationDataService;
import domain.EducationData;

@Controller
@RequestMapping("educationData/")
public class EducationDataController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EducationDataService	educationDataService;


	// Constructors -----------------------------------------------------------

	public EducationDataController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int educationDataId) {
		ModelAndView result;
		EducationData educationData;

		educationData = this.educationDataService.findOne(educationDataId);

		result = new ModelAndView("educationData/display");
		result.addObject("educationData", educationData);

		return result;
	}

}

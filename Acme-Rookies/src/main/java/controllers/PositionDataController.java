
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionDataService;
import domain.PositionData;

@Controller
@RequestMapping("positionData/")
public class PositionDataController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PositionDataService	positionDataService;


	// Constructors -----------------------------------------------------------

	public PositionDataController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int positionDataId) {
		ModelAndView result;
		PositionData positionData;

		positionData = this.positionDataService.findOne(positionDataId);

		result = new ModelAndView("positionData/display");
		result.addObject("positionData", positionData);

		return result;
	}

}

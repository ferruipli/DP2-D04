
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MiscellaneousDataService;
import services.UtilityService;
import domain.MiscellaneousData;

@Controller
@RequestMapping("miscellaneousData/")
public class MiscellaneousDataController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private UtilityService				utilityService;


	// Constructors -----------------------------------------------------------

	public MiscellaneousDataController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int miscellaneousDataId) {
		ModelAndView result;
		MiscellaneousData miscellaneousData;
		List<String> attachments;

		miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
		attachments = this.utilityService.getSplittedString(miscellaneousData.getAttachments());

		result = new ModelAndView("miscellaneousData/display");
		result.addObject("miscellaneousData", miscellaneousData);
		result.addObject("attachments", attachments);

		return result;
	}

}

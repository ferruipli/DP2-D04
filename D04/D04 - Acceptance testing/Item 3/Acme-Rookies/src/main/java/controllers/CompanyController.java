
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import domain.Company;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends AbstractController {

	// Services

	@Autowired
	private CompanyService	companyService;


	// Constructor

	public CompanyController() {
		super();
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Company> companies;

		result = new ModelAndView("actor/list");

		companies = this.companyService.findAll();

		result.addObject("actors", companies);
		result.addObject("requestURI", "company/list.do");

		return result;
	}

}

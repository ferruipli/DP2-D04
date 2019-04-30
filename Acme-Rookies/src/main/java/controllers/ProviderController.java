
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProviderService;
import domain.Provider;

@Controller
@RequestMapping(value = "/provider")
public class ProviderController extends AbstractController {

	// Servcies

	@Autowired
	private ProviderService	providerService;


	// Constructor

	public ProviderController() {
		super();
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Provider> providers;

		result = new ModelAndView("actor/list");

		providers = this.providerService.findAll();

		result.addObject("actors", providers);
		result.addObject("requestURI", "provider/list.do");

		return result;
	}

}

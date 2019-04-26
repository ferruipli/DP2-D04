
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.HackerService;
import domain.Company;
import domain.Hacker;
import forms.RegistrationForm;

@Controller
@RequestMapping(value = "/actor")
public class ActorController extends ActorAbstractController {

	// Services

	@Autowired
	private HackerService	hackerService;

	@Autowired
	private CompanyService	companyService;


	// Constructor

	public ActorController() {
		super();
	}

	// Display

	@Override
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) final Integer actorId) {
		ModelAndView result;

		try {
			result = super.display(actorId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	// Register Company

	@RequestMapping(value = "/registerCompany", method = RequestMethod.GET)
	public ModelAndView createCompany() {
		ModelAndView result;
		String rol;
		Company company;

		rol = "Company";
		company = new Company();
		result = this.createModelAndView(company);
		result.addObject("rol", rol);
		result.addObject("urlAdmin", "");

		return result;
	}

	@RequestMapping(value = "/registerCompany", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCompany(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Company company;

		company = this.companyService.reconstruct(registrationForm, binding);

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Company");
		} else
			try {
				this.companyService.save(company);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("Expired credit card"))
					result = this.createModelAndView(registrationForm, "expired.creditCard");
				else
					result = this.createModelAndView(registrationForm, "actor.registration.error");
				result.addObject("rol", "Company");
			}

		return result;
	}

	// Register Hacker

	@RequestMapping(value = "/registerHacker", method = RequestMethod.GET)
	public ModelAndView createHacker() {
		ModelAndView result;
		String rol;
		Hacker hacker;

		rol = "Hacker";
		hacker = new Hacker();
		result = this.createModelAndView(hacker);
		result.addObject("rol", rol);
		result.addObject("urlAdmin", "");

		return result;
	}

	@RequestMapping(value = "/registerHacker", method = RequestMethod.POST, params = "save")
	public ModelAndView saveHacker(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Hacker hacker;

		hacker = this.hackerService.reconstruct(registrationForm, binding);

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Hacker");
		} else
			try {
				this.hackerService.save(hacker);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("Expired credit card"))
					result = this.createModelAndView(registrationForm, "expired.creditCard");
				else
					result = this.createModelAndView(registrationForm, "actor.registration.error");
				result.addObject("rol", "Hacker");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createModelAndView(final Company company) {
		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.companyService.createForm(company);

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Hacker hacker) {
		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.hackerService.createForm(hacker);

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final RegistrationForm registrationForm) {
		ModelAndView result;

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final RegistrationForm registrationForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/singup");
		result.addObject("registrationForm", registrationForm);
		result.addObject("messageCode", messageCode);

		return result;
	}

}

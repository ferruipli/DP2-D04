
package controllers.authenticated;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.CompanyService;
import services.HackerService;
import controllers.ActorAbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Hacker;
import forms.RegistrationForm;

@Controller
@RequestMapping(value = "/actor/administrator,company,hacker")
public class ActorMultiUserController extends ActorAbstractController {

	// Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private ActorService			actorService;


	// Constructor

	public ActorMultiUserController() {
		super();
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int actorId) {
		ModelAndView result;
		Administrator administrator;
		Company company;
		final Hacker hacker;
		Actor actor;

		result = new ModelAndView();

		try {
			actor = this.actorService.findOneToDisplayEdit(actorId);
			if (actor instanceof Administrator) {
				administrator = this.administratorService.findOneToDisplayEdit(actorId);
				result = this.createModelAndView(administrator);
				result.addObject("rol", "Administrator");
			} else if (actor instanceof Company) {
				company = this.companyService.findOneToDisplayEdit(actorId);
				result = this.createModelAndView(company);
				result.addObject("rol", "Company");
			} else if (actor instanceof Hacker) {
				hacker = this.hackerService.findOneToDisplayEdit(actorId);
				result = this.createModelAndView(hacker);
				result.addObject("rol", "Hacker");
			}

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAdmin")
	public ModelAndView saveAdministrator(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.reconstruct(registrationForm, binding);

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Administrator");
		} else
			try {
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Administrator");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteAdmin")
	public ModelAndView deleteAdministrator(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
		Administrator administrator;
		ModelAndView result;

		administrator = this.administratorService.findOneToDisplayEdit(registrationForm.getId());

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Administrator");
		} else
			try {
				this.administratorService.delete(administrator);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Administrator");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCompany")
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
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Company");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteCompany")
	public ModelAndView deleteCompany(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
		ModelAndView result;
		Company company;

		company = this.companyService.findOneToDisplayEdit(registrationForm.getId());

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Company");
		} else
			try {
				this.companyService.delete(company);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Company");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveHacker")
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
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Hacker");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteHacker")
	public ModelAndView deleteHacker(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
		ModelAndView result;
		Hacker hacker;

		hacker = this.hackerService.findOneToDisplayEdit(registrationForm.getId());

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Hacker");
		} else
			try {
				this.hackerService.delete(hacker);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Hacker");
			}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createModelAndView(final Hacker hacker) {
		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.hackerService.createForm(hacker);

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Company company) {
		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.companyService.createForm(company);

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Administrator administrator) {
		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.administratorService.createForm(administrator);

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

		result = new ModelAndView("actor/edit");
		result.addObject("registrationForm", registrationForm);
		result.addObject("messageCode", messageCode);

		return result;
	}

}

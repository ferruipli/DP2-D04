
package controllers.authenticated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;
import domain.CreditCard;
import forms.CreditCardForm;

@Controller
@RequestMapping(value = "/creditCard/administrator,company,hacker")
public class CreditCardMultiUserController extends AbstractController {

	// Services

	@Autowired
	private ActorService	actorService;


	// Constructor

	public CreditCardMultiUserController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int actorId) {
		ModelAndView result;
		CreditCard creditCard;
		Actor actor;

		try {
			actor = this.actorService.findOneToDisplayEdit(actorId);
			creditCard = actor.getCreditCard();

			result = this.createModelAndView(creditCard);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CreditCardForm creditCardForm, final BindingResult binding) {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.actorService.reconstructCreditCard(creditCardForm, binding);

		if (binding.hasErrors())
			result = this.createModelAndView(creditCardForm);
		else
			try {
				this.actorService.updateCreditCard(creditCard);
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("Expired credit card"))
					result = this.createModelAndView(creditCardForm, "expired.creditCard");
				else
					result = this.createModelAndView(creditCardForm, "creditCard.create.error");
			}

		return result;
	}

	// Ancillary methods --------------------------
	protected ModelAndView createModelAndView(final CreditCard creditCard) {
		ModelAndView result;
		CreditCardForm creditCardForm;

		creditCardForm = this.actorService.createCreditCardForm(creditCard);

		result = this.createModelAndView(creditCardForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final CreditCardForm creditCardForm) {
		ModelAndView result;

		result = this.createModelAndView(creditCardForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final CreditCardForm creditCardForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("creditCard/edit");
		result.addObject("creditCardForm", creditCardForm);
		result.addObject("messageCode", messageCode);

		return result;

	}

}

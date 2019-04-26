
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MessageService;
import controllers.AbstractController;
import domain.Message;

@Controller
@RequestMapping("/message/administrator")
public class MessageAdministratorController extends AbstractController {

	@Autowired
	private MessageService	messageService;


	// Constructors -----------------------------------------------------------
	public MessageAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message broadcast;

		broadcast = this.messageService.createBroadcast();

		result = this.broadcastModelAndView(broadcast);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "send")
	public ModelAndView broadcast(final Message broadcast, final BindingResult binding) {
		ModelAndView result;
		Message broadcastRec;

		broadcastRec = this.messageService.reconstruct(broadcast, binding);
		if (binding.hasErrors())
			result = this.broadcastModelAndView(broadcast);
		else
			try {
				this.messageService.sendBroadcast(broadcastRec);
				result = new ModelAndView("redirect:/message/administrator,company,rookie/list.do");
			} catch (final Throwable oops) {
				result = this.broadcastModelAndView(broadcastRec, "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "breachNotification", method = RequestMethod.GET)
	public ModelAndView breach() {
		ModelAndView result;

		try {
			this.messageService.breachNotification();
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView broadcastModelAndView(final Message broadcast) {
		ModelAndView result;

		result = this.broadcastModelAndView(broadcast, null);

		return result;
	}

	protected ModelAndView broadcastModelAndView(final Message broadcast, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("message/send");
		result.addObject("message", broadcast);
		result.addObject("isBroadcastMessage", true);
		result.addObject("actionURI", "message/administrator/broadcast.do");
		result.addObject("messageCode", messageCode);

		return result;
	}

}


package controllers.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import controllers.AbstractController;
import domain.Item;

@Controller
@RequestMapping("/item/provider")
public class ItemProviderController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ItemService	itemService;


	// Constructors -----------------------------------------------------------

	public ItemProviderController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Item item;

		try {
			item = this.itemService.create();
			result = this.createEditModelAndView(item);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int itemId) {
		ModelAndView result;
		Item item;

		try {
			item = this.itemService.findOneToProviderEdit(itemId);
			result = this.createEditModelAndView(item);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Item item, final BindingResult binding) {
		ModelAndView result;
		Item itemRec;

		try {
			itemRec = this.itemService.reconstruct(item, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(item);
			else
				try {
					this.itemService.save(itemRec);
					result = new ModelAndView("redirect:../list.do?providerId=" + itemRec.getProvider().getId());
				} catch (final IllegalArgumentException e1) {
					result = this.createEditModelAndView(itemRec, "item.commit.url");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(itemRec, "item.commit.error");

				}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Item item) {
		ModelAndView result;

		try {
			this.itemService.delete(item);
			result = new ModelAndView("redirect:../list.do?providerId=" + item.getProvider().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Item item) {
		ModelAndView result;

		result = this.createEditModelAndView(item, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Item item, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("item/edit");
		result.addObject("item", item);
		result.addObject("messageCode", messageCode);

		return result;
	}

}

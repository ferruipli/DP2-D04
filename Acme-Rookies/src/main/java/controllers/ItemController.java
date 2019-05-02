
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.ProviderService;
import services.UtilityService;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping(value = "/item")
public class ItemController extends AbstractController {

	// Services

	@Autowired
	private ItemService		itemService;

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private UtilityService	utilityService;


	// Constructor

	public ItemController() {
		super();
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int providerId) {
		ModelAndView result;
		Collection<Item> items;
		Provider principal;
		Provider owner;

		try {
			result = new ModelAndView("item/list");
			items = this.itemService.findItemsByProvider(providerId);
			owner = this.providerService.findOne(providerId);

			try {
				principal = this.providerService.findByPrincipal();
			} catch (final Exception e1) {
				principal = null;
			}

			result.addObject("principal", principal);
			result.addObject("owner", owner);
			result.addObject("items", items);
			result.addObject("requestURI", "item/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/allItemsList", method = RequestMethod.GET)
	public ModelAndView allItemsList() {
		ModelAndView result;
		Collection<Item> items;
		Provider principal;

		try {
			result = new ModelAndView("item/list");

			items = this.itemService.findAll();
			try {
				principal = this.providerService.findByPrincipal();
			} catch (final Exception e1) {
				principal = null;
			}
			if (principal != null)
				result.addObject("principal", principal);

			result.addObject("owner", null);
			result.addObject("items", items);
			result.addObject("requestURI", "item/allItemsList.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int itemId) {
		ModelAndView result;
		final Item item;
		Collection<String> pictures;

		try {
			result = new ModelAndView("item/display");
			item = this.itemService.findOne(itemId);
			pictures = this.utilityService.getSplittedString(item.getPicture());

			result.addObject("pictures", pictures);
			result.addObject("item", item);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

}

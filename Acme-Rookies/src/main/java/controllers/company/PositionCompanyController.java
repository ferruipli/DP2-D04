
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CompanyService;
import services.PositionService;
import services.ProblemService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping(value = "/position/company")
public class PositionCompanyController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private PositionService	positionService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private ProblemService	problemService;


	// Constructor ------------------------------------

	public PositionCompanyController() {
		super();
	}

	// List------------------------------------

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Position position;

		position = this.positionService.create();

		result = this.createEditModelAndView(position);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;

		try {
			position = this.positionService.findOneToEditDelete(positionId);

			result = this.createEditModelAndView(position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Position position, final BindingResult binding) {
		ModelAndView result;
		Position positionRec;
		final Company principal;
		positionRec = this.positionService.reconstruct(position, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(position);
		else
			try {

				principal = this.companyService.findByPrincipal();
				this.positionService.save(positionRec);
				result = new ModelAndView("redirect:../list.do?companyId=" + principal.getId());
			} catch (final TransactionSystemException oops) {
				result = new ModelAndView("redirect:../../error.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(position, "position.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position position, final BindingResult binding) {
		ModelAndView result;
		Position positionBbdd;
		int companyId;

		try {

			positionBbdd = this.positionService.findOneToEditDelete(position.getId());
			companyId = positionBbdd.getCompany().getId();

			this.positionService.delete(positionBbdd);
			result = new ModelAndView("redirect:../list.do?companyId=" + companyId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(position, "position.delete.error");
		}

		return result;
	}

	@RequestMapping(value = "/makeFinal", method = RequestMethod.GET)
	public ModelAndView makeFinal(@RequestParam final int positionId, final RedirectAttributes redir) {
		ModelAndView result;
		Position position;

		position = this.positionService.findOne(positionId);

		try {
			this.positionService.makeFinal(position);
		} catch (final Throwable oops) {
			redir.addFlashAttribute("messageCode", "position.make.final.error");
		}

		result = new ModelAndView("redirect:/position/list.do?companyId=" + position.getCompany().getId());

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int positionId, final RedirectAttributes redir) {
		ModelAndView result;
		Position position;

		position = this.positionService.findOne(positionId);

		try {
			this.positionService.cancel(position);
		} catch (final Throwable oops) {
			redir.addFlashAttribute("messageCode", "position.cancel.error");
		}

		result = new ModelAndView("redirect:/position/list.do?companyId=" + position.getCompany().getId());

		return result;
	}
	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final Position position) {
		ModelAndView result;

		result = this.createEditModelAndView(position, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position, final String messageCode) {
		ModelAndView result;
		Company principal;
		Collection<Problem> problems;

		principal = this.companyService.findByPrincipal();
		problems = this.problemService.findFinalByCompany();

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		result.addObject("principal", principal);
		result.addObject("messageCode", messageCode);
		result.addObject("problems", problems);

		return result;

	}
}

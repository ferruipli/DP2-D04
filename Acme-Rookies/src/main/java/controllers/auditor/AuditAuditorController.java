
package controllers.auditor;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.AuditService;
import services.AuditorService;
import services.PositionService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping(value = "/audit/auditor")
public class AuditAuditorController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private AuditService	auditService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private PositionService	positionService;


	// Constructor ------------------------------------

	public AuditAuditorController() {
		super();
	}

	// List------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Audit> audits;
		Auditor principal;

		try {
			result = new ModelAndView("audit/list");
			principal = this.auditorService.findByPrincipal();
			audits = this.auditService.findAuditsByAuditor(principal);

			result.addObject("audits", audits);
			result.addObject("requestURI", "audit/auditor/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}
	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;
		Position position;

		try {
			position = this.positionService.findOne(auditId);
			audit = this.auditService.create(position);

			result = this.createEditModelAndView(audit, auditId);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;

		try {
			audit = this.auditService.findOneToEditDelete(auditId);

			result = this.createEditModelAndView(audit, audit.getPosition().getId());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audit audit, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		Audit auditRec;
		Integer positionId;
		String paramPositionId;
		Position position;

		paramPositionId = request.getParameter("positionId");
		positionId = paramPositionId.isEmpty() ? null : Integer.parseInt(paramPositionId);
		position = this.positionService.findOne(positionId);

		auditRec = this.auditService.reconstruct(audit, position, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(audit, audit.getPosition().getId());
		else
			try {

				this.auditService.save(auditRec);
				result = new ModelAndView("redirect:../audit/auditor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audit, audit.getPosition().getId(), "audit.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		Audit auditBbdd;

		try {
			auditBbdd = this.auditService.findOneToEditDelete(audit.getId());

			this.auditService.delete(auditBbdd);
			result = new ModelAndView("redirect:../../audit/auditor/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audit, audit.getPosition().getId(), "problem.delete.error");
		}

		return result;
	}

	@RequestMapping(value = "/makeFinal", method = RequestMethod.GET)
	public ModelAndView makeFinal(@RequestParam final int auditId, final RedirectAttributes redir) {
		ModelAndView result;
		Audit audit;

		audit = this.auditService.findOne(auditId);

		try {
			this.auditService.makeFinal(audit);
		} catch (final Throwable oops) {
			redir.addFlashAttribute("messageCode", "audit.make.final.error");
		}

		result = new ModelAndView("redirect:/audit/auditor/list.do");

		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final Audit audit, final int positionId) {
		ModelAndView result;

		result = this.createEditModelAndView(audit, positionId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final int positionId, final String messageCode) {
		ModelAndView result;
		Auditor principal;

		principal = this.auditorService.findByPrincipal();

		result = new ModelAndView("audit/edit");
		result.addObject("messageCode", messageCode);
		result.addObject("audit", audit);
		result.addObject("positionId", positionId);
		result.addObject("principalId", principal.getId());

		return result;

	}

}

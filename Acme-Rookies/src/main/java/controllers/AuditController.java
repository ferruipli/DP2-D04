
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.AuditService;
import services.AuditorService;
import services.PositionService;
import domain.Audit;
import domain.Auditor;

@Controller
@RequestMapping(value = "/audit")
public class AuditController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private AuditService	auditService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private PositionService	positionService;


	// Constructor ------------------------------------

	public AuditController() {
		super();
	}

	// Display ------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;
		Auditor principal;
		UserAccount userPrincipal;

		result = new ModelAndView("audit/display");

		try {

			try {
				userPrincipal = LoginService.getPrincipal();
			} catch (final Exception e1) {
				userPrincipal = null;
			}

			audit = this.auditService.findOne(auditId);
			//Is authenticated as auditor and it the owner of the audit
			if (userPrincipal != null && userPrincipal.getAuthorities().toString().equals("[AUDITOR]") && audit.getAuditor().getId() == this.auditorService.findByPrincipal().getId()) {
				principal = this.auditorService.findByPrincipal();
				audit = this.auditService.findOne(auditId);

				result.addObject("isOwner", true);
				result.addObject("principal", principal);
			} else
				audit = this.auditService.findOneToDisplay(auditId);
			result.addObject("audit", audit);

			result.addObject("requestURI", "audit/display.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

}

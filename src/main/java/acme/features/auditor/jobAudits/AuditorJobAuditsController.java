
package acme.features.auditor.jobAudits;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/auditor/job/")
public class AuditorJobAuditsController extends AbstractController<Auditor, Job> {

	@Autowired
	AuditorJobAuditsListService	listService;

	@Autowired
	AuditorJobAuditsShowService	showService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_JOBS_WRITE_AUDIT, BasicCommand.LIST, this.listService);
		super.addCustomCommand(CustomCommand.LIST_NON_JOBS_WRITE_AUDIT, BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}

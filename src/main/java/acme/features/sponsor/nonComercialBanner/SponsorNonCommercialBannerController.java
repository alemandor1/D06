
package acme.features.sponsor.nonComercialBanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.banners.NonCommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/sponsor/non-commercial-banner/")
public class SponsorNonCommercialBannerController extends AbstractController<Sponsor, NonCommercialBanner> {

	@Autowired
	private SponsorNonCommercialBannerListService	listService;

	@Autowired
	private SponsorNonCommercialBannerShowService	showService;

	@Autowired
	private SponsorNonCommercialBannerCreateService	createService;

	@Autowired
	private SponsorNonCommercialBannerDeleteService	deleteService;

	@Autowired
	private SponsorNonCommercialBannerUpdateService	updateService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}

}


package acme.features.authenticated.announcement;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.announcements.Announcement;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedAnnouncementShowService implements AbstractShowService<Authenticated, Announcement> {

	@Autowired
	AuthenticatedAnnouncementRepository repository;


	@Override
	public boolean authorise(final Request<Announcement> request) {
		assert request != null;

		return this.repository.isCorrectAnnouncement(request.getModel().getInteger("id"), Date.valueOf(LocalDate.now().minusMonths(1)));
	}

	@Override
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "link", "description", "moment");

	}

	@Override
	public Announcement findOne(final Request<Announcement> request) {
		assert request != null;
		Announcement result;
		result = this.repository.findOneById(Integer.parseInt(request.getServletRequest().getParameter("id")));
		return result;
	}

}

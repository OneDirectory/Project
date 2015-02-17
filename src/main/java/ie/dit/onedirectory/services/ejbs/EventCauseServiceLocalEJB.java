package ie.dit.onedirectory.services.ejbs;

import ie.dit.onedirectory.dao.EventCauseDAO;
import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.services.EventCauseServiceLocal;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
@Stateless
public class EventCauseServiceLocalEJB implements EventCauseServiceLocal {

	@EJB
	private EventCauseDAO dao;
	
	public void setDao(EventCauseDAO dao){
		this.dao = dao;
	}
	
	public Collection<EventCause> getEventCauses() {
		return dao.getAllEventCauses();
	}

	public void addEventCauses(Collection<EventCause> eventCauseList) {
		dao.addEventCauses(eventCauseList);
	}

}

package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.EventCause;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface EventCauseDAO {

	Collection<EventCause> getAllEventCauses();
	public void addEventCauses(Collection<EventCause> eventCauseList);
}

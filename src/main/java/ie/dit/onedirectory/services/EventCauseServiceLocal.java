package ie.dit.onedirectory.services;

import ie.dit.onedirectory.entities.EventCause;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface EventCauseServiceLocal {
	public Collection<EventCause> getEventCauses();
	public void addEventCauses(Collection<EventCause> eventCauseList);
}

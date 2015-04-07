/**
 * Interface for the data access layer for event causes
 * Returns a collection of all event causes and adding
 * an event cause
 */
package ie.dit.onedirectory.dao;

import ie.dit.onedirectory.entities.EventCause;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface EventCauseDAO {

	Collection<EventCause> getAllEventCauses();
	public void addEventCause(EventCause eventCause);
}

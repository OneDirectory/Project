package ie.dit.onedirectory.entities.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import ie.dit.onedirectory.dao.EventCauseDAO;
import ie.dit.onedirectory.dao.jpa.JPAEventCauseDAO;
import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.entities.pks.EventCauseId;
import ie.dit.onedirectory.rest.EventCauseREST;
import ie.dit.onedirectory.services.EventCauseServiceLocal;
import ie.dit.onedirectory.services.ejbs.EventCauseServiceLocalEJB;
import ie.dit.onedirectory.utilities.DataValidator;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;


@RunWith(Arquillian.class)
public class EventCauseTest {

	@Deployment
	public static Archive<?> createDeployment()
	{		
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, 
						EventCauseServiceLocalEJB.class.getPackage(),
						EventCauseServiceLocal.class.getPackage(),
						EventCauseDAO.class.getPackage(),
						JPAEventCauseDAO.class.getPackage(),
						EventCause.class.getPackage(),
						EventCauseId.class.getPackage(),
						DataValidator.class.getPackage())
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@EJB
	EventCauseServiceLocal service;

	private static String INITIAL_DESCRIPTION = "INITIAL CTXT SETUP-CSFB UNDEFINED MOB FREQ REL";
	private static String UPDATED_DESCRIPTION = "UE CTXT RELEASE-UNKNOWN OR ALREADY ALLOCATED ENB UE S1AP ID";

	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
		startTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

	@Test
	public void EntityTest() throws Exception {

		EventCause ec = new EventCause(13, 47, INITIAL_DESCRIPTION);

		em.persist(ec);

		EventCauseId ecID = new EventCauseId(13, 47); 

		EventCause loadedEC = em.find(EventCause.class, ecID);

		assertEquals("Event Cause Insertion Failed", INITIAL_DESCRIPTION, loadedEC.getDescription());

		loadedEC.setDescription(UPDATED_DESCRIPTION);

		EventCause updatedEC = em.find(EventCause.class, ecID);

		assertEquals("Event Cause Update Failed", UPDATED_DESCRIPTION, loadedEC.getDescription());

		em.remove(updatedEC);

		EventCause shouldBeNull = em.find(EventCause.class, ecID);

		assertNull("Event Cause Failed to delete", shouldBeNull);

	}

	@Test
	public void AddEventCauseTest() throws Exception {
		EventCause ec = new EventCause(13, 47, INITIAL_DESCRIPTION);
		EventCause ec2 = new EventCause(17, 43, UPDATED_DESCRIPTION);

		service.addEventCause(ec);

		assertEquals("EventCauseServiceLocal Failed to Add", service.getEventCauses().size(), 1);

		service.addEventCause(ec2);

		assertEquals("EventCauseServiceLocal Failed to Add", service.getEventCauses().size(), 2);

	}

	@Test
	public void AddEventCausesTest() throws Exception {

		EventCause ec = new EventCause(13, 47, INITIAL_DESCRIPTION);
		EventCause ec2 = new EventCause(17, 43, UPDATED_DESCRIPTION);
		EventCause ec3 = new EventCause(19, 41, UPDATED_DESCRIPTION);
		EventCause ec4 = new EventCause(23, 37, UPDATED_DESCRIPTION);

		Collection<EventCause> eventCauseList = new ArrayList<EventCause>();

		eventCauseList.add(ec);
		eventCauseList.add(ec2);
		eventCauseList.add(ec3);
		eventCauseList.add(ec4);

		service.addEventCauses(eventCauseList);

		assertEquals("EventCauseServiceLocal Failed to Add", service.getEventCauses().size(), 4);
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from EventCause").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}	
	
}

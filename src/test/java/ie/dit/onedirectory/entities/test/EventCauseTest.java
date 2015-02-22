package ie.dit.onedirectory.entities.test;

import static org.junit.Assert.*;

import java.io.IOException;

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
						EventCauseREST.class.getPackage(),
						EventCause.class.getPackage(),
						EventCauseId.class.getPackage())
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		}
	
	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;
	
	private static String INITIAL_DESCRIPTION = "INITIAL CTXT SETUP-CSFB UNDEFINED MOB FREQ REL";
	private static String UPDATED_DESCRIPTION = "UE CTXT RELEASE-UNKNOWN OR ALREADY ALLOCATED ENB UE S1AP ID";
	
	@Before
	public void preparePersistenceTest() throws Exception {
	    clearData();
	   // insertData();
	    startTransaction();
	}
	
	@After
	public void commitTransaction() throws Exception {
	    utx.commit();
	}
	
	@Test
	public void test() throws Exception {
		EventCause ec = new EventCause(13, 47, INITIAL_DESCRIPTION);
		em.persist(ec);

		assertTrue(true);
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

package ie.dit.onedirectory.entities.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import ie.dit.onedirectory.dao.FailureClassDAO;
import ie.dit.onedirectory.dao.jpa.JPAFailureClassDAO;
import ie.dit.onedirectory.entities.FailureClass;
import ie.dit.onedirectory.services.FailureClassServiceLocal;
import ie.dit.onedirectory.services.ejbs.FailureClassServiceLocalEJB;
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
public class FailureTest {

	@Deployment
	public static Archive<?> createDeployment()
	{		
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, 
						FailureClassServiceLocalEJB.class.getPackage(),
						FailureClassServiceLocal.class.getPackage(),
						FailureClassDAO.class.getPackage(),
						JPAFailureClassDAO.class.getPackage(),
						//FailureClassREST.class.getPackage(),
						FailureClass.class.getPackage(),
						DataValidator.class.getPackage())
						//FailureClassId.class.getPackage())
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@EJB
	FailureClassServiceLocal service;

	private static String INITIAL_DESCRIPTION = "EMERGENCY";
	private static String UPDATED_DESCRIPTION = "MO SIGNALLING";

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

		FailureClass ec = new FailureClass(13, 
				INITIAL_DESCRIPTION
				);
		
		em.persist(ec);

		Integer ecID = 13; 

		FailureClass loadedEC = em.find(FailureClass.class, ecID);
		assertEquals("Insertion Failed", INITIAL_DESCRIPTION, loadedEC.getDescription());

		loadedEC.setDescription(UPDATED_DESCRIPTION);
		FailureClass updatedEC = em.find(FailureClass.class, ecID);
		assertEquals("Update Failed", UPDATED_DESCRIPTION, loadedEC.getDescription());
		
		em.remove(updatedEC);
		FailureClass shouldBeNull = em.find(FailureClass.class, ecID);
		assertNull("Event Cause Failed to delete", shouldBeNull);
	}

	@Test
	public void ServiceLocalTest() throws Exception {

		FailureClass ec = new FailureClass(13, 
				INITIAL_DESCRIPTION
				);

		service.addFailureClass(ec);

		assertEquals("FailureClassServiceLocal Failed to Add", service.getAllFailureClasses().size(), 1);

		FailureClass ec2 = new FailureClass(3, 
				INITIAL_DESCRIPTION
				);
		
		service.addFailureClass(ec2);
		assertEquals("FailureClassServiceLocal Failed to Add", service.getAllFailureClasses().size(), 2);
	}


	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from FailureClass").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}	
}

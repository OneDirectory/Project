package ie.dit.onedirectory.entities.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import ie.dit.onedirectory.dao.CellHierarchyDAO;
import ie.dit.onedirectory.dao.jpa.JPACellHierarchyDAO;
import ie.dit.onedirectory.entities.CellHierarchy;
import ie.dit.onedirectory.rest.CellHierarchyREST;
import ie.dit.onedirectory.services.CellHierarchyServiceLocal;
import ie.dit.onedirectory.services.ejbs.CellHierarchyServiceLocalEJB;
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
public class CellHierarchyTest {

	@Deployment
	public static Archive<?> createDeployment()
	{		
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, 
						CellHierarchyServiceLocalEJB.class.getPackage(),
						CellHierarchyServiceLocal.class.getPackage(),
						CellHierarchyDAO.class.getPackage(),
						JPACellHierarchyDAO.class.getPackage(),
						CellHierarchyREST.class.getPackage(),
						CellHierarchy.class.getPackage())
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;
	
	@EJB
	CellHierarchyServiceLocal service;

	private static String INITIAL_HIER3 = "CANADA";
	private static String UPDATED_HIER3 = "SWEDEN";
	private static String INITIAL_HIER32 = "VERIZON WIRELESS";
	private static String UPDATED_HIER32 = "ALIANT MOBILITY CA";
	private static String INITIAL_HIER321 = "VERIZON WIRELESS";
	private static String UPDATED_HIER321 = "ALIANT MOBILITY CA";
	
	
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

		CellHierarchy ec = new CellHierarchy(13, INITIAL_HIER3, INITIAL_HIER32, INITIAL_HIER321);
		em.persist(ec);

		Integer ecID = 13; 

		CellHierarchy loadedEC = em.find(CellHierarchy.class, ecID);
		assertEquals("Insertion Failed", INITIAL_HIER3, loadedEC.getHier3Id());

		loadedEC.setHier3Id(UPDATED_HIER3);
		CellHierarchy updatedEC = em.find(CellHierarchy.class, ecID);

		assertEquals("Update Failed", UPDATED_HIER3, loadedEC.getHier3Id());
		
		loadedEC.setHier32Id(UPDATED_HIER32);
		updatedEC = em.find(CellHierarchy.class, ecID);
		
		assertEquals("Update Failed", UPDATED_HIER32, loadedEC.getHier32Id());
		
		loadedEC.setHier321Id(UPDATED_HIER321);
		updatedEC = em.find(CellHierarchy.class, ecID);

		assertEquals("Update Failed", UPDATED_HIER321, loadedEC.getHier321Id());

		em.remove(updatedEC);
		CellHierarchy shouldBeNull = em.find(CellHierarchy.class, ecID);
		assertNull("Failed to Delete", shouldBeNull);
	}

	@Test
	public void ServiceLocalTest() throws Exception {

		CellHierarchy ec = new CellHierarchy(13, INITIAL_HIER3, INITIAL_HIER32, INITIAL_HIER321);
		
		service.addCellHierarchy(ec);
		
		assertEquals("Failed to Add", service.getAllCellHierarchies().size(), 1);
	
		CellHierarchy ec2 = new CellHierarchy(17,UPDATED_HIER3, UPDATED_HIER32, UPDATED_HIER321);
		service.addCellHierarchy(ec2);
		
		assertEquals("Failed to Add", service.getAllCellHierarchies().size(), 2);
	
	}


	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from CellHierarchy").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}	
}

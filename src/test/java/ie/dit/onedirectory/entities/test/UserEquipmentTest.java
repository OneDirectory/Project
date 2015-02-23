package ie.dit.onedirectory.entities.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import ie.dit.onedirectory.dao.UserEquipmentDAO;
import ie.dit.onedirectory.dao.jpa.JPAUserEquipmentDAO;
import ie.dit.onedirectory.entities.UserEquipment;
import ie.dit.onedirectory.services.UserEquipmentServiceLocal;
import ie.dit.onedirectory.services.ejbs.UserEquipmentServiceLocalEJB;

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
public class UserEquipmentTest {

	@Deployment
	public static Archive<?> createDeployment()
	{		
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, 
						UserEquipmentServiceLocalEJB.class.getPackage(),
						UserEquipmentServiceLocal.class.getPackage(),
						UserEquipmentDAO.class.getPackage(),
						JPAUserEquipmentDAO.class.getPackage(),
						//UserEquipmentREST.class.getPackage(),
						UserEquipment.class.getPackage())
						//UserEquipmentId.class.getPackage())
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@EJB
	UserEquipmentServiceLocal service;

	private static String INITIAL_MARKETINGNAME = "G410";
	private static String UPDATED_MARKETINGNAME = "M930 NA DB";
	private static String INITIAL_MANUFACTURER = "MITSUBISHI";
	private static String UPDATED_MANUFACTURER = "SIEMENS";
	private static String INITIAL_ACCESS_CAPABILITY = "GSM 1900, GSM850 (GSM800)";
	private static String UPDATED_ACCESS_CAPABILITY	= "GSM 1800, GSM 1900, GSM 900, GSM850 (GSM800)";
	private static String INITIAL_MODEL = "TM3000-C ATD";
	private static String UPDATED_MODEL = "ALCATEL OT-807A";
	private static String INITIAL_VENDORNAME = "COMPAL COM.INC";
	private static String UPDATED_VENDORNAME = "INTEL";
	private static String INITIAL_UETYPE = "PC";
	private static String UPDATED_UETYPE = "HANDHELD";
	private static String INITIAL_OS = "BLACKBERRY";
	private static String UPDATED_OS = "IOS";
	private static String INITIAL_INPUTMODE = "TOUCH_SCREEN";
	private static String UPDATED_INPUTMODE = "BASIC";

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

		UserEquipment ec = new UserEquipment(13, 
				INITIAL_MARKETINGNAME,
				INITIAL_MANUFACTURER,
				INITIAL_ACCESS_CAPABILITY,
				INITIAL_MODEL,
				INITIAL_VENDORNAME, 
				INITIAL_UETYPE,	
				INITIAL_OS,
				INITIAL_INPUTMODE
				);
		
		em.persist(ec);

		Integer ecID = 13; 

		UserEquipment loadedEC = em.find(UserEquipment.class, ecID);
		assertEquals("Insertion Failed", INITIAL_MARKETINGNAME, loadedEC.getMarketingName());

		loadedEC.setMarketingName(UPDATED_MARKETINGNAME);
		UserEquipment updatedEC = em.find(UserEquipment.class, ecID);
		assertEquals("Update Failed", UPDATED_MARKETINGNAME, loadedEC.getMarketingName());

		loadedEC.setManufacturer(UPDATED_MANUFACTURER );
		updatedEC = em.find(UserEquipment.class, ecID);
		assertEquals("Update Failed", UPDATED_MANUFACTURER , loadedEC.getManufacturer());
		
		loadedEC.setAccessCapability(UPDATED_ACCESS_CAPABILITY);
		updatedEC = em.find(UserEquipment.class, ecID);
		assertEquals("Update Failed", UPDATED_ACCESS_CAPABILITY , loadedEC.getAccessCapability());
		
		loadedEC.setModel(UPDATED_MODEL);
		updatedEC = em.find(UserEquipment.class, ecID);
		assertEquals("Update Failed", UPDATED_MODEL, loadedEC.getModel());
		
		loadedEC.setVendorName(UPDATED_VENDORNAME);
		updatedEC = em.find(UserEquipment.class, ecID);
		assertEquals("Update Failed", UPDATED_VENDORNAME, loadedEC.getVendorName());
		
		loadedEC.setUeType(UPDATED_UETYPE);
		updatedEC = em.find(UserEquipment.class, ecID);
		assertEquals("Update Failed", UPDATED_MODEL, loadedEC.getModel());
		
		loadedEC.setOs(UPDATED_OS);
		updatedEC = em.find(UserEquipment.class, ecID);
		assertEquals("Update Failed", UPDATED_OS, loadedEC.getOs());
		
		loadedEC.setInputMode(UPDATED_INPUTMODE);
		updatedEC = em.find(UserEquipment.class, ecID);
		assertEquals("Update Failed", UPDATED_INPUTMODE, loadedEC.getInputMode());
		
		em.remove(updatedEC);
		UserEquipment shouldBeNull = em.find(UserEquipment.class, ecID);
		assertNull("Event Cause Failed to delete", shouldBeNull);
	}

	@Test
	public void ServiceLocalTest() throws Exception {

		UserEquipment ec = new UserEquipment(13, 
				INITIAL_MARKETINGNAME,
				INITIAL_MANUFACTURER,
				INITIAL_ACCESS_CAPABILITY,
				INITIAL_MODEL,
				INITIAL_VENDORNAME, 
				INITIAL_UETYPE,	
				INITIAL_OS,
				INITIAL_INPUTMODE
				);

		service.addUserEquipment(ec);

		assertEquals("UserEquipmentServiceLocal Failed to Add", service.getAllUserEquipment().size(), 1);

		UserEquipment ec2 = new UserEquipment(3, 
				INITIAL_MARKETINGNAME,
				INITIAL_MANUFACTURER,
				INITIAL_ACCESS_CAPABILITY,
				INITIAL_MODEL,
				INITIAL_VENDORNAME, 
				INITIAL_UETYPE,	
				INITIAL_OS,
				INITIAL_INPUTMODE
				);
		
		service.addUserEquipment(ec2);
		assertEquals("UserEquipmentServiceLocal Failed to Add", service.getAllUserEquipment().size(), 2);
	}


	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from UserEquipment").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}	
}

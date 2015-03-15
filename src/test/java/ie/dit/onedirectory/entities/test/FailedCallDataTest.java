package ie.dit.onedirectory.entities.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import ie.dit.onedirectory.dao.FailedCallDataDAO;
import ie.dit.onedirectory.dao.jpa.JPAFailedCallDataDAO;
import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.entities.FailureClass;
import ie.dit.onedirectory.entities.MarketOperator;
import ie.dit.onedirectory.entities.UserEquipment;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;
import ie.dit.onedirectory.services.ejbs.FailedCallDataServiceLocalEJB;
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
public class FailedCallDataTest {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackages(true,
						FailedCallDataServiceLocalEJB.class.getPackage(),
						FailedCallDataServiceLocal.class.getPackage(),
						FailedCallDataDAO.class.getPackage(),
						JPAFailedCallDataDAO.class.getPackage(),
						// FailedCallDataREST.class.getPackage(),
						FailedCallData.class.getPackage(),
						DataValidator.class.getPackage())
				// FailedCallDataId.class.getPackage())
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@EJB
	FailedCallDataServiceLocal service;

	private static Integer INITIAL_EVENT_ID = 4099;
	private static Integer UPDATED_EVENT_ID = 4021;
	private static Integer INITIAL_FAILURE_ID = 1;
	private static Integer UPDATED_FAILURE_ID = 0;
	private static Integer INITIAL_TYPE_ALLOCATION_CODE = 21060800;
	private static Integer UPDATED_TYPE_ALLOCATION_CODE = 21060853;
	private static Integer INITIAL_MARKET_ID = 344;
	private static Integer UPDATED_MARKET_ID = 314;
	private static Integer INITIAL_OPERATOR_ID = 560;
	private static Integer UPDATED_OPERATOR_ID = 21;
	private static Integer INITIAL_CELL_ID = 999;
	private static Integer UPDATED_CELL_ID = 21;
	private static Integer INITIAL_DURATION = 596;
	private static Integer UPDATED_DURATION = 879;
	private static Integer INITIAL_CAUSE_CODE = 569;
	private static Integer UPDATED_CAUSE_CODE = 222;
	private static String INITIAL_NETWORK_ELEMENT_VERSION = "11B";
	private static String UPDATED_NETWORK_ELEMENT_VERSION = "12B";
	private static String INITIAL_IMSI = "344930000000011";
	private static String UPDATED_IMSI = "344930000000122";
	
	private static String INITIAL_MODEL = "VEA3";
	private static String UPDATED_MODEL = "Dirland Miniphone";

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

		String dateString = "2013-11-11";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf1.parse(dateString);
		String dateString2 = "2013-12-11";
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = sdf2.parse(dateString);
		java.sql.Date dateOne = new java.sql.Date(date.getTime());
		java.sql.Date dateTwo = new java.sql.Date(date2.getTime());

		EventCause evtCause = new EventCause(1, 1, "");
		FailureClass failureClass = new FailureClass(1, "");
		UserEquipment ue = new UserEquipment();
		ue.setTac(100);

		FailedCallData fcd = new FailedCallData(dateOne, INITIAL_EVENT_ID,
				INITIAL_FAILURE_ID, INITIAL_TYPE_ALLOCATION_CODE,
				INITIAL_MARKET_ID, INITIAL_OPERATOR_ID, INITIAL_CELL_ID,
				INITIAL_DURATION, INITIAL_CAUSE_CODE,
				INITIAL_NETWORK_ELEMENT_VERSION, INITIAL_IMSI, "INITIAL_MODEL", "", "");

		em.persist(fcd);

		Integer id = fcd.getId();

		FailedCallData loadedEC = em.find(FailedCallData.class, id);
		assertEquals("Insertion Failed", dateOne, loadedEC.getDateTime());

		loadedEC.setDuration(UPDATED_DURATION);
		FailedCallData updatedEC = em.find(FailedCallData.class, id);
		assertEquals("Update Failed", UPDATED_DURATION, loadedEC.getDuration());

		loadedEC.setEventCause(evtCause);
		updatedEC = em.find(FailedCallData.class, id);
		assertEquals("Update Failed", evtCause.getEventId(), loadedEC
				.getEventCause().getEventId());

		loadedEC.setEventCause(evtCause);
		updatedEC = em.find(FailedCallData.class, id);
		assertEquals("Update Failed", evtCause.getCauseCode(), loadedEC
				.getEventCause().getCauseCode());

		loadedEC.setImsi(UPDATED_IMSI);
		updatedEC = em.find(FailedCallData.class, id);
		assertEquals("Update Failed", UPDATED_IMSI, loadedEC.getImsi());

		loadedEC.setNetworkElementVersion(UPDATED_NETWORK_ELEMENT_VERSION);
		updatedEC = em.find(FailedCallData.class, id);
		assertEquals("Update Failed", UPDATED_NETWORK_ELEMENT_VERSION,
				loadedEC.getNetworkElementVersion());

		loadedEC.setFailureClass(failureClass);
		updatedEC = em.find(FailedCallData.class, id);
		assertEquals("Update Failed", failureClass.getFailureId(), loadedEC
				.getFailureClass().getFailureId());

		loadedEC.setFailureClass(failureClass);
		;
		updatedEC = em.find(FailedCallData.class, id);
		assertEquals("Update Failed", failureClass.getDescription(), loadedEC
				.getFailureClass().getDescription());

		loadedEC.setUserEquipment(ue);
		;
		updatedEC = em.find(FailedCallData.class, id);
		assertEquals("Update Failed", ue.getTac(), loadedEC.getUserEquipment()
				.getTac());

		em.remove(updatedEC);
		FailedCallData shouldBeNull = em.find(FailedCallData.class, 0);
		assertNull("Failed to delete", shouldBeNull);

	}

	@Test
	public void ServiceLocalTest() throws Exception {

		String dateString = "2013-11-11";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf1.parse(dateString);
		String dateString2 = "2013-12-11";
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = sdf2.parse(dateString);
		java.sql.Date dateOne = new java.sql.Date(date.getTime());
		java.sql.Date dateTwo = new java.sql.Date(date2.getTime());

		EventCause evt = new EventCause();
		evt.setCauseCode(INITIAL_CAUSE_CODE);
		evt.setEventId(INITIAL_EVENT_ID);
		
		MarketOperator mko = new MarketOperator(INITIAL_MARKET_ID, INITIAL_OPERATOR_ID,"country","name");
		
		FailureClass fc = new FailureClass(INITIAL_FAILURE_ID,"description");
		
		UserEquipment ue = new UserEquipment();
		ue.setTac(INITIAL_TYPE_ALLOCATION_CODE);
		ue.setModel(INITIAL_MODEL);
		
		em.persist(evt);
		em.persist(mko);
		em.persist(fc);
		em.persist(ue);
		
		
		FailedCallData fcd = new FailedCallData(dateOne, INITIAL_EVENT_ID,
				INITIAL_FAILURE_ID, INITIAL_TYPE_ALLOCATION_CODE,
				INITIAL_MARKET_ID, INITIAL_OPERATOR_ID, INITIAL_CELL_ID,
				INITIAL_DURATION, INITIAL_CAUSE_CODE,
				INITIAL_NETWORK_ELEMENT_VERSION, INITIAL_IMSI, "INITIAL_MODEL", "", "");

		FailedCallData fcd2 = new FailedCallData(dateTwo, INITIAL_EVENT_ID,
				INITIAL_FAILURE_ID, INITIAL_TYPE_ALLOCATION_CODE,
				INITIAL_MARKET_ID, INITIAL_OPERATOR_ID, INITIAL_CELL_ID,
				INITIAL_DURATION, INITIAL_CAUSE_CODE,
				INITIAL_NETWORK_ELEMENT_VERSION, UPDATED_IMSI, "UPDATED_MODEL", "", "");

		service.addFailedCalledDatum(fcd);
		//em.persist(fcd2);

		assertEquals("Failed", service.getAllIMSI().size(), 1);


		assertEquals("Failed",
				service.getEventIdAndCauseCodeByIMSI(INITIAL_IMSI).size(), 1);

		assertEquals(
				"Failed",
				service.getCountBetweenDatesForAllIMSI(dateOne, dateTwo).size(),
				1);
		
		assertEquals("Failed", service.getEventIdAndCauseCodeByModel(INITIAL_MODEL).size(), 1);
		
		assertEquals("Failed", service.getFailedCallDataByModel(INITIAL_MODEL, dateOne, dateTwo).size(),1);

	}
	

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from FailedCallData").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
}

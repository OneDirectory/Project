package ie.dit.onedirectory.entities.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import ie.dit.onedirectory.dao.FailedCallDataDAO;
import ie.dit.onedirectory.dao.jpa.JPAFailedCallDataDAO;
import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;
import ie.dit.onedirectory.services.ejbs.FailedCallDataServiceLocalEJB;

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
	public static Archive<?> createDeployment()
	{		
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, 
						FailedCallDataServiceLocalEJB.class.getPackage(),
						FailedCallDataServiceLocal.class.getPackage(),
						FailedCallDataDAO.class.getPackage(),
						JPAFailedCallDataDAO.class.getPackage(),
						//FailedCallDataREST.class.getPackage(),
						FailedCallData.class.getPackage())
						//FailedCallDataId.class.getPackage())
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@EJB
	FailedCallDataServiceLocal service;

	//private static Date INITIAL_DATE_TIME = new SimpleDateFormat( "yyyyMMdd").parse( "20100520");
//	private static Date UPDATED_DATE_TIME = new SimpleDateFormat( "yyyyMMdd").parse( "20100620");
	private static Integer INITIAL_EVENT_ID = 4099;
	private static Integer UPDATED_EVENT_ID = 4021;
	private static Integer INITIAL_FAILURE_ID = 1;
	private static Integer UPDATED_FAILURE_ID = 0;
	private static Integer INITIAL_TYPE_ALLOCATION_CODE = 21060800;
	private static Integer UPDATED_TYPE_ALLOCATION_CODE	= 21060853;
	private static Integer INITIAL_MARKET_ID = 344; 
	private static Integer UPDATED_MARKET_ID = 314;
	private static Integer INITIAL_OPERATOR_ID = 560;
	private static Integer UPDATED_OPERATOR_ID= 21;
	private static Integer INITIAL_CELL_ID = 999;
	private static Integer UPDATED_CELL_ID = 21;
	private static Integer INITIAL_DURATION= 596;
	private static Integer UPDATED_DURATION= 879;
	private static Integer INITIAL_CAUSE_CODE = 569;
	private static Integer UPDATED_CAUSE_CODE = 222;
	private static String INITIAL_NETWORK_ELEMENT_VERSION  = "11B";
	private static String UPDATED_NETWORK_ELEMENT_VERSION = "12B";
	private static String INITIAL_IMSI = "344930000000011";
	private static String UPDATED_IMSI = "344930000000011";

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
		 
	}

	@Test
	public void ServiceLocalTest() throws Exception {

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

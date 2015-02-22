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
import ie.dit.onedirectory.entities.pks.FailedCallDataId;
import ie.dit.onedirectory.rest.FailedCallDataREST;
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
						FailedCallData.class.getPackage())
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;
	
	@EJB
	FailedCallDataServiceLocal service;

	static SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	static String dateInString = "31-08-1982 10:20:56";
	private static Date INITIAL_DATE  = sdf.parse(dateInString);
	
	private static String UPDATED_COUNTRY = "SWEDEN";
	private static String INITIAL_OPERATOR_NAME = "VERIZON WIRELESS";
	private static String UPDATED_OPERATOR_NAME = "ALIANT MOBILITY CA";
	
	
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

		FailedCallData ec = new FailedCallData(13, 47, INITIAL_COUNTRY, INITIAL_OPERATOR_NAME);
		em.persist(ec);

		FailedCallDataId ecID = new FailedCallDataId(13, 47); 

		FailedCallData loadedEC = em.find(FailedCallData.class, ecID);
		assertEquals("Insertion Failed", INITIAL_COUNTRY, loadedEC.getCountry());

		loadedEC.setCountry(UPDATED_COUNTRY);
		FailedCallData updatedEC = em.find(FailedCallData.class, ecID);

		assertEquals("Update Failed", UPDATED_COUNTRY, loadedEC.getCountry());
		
		loadedEC.setOperatorName(UPDATED_OPERATOR_NAME);
		updatedEC = em.find(FailedCallData.class, ecID);

		assertEquals("Update Failed", UPDATED_OPERATOR_NAME, loadedEC.getOperatorName());

		em.remove(updatedEC);
		FailedCallData shouldBeNull = em.find(FailedCallData.class, ecID);
		assertNull("Failed to Delete", shouldBeNull);
	}

	@Test
	public void ServiceLocalTest() throws Exception {

		FailedCallData ec = new FailedCallData(13, 47, INITIAL_COUNTRY, INITIAL_OPERATOR_NAME);
		
		service.addFailedCallData(ec);
		
		assertEquals("Failed to Add", service.getFailedCallDatas().size(), 1);

		FailedCallData ec2 = new FailedCallData(17, 31,UPDATED_COUNTRY, UPDATED_OPERATOR_NAME);
		service.addFailedCallData(ec2);
		
		assertEquals("Failed to Add", service.getFailedCallDatas().size(), 2);
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

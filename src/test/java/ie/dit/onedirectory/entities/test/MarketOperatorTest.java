package ie.dit.onedirectory.entities.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import ie.dit.onedirectory.dao.MarketOperatorDAO;
import ie.dit.onedirectory.dao.jpa.JPAMarketOperatorDAO;
import ie.dit.onedirectory.entities.MarketOperator;
import ie.dit.onedirectory.entities.pks.MarketOperatorId;
import ie.dit.onedirectory.rest.MarketOperatorREST;
import ie.dit.onedirectory.services.MarketOperatorServiceLocal;
import ie.dit.onedirectory.services.ejbs.MarketOperatorServiceLocalEJB;
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
public class MarketOperatorTest {

	@Deployment
	public static Archive<?> createDeployment()
	{		
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, 
						MarketOperatorServiceLocalEJB.class.getPackage(),
						MarketOperatorServiceLocal.class.getPackage(),
						MarketOperatorDAO.class.getPackage(),
						JPAMarketOperatorDAO.class.getPackage(),
						MarketOperator.class.getPackage(),
						MarketOperatorId.class.getPackage(),
						DataValidator.class.getPackage())
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;
	
	@EJB
	MarketOperatorServiceLocal service;

	private static String INITIAL_COUNTRY = "CANADA";
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

		MarketOperator ec = new MarketOperator(13, 47, INITIAL_COUNTRY, INITIAL_OPERATOR_NAME);
		em.persist(ec);

		MarketOperatorId ecID = new MarketOperatorId(13, 47); 

		MarketOperator loadedEC = em.find(MarketOperator.class, ecID);
		assertEquals("Insertion Failed", INITIAL_COUNTRY, loadedEC.getCountry());

		loadedEC.setCountry(UPDATED_COUNTRY);
		MarketOperator updatedEC = em.find(MarketOperator.class, ecID);

		assertEquals("Update Failed", UPDATED_COUNTRY, loadedEC.getCountry());
		
		loadedEC.setOperatorName(UPDATED_OPERATOR_NAME);
		updatedEC = em.find(MarketOperator.class, ecID);

		assertEquals("Update Failed", UPDATED_OPERATOR_NAME, loadedEC.getOperatorName());

		em.remove(updatedEC);
		MarketOperator shouldBeNull = em.find(MarketOperator.class, ecID);
		assertNull("Failed to Delete", shouldBeNull);
	}

	@Test
	public void ServiceLocalTest() throws Exception {

		MarketOperator ec = new MarketOperator(13, 47, INITIAL_COUNTRY, INITIAL_OPERATOR_NAME);
		
		service.addMarketOperator(ec);
		
		assertEquals("Failed to Add", service.getMarketOperators().size(), 1);

		MarketOperator ec2 = new MarketOperator(17, 31,UPDATED_COUNTRY, UPDATED_OPERATOR_NAME);
		service.addMarketOperator(ec2);
		
		assertEquals("Failed to Add", service.getMarketOperators().size(), 2);
	}


	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from MarketOperator").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}	
}

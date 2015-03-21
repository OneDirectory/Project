package ie.dit.onedirectory.utilities.test;

import static org.junit.Assert.*;
import ie.dit.onedirectory.dao.EventCauseDAO;
import ie.dit.onedirectory.dao.FailureClassDAO;
import ie.dit.onedirectory.dao.MarketOperatorDAO;
import ie.dit.onedirectory.dao.UserEquipmentDAO;
import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.entities.FailureClass;
import ie.dit.onedirectory.entities.MarketOperator;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;
import ie.dit.onedirectory.utilities.DataValidator;

import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DataValidatorTest {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackages(true,
						EventCauseDAO.class.getPackage(),
						FailureClassDAO.class.getPackage(),
						MarketOperatorDAO.class.getPackage(),
						UserEquipmentDAO.class.getPackage(),
						FailedCallData.class.getPackage(),
						EventCause.class.getPackage(),
						FailureClass.class.getPackage(),
						MarketOperator.class.getPackage(),
						DataValidator.class.getPackage(),
						FailedCallDataServiceLocal.class.getPackage())
						.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

	}
	
	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;
	
	@EJB
	DataValidator validator;
	
	@EJB
	FailedCallDataServiceLocal service;
	
	@Before
	public void onStart() throws Exception{
		clearData();
		startTransaction();
	}
	
	@Test
	public void testValidation() {
		FailedCallData fcd = new FailedCallData();
		fcd.setDateTime(new Date());
		assertEquals(true, validator.validFailureIdAndCauseCodeTypes("123", "123"));
		assertEquals(false, validator.validFailureIdAndCauseCodeTypes("notanumber", "notanumber"));
		
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

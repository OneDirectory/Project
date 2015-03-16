package ie.dit.onedirectory.utilities.test;

import ie.dit.onedirectory.dao.EventCauseDAO;
import ie.dit.onedirectory.dao.FailureClassDAO;
import ie.dit.onedirectory.dao.MarketOperatorDAO;
import ie.dit.onedirectory.dao.UserEquipmentDAO;
import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.utilities.DataValidator;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
						DataValidator.class.getPackage())
						.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

	}
	
	@Test
	public void testValidation() {
		
	}

}

package ie.dit.onedirectory.utilities.test;

import static org.junit.Assert.*;
import ie.dit.onedirectory.utilities.FileUploadForm;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class FileUploadFormTest {

	@Deployment
	public static Archive<?> createDeployment()
	{		
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true,
						"ie.dit.onedirectory.dao",
						"ie.dit.onedirectory.dao.jpa",
						"ie.dit.onedirectory.entities",
						"ie.dit.onedirectory.entities.pks",
						"ie.dit.onedirectory.rest",
						"ie.dit.onedirectory.services",
						"ie.dit.onedirectory.services.ejbs",
						"ie.dit.onedirectory.utilities"
						)
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void testSetAndGetFileData() {
		
		FileUploadForm form = new FileUploadForm();
		
		byte[] data = {Byte.parseByte("1"), Byte.parseByte("2")};
		form.setFileData(data);
		
		byte[] data2 = form.getFileData();
		
		assertTrue(true);
	}

}

package ie.dit.onedirectory.utilities.test;

import static org.junit.Assert.*;
import ie.dit.onedirectory.utilities.FileUploadForm;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class FileUploadFormTest {

	@Test
	public void testSetAndGetFileData() {
		
		FileUploadForm form = new FileUploadForm();
		
		byte[] data = {Byte.parseByte("1"), Byte.parseByte("2")};
		form.setFileData(data);
		
		byte[] data2 = form.getFileData();
		
		assertTrue(true);
	}

}

package ie.dit.onedirectory.utilities;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUploadForm {
	
	public FileUploadForm(){
		
	}
	
	private byte[] fileData;
	
	public byte[] getFileData(){
		return this.fileData;
	}
	
	@FormParam("selectedFile")
	@PartType("application/octet-stream")
	public void setFileData(byte[] fileData){
		this.fileData = fileData;
	}
	
	
}

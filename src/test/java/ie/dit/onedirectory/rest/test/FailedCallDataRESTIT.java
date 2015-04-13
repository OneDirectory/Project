package ie.dit.onedirectory.rest.test;

import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;
import ie.dit.onedirectory.services.UserServiceLocal;

import java.io.File;
import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.poi.util.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.http.ContentType;

@RunWith(Arquillian.class)
public class FailedCallDataRESTIT{

	private static final String TEST_FILE = "src/test/resources/data.xls";

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive archive = ShrinkWrap
				.create(WebArchive.class, "test.war")

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
						.setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		File[] libs;

		libs = Maven.resolver()
				.resolve("com.jayway.restassured:rest-assured:2.4.0")
				.withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		libs = Maven.resolver().resolve("org.apache.poi:poi:3.11")
				.withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		return archive;
	}

	@Before
	public void setUp() throws Exception{

		// Set up rest assured connection
		RestAssured.config = config()
				.logConfig(new LogConfig(System.out, true));
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "test";
		RestAssured.port = 8080;
		
		

	}

	// Populate the Event Cause Table
	@Test
	public void eventCauseUploadTest() throws IOException {
		given()
		.multiPart("selectedFile", new File(TEST_FILE))
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.post("/rest/eventcauses/upload");
	}

	// Populate the Failure table
	@Test
	public void testFailureUpload() throws IOException {
		given()
		.multiPart("selectedFile", new File(TEST_FILE))
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.post("/rest/failureclasses/upload");
	}

	// Populate the Market Operator table
	@Test
	public void testMarktetOperatorUpload() throws IOException {
		given()
		.multiPart("selectedFile", new File(TEST_FILE))
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.post("/rest/marketoperators/upload");
	}

	// Populate the User Equipment Table
	@Test
	public void testUserEquipmentUpload() throws IOException {
		given()
		.multiPart("selectedFile", new File(TEST_FILE))
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.post("/rest/userequipment/upload");
	}

	// Populate the Failed Call Data Table
	@Test
	public void testFailedCallDataUpload() {
		given()
		.multiPart("selectedFile", new File(TEST_FILE))
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.post("/rest/failedcalldata/upload");
	}
	
	@Test
	public void testFailedCallDateGet() {
		expect()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/get");
	}

	@Test
	public void testgetEventIdAndCauseCodeByIMSI(){
		given()
		.pathParam("imsi", "344930000000011")
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/imsi/{imsi}");
	}

	@Test
	public void testGetCountBetweenDatesForAllIMSI(){
		given()
		.pathParam("dates", "2012-01-01£2013-01-01")
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/count/{dates}");

	}

	@Test
	public void testGetAllIMSIWithCallFailuresBetweenDates(){
		given()
		.pathParam("dates", "2012-01-01£2013-01-01")
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/dateIMSI/{dates}");

	}

	@Test
	public void testGetAllIMSI(){

		expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/imsi");

	}

	@Test
	public void testGetAllImsiForFailureClass(){
		given()
		.pathParam("failureID", "1")
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/imsibyfailureclass/{failureID}");

	}

	@Test
	public void testEventIDCauseCodeByModel(){
		given()
		.pathParam("model", "restTest")
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/model/{model}");

	}

	@Test
	public void testGetFailedCallDataByModel(){
		given()
		.pathParam("model", "restTest£2012-01-01£2013-01-01")
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/getFailedCallDataByModel/{model}");

	}

}

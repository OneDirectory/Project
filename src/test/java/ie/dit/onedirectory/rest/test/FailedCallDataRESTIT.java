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

		RestAssured.config = config()
				.logConfig(new LogConfig(System.out, true));
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "test";
		RestAssured.port = 8080;

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
	public void testGetEventIdAndCauseCodeByModel() {
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
	public void testGetEventCauseByIMSI() {
		given()
		.pathParam("imsi", "344930000000011" )
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/imsi/{imsi}");
	}

	@Test
	public void testGetUniqueCauseCodesForImsi() {
		given()
		.pathParam("imsi", "344930000000011" )
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/uniqueCauseCodes/{imsi}");
	}

	@Test
	public void testGetAllIMSIWithCallFailuresBetweenDates() {
		given()
		.pathParam("dates2", "2012-01-01£2013-01-01")
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/dateIMSI/{dates2}");
	}

	@Test
	public void testGetCountBetweenDatesForAllIMSI() {
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
	public void testGetCountFailedCallsInTimePeriodByImsi() {
		given()
		.pathParam("params", "344930000000011£2012-01-01£2013-01-01")
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/getCountFailedCallsInTimePeriodByImsi/{params}");
	}

	@Test
	public void testGetEventIdAndCauseCodeByIMSI(){
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

	@Test
	public void testTopTenMOCombinations() {
		given()
		.pathParam("dates", "2012-01-01£2013-01-01")
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.get("/rest/failedcalldata/topTenMOCombinations/{dates}");
	}

	@Test 
	public void testUploadFailedCallData() throws IOException {
		given()
		.multiPart("selectedFile", new File(TEST_FILE))
		.expect()
		.statusCode(307)
		.log().ifError()
		.when()
		.post("/rest/failedcalldata/upload");	
	}

}

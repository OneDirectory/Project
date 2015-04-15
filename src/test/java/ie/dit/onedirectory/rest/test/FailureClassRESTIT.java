package ie.dit.onedirectory.rest.test;

import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

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
public class FailureClassRESTIT {

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
	public void testEndPoint() {
		expect()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.log().ifError()
		.when()
		.get("/rest/failureclasses");
	}

	@Test
	public void testUpload() throws IOException {	
		given()
		.multiPart("selectedFile", new File(TEST_FILE))
		.expect()
		.statusCode(200)
		.log().ifError()
		.when()
		.post("/rest/failureclasses/upload");	
	}

}

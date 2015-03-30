package ie.dit.onedirectory.REST.test;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.config;

import java.io.File;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.jayway.restassured.http.ContentType;

@RunWith(Arquillian.class)
public class FailureClassRESTIT{

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

		File[] libs = Maven.resolver()
				.resolve("com.jayway.restassured:rest-assured:2.4.0")
				.withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		libs = Maven.resolver().resolve("org.apache.poi:poi:3.11")
				.withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		libs = Maven.resolver().resolve("org.apache.commons:commons-io:1.3.2")
				.withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		libs = Maven.resolver()
				.resolve("commons-logging:commons-logging:1.1.3")
				.withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		libs = Maven.resolver()
				.resolve("org.glassfish:javax.json:1.0")
				.withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		return archive;

	}
	
	@Before
	public void setUp() throws InterruptedException{
	RestAssured.config = config()
			.logConfig(new LogConfig(System.out, true));
	RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	RestAssured.basePath = "test";
	RestAssured.port = 8080;
	}
	
    @Test
    public void testEndPoint() {
    	expect()
        .statusCode(200).contentType(ContentType.JSON)
        .log().ifError()
     .when()
     .get("/rest/failureclasses");
    }
    
    @Test
    public void testAdd() {
    	expect()
        .statusCode(204)
        .log().ifError()
     .when()
     .get("/rest/failureclasses/add");
    }
    
}

package ie.dit.onedirectory.rest.test;

import ie.dit.onedirectory.dao.UserDAO;
import ie.dit.onedirectory.dao.jpa.UserDAOImplemetation;
import ie.dit.onedirectory.entities.User;
import ie.dit.onedirectory.rest.UserREST;
import ie.dit.onedirectory.services.UserServiceLocal;
import ie.dit.onedirectory.services.ejbs.UserServiceEJB;

import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
//import com.jayway.restassured.module.jsv.JsonSchemaValidator.*;
import com.jayway.restassured.matcher.RestAssuredMatchers.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserTest {

	private static final String USER_TEST_FILE = "src/test/resources/testAddUser.html";


	@Deployment
	public static WebArchive createDeployment() {
		WebArchive archive = ShrinkWrap
				.create(WebArchive.class, "test.war")

				.addPackages(true, "ie.dit.onedirectory.dao",
						"ie.dit.onedirectory.dao.jpa",
						"ie.dit.onedirectory.entities",
						"ie.dit.onedirectory.entities.pks",
						"ie.dit.onedirectory.rest",
						"ie.dit.onedirectory.services",
						"ie.dit.onedirectory.services.ejbs",
						"ie.dit.onedirectory.utilities")
						.addAsResource("test-persistence.xml",
								"META-INF/persistence.xml")
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

		libs = Maven.resolver().resolve("org.codehaus.jackson:jackson-core-asl:1.9.13")
				.withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		libs = Maven.resolver().resolve("org.codehaus.jackson:jackson-mapper-asl:1.9.13")
				.withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		libs = Maven.resolver().resolve("org.hamcrest:hamcrest-all:1.3")
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
		.when().get("/rest/user");
	}
/*
	@Test
	public void testFindUserById(){
		given()
		.pathParam("userID", 1)
		.contentType(ContentType.JSON)
		.expect()
		.statusCode(200) 
		.log().ifError()
		.when()
		.get("/rest/user/{userID}");	
	}
*/

	@Test
	public void testAddUser(){
		User user = new User(1,"Support Engineer","1234","cal","mcg");

		given()
		.contentType("application/json")
		.body(user)
		.expect()
		.statusCode(204) 	
		.log().ifError()			
		.when()														
		.post("/rest/user/add");	
	}

	@Test
	public void test() {
		assertTrue(true);
	}

}

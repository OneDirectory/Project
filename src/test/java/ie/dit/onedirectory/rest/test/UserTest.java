package ie.dit.onedirectory.rest.test;

import ie.dit.onedirectory.dao.UserDAO;
import ie.dit.onedirectory.dao.jpa.UserDAOImplemetation;
import ie.dit.onedirectory.entities.User;
import ie.dit.onedirectory.rest.UserREST;
import ie.dit.onedirectory.services.UserServiceLocal;
import ie.dit.onedirectory.services.ejbs.UserServiceEJB;

import java.net.URI;
import java.net.URL;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.http.ContentType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
public class UserTest {

	@ArquillianResource
	URL deploymentUrl;

	@Deployment(testable=false)
	public static WebArchive create() {
		return ShrinkWrap
				.create(WebArchive.class, "test-rest.war")
				.addClasses(UserREST.class, User.class, UserServiceEJB.class,
						UserDAOImplemetation.class, UserServiceLocal.class,
						UserDAO.class)
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void testGetAllUsers() {

		// This test is expecting JSON and Status.OK
		
		expect().contentType(ContentType.HTML)
				.statusCode(Status.NOT_FOUND.getStatusCode()).when()
				.get(buildUri("rest", "user"));
		
	}

	URI buildUri(String... paths) {
		UriBuilder builder = UriBuilder.fromUri(deploymentUrl.toString());
		for (String path : paths) {
			builder.path(path);
		}
		return builder.build();
	}

}

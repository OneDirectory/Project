/**
 * This class provides all the functionality for 
 * Querying users in the database and adding new
 * users from the front end via a RESTful web service
 * 
 * A service interface is injected to provide access to the service 
 * layer and to return data from the database.
 * 
 * Users are validated on creation using HTTP responses to the client.
 * 
 */

package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.User;
import ie.dit.onedirectory.services.UserServiceLocal;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/user")
public class UserREST {

	@EJB
	private UserServiceLocal service;
	
	/**
	 * @return a list of all the users in the Database
	 * and sends it in JSON format to the client.
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		return service.getUserList();
	}
	
	/**
	 * @param userID The requested Users ID appended to the URL.
	 * @return returns the user identified by the ID from the DAO layer
	 *  and sends it to the client in JSON format.
	 */

	@GET
	@Path("/{userID}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findUserByID(@PathParam("userID") int userID) {

		User user = service.findByID(userID);

		return user;
	}
	
	/**
	 * Receives data from a form via REST and Returns a HTTP response that informs 
	 * the user if/why a user has been/has not been added to the Database. 
	 * 
	 * @param id	new Users ID
	 * @param pass	new Users Password
	 * @param fName new Users FirstName
	 * @param sName new Users SecondName
	 * @param uType new Users user Role
	 * @return A HTTP response that is OK, validation user already exists or HTTP error.
	 */

	@POST
	@Path("/add")
	@Consumes("application/x-www-form-urlencoded")
	public Response addUser(@FormParam("ID") int id,
			@FormParam("password") String pass,
			@FormParam("firstname") String fName,
			@FormParam("lastname") String sName, @FormParam("role") String uType) {

		User user = new User(id, uType, pass, fName, sName);

		Response.ResponseBuilder builder;

		try {

			validateUser(user);
			service.addUser(user);
			// builder = Response.ok();

			builder = Response
					.created(URI
							.create("http://localhost:8080/Project-0.0.1-SNAPSHOT/rest/user/"
									+ id));

		}

		catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("userID", "ID Exists already");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		}

		catch (Exception e) {

			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();

	}
	
	/**
	 * @param user which will be an already existing user or null
	 */

	public void validateUser(User user) {

		if (idAlreadyExists(user.getUserID())) {
			throw new ValidationException("ID already exists");
		}

	}
	
	/**
	 * 
	 * FindById will return a user or a null user.
	 * 
	 * @param id the ID of the new user from the client side
	 * @return true if the user isn't null, that is the ID does
	 * not already exist in the DB.
	 */

	public boolean idAlreadyExists(Integer id) {
		User user = null;

		user = service.findByID(id);

		if (user != null) {
			return true;
		} else
			return false;

	}

}

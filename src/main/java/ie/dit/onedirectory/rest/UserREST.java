package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.User;
import ie.dit.onedirectory.services.UserServiceLocal;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/user")
public class UserREST {

	@EJB
	private UserServiceLocal service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		return service.getUserList();
	}

	@GET
	@Path("/{userID}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findUserByID(@PathParam("userID") int userID) {

		User user = service.findByID(userID);

		return user;
	}

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

	public void validateUser(User user) {

		if (idAlreadyExists(user.getUserID())) {
			throw new ValidationException("ID already exists");
		}

	}

	public boolean idAlreadyExists(Integer id) {
		User user = null;

		user = service.findByID(id);

		if (user != null) {
			return true;
		} else
			return false;

	}

}

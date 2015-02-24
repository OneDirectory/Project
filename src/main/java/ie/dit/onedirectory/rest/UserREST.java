package ie.dit.onedirectory.rest;

import ie.dit.onedirectory.entities.User;
import ie.dit.onedirectory.services.UserServiceLocal;

import java.io.IOException;
import java.util.Collection;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		return service.getUserList();
	}

	@POST
	@Path("/add")
	@Consumes("application/x-www-form-urlencoded")
	public void addUser(@FormParam("id") int id,
			@FormParam("password") String pass,
			@FormParam("firstname") String fName,
			@FormParam("lastname") String sName,
			@FormParam("role") String uType) {

		System.out.println("jhgcdkhgxcdkhggx");

		 User user = new User(id,uType,pass, fName, sName);

		// Response.ResponseBuilder builder = null;

		service.addUser(user);
		// return null;

	}

	// /protected void doPost(HttpServletRequest request, HttpServletResponse
	// response)
	// throws ServletException, IOException {
	//
	// int iD = request.getParameter("ID");
	// String password = request.getParameter("password");
	// String fName = request.getParameter("fName");
	// String sName = request.getParameter("sName");
	// String uType = request.getParameter("uType");
	// System.out.println(fName+" "+sName);
	// }
	//

}

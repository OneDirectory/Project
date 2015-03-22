package ie.dit.onedirectory.servlets;

import java.io.IOException;

import ie.dit.onedirectory.entities.User;
import ie.dit.onedirectory.services.UserServiceLocal;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LogInServlet extends HttpServlet {
	
	@EJB
	private UserServiceLocal service;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String userIDPassed = request.getParameter("id");
		String pwdPassed = request.getParameter("pass");
		
		User user = service.findByID(Integer.parseInt(userIDPassed));
		Integer dbUser = user.getUserID();
		String dbPwd = user.getUserPassword();
		
		String type = user.getUserType();
		
		if(dbPwd.equals(pwdPassed)){
			
			HttpSession session = request.getSession();
			session.setAttribute("user", user.getUserType());
			
			String userPage = getUserPage(type);
			
			session.setMaxInactiveInterval(30*60);
			Cookie userName = new Cookie("user", user.getUserFName());
			userName.setMaxAge(30*60);
			response.addCookie(userName);
			response.sendRedirect("http://localhost:8080/project/"+userPage);
			
			
		}
		

	}

	private String getUserPage(String type) {
		String appendType;
		if(type.equals("Support Engineer")){
			appendType="SEPage.jsp";
		}
		else if(type.equals("Network Management Engineer")){
			appendType="NMEPage.jsp";
		}
		else if(type.equals("Customer Service Rep")){
			appendType="CSRPage.jsp";
		}
		else 
			appendType="adminPage.jsp";
		
		
		
		return appendType;
	}

}

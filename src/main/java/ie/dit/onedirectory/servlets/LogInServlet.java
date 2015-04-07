/**
 * Login in servlet to provide a session login for
 * a user. Takes an ID and password input request,
 * Validates the users authenticity.
 * If valid it creates a session and a cookie for that user
 * If not redirects back to the login page.
 */
package ie.dit.onedirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import ie.dit.onedirectory.entities.User;
import ie.dit.onedirectory.services.UserServiceLocal;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
			HttpServletResponse response) throws IOException, ServletException {

		String userIDPassed = request.getParameter("id");
		String pwdPassed = request.getParameter("pass");

		User user = service.findByID(Integer.parseInt(userIDPassed));

		if (user != null) {
			Integer dbUser = user.getUserID();
			String dbPwd = user.getUserPassword();

			String type = user.getUserType();

			if (dbPwd.equals(pwdPassed)) {

				HttpSession session = request.getSession();
				session.setAttribute("user", user.getUserType());

				String userPage = getUserPage(type);

				session.setMaxInactiveInterval(30 * 60);
				Cookie userName = new Cookie("user", user.getUserFName());
				userName.setMaxAge(30 * 60);
				response.addCookie(userName);
				response.sendRedirect("http://localhost:8080/project/"
						+ userPage);

			} else {

				redirectToLogInPage(request, response);
			}

		} else {
			redirectToLogInPage(request, response);
		}

	}

	private void redirectToLogInPage(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/index.jsp");
		//
		PrintWriter out = response.getWriter();
		response.sendRedirect("http://localhost:8080/project/");
		out.print("alert('Password expired, please update your password..');");
		rd.include(request, response);

	}

	private String getUserPage(String type) {
		String appendType;
		if (type.equals("Support Engineer")) {
			appendType = "SEPage.jsp";
		} else if (type.equals("Network Management Engineer")) {
			appendType = "NMEPage.jsp";
		} else if (type.equals("Customer Service Rep")) {
			appendType = "CSRPage.jsp";
		} else
			appendType = "adminPage.jsp";

		return appendType;
	}

}

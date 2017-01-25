package hr.fer.zemris.java.hw15.web.servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet processing registration of new user
 * @author Teo Toplak
 *
 */
@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
		req.getRequestDispatcher("/WEB-INF/pages/Registration.jsp").forward(req, resp);
	}
}




package hr.fer.zemris.java.hw15.web.servlets;



import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet processing main page of this application
 * @author Teo Toplak
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req,resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		process(req,resp);
	}
	
	/**
	 * Method processing request
	 * @param req request
	 * @param resp response
	 * @throws ServletException servlet exception
	 * @throws IOException IO exception
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
		req.setAttribute("users", users);
		
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp")
		.forward(req, resp);
	}
	
}

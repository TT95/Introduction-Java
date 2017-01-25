package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for starting page of this application.
 * @author Teo Toplak
 *
 */
@WebServlet("/index")
public class Index extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Poll> polls = DAOProvider.getDao().getPolls();		

		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp")
		.forward(req, resp);
	}
	
}

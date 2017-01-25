package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet used in process for voting aciton.
 * Sets up attributes with all bands, their ids, and
 * link example from that option
 * @author Teo Toplak
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		long id = Long.parseLong(req.getParameter("id"));
		req.getServletContext().setAttribute("pollID", id);
		
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(id);
		
		req.setAttribute("pollOptions", pollOptions);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp")
		.forward(req, resp);
	}
	
}

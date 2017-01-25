package hr.fer.zemris.java.hw14.servlets;


import hr.fer.zemris.java.hw14.dao.DAOProvider;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet used in process of voting.
 * Called when user voted for some of the options.
 * By given parameter increments votes for selected option.
 * Passes work of displaying to other servlet.
 * @author Teo Toplak
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		long id=0;
		try {
			id = Long.parseLong(req.getParameter("id"));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long pollID = (long)req.getServletContext().getAttribute("pollID");
		
		DAOProvider.getDao().updatePollOption(pollID, id);

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}

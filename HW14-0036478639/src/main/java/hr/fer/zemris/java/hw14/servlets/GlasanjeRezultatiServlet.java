package hr.fer.zemris.java.hw14.servlets;


import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Final servlet in process of voting action.
 * Produces and sets approppriate attributes needed
 * for representing final results of voting to client.
 * @author Teo Toplak
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		long pollID = (long)req.getServletContext().getAttribute("pollID");
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		
		Integer maxScore =Integer.parseInt(pollOptions.get(0).getVotesCount());
		
		List<PollOption> winners = new ArrayList<PollOption>();
		for(PollOption res : pollOptions) {
			if(Integer.parseInt(res.getVotesCount())==maxScore) {
				winners.add(res);
			}
		}
		
		req.setAttribute("winners", winners);		
		req.getServletContext().setAttribute("results", pollOptions);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").
		forward(req, resp);
		
		
	}
	
}

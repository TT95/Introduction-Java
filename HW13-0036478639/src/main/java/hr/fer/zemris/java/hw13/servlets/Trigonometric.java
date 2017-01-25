package hr.fer.zemris.java.hw13.servlets;


import hr.fer.zemris.java.hw13.utility.TrigonometricUtil;
import hr.fer.zemris.java.hw13.utility.TrigonometricUtil.Trigo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for representing trigonometric values 
 * of sin and cos functions in givent interval.
 * Interval is set with parameters for page
 * @author Teo Toplak
 *
 */
@WebServlet("/trigonometric")
public class Trigonometric extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Integer startFrom = null;
		Integer endAt = null;
		
		try {
			startFrom = Integer.valueOf(req.getParameter("a"));			
		} catch (Exception e) {
			startFrom=0;
		}
		
		try {
			endAt = Integer.valueOf(req.getParameter("b"));
		} catch (Exception ex){
			endAt=360;
		}
		
		if(startFrom > endAt) {
			Integer tmp = startFrom;
			startFrom = endAt;
			endAt = tmp;
		}

		List<Trigo> results = new ArrayList<>();

		for(int i=startFrom, n= endAt.intValue(); i<=n;i++) {
			results.add(TrigonometricUtil.utility(i));
		}

		req.setAttribute("results", results);
		
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp")
		.forward(req, resp);
	}
	
	
}




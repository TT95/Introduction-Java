package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for setting color for all background pages
 * in this application 
 * @author Teo Toplak
 *
 */
@WebServlet("/setcolor")
public class SetColor extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		String color;
		
		try {
			color = req.getParameter("color");			
		} catch (Exception e) {
			color="white";
		}
		
		req.getSession().setAttribute("pickedBgCol", color);
		
		req.getRequestDispatcher("/colors.jsp")
		.forward(req, resp);
	}
}
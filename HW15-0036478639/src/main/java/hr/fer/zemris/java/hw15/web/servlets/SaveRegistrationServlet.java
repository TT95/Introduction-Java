package hr.fer.zemris.java.hw15.web.servlets;


import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.BlogUserForm;
import hr.fer.zemris.java.hw15.utility.Encrypt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet handling saving of new user into database.
 * 
 * @author Teo Toplak
 *
 */
@WebServlet("/servleti/save")
public class SaveRegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}
	
	/**
	 * Method processing request
	 * @param req request
	 * @param resp response
	 * @throws ServletException servlet exception
	 * @throws IOException IO exception
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		if("Cancel".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath());
			return;
		}

		BlogUserForm f = new BlogUserForm();
		f.fillFromRequest(req);
		f.validate();
		
		if(f.hasErrors()) {
			req.setAttribute("user", f);
			req.getRequestDispatcher("/WEB-INF/pages/Registration.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = new BlogUser();
		user.setFirstName(f.getFirstName());
		user.setLastName(f.getLastName());
		user.setEmail(f.getEmail());
		user.setNick(f.getNick());
		user.setPasswordHash(Encrypt.getHash(f.getPassword()));
		DAOProvider.getDAO().addUser(user);
		
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		
		resp.sendRedirect(req.getServletContext().getContextPath());
	}
}

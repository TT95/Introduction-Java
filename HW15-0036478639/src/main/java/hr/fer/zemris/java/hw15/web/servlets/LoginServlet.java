package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.LoginForm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet processing request for login
 * @author Teo Toplak
 *
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

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

		LoginForm f = new LoginForm();
		f.fillFromRequest(req);
		f.validate();
		
		if(f.hasErrors()) {
			req.setAttribute("user", f);
			List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
			req.setAttribute("users", users);
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getUser(f.getNick());
		
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		
		
		
		resp.sendRedirect(req.getServletContext().getContextPath()+"/servleti/main");
	}
}
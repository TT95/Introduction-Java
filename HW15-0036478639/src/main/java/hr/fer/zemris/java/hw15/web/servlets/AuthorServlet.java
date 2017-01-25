package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.NewEntryForm;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet processing all urls at servlets/author
 * Has many purposes, such as showing entries for different users,
 * editing, adding entries, showing comments under those entries, 
 * provides ability to add comments etc..
 * 
 * Servlet work by parsing url after /author/ and then 
 * uses some of its methods to process that request depending
 * on that string.
 * 
 * 
 * @author Teo Toplak
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	/**
	 * Id of entry currently selected by user
	 */
	private String selectedEntryId;
	
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
	 * Processes any http request
	 * @param req request 
	 * @param resp response 
	 * @throws ServletException excpetion when problem iwth servlet occurs
	 * @throws IOException expection when IO problem occurs
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getPathInfo().substring(1);
		String[] arr = path.split("/");
		req.setAttribute("nick", arr[0]);
		
		if(arr.length>1) {
			if(arr[1].equals("new")) {
				newEntry(req, resp);
			} else if(arr[1].equals("edit")){
				editEntry(req, resp);
			} else if(arr[1].equals("create")) {
				createEntry(arr[0], req, resp);
			}else if(arr[1].equals("update")) {
				updateEntry(arr[0], req, resp);
			} else {
				showEntry(arr[0],arr[1], req, resp);
			}
		} else {
			showEntries(arr[0], req, resp);
		}
		
	}

	/**
	 * Method for updating entry (used when user edits entry)
	 * @param nick nickname
	 * @param req request
	 * @param resp response
	 * @throws IOException IO exception
	 * @throws ServletException servlet exception
	 */
	private void updateEntry(String nick, HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		
		req.setCharacterEncoding("UTF-8");
		
		NewEntryForm f = new NewEntryForm();
		f.fillFromRequest(req);
		f.validate();
		
		if(f.hasErrors()) {
			req.setAttribute("entry", f);
			req.getRequestDispatcher("/WEB-INF/pages/EditEntry.jsp").forward(req, resp);
			return;
		}
		

		DAOProvider.getDAO().updateBlogEntry(selectedEntryId, f.getTitle(), f.getText());

		resp.sendRedirect(req.getServletContext().getContextPath()+"/servleti/author/"+nick);
	}

	/**
	 * Method used for creating entries (used when user wants do add entry)
	 * @param nick nickname
	 * @param req request
	 * @param resp response
	 * @throws ServletException servlet exception
	 * @throws IOException IO exception
	 */
	private void createEntry(String nick, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		req.setCharacterEncoding("UTF-8");
		
		NewEntryForm f = new NewEntryForm();
		f.fillFromRequest(req);
		f.validate();
		
		if(f.hasErrors()) {
			req.setAttribute("entry", f);
			req.getRequestDispatcher("/WEB-INF/pages/NewEntry.jsp").forward(req, resp);
			return;
		}
		
		BlogEntry blogEntry = new BlogEntry();
		
		blogEntry.setCreatedAt(new Date());
		blogEntry.setCreator(DAOProvider.getDAO().getUser(
				(Long)req.getSession().getAttribute("current.user.id")));
		blogEntry.setLastModifiedAt(new Date());
		blogEntry.setText(f.getText());
		blogEntry.setTitle(f.getTitle());
		DAOProvider.getDAO().addBlogEntry(blogEntry);
		
		resp.sendRedirect(req.getServletContext().getContextPath()+"/servleti/author/"+nick);
	}

	/**
	 * Shows entry with comments under provided user nickname
	 * @param nick nickname
	 * @param id id 
	 * @param req request
	 * @param resp response
	 * @throws ServletException servlet excception
	 * @throws IOException IO excpetion
	 */
	private void showEntry(String nick,String id, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getParameter("newComment")!=null) {
			DAOProvider.getDAO().addComment(
					(Long)req.getSession().getAttribute("current.user.id"),
					req.getParameter("newComment"), id);
			
		}
		selectedEntryId = id;
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(id));
		req.setAttribute("entry", blogEntry);
		
		req.getRequestDispatcher("/WEB-INF/pages/Entry.jsp")
		.forward(req, resp);
	}

	/**
	 * Edits entry (used for showing form for entry editing)
	 * @param req request
	 * @param resp response
	 * @throws ServletException servlet excpetion
	 * @throws IOException IO exception
	 */
	private void editEntry(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(selectedEntryId));
		NewEntryForm f = new NewEntryForm();
		f.setText(blogEntry.getText());
		f.setTitle(blogEntry.getTitle());
		
		req.setAttribute("entry",f);
		req.getRequestDispatcher("/WEB-INF/pages/EditEntry.jsp")
		.forward(req, resp);
		
	}

	/**
	 * Creates new entry. (method which shows form for creating new entry)
	 * @param req request
	 * @param resp response
	 * @throws ServletException servlet exception
	 * @throws IOException IO exception
	 */
	private void newEntry(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/pages/NewEntry.jsp")
		.forward(req, resp);
		
	}

	/**
	 * Shows entries under provided user nickname
	 * @param nick nickname
	 * @param req request
	 * @param resp response
	 * @throws ServletException servlet excception
	 * @throws IOException IO excpetion
	 */
	private void showEntries(String nick, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(nick);
		req.setAttribute("entries", entries);
		
		req.getRequestDispatcher("/WEB-INF/pages/Entries.jsp")
		.forward(req, resp);
		
	}
	
}

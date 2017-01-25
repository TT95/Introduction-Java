package hr.fer.zemris.java.hw13.servlets;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet used in process of voting.
 * Called when user voted for some of the bands.
 * By given parameter increments votes for selected band.
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

		String id = null;
		try {
			id = req.getParameter("id");			
		} catch (Exception e) {
			//bad luck brian
		}
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		List<String> list = Files.readAllLines(Paths.get(fileName),
                StandardCharsets.UTF_8);
		List<Voting> results = new ArrayList<Voting>();
		for(String s : list) {
			String[] def = s.split("\t");
			if(def[0].equals(id)) {
				Integer newNum =  Integer.parseInt(def[1]) + 1;
				def[1] = newNum.toString();
			}
			results.add(new Voting(def[0], def[1]));
		}
		String fin="";
		for(Voting vote : results) {
			fin+=vote.getId()+"\t"+vote.getNum()+"\n";
		}
		byte[] bytes = fin.getBytes();
		FileOutputStream stream = new FileOutputStream(fileName, false);
		stream.write(bytes);
		stream.close();
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
	/**
	 * Class representing voted candidate.
	 * @author Teo Toplak
	 *
	 */
	public static class Voting {
		/**
		 * id of band
		 */
		private String id;
		/**
		 * number of votings
		 */
		private String num;
		/**
		 * Constructor taking id, and number of 
		 * votes for band as arguments
		 * @param id band id
		 * @param num number of voes for band
		 */
		public Voting(String id, String num) {
			super();
			this.id = id;
			this.num = num;
		}
		/**
		 * id getter
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * number of votes getter
		 * @return the num
		 */
		public String getNum() {
			return num;
		}
		
	}
}

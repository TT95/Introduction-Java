package hr.fer.zemris.java.hw13.servlets;

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
 * Servlet used in process for voting aciton.
 * Sets up attributes with all bands, their ids, and
 * song example from that band
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
		
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		List<String> list = Files.readAllLines(Paths.get(fileName),
                StandardCharsets.UTF_8);
		List<Band> bands = new ArrayList<Band>();
		for(String s : list) {
			String[] def = s.split("\t");
			bands.add(new Band(def[0], def[1], def[2]));
		}
		req.setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp")
		.forward(req, resp);
	}
	
	/**
	 * Class representing one band.
	 * @author Teo Toplak
	 *
	 */
	public static class Band {
		/**
		 * band id
		 */
		private String id;
		/**
		 * name of band
		 */
		private String name;
		/**
		 * bands song example
		 */
		private String song;
		
		/**
		 * Constructor taking id, name,
		 * and song example as arguments
		 * @param id id
		 * @param name name of band
		 * @param song song example
		 */
		public Band(String id, String name,
				String song) {
			super();
			this.id = id;
			this.name = name;
			this.song=song;
		}
		/**
		 * id getter
		 * @return the num
		 */
		public String getId() {
			return id;
		}
		/**
		 * name getter
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * song getter
		 * @return the song
		 */
		public String getSong() {
			return song;
		}
		
		
		
	}
}

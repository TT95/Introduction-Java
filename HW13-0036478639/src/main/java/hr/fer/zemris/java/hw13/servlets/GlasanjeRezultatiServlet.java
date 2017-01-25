package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.servlets.GlasanjeGlasajServlet.Voting;
import hr.fer.zemris.java.hw13.servlets.GlasanjeServlet.Band;

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
		
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		List<String> list = Files.readAllLines(Paths.get(fileName),
                StandardCharsets.UTF_8);
		List<Voting> results = new ArrayList<Voting>();
		for(String s : list) {
			String[] def = s.split("\t");
			results.add(new Voting(def[0], def[1]));
		}
		
		fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		List<String> listSec = Files.readAllLines(Paths.get(fileName),
                StandardCharsets.UTF_8);
		List<Band> bands = new ArrayList<Band>();
		for(String s : listSec) {
			String[] def = s.split("\t");
			bands.add(new Band(def[0], def[1], def[2]));
		}
		
		List<Results> fin = new ArrayList<>();
		for(Voting vote : results) {
			for(Band band : bands) {
				if(band.getId().equals(vote.getId())) {
					fin.add(new Results(band.getName(), vote.getNum()));
				}
			}
		}
		//sorting
		for(int i=0;i<fin.size();i++) {
			for(int j=i; j<fin.size();j++) {
				if(Integer.parseInt(fin.get(i).getNum())<
						Integer.parseInt(fin.get(j).getNum())) {
					Results first = fin.get(i);
					Results seconds = fin.get(j);
					fin.remove(i);
					fin.add(i, seconds);
					fin.remove(j);
					fin.add(j, first);
				}
			}
		}
		
		Integer maxScore =Integer.parseInt(fin.get(0).getNum());
		List<String> winnersNames = new ArrayList<String>();
		for(Results res : fin) {
			if(Integer.parseInt(res.getNum())==maxScore) {
				winnersNames.add(res.getName());
			}
		}
		List<Band> winnerList = new ArrayList<Band>();
		for(Band band : bands) {
			if(winnersNames.contains(band.getName())) {
				winnerList.add(band);
			}
		}
		
		req.setAttribute("winners", winnerList);		
		req.getServletContext().setAttribute("results", fin);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").
		forward(req, resp);
		
		
	}
	
	/**
	 * Class representing result of vote.
	 * @author Teo Toplak
	 *
	 */
	public static class Results {
		/**
		 * name of band
		 */
		private String name;
		/**
		 * number of votes for band
		 */
		private String num;
		/**
		 * Constructor taking name of band 
		 * and number of votes for band as 
		 * arguments.
		 * @param name name of band
		 * @param num number of votes for band
		 */
		public Results(String name, String num) {
			this.name = name;
			this.num = num;
		}
		/**
		 * nema getter
		 * @return the name
		 */
		public String getName() {
			return name;
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

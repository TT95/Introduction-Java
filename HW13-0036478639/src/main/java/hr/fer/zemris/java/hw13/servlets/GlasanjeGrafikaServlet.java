package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.chart.PieChart;
import hr.fer.zemris.java.hw13.servlets.GlasanjeRezultatiServlet.Results;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used in process of voting for bands.
 * Creates graphic representation of voting results in
 * form of pie diagram
 * @author Teo Toplak
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		
		@SuppressWarnings("unchecked")
		List<Results> list = (List<Results>)req.getServletContext().getAttribute("results");
		
		Map<String, Integer> map = new HashMap<>();
		for(Results res : list) {
			map.put(res.getName(),Integer.parseInt(res.getNum()));
		}
		
		PieChart chart = new PieChart("Comparison", "Voting results",map);
		Dimension size = chart.getChartSize();
		
		BufferedImage image = new BufferedImage(
				size.width, size.height, BufferedImage.TYPE_INT_RGB
				);
		Graphics2D g = image.createGraphics();
		g.fillRect(0,0,size.width,size.height);
		chart.getPanel().paintComponent(g);
		
		resp.setContentType("image/png");
		OutputStream os = resp.getOutputStream();
		ImageIO.write(image, "png", os);
		os.flush();
		os.close();
	}
}
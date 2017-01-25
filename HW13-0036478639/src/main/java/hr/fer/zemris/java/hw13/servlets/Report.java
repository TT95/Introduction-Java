package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.chart.PieChart;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for creating example image of
 * graphical pie chart.
 * @author Teo Toplak
 *
 */
@WebServlet("/report")
public class Report extends HttpServlet{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		
		Map<String, Integer> map = new HashMap<>();
		map.put("Linux", 29);
		map.put("Mac", 20);
		map.put("Windows", 51);
		
		PieChart chart = new PieChart("Comparison", "Which operating system are you using?",
				map);
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

		os.close();
		
	}
}
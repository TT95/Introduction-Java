package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Worker class which process request given from server.
 * Returns to client circle with size 200x200 with random color.
 * @author Teo Toplak
 *
 */
public class CircleWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) {
		int width = 200;
		int height = 200;
		
		context.setMimeType("image/png");
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = bim.createGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		g.setColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
		g.fillOval(0, 0, width, height);
		g.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
		ImageIO.write(bim, "png", bos);
		context.write(bos.toByteArray());
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
}

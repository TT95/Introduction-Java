package hr.fer.zemris.java.hw12.jvdraw.actions;


import hr.fer.zemris.java.hw12.jvdraw.JDrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Action exporting drawed image on {@link JVDraw}.
 * Only exportes part of canvas which is filled (crops only
 * part of canvas for exporting).<p>
 * Can be saved as:<br>
 * -jpg<br>
 * -png<br>
 * -gif<br> <p>
 * If you select existing document, extension selected wont matter.
 * Accesible in menu bar.
 * @author Teo Toplak
 *
 */
public class ExportDocumentAction extends AbstractAction{
	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Instance of {@link JVDraw} using this action
	 */
	private JVDraw jVDraw;
	
	/**
	 * Constructor accepting {@link JVDraw} instance as only argument.
	 * @param jVDraw instance of {@link JVDraw}
	 */
	public ExportDocumentAction(JVDraw jVDraw) {
		this.jVDraw=jVDraw;
		putValue(NAME, "Export As..");
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser fc = new JFileChooser();
		
		Object[] possibilities = {".jpg", ".png", ".gif"};
		String extension = (String)JOptionPane.showInputDialog(
                jVDraw,
                "Choose extension",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                null);
		fc.setDialogTitle("Save document");
		if (fc.showSaveDialog(jVDraw) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(jVDraw,
					"Save failed", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String fileChosen = fc.getSelectedFile().toPath().toString();
		if( !(fileChosen.endsWith(".jpg") || fileChosen.endsWith(".png")
				|| fileChosen.endsWith(".gif"))) {
			fileChosen+=extension;
		}
		Path file = Paths.get(fileChosen);
		if (Files.exists(file)) {
			int rez = JOptionPane
					.showConfirmDialog(
							jVDraw,
							"Selected file ("
									+ file
									+ ") already exists. Replace file?",
									"Warning", JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE);
			if (rez != JOptionPane.YES_OPTION) {
				return;
			}
		}
		Path openedFilePath = file;
		JDrawingModel model = jVDraw.getModel();
		int xmax=0;
		int xmin=Integer.MAX_VALUE;
		int ymax=0;
		int ymin=Integer.MAX_VALUE;
		for(int i=0;i<model.getSize();i++) {
			GeometricalObject o = model.getObject(i);
			Point point1;
			Point point2;
			if(o instanceof Line) {
				Line line = (Line)o;
				point1 = line.getStart();
				point2 = line.getEnd();
			} else {
				int radius;
				Point center;
				if(o instanceof Circle) {
					Circle circle = (Circle)o;
					radius = (int) circle.getRadius()/2;
					center = circle.getCenter();
				} else {
					FCircle circle = (FCircle)o;
					radius = (int) circle.getRadius()/2;
					center = circle.getCenter();
				}
				point1 = new Point(center.x-radius,center.y-radius);
				point2 = new Point(center.x+radius,center.y+radius);
			}
			xmax = Math.max(xmax, point1.x);
			xmax = Math.max(xmax, point2.x);
			ymax = Math.max(ymax, point1.y);
			ymax = Math.max(ymax, point2.y);
			xmin = Math.min(xmin, point1.x);
			xmin = Math.min(xmin, point2.x);
			ymin = Math.min(ymin, point1.y);
			ymin = Math.min(ymin, point2.y);
		}
		
		int box_width = xmax-xmin;
		int box_height = ymax-ymin;
		
	
		BufferedImage image = new BufferedImage(
				box_width, box_height, BufferedImage.TYPE_3BYTE_BGR
				);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
		g.fillRect(0, 0, box_width, box_height);
		for(int i=0;i<model.getSize();i++) {
			GeometricalObject o = model.getObject(i);
			if(o instanceof Line) {
				Line originalLine = (Line)o;
				Line line = new Line(originalLine.getName(), originalLine.getStart(),
						originalLine.getEnd(), originalLine.getColor());
				line.setStart(new Point(line.getStart().x-xmin,line.getStart().y-ymin));
				line.setEnd(new Point(line.getEnd().x-xmin,line.getEnd().y-ymin));
				o=line;
			} else if(o instanceof Circle) {
				Circle originalCircle = (Circle)o;
				Circle circle = new Circle(originalCircle.getName(),originalCircle.getCenter(),
						originalCircle.getRadius(),originalCircle.getOutlineColor());
				circle.setCenter(new Point(circle.getCenter().x-xmin,circle.getCenter().y-ymin));
				o=circle;
			} else {
				FCircle originalCircle = (FCircle)o;
				FCircle circle = new FCircle(originalCircle.getName(),originalCircle.getCenter(),
						originalCircle.getRadius(),originalCircle.getOutlineColor(),
						originalCircle.getAreaColor());
				circle.setCenter(new Point(circle.getCenter().x-xmin,circle.getCenter().y-ymin));
				o = circle;
			}
			o.paintComponent(g);
		}
		g.dispose();
		File fileMan = openedFilePath.toFile();

		try {
			ImageIO.write(image, "png", fileMan);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(jVDraw,
					"Error while writing file " + openedFilePath
					+ ": " + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JOptionPane.showMessageDialog(jVDraw, "File exported!");
	}
}

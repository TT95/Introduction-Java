package hr.fer.zemris.java.hw12.jvdraw.actions;


import hr.fer.zemris.java.hw12.jvdraw.JDrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Class representing open action in {@link JVDraw}.<br>
 * Important: if there is already something drawn on canvas,
 * this action will only add new objects to canvas, 
 * keeping existing objects.
 * @author Teo Toplak
 *
 */
public class OpenDocumentAction extends AbstractAction{

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
	public OpenDocumentAction(JVDraw jVDraw) {
		this.jVDraw=jVDraw;
		putValue(NAME, "Open");
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(jVDraw) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path file = fc.getSelectedFile().toPath();
		if (!Files.isReadable(file)) {
			JOptionPane.showMessageDialog(jVDraw,
					"Selected file (" + file + ") is not readable.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<String>text;
		try {
			 text = Files.readAllLines(file);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					jVDraw,
					"Error while reading file " + file + ": "
							+ e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JDrawingModel model = jVDraw.getModel();
		int index=0;
		try {
			for(String s : text) {
				String[] arr = s.split("\\s+");
				switch (arr[0]) {
				case "LINE":
					model.add(new Line("Doc-Object_" + index + ":" + "line",
							new Point(Integer.parseInt(arr[1]), Integer
									.parseInt(arr[2])),
							new Point(Integer.parseInt(arr[3]), Integer
									.parseInt(arr[4])), new Color(Integer
									.parseInt(arr[5]), Integer
									.parseInt(arr[6]), Integer
									.parseInt(arr[7]))));
					break;
				case "CIRCLE":
					model.add(new Circle("Doc-Object_" + index + ":" + "line",
							new Point(Integer.parseInt(arr[1]), Integer
									.parseInt(arr[2])), Double
									.parseDouble(arr[3]), new Color(Integer
									.parseInt(arr[4]), Integer
									.parseInt(arr[5]), Integer
									.parseInt(arr[6]))));
					break;
				case "FCIRCLE":
					model.add(new FCircle("Doc-Object_" + index + ":" + "line",
							new Point(Integer.parseInt(arr[1]), Integer
									.parseInt(arr[2])), Double
									.parseDouble(arr[3]), new Color(Integer
									.parseInt(arr[4]), Integer
									.parseInt(arr[5]), Integer
									.parseInt(arr[6])), new Color(Integer
									.parseInt(arr[7]), Integer
									.parseInt(arr[8]), Integer
									.parseInt(arr[9]))));
					break;
				default:
					System.out.println("Invalid document!");
					return;
				}
				index++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Invalid document!");
			return;
		}
		model.fire();
	}
}


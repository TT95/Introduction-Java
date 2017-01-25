package hr.fer.zemris.java.hw12.jvdraw.actions;
import hr.fer.zemris.java.hw12.jvdraw.JDrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JVDraw;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 * Classs representing Save As action.
 * Accesible through menu bar inn {@link JVDraw}.
 * @author Teo Toplak
 *
 */
public class SaveAsDocumentAction extends AbstractAction{
	
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
	public SaveAsDocumentAction(JVDraw jVDraw) {
		this.jVDraw=jVDraw;
		putValue(NAME, "Save As..");
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser fc = new JFileChooser();
		
		if (fc.showSaveDialog(jVDraw) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(jVDraw,
					"Save failed", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String fileChosen = fc.getSelectedFile().toPath().toString();
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
		StringBuilder str = new StringBuilder();
		
		for(int i=0;i<model.getSize();i++) {
			str.append(model.getObject(i).asText());
			str.append("\n");
		}
		
		try {
			Files.write(openedFilePath,
					str.toString().getBytes());
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(jVDraw,
					"Error while writing file " + openedFilePath
							+ ": " + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		jVDraw.setSaved(true);
	}
}

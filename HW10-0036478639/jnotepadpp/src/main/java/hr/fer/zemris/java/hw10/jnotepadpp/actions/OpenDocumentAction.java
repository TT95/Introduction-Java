package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.TabInfo;
import hr.fer.zemris.java.hw10.jnotepadpp.listeners.NotepadListeners;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Class representing OpenDocumentAction.
 * Accessible through menu/toolbar in {@link JNotepadPP}.
 * @author Teo Toplak
 *
 */
public class OpenDocumentAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private JNotepadPP notepad;
	/**
	 * Constructor taking key string, {@link ILocalizationProvider} localization provider
	 * for notepad and notepad reference. Also can be used for setting mnenonic and
	 * accelerator key
	 * @param key string key
	 * @param lp localization provider
	 * @param notepad notepad reference
	 */
	public OpenDocumentAction(String key, ILocalizationProvider lp,JNotepadPP notepad) {
		this.notepad=notepad;
		putValue(NAME, lp.getString(key));
		putValue(Action.MNEMONIC_KEY,KeyEvent.VK_O);
		
		lp.addLocalizationListener(()-> {
			putValue(NAME, lp.getString(key));
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path file = fc.getSelectedFile().toPath();
		if (!Files.isReadable(file)) {
			JOptionPane.showMessageDialog(notepad,
					"Selected file (" + file + ") is not readable.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JTextArea text = new JTextArea();
		
		
		try {
			byte[] okteti = Files.readAllBytes(file);
			text.setText(new String(okteti, StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					notepad,
					"Error while reading file " + file + ": "
							+ e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		notepad.getTabsList().add(new TabInfo(file, false));
		notepad.getPane().addTab(file.getFileName().toString(), 
				notepad.getIconChanged(),new JScrollPane(text),file.toString());
		text.addCaretListener(NotepadListeners.getCaretListener(notepad));
		text.setFont(notepad.getDefaultFont());
		text.getDocument().addDocumentListener(NotepadListeners.getDocumentListener(notepad));
		
	}
}


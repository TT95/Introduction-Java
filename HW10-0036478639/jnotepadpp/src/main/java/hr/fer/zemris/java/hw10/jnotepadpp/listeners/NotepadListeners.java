package hr.fer.zemris.java.hw10.jnotepadpp.listeners;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;

import javax.swing.JTextArea;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

/**
 * Class containing listener for {@link JNotepadPP} use.
 * Containig listeners for documents and caret.
 * @author Teo Toplak
 *
 */
public class NotepadListeners {

	/**
	 * Returns document listener.
	 * @param notepad notepad
	 * @return document listener
	 */
	public static DocumentListener getDocumentListener(JNotepadPP notepad) {
		return new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				notepad.setCurrTabChange(true);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				notepad.setCurrTabChange(true);
				
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				notepad.setCurrTabChange(true);
				
			}
		};
	}
	
	/**
	 * Returns caret listener.
	 * @param notepad notepad
	 * @return caret listener
	 */
	public static CaretListener getCaretListener(JNotepadPP notepad) {
		return  e -> {
			JTextArea area = notepad.getCurrTabText();
			Caret caret = area.getCaret();
			String text="";
			try {
				text = area.getText(0,caret.getDot());
			} catch (BadLocationException ignorable) {System.out.println("josko abrakadabra"); }
			char[] arr = text.toCharArray();
			int line = 1;
			int col=0;
			int lastLine = text.lastIndexOf("\n");
			for(int i=0;i<arr.length;i++) {
				if(i>lastLine) {
					if(arr[i]=='\t') {
						while(col % 13 !=0 || col < 13) {
							col++;
						}
					} else {
						col++;
					}
				}
				if(arr[i]=='\n') {
					line++;
				}
			}
			String selectedText = area.getSelectedText(); 
			int selected=0;
			boolean enable = false;
			if(selectedText != null) {
				selected= selectedText.length();
				enable = true;
			}
			notepad.getUpperCaseItem().setEnabled(enable);
			notepad.getLowerCaseItem() .setEnabled(enable);
			notepad.getInvertCaseItem().setEnabled(enable);
			if(notepad.getCaretPos() != null) {
				notepad.getCaretPos().setText("Ln:"+line+" Col: "+col+" Sel:"+selected);
			}
			notepad.getLengthStatusBar().setText("length:"+area.getText().length());
		};
	}
}

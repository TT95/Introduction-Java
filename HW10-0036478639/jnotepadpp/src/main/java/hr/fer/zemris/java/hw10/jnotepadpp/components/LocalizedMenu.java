package hr.fer.zemris.java.hw10.jnotepadpp.components;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.JMenu;

/**
 * Class representning menu on {@link JNotepadPP} for
 * selecting languages.
 * @author Teo Toplak
 *
 */
public class LocalizedMenu extends JMenu{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor taking string key and {@link ILocalizationProvider}
	 * as provider of localization for program
	 * @param key string key
	 * @param lp provider of localization for program
	 */
	public LocalizedMenu(String key, ILocalizationProvider lp) {
		super(lp.getString(key));
		lp.addLocalizationListener(()-> {
			setText(lp.getString(key));
		});
	}
}

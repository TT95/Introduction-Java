package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.LocalizationProvider;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Class representing SetLanguageAction.
 * Accessible through menu/toolbar in {@link JNotepadPP}.
 * @author Teo Toplak
 *
 */
public class SetLanguageAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private String language;
	/**
	 * Constructor taking key string, {@link ILocalizationProvider} localization provider
	 * for notepad and notepad reference. Also can be used for setting mnenonic and
	 * accelerator key
	 * @param key string key
	 * @param lp localization provider
	 * @param language language
	 */
	public SetLanguageAction(String key, ILocalizationProvider lp, String language) {
		this.language = language;
		putValue(NAME, lp.getString(key));
		lp.addLocalizationListener(()-> {
			putValue(NAME, lp.getString(key));
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(language);
	}
}


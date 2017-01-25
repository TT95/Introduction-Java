package hr.fer.zemris.java.hw10.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization provider.
 * Singleton which stores current language and when that action happens
 *  notifies all listeners.
 * @author Teo Toplak
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * language
	 */
	private String language;
	/**
	 * bundle for laguages
	 */
	private ResourceBundle bundle;
	/**
	 * instane of this class (singleton property)
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	/**
	 * construcotr taking no arguments
	 */
	private LocalizationProvider() {}
	
	/**
	 * Returns instance of this singleton
	 * @return instance of this singleton
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	/**
	 * Sets given language.
	 * Making {@link ResourceBundle} object for localization.
	 * @param language language
	 */
	public void setLanguage(String language) {
		this.language=language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw10.jnotepadpp.languages.translations", locale);
		fire();
	}
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * returns current language
	 * @return current language
	 */
	public String getLanguage() {
		return language;
	}
	
	
}

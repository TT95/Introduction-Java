package hr.fer.zemris.java.hw10.jnotepadpp.localization;

/**
 * Interface for localization provider.
 * Demands method for adding, removing listeners.
 * @author Teo Toplak
 *
 */
public interface ILocalizationProvider {
	/**
	 * Method adds localization listener.
	 * @param listener listener
	 */
	public void addLocalizationListener(ILocalizationListener listener);
	/**
	 * Method removes localization listener.
	 * @param listener listener
	 */
	public void removeLocalizationListener(ILocalizationListener listener);
	/**
	 * Method returns key string for appropriate language
	 * @param key string key
	 * @return string 
	 */
	public String getString(String key);
}

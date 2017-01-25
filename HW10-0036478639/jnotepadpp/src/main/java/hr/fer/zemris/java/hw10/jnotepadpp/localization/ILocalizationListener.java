package hr.fer.zemris.java.hw10.jnotepadpp.localization;

/**
 * localization listener interface.
 * Demands only one method used for firing all listeners.
 * @author Teo Toplak
 *
 */
public interface ILocalizationListener {
	/**
	 * Method used for firing all listeners.
	 */
	public void localizationChanged();
}

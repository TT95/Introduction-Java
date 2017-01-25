package hr.fer.zemris.java.hw10.jnotepadpp.localization;

/**
 * Localization provider bridge.
 * This class is made for shuting down all references from localization
 * pattern to notepad for legal exiting program.
 * 
 * @author Teo Toplak
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * true if conneceted to localization singleto (pattern)
	 */
	private boolean connected;
	/**
	 * localization provider parent
	 */
	private ILocalizationProvider parent;
	/**
	 * localzation provider listener made for firing
	 * all other listeners subscribed to this one.
	 */
	private ILocalizationListener listener;
	
	/**
	 * Constructor taking provider as argument
	 * @param provider localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.parent=provider;
		listener= () -> fire();
	}
	/**
	 * Connecting bridge
	 */
	public void connect() {
		if(!connected) {
			parent.addLocalizationListener(listener);
		}
		connected = true;
	}
	/**
	 * Disconnecting bridge
	 */
	public void disconnect() {
		if(connected) {
			parent.removeLocalizationListener(listener);
		}
		connected = false;

	}
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	
}

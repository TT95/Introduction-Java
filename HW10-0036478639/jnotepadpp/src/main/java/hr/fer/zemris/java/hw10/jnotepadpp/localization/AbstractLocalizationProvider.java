package hr.fer.zemris.java.hw10.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents abstract localization provider.
 * Implements ability to add or remove listeners.
 * Also notifies all listeners when change is made.
 * @author Teo Toplak
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	/**
	 * list of listeners
	 */
	private List<ILocalizationListener> list;
	
	/**
	 * Constructor initializing list of listeners
	 */
	public AbstractLocalizationProvider() {
		list = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		list.add(listener);
	}
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		list.remove(listener);
	}
	/**
	 * Method notifies all listeners.
	 */
	public void fire() {
		for(ILocalizationListener listener : list) {
			listener.localizationChanged();
		}
	}
}

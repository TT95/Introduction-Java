package hr.fer.zemris.java.hw10.jnotepadpp.localization;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Used for localization of notepad.
 * Used for connecting and disconnecting {@link LocalizationProviderBridge}.
 * @author Teo Toplak
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * Constructors accepts {@link ILocalizationProvider} and {@link JFrame}
	 * for implementing localization.
	 * @param provider provider
	 * @param frame frame
	 */
	public FormLocalizationProvider(ILocalizationProvider provider,JFrame frame) {
		super(provider);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

		
}

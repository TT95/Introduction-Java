package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Sucelje namjenjeno za stvaratelja likova
 * @author Teo Toplak
 *
 */
public interface StvarateljLika {

	/**
	 * vraca naziv lika
	 * @return
	 */
	String nazivLika();
	
	/**
	 * Metoda koja sluzi za proizvodnju instance lika iz parametara zadanih putem stringa.
	 */
	GeometrijskiLik stvoriIzStringa(String parametri);
}

package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja geometrijski lik.
 */
public abstract class GeometrijskiLik{

	//nisam radio sve gettere i settere ni u jednoj od klasa likova jer nije bilo potrebno za problem ove zadace
	
	/**
	 * ime lika
	 */
	private String ime;

	/**
	 * Konstruktor sa mogucnosti stvaranja instance lika bez zadanih parametara
	 */
	public GeometrijskiLik() {
		this.ime="Nepoznato";
	}

	/**
	 * Konstruktor sa mogucnosti stvaranja instance lika sa zadanim imenom lika
	 */
	public GeometrijskiLik(String ime) {
		this.ime=ime;
	}
	
	/**
	 * Getter za ime
	 * @return
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Metoda koja vraca true ako je dana tocka unutar instance geometrijskog lika.
	 * @true ako je dana tocka unutar instance geometrijskog lika
	 */
	public abstract boolean sadrziTocku(int x, int y);

	/**
	 * Metoda koja popunjuje lik na zadanom rasteru.
	 */
	public void popuniLik(Slika slika) {
		// ne znamo slozenost danih metoda
		// int sirina = slika.getSirina();
		// int visina = slika.getVisina();

		for (int y = 0, visina = slika.getVisina(), sirina = slika.getSirina(); y < visina; y++) {
			for (int x = 0; x < sirina; x++) {
				if (this.sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}

	
}

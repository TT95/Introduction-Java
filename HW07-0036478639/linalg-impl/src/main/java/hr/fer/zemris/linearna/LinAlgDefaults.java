package hr.fer.zemris.linearna;

/**
 * Klasa koja nudi korisniku factory metode za defaultnim vektoromm i defaultnom
 * matricom
 * 
 * @author Teo Toplak
 *
 */
public class LinAlgDefaults {

	/**
	 * Factory metoda koja vraca defaultnu matricu.
	 * @param rows broj redova
	 * @param cols broj stupaca
	 * @return matricu
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}
	
	/**
	 * Factory metoda koja vraca defaultni vektor.
	 * @param dimension dimenzija vektora
	 * @return vektor
	 */
	public static IVector defaultVector(int dimension) {
		return new Vector(false, true, new double[dimension]);
	}
}

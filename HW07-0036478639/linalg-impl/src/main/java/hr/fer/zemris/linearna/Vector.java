package hr.fer.zemris.linearna;

/**
 * Klasa koja predstavlja implementaciju vektora.
 * Za detaljniju dokumentaciju pogledati IVector sucelje
 * @author Teo Toplak
 *
 */
public class Vector extends AbstractVector{

	/**
	 * polje elemenata
	 */
	private double[] elements;
	/**
	 * dimenzija vektora
	 */
	private int dimension;
	/**
	 * boolean koji ukazuje da li je je vektor modifabilan
	 */
	private boolean readOnly;
	
	/**
	 * Konstruktor koji prima boolean koji ukazuje da li je je vektor
	 * modifabilan, boolean kojim se (ne)dopusta koristenje reference na polje
	 * elemenata, i polje elemenata
	 * 
	 * @param readOnly  boolean koji ukazuje da li je je vektor modifabilan
	 * @param canUseOriginal boolean kojim se (ne)dopusta koristenje reference na polje elemenata
	 * @param elements polje elemenata
	 */
	public Vector(boolean readOnly,boolean canUseOriginal,double[] elements) {
		
		if(canUseOriginal) {
			this.elements = elements;
		} else {
			this.elements = elements.clone();
		}
		this.readOnly = readOnly;
		dimension=elements.length;
	}

	/**
	 * Konstruktor koji prima jedino polje elemenara kao argument
	 * @param elements polje elemenata
	 */
	public Vector(double[] elements) {
		this.elements = elements.clone();
		this.dimension=elements.length;
	}
	
	@Override
	public double get(int index) {
		return elements[index];
	}
	
	@Override
	public IVector set(int index, double value)
			throws UnmodifiableObjectException {
		if(readOnly) {
			throw new UnmodifiableObjectException();
		}
		elements[index]=value;
		return this;
	}
	
	@Override
	public int getDimension() {
		return dimension;
	}
	
	@Override
	public IVector copy() {
		/*nisam koristio metodu newInstance jer u slucaju da je unmodifiable
		nemogu prekopirat sadrzaj*/
		return new Vector(readOnly,false,elements);
	}
	
	@Override
	public IVector newInstance(int dimension) {
		return new Vector(readOnly,true,new double[dimension]);
	}
	
	@Override
	public double[] toArray() {
		return elements.clone();
	}
}

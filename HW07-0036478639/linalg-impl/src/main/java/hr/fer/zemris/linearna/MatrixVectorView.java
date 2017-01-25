package hr.fer.zemris.linearna;

/**
 * Proxy klasa sa zivim pogledom na vektor te se prestavlja kao matrica tog
 * jednostupcanog/jednoredcanog vektora
 * 
 * @author Teo Toplak
 *
 */
public class MatrixVectorView extends AbstractMatrix{

	/**
	 * originalni vektor
	 */
	private IVector original;
	
	/**
	 * boolean koji ukazuje da li je matrica jednostupcana ili jednoredcana
	 */
	private boolean asRowMatrix;
	
	/**
	 * Konstruktor koji radi instancu ove klase na temelji originalnog vektora i
	 * booleana koji ukazuje da li je matrica jednostupcana ili jednoredcana
	 * 
	 * @param original originalni vektor
	 * @param asRowMatrix boolean koji ukazuje da li 
	 * 			je matrica jednostupcana ili jednoredcana
	 */
	public MatrixVectorView(IVector original, boolean asRowMatrix) {
		this.original=original;
		this.asRowMatrix=asRowMatrix;
	}

	@Override
	public int getRowsCount() {
		if(asRowMatrix) {
			return 1;
		}
		return original.getDimension();
	}

	@Override
	public int getColsCount() {
		if(asRowMatrix) {
			return original.getDimension();
		}
		return 1;
	}

	@Override
	public double get(int row, int col) {
		if(row==0 || col==0) {
			return original.get(Math.max(row, col));
		}
		throw new IncompatibleOperandException("Zadani stupac/redak"
				+ " je izvan dimenzija matrice!");
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		if(row==0 || col==0) {
			original.set(Math.max(row, col),value);
			return this;
		}
		throw new IncompatibleOperandException("Zadani stupac/redak"
				+ " je izvan dimenzija matrice!");
	}

	@Override
	public IMatrix copy() {
		return new MatrixVectorView(original, asRowMatrix);
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		if(asRowMatrix) {
			return new Matrix(1, original.getDimension());
		}
		return new Matrix(original.getDimension(),1);
	}
	
	
}

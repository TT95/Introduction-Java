package hr.fer.zemris.linearna;

/**
 * Proxy klasa koji ima pogled na matricu a predstavlja se kao vektor.
 * @author Teo Toplak
 *
 */
public class VectorMatrixView extends AbstractVector{

	/**
	 * originalna matrica
	 */
	private IMatrix original;
	/**
	 * dimenzija vektora
	 */
	private int dimension;
	/**
	 * boolean koji ukazuje da li je matrica jednoredcana ili jednostupcana
	 */
	private boolean rowMatrix;
	
	/**
	 * Konstruktor koji kao argument prima originalni vektor
	 * @param original
	 */
	public VectorMatrixView(IMatrix original) {
		this.original=original;
		if(original.getColsCount()==1) {
			rowMatrix=false;
		} else {
			rowMatrix=true;
		}
		dimension=Math.max(original.getColsCount(),
				original.getRowsCount());
	}

	@Override
	public double get(int index) {
		if(rowMatrix) {
			return original.get(0, index);
		} else {
			return original.get(index, 0);
		}
	}


	@Override
	public IVector set(int index, double value) {
		if(rowMatrix) {
			original.set(0, index, value);
		} else {
			original.set(index, 0, value);
		}
		return this;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public IVector copy() {
		return new VectorMatrixView(original.copy());
	}

	@Override
	public IVector newInstance(int dimension) {
		return LinAlgDefaults.defaultVector(dimension);
	}

	@Override
	public double[] toArray() {
		double[] arr = new double[dimension];
		if(rowMatrix) {
			for(int i=0;i<dimension;i++) {
				arr[i]=original.get(0,i);
			}
		} else {
			for(int i=0;i<dimension;i++) {
				arr[i]=original.get(i,0);
			}
		}
		return arr;
	}
	
}

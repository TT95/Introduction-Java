
package hr.fer.zemris.linearna;

/**
 * Proxy klasa koja se koristi kao zivi pogled na matricu u smislu da se ponasa
 * kao transponirana originalna matrica.
 * 
 * @author Teo Toplak
 *
 */
public class MatrixTransposeView extends AbstractMatrix{

	/**
	 * originalna matrica
	 */
	private IMatrix original;
	
	
	/**
	 * Konstruktor koji radi instancu ove klase na temelju originalne matrice
	 * @param original
	 */
	public MatrixTransposeView(IMatrix original) {
		this.original=original;
	}
	
	@Override
	public int getRowsCount() {
		return original.getColsCount();
	}
	
	@Override
	public int getColsCount() {
		return original.getRowsCount();
	}
	
	@Override
	public double get(int row, int col) {
		return original.get(col, row);
	}
	
	@Override
	public IMatrix set(int row, int col, double value) {
		return original.set(col, row, value);
	}
	
	@Override
	public IMatrix copy() {
		return new MatrixTransposeView(original.copy());
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new Matrix(original.getColsCount(),this.getRowsCount());
	}

	@Override
	public IMatrix nScalarMultiply(double value) {
		return new MatrixTransposeView(original.nScalarMultiply(value));
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		original.scalarMultiply(value);
		return this;
	}

	@Override
	public IMatrix makeIdentity() {
		original.makeIdentity();
		return this;
	}
}

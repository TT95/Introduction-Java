package hr.fer.zemris.linearna;

/**
 * Klasa koja predstavlja matricu.
 * Za detajniju dokumentaciju pogledati javadoc za IMatrix
 * @author Teo Toplak
 *
 */
public class Matrix extends AbstractMatrix{
	
	/**
	 * Matrica elemenata
	 */
	private double elements[][];
	/**
	 * broj redaka
	 */
	int rows;
	/**
	 * broj stupaca
	 */
	int cols;
	
	/**
	 * Konstruktor zadan samo sa brojem redaka i stupaca
	 * @param rows broj redaka
	 * @param cols broj stupaca
	 */
	public Matrix(int rows,int cols) {
		elements=new double[rows][cols];
		this.rows=rows;
		this.cols=cols;
	}

	/**
	 * Konstruktor koji radi instancu matrice pomocu broja redaka i
	 * stupaca,matricom elemenata i booleanom koji govori da li se dana
	 * referenca na matricu elemenata moze direktno koristiti.
	 * 
	 * @param rows broj redaka
	 * @param cols broj stupaca
	 * @param array matrica elemenata
	 * @param useGiven indikator za direktno koristenje reference "array"
	 */
	public Matrix(int rows, int cols, double[][] array, boolean useGiven ) {
		if(useGiven) {
			elements=array;
		} else {
			elements=array.clone();
		}
		this.rows = rows;
		this.cols = cols;
	}
	
	@Override
	public int getRowsCount() {
		return rows;
	}
	
	@Override
	public int getColsCount() {
		return cols;
	}
	
	@Override
	public double get(int row, int col) {
		return elements[row][col];
	}
	
	@Override
	public IMatrix set(int row, int col, double value) {
		elements[row][col]=value;
		return this;
	}
	
	@Override
	public IMatrix copy() {
		return new Matrix(rows,cols,this.toArray(),true);
	}
	
	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new Matrix(rows, cols);
	}

	
}

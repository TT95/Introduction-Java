package hr.fer.zemris.linearna;

/**
 * Proxy klasa koja daje zivi pogled na matricu te ju promatra bez odredenih
 * stupaca/redaka
 * 
 * @author Teo Toplak
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix{

	/**
	 * originalna, promatrana matrica
	 */
	private IMatrix original;
	/**
	 * polje indexa redaka koji se koriste iz originalne matrice
	 */
	private int[] rowIndexes;
	/**
	 * polje indexa stupaca koji se koriste iz originalne matrice
	 */
	private int[] colIndexes;
	
	/**
	 * Konstruktor koji prima originalnu matricu, te broj redaka/stupaca
	 * @param original originalna matrica
	 * @param row broj redaka
	 * @param col broj stupaca
	 */
	public MatrixSubMatrixView(IMatrix original, int row, int col) {
		rowIndexes=new int[original.getRowsCount()-1];
		colIndexes=new int[original.getColsCount()-1];
		this.original=original;
		int index=0;
		for(int i=0;i<original.getRowsCount();i++) {
			if(i!=row) {
				rowIndexes[index]=i;
				index++;
			}
		}
		index=0;
		for(int i=0;i<original.getColsCount();i++) {
			if(i!=col) {
				colIndexes[index]=i;
				index++;
			}
		}
	}
	/**
	 * Konstruktor koristen jedino od strane metode subMatrix unutar ove klase.
	 * Dodatno "izbacuje" redke/stupce
	 * @param original originalna matrica
	 * @param rowIndexes niz indexa redaka iz originalne matrice
	 * @param colIndexes niz indexa stupaca iz originalne matrice
	 */
	private MatrixSubMatrixView(IMatrix original, int[] rowIndexes, int[] colIndexes) {
		this.rowIndexes=rowIndexes;
		this.original=original;
		this.colIndexes=colIndexes;
	}
	
	
	/**
	 * Metoda koja uzima jos dodatnu podmatricu originalne matrice.
	 * Dodatno "skracuje" pogled na originalnu matricu
	 */
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if(rowIndexes.length<2 || colIndexes.length<2) {
			throw new IncompatibleOperandException("Matrica premala "
					+ "za operaciju subMatrix!");
		}
		int index=0;
		int[] newRowIndexes= new int[rowIndexes.length-1];
		for(int i=0;i<rowIndexes.length;i++) {
			if(rowIndexes[i]!=row) {
				newRowIndexes[index]=rowIndexes[i];
				index++;
			}
		}
		index=0;
		int[] newColIndexes= new int[colIndexes.length-1];
		for(int i=0;i<colIndexes.length;i++) {
			if(colIndexes[i]!=row) {
				newColIndexes[index]=colIndexes[i];
				index++;
			}
		}
		if(newColIndexes.length==colIndexes.length || newRowIndexes.length==rowIndexes.length) {
			throw new IncompatibleOperandException("Zadani redak/stupac"
					+ " je van dimenzija matrice");
		}
		if(liveView) {
			return new MatrixSubMatrixView(original,newRowIndexes,newColIndexes);
		} else {
			double[][] matrix= new double[newRowIndexes.length][newColIndexes.length];
			for(int i=0;row<newRowIndexes.length;row++) {
				for(int j=0;col<newColIndexes.length;col++) {
					matrix[i][j]=original.get(newRowIndexes[i], newColIndexes[j]);
				}
			}
			return new Matrix(newRowIndexes.length, newColIndexes.length, matrix, true);
		}
	}
	@Override
	public int getRowsCount() {
		return rowIndexes.length;
	}
	@Override
	public int getColsCount() {
		return colIndexes.length;
	}
	@Override
	public double get(int row, int col) {
		return original.get(rowIndexes[row], colIndexes[col]);
	}
	@Override
	public IMatrix set(int row, int col, double value) {
		original.set(rowIndexes[row], colIndexes[col],value);
		return this;
	}
	@Override
	public IMatrix copy() {
		return new MatrixSubMatrixView(original,rowIndexes,colIndexes);
	}
	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new Matrix(rowIndexes.length, colIndexes.length);
	}
}

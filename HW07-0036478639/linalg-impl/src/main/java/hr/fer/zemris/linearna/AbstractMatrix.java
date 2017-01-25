package hr.fer.zemris.linearna;

/**
 * Abstraktna klasa za matricu.
 * Za detaljniju dokumentaciju pogledati sucelje IMatrix
 * @author Teo Toplak
 *
 */
public abstract class AbstractMatrix implements IMatrix{

	@Override
	public IMatrix add(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount()
				|| this.getColsCount() != other.getColsCount())
			throw new IncompatibleOperandException("Matrice nisu "
					+ "kompatibilne za zbrajanje");
		for(int row=0;row<this.getRowsCount();row++) {
			for(int col=0;col<this.getColsCount();col++) {
				this.set(row, col, this.get(row, col)+other.get(row, col));
			}
		}
		return this;
	}
	
	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}
	
	@Override
	public IMatrix sub(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount()
				|| this.getColsCount() != other.getColsCount())
			throw new IncompatibleOperandException("Matrice nisu "
					+ "kompatabilne za oduzimanje");
		for(int row=0;row<this.getRowsCount();row++) {
			for(int col=0;col<this.getColsCount();col++) {
				this.set(row, col, this.get(row, col)-other.get(row, col));
			}
		}
		return this;
	}
	
	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}
	
	@Override
	public IMatrix nMultiply(IMatrix other) {
		if(this.getColsCount()!=other.getRowsCount()) {
			throw new IncompatibleOperandException("Matrice"
					+ " nisu kompatibilne za mnozenje!");
		}
		int sum;
		IMatrix newM= new Matrix(this.getRowsCount(),other.getColsCount(),
				new double[this.getRowsCount()][other.getColsCount()],true);
		for(int firstMatrixRow=0;firstMatrixRow<this.getRowsCount();firstMatrixRow++) {
			for(int secMatrixCol=0;secMatrixCol<other.getColsCount();secMatrixCol++) {
				sum=0;
				for(int index=0;index<this.getColsCount();index++) {
					sum+=this.get(firstMatrixRow, index)*other.get(index, secMatrixCol);
				}
				newM.set(firstMatrixRow, secMatrixCol, sum);
			}
		}
		return newM;
	}
	
	
	
	@Override
	public double determinant() throws IncompatibleOperandException {
		if(this.getColsCount()!=this.getRowsCount()) {
			throw new IncompatibleOperandException("Matrica nije"
					+ " kvadratna - nije moguce izracunati determinantu!");
		}
		double[][] matrix= this.toArray();
		return determ(matrix);
	}
	
	
	
	/**
	 * Metoda za racunanje determinante double matrice.
	 * Koristim rekurzivni algoritam "Laplace expansion".
	 * @param matrix matrica ciju determinantu racunamo
	 * @return determinantu
	 */
	private static double determ(double[][] matrix){ 
		double sum=0; 
		int numSign;		
		if(matrix.length==1){  
			return(matrix[0][0]);
		}
		
		for(int i=0;i<matrix.length;i++){ 		
			double[][]smaller= new double[matrix.length-1][matrix.length-1]; 
			for(int row=1;row<matrix.length;row++){
				for(int col=0;col<matrix.length;col++){
					if(col<i){
						smaller[row-1][col]=matrix[row][col];
					}
					else if(col>i){
						smaller[row-1][col-1]=matrix[row][col];
					}
				}
			}
			if(i%2==0){ 
				numSign=1;
			}
			else{
				numSign=-1;
			}
			sum+=numSign*matrix[0][i]*(determ(smaller)); 
		}
		return(sum); 
	}
	
	/**
	 * Za inverz matrice sam koristio Gauss-Jordanovu metodu-
	 * URL: http://en.wikipedia.org/wiki/Gaussian_elimination
	 *  
	 */
	@Override
	public IMatrix nInvert() {
		if(this.determinant()==0) {
			throw new IncompatibleOperandException("Matrica je "
					+ "singularna - nije moguc inverz!");
		}
		double[][] matrix= this.toArray();
		matrix=invert(matrix);
		return new Matrix(this.getRowsCount(),this.getColsCount(),matrix,true);
		
	}
	
	/**
	 * Pomocna metoda koristena za racunanje inverza u metodi {@link #nInvert()}.
	 * @param a matrica
	 * @return matricu
	 */
    private static double[][] invert(double a[][]) 
    {
        int len = a.length;
        double finalMatrix[][] = new double[len][len];
        double tempMatrix[][] = new double[len][len];
        int index[] = new int[len];
        for (int i=0; i<len; ++i) 
            tempMatrix[i][i] = 1;
        
        gaussian(a, index);
        
        for (int i=0; i<len-1; ++i)
            for (int j=i+1; j<len; ++j)
                for (int k=0; k<len; ++k)
                    tempMatrix[index[j]][k]
                    	    -= a[index[j]][i]*tempMatrix[index[i]][k];
 
        for (int i=0; i<len; ++i) 
        {
            finalMatrix[len-1][i] = tempMatrix[index[len-1]][i]/a[index[len-1]][len-1];
            for (int j=len-2; j>=0; --j) 
            {
                finalMatrix[j][i] = tempMatrix[index[j]][i];
                for (int k=j+1; k<len; ++k) 
                {
                    finalMatrix[j][i] -= a[index[j]][k]*finalMatrix[k][i];
                }
                finalMatrix[j][i] /= a[index[j]][j];
            }
        }
        return finalMatrix;
    }
  
    /**
     * Pomocna metoda za racunanje inverza koristena u metodi {@link #invert(double[][])}.
     * @param a matrica
     * @param index index
     */
    public static void gaussian(double a[][], int index[]) {
        int inLen = index.length;
        double rescaleFac[] = new double[inLen];
        for (int i=0; i<inLen; ++i) 
            index[i] = i;
        for (int i=0; i<inLen; ++i) 
        {
            double c1 = 0;
            for (int j=0; j<inLen; ++j) 
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            rescaleFac[i] = c1;
        }
        int k = 0;
        for (int j=0; j<inLen-1; ++j) 
        {
            double pivotingElem1 = 0;
            for (int i=j; i<inLen; ++i) 
            {
                double pivotingElem0 = Math.abs(a[index[i]][j]);
                pivotingElem0 /= rescaleFac[index[i]];
                if (pivotingElem0 > pivotingElem1) 
                {
                    pivotingElem1 = pivotingElem0;
                    k = i;
                }
            }
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<inLen; ++i) 	
            {
                double pj = a[index[i]][j]/a[index[j]][j];
                a[index[i]][j] = pj; 
                for (int l=j+1; l<inLen; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }

    @Override
    public double[][] toArray() {
    	double[][] matrix= new double[this.getRowsCount()][this.getColsCount()];
		for(int row=0;row<this.getRowsCount();row++) {
			for(int col=0;col<this.getColsCount();col++) {
				matrix[row][col]=this.get(row, col);
			}
		}
		return matrix;
    }
    
    @Override
    public IMatrix nTranspose(boolean liveView) {
    	if(liveView) {
    		return new MatrixTransposeView(this);
    	} else {
    		double[][] newM= new double[this.getColsCount()][this.getRowsCount()];
    		for(int row=0;row<this.getColsCount();row++) {
    			for(int col=0;col<this.getRowsCount();col++) {
    				newM[row][col]=this.get(col, row);
    			}
    		}
    		return new Matrix(this.getColsCount(), this.getRowsCount(), newM, true);
    	}
    }
    
    @Override
    public IMatrix subMatrix(int row, int col, boolean liveView) {
    	if(this.getColsCount()<2 || this.getRowsCount()<2) {
    		throw new IncompatibleOperandException("Matrica je premala"
    				+ " za metodu subMatrix()!");
    	}
    	if(row>this.getRowsCount()-1 || col>this.getColsCount()) {
    		throw new IncompatibleOperandException("Zadan stupac/redak je "
    				+ "izvan dimenzija matrice!");
    	}
    	if(liveView) {
    		return new MatrixSubMatrixView(this, row, col);
    	} else {
    		int rowSmall=0;
    		int colSmall=0;
    		double[][]newMat=new double[this.getRowsCount()-1][this.getColsCount()-1];
    		for(int rowBig=0;rowBig<this.getRowsCount();rowBig++) {
    			for(int colBig=0;colBig<this.getColsCount();colBig++) {
    				if(rowBig!=row && colBig!=col) {
    					newMat[rowSmall][colSmall]=this.get(rowBig, colBig);
    					colSmall++;
    					if(!(colSmall<this.getColsCount()-1)) {
    						colSmall=0;
    						rowSmall++;
    					}
    				}
    			}
    		}
    		return new Matrix(this.getRowsCount()-1,this.getColsCount()-1,newMat,true);
    	}
    }
    
    @Override
    public IVector toVector(boolean liveView) {
    	if(this.getColsCount()!=1 && this.getRowsCount()!=1) {
    		throw new IncompatibleOperandException("Za konvertiranje matrice"
    				+ " u vektor broj stupaca ili broj redaka moraju biti 1");
    	}
    	if(liveView) {
    		return new VectorMatrixView(this);
    	} else {
    		double[] newV = new double[Math.max(this.getColsCount(),
    				this.getRowsCount())];
    		for(int i=0;i<this.getRowsCount();i++) {
    			for(int j=0;j<this.getColsCount();j++) {
    				newV[i+j]=this.get(i, j);
    			}
    		}
    		return new Vector(newV);
    	}
    }
    
	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		for(int row=0;row<this.getRowsCount();row++) {
			for(int col=0;col<this.getColsCount();col++) {
				this.set(row, col, value*this.get(row, col));
			}
		}
		return this;
	}
	
	@Override
	public IMatrix makeIdentity() {
		if(this.getColsCount()!=this.getRowsCount()) {
			throw new UnsupportedOperationException("Za pretvaranje matrice"
					+ " u jednicnu nuzno mora biti kvadratna");
		}
		for(int row=0;row<this.getRowsCount();row++) {
			for(int col=0;col<this.getColsCount();col++) {
				if(row==col) {
					this.set(row, col, 1);
				} else {
					this.set(row, col, 0);
				}
			}
		}
		return this;
	}
	
	@Override
	public String toString() {
	return toString(3);
	}
	
	/**
	 * Metoda za ispis matrice, cije se vrijednosti prikazuju ovisno o unesenom
	 * parametru decimals.
	 * 
	 * @param decimals na kolko ce se decimala ispisivati vrijednosti.
	 * @return string za ispis
	 */
	public String toString(int decimals) {
		double[][] matrix=this.toArray();
		String str="";
		for(int row=0;row<this.getRowsCount();row++) {
			str+="[ ";
			for(int col=0;col<this.getColsCount();col++) {
				str+=String.format("%."+decimals+"f ",matrix[row][col]);
			}
			str+="]\n";
		}
		return str;
	}
	
}

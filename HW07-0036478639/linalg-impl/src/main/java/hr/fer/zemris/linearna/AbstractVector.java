package hr.fer.zemris.linearna;


/**
 * Abstraktna klasa za vektor.
 * Za detaljniju dokumentaciju pogledati sucelje IVector
 * @author Teo Toplak
 *
 */
public abstract class AbstractVector implements IVector {

	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if(this.getDimension()!=other.getDimension()) {
			throw new IncompatibleOperandException("Vektori nisu"
					+ " kompatibilni za zbrajanje!");
		}
		for(int i= this.getDimension()-1; i>=0;i--) {
			this.set(i, this.get(i)+other.get(i));
		}
		return this;
	}
	
	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		return this.copy().add(other);
	}
	
	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if(this.getDimension()!=other.getDimension()) {
			throw new IncompatibleOperandException("Vektori nisu"
					+ "kompatibilni za oduzimanje!");
		}
		for(int i= this.getDimension()-1; i>=0;i--) {
			this.set(i, this.get(i)-other.get(i));
		}
		return this;
	}
	
	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		return this.copy().sub(other);
	}
	
	@Override
	public IVector scalarMultiply(double byValue) {
		for(int i=0;i<this.getDimension();i++) {
			this.set(i, this.get(i)*byValue);
		}
		return this;
	}
	
	@Override
	public IVector nScalarMultiply(double byValue) {
		return this.copy().scalarMultiply(byValue);
	}
	
	@Override
	public double norm() {
		double suma=0;
		for(int i=0;i<this.getDimension();i++) {
			suma+=Math.pow(this.get(i), 2);
		}
		//po definiciji normalizacije ako su svi elementi 0 onda vrati 1
		if(suma==0) {
			return 1;
		} else {
			return Math.sqrt(suma);
		}	
		
	}
	
	@Override
	public IVector normalize() {
		double norm= this.norm();
		for(int i=0;i<this.getDimension();i++) {
			this.set(i, this.get(i)/norm);
		}
		return this;
	}
	
	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}
	
	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		
		if(this.getDimension()!=other.getDimension()) {
			throw new IncompatibleOperandException("Vektori nisu "
					+ "kompatibilni za racunanje kosinusa kuta!");
		}
		double numerator=0;
		for(int i=0;i<this.getDimension();i++) {
			numerator+=this.get(i)*other.get(i);
		}
		double denominator=this.norm()*other.norm();
		return numerator/denominator;
	}
	
	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		return this.norm()*other.norm()*this.cosine(other);
	}
	
	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		if(this.getDimension()!=3 || other.getDimension()!=3) {
			throw new IncompatibleOperandException("Vektori nisu "
					+ "kompatibilni za vektorski produkt");
		}
		IVector newV = this.copy();
		newV.set(0, this.get(1)*other.get(2) - this.get(2)*other.get(1));
		newV.set(1, (this.get(0)*other.get(2) - this.get(2)*other.get(0))*-1);
		newV.set(2, this.get(0)*other.get(1) - this.get(1)*other.get(0));
		return newV;
	}
	
	@Override
	public IVector nFromHomogeneus() {
		if(this.getDimension()==1 || this.get(this.getDimension()-1)==0) {
			throw new UnsupportedOperationException("Vektor nije"
					+ " kompatibilan za transformaciju u radni prostor!");
		}
		IVector newVector = this.copyPart(this.getDimension()-1);
		for(int i=0;i<newVector.getDimension();i++) {
			newVector.set(i, newVector.get(i) / this.get(this.getDimension()-1));
		}
		return newVector;
	}
	@Override
	public IVector copyPart(int n) {
		IVector newV= this.newInstance(n);
		for(int i=0;i<n;i++) {
			newV.set(i, 0);
		}
		int minDim=Math.min(n, this.getDimension());
		for(int i=0;i<minDim;i++) {
			newV.set(i, this.get(i));
		}
		return newV;
	}
	
	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		if(liveView) {
			return new MatrixVectorView(this, true);
		} else {
			double[][] matrix = new double[1][this.getDimension()];
			for(int i=0;i<this.getDimension();i++) {
				matrix[0][i]=this.get(i);
			}
			return new Matrix(1, this.getDimension(), matrix, true);
		}
	}
	
	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		if(liveView) {
			return new MatrixVectorView(this, false);
		} else {
			double[][] matrix = new double[this.getDimension()][1];
			for(int i=0;i<this.getDimension();i++) {
				matrix[i][0]=this.get(i);
			}
			return new Matrix(this.getDimension(), 1, matrix, true);
		}
	}
	
	/**
	 * Metoda koja pretvara vektor u string namijenjen za ispis. Ovo je
	 * defaultna metoda koja ispisuje vrijednosti vektora na 3 decimale.
	 * @return string za ispis
	 */
	public String toString() {
		return toString(3);
	}
	
	
	/**
	 * Metoda koja pretvara vektor u string namijenjen za ispis.
	 * @param decimals broj decimala za ispis
	 * @return string za ispis
	 */
	public String toString(int decimals) {
		String str="[ ";
		for(int i=0;i<this.getDimension();i++) {
			str+=String.format("%."+decimals+"f ", this.get(i));
		}
		return str+"] \n";
	}
}

package hr.fer.zemris.java.complex;

import java.util.ArrayList;
import java.util.List;

/**
 * Complex polynom
 * @author Teo Toplak
 *
 */
public class ComplexPolynomial {


	/**
	 * List of factors
	 */
	private List<Complex> factorsList;
	
	/**
	 * Constructor taking variable number of factors
	 * @param factors factors
	 */
	public ComplexPolynomial(Complex ...factors) {
		factorsList = new ArrayList<>();
		for(Complex c : factors) {
			factorsList.add(c);
		}
	}
	
	/**
	 * Constructor taking factor list as argument
	 * @param factorsList factor list
	 */
	private ComplexPolynomial(List<Complex> factorsList) {
		this.factorsList=factorsList;
	}
	
	/**
	 * Returnes order of this polynom
	 * @return order of polynom
	 */
	public short order() {
		return (short)(factorsList.size()-1);
	}
	
	/**
	 * Multiplies polynomes.
	 * @param p polynom
	 * @return resulting polynom of this operation
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		List<Complex> newFacList= new ArrayList<>();
		for(int i=0;i<this.order()+p.order()+1;i++) {
			newFacList.add(Complex.ZERO);
		}
		List<Complex> firstFacList= p.getFactorsList();
		for(int order1=0;order1<firstFacList.size();order1++) {
			for(int order2=0;order2<factorsList.size();order2++) {
				newFacList.set(order1 + order2,
						newFacList.get(order1 + order2).add(
								firstFacList.get(order1).multiply(
										factorsList.get(order2))));
			}
		}
		return new ComplexPolynomial(newFacList);
	}
	
	/**
	 * Derives polynom
	 * @return result of derivation
	 */
	public ComplexPolynomial derive() {
		List<Complex> newFacList= new ArrayList<>();
		for(int i=0;i<factorsList.size()-1;i++) {
			newFacList.add(factorsList.get(i+1).multiply(new Complex(i+1,0)));
		}
		return new ComplexPolynomial(newFacList);
	}
	
	/**
	 * Computes polynomial value at given point z
	 * @param z interesting point
	 * @return polynomial value of given point
	 */
	public Complex apply(Complex z) {
		Complex finalComplex= new Complex();
		for(int i=0;i<factorsList.size();i++) {
			finalComplex = finalComplex.add(z.power(i).multiply(factorsList.get(i)));
		}
		return finalComplex;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for(int i=factorsList.size()-1;i>0;i--) {
			if(factorsList.get(i).getMagnitude()==0) {
				continue;
			}
			string.append("("+factorsList.get(i)+")z^"+i+"+");
		}
		string.append("("+factorsList.get(0)+")");
		return string.toString();
	}

	/**
	 * Factor list getter
	 * @return factor list
	 */
	private List<Complex> getFactorsList() {
		return factorsList;
	}
	
	
}

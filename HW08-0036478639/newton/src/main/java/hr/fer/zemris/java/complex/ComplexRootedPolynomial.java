package hr.fer.zemris.java.complex;

import java.util.ArrayList;
import java.util.List;

/**
 * Complex number represented through roots
 * @author Teo Toplak
 *
 */
public class ComplexRootedPolynomial {


	/**
	 * List of rootes
	 */
	private List<Complex> rootsList;
	
	/**
	 * Constructor which takes variable number of roots
	 * @param roots roots
	 */
	public ComplexRootedPolynomial(Complex ...roots) {
		rootsList = new ArrayList<>();
		for(Complex root : roots) {
			rootsList.add(root);
		}
	}
	
	public ComplexRootedPolynomial(List<Complex> rootsList) {
		this.rootsList=rootsList;
	}
	
	public int order() {
		return (short)(rootsList.size());
	}
	
	/**
	 * Computes polynomial value at given point z
	 * @param z interesting point
	 * @return polynomial value of given point
	 */
	public Complex apply(Complex z) {
		Complex c = Complex.ONE;
		for(Complex root : rootsList) {
			c = c.multiply(z.sub(root));
		}
		
		return c;
	}
	
	/**
	 * Converts this representation to {@link ComplexPolynomial} type
	 * @return {@link ComplexPolynomial} type of polynom
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial newPoly = new ComplexPolynomial(Complex.ONE);
		for(Complex root : rootsList) {
			newPoly = newPoly.multiply(new ComplexPolynomial(root.negate(),Complex.ONE));
		}
		return newPoly;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for(Complex root : rootsList) {
			string.append("(z"+ root.negate() + ")");
		}
		return string.toString();
	}
		
	/**
	 * Finds index of closest root for given complex number z that is within
	 * treshold. If there is no such root, returns -1.
	 * @param z complex number  
	 * @param treshold treshold
	 * @return index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index=-2;
		double minDistance = Double.MAX_VALUE;
		for(int i=0;i<rootsList.size();i++) {
			double distance=z.sub(rootsList.get(i)).getMagnitude();
			if(distance<treshold && distance<minDistance) {
				minDistance = distance;
				index = i;
			}
		}
		return index+1;
	}

}

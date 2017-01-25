package hr.fer.zemris.java.complex;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexPolynomial;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;

/**
 * This is testing class for {@link Complex} , {@link ComplexPolynomial} and
 * {@link ComplexRootedPolynomial} class.
 * 
 * @author Teo Toplak
 *
 */
public class ComplexPolynomialTest {

	
	private Complex c1;
	private Complex c2;
	private Complex c3;
	private Complex c4;
	private Complex c5;
	private Complex c6;
	private Complex c7;
	
	//test names without "Root" in it refer to ComplexPolynomal.class
	//i did most of the test by printing results, because of more efficient testing
	
	/*
	 * Looks like small amount of tests but coverage is above 93%-100% 
	 * for all classes since a lot of methods call each other
	*/
	
	public ComplexPolynomialTest() {
		c1 = new Complex(2,-4);
		c2 = new Complex(0,2);
		c3 = new Complex(-1,0);
		c4 = new Complex(3,3);
		c5 = new Complex(2,5);
		c6 = new Complex(-1,-1);
		c7 = new Complex();
	}
	
	@Test
	public void multiplyTest() {
		ComplexPolynomial poly1 = new ComplexPolynomial(c1,c2,c3);
		ComplexPolynomial poly2 = new ComplexPolynomial(c7,c4);
		System.out.println(poly1);
		System.out.println(poly2);
		System.out.println(poly1.multiply(poly2));
		Assert.assertTrue(poly1.multiply(poly2).order()==3);
	}
	
	@Test
	public void deriveTest() {
		ComplexPolynomial poly1 = new ComplexPolynomial(c1,c2,c3,c4,c5);
		ComplexPolynomial rez = poly1.derive();
		System.out.println(poly1);
		System.out.println(poly1.derive());
		System.out.println(rez);
	}
	
	@Test
	public void appyTest() {
		ComplexPolynomial poly1 = new ComplexPolynomial(c1,c6);
		Complex rez = poly1.apply(c3);
		System.out.println(poly1);
		System.out.println(rez);
	}
	
	@Test
	public void applyRootTest() {
		ComplexRootedPolynomial poly1 = new ComplexRootedPolynomial(c1,c2,c3);
		System.out.println(poly1.apply(c4));
		
	}
	
	@Test
	public void toComplexPolynomRootTest() {
		ComplexRootedPolynomial poly1 = new ComplexRootedPolynomial(c1,c2,c3);
		System.out.println(poly1.toComplexPolynom());
	}
	
	@Test
	public void indexOfClosestRootForRootTest() {
		ComplexRootedPolynomial poly1 = new ComplexRootedPolynomial(c1,c2,c3,c4,c5,c6,c7);
		Assert.assertTrue(poly1.indexOfClosestRootFor(new Complex(1,0.4),2) ==7 );
		
	}
	
	@Test
	public void divideComplexTest() {
		Assert.assertTrue(Math.abs(c1.divide(c5).getReal()+0.551724)<1e-06);
	}
	
	@Test
	public void parseComplexTest() {
		Assert.assertTrue(Complex.parseString("-1 + i0 ").equals(new Complex(-1,0)));
		Assert.assertTrue(Complex.parseString("1 ").equals(new Complex(1,0 )));
		Assert.assertTrue(Complex.parseString("i ").equals(new Complex(0,1 )));
		Assert.assertTrue(Complex.parseString(" 0 - i1").equals(new Complex( 0,-1)));
		Assert.assertTrue(Complex.parseString(" 0+i0").equals(new Complex(0,0 )));
		Assert.assertTrue(Complex.parseString(" i0").equals(new Complex(0,0 )));
		Assert.assertTrue(Complex.parseString(" 23 - i").equals(new Complex(23,-1 )));
		Assert.assertTrue(Complex.parseString("1i - 21 ").equals(new Complex( -21,1)));
		Assert.assertTrue(Complex.parseString("1i - 21 ").equals(new Complex( -21,1)));
		Assert.assertTrue(Complex.parseString("i - 21 ").equals(new Complex( -21,1)));
		Assert.assertTrue(Complex.parseString("i").equals(new Complex( 0,1)));
		Assert.assertTrue(Complex.parseString("-i").equals(new Complex( 0,-1)));
		Assert.assertTrue(Complex.parseString("1").equals(new Complex( 1,0)));
		Assert.assertTrue(Complex.parseString("-1").equals(new Complex( -1,0)));

	}
}

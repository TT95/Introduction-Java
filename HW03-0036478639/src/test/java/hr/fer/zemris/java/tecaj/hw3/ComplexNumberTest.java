package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class which represents tester for class ComplexNumber
 * @author Teo Toplak
 *
 */
public class ComplexNumberTest {
	
	@Test
	public void ComplexNumberTestParseAndGetters(){
		ComplexNumber test = ComplexNumber.parse("-3-4i");
		ComplexNumber expected=new ComplexNumber(-3,-4);
		Assert.assertTrue(test.getAngle()==expected.getAngle());
		Assert.assertTrue(-3 == test.getReal());
		Assert.assertTrue(5 == test.getMagnitude());
		Assert.assertTrue(-4 == test.getImaginary());
	}
	
	@Test
	public void ComplexNumberTestExample(){
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1];
		Assert.assertTrue(Math.abs(-1.618175419-c3.getReal())<1E-6);
	}
	
	@Test
	public void ComplexNumberTestMulAndDiv(){
		ComplexNumber c5 = new ComplexNumber(2, 3);
		ComplexNumber c6 = new ComplexNumber(1, 6);
		Assert.assertTrue(Math.abs(-0.243243243-c5.div(c6).getImaginary())<1E-6);
		Assert.assertTrue("-16.0+15.0i".equals(c5.mul(c6).toString()));
	}
	
	@Test
	public void ComplexNumberTestSub(){
		ComplexNumber c5 = new ComplexNumber(2, 3);
		ComplexNumber c6 = new ComplexNumber(1, 6);
		Assert.assertTrue("1.0-3.0i".equals(c5.sub(c6).toString()));
	}
	
	@Test
	public void ComplexNumberTestConstructor(){
		ComplexNumber c5 = new ComplexNumber(0, 3);
		ComplexNumber test = ComplexNumber.parse("3i");
		Assert.assertTrue(c5.toString().equals(test.toString()));
	}

	@Test
	public void ComplexNumberTestConstructors(){
		ComplexNumber c5 = ComplexNumber.fromImagnary(12);
		ComplexNumber c6 = ComplexNumber.fromReal(12);
		Assert.assertTrue(c5.toString().equals("+12.0i"));
		Assert.assertTrue(c6.toString().equals("12.0"));
	}
	
}

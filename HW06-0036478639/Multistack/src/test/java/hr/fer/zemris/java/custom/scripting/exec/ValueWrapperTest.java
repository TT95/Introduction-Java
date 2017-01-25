package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Test;

import junit.framework.Assert;

/**
 * Class testing ValueWrapper class.
 * @author Teo Toplak
 *
 */
@SuppressWarnings("deprecation")
public class ValueWrapperTest {

	
	@Test
	public void incrementTest() {
		ValueWrapper wrap=new ValueWrapper(12);
		wrap.increment(1);
		Assert.assertEquals(wrap.getValue(), 13);
		wrap.increment(1.1);
		Assert.assertEquals(wrap.getValue(), 14.1);
		wrap.increment("12.3");
		Assert.assertEquals(wrap.getValue(), 26.4);
		wrap.increment("1E+02");
		Assert.assertEquals(wrap.getValue(), 126.4);
	}
	
	@Test(expected=RuntimeException.class)
	public void parseExcepTest() {
		ValueWrapper wrap=new ValueWrapper(12);
		wrap.increment("as");
	}
	
	@Test
	public void decrementTest() {
		ValueWrapper wrap=new ValueWrapper(12);
		wrap.decrement(1);
		Assert.assertEquals(wrap.getValue(), 11);
		wrap.decrement(1.0);
		Assert.assertEquals(wrap.getValue(), 10.0);
	}
	
	@Test
	public void multiplyTest() {
		ValueWrapper wrap=new ValueWrapper(12);
		wrap.multiply(1);
		Assert.assertEquals(wrap.getValue(), 12);
		wrap.multiply(1.0);
		Assert.assertEquals(wrap.getValue(), 12.0);
	}
	
	@Test
	public void divideTest() {
		ValueWrapper wrap=new ValueWrapper(11);
		wrap.divide(2);
		Assert.assertEquals(wrap.getValue(), 5);
		wrap.divide(1.0);
		Assert.assertEquals(wrap.getValue(), 5.0);
	}
	
	@Test(expected=ArithmeticException.class)
	public void divideExcepTest() {
		ValueWrapper wrap=new ValueWrapper(11);
		wrap.divide(0);
	}
	
	@Test
	public void compareTest() {
		ValueWrapper wrap=new ValueWrapper(11);
		Assert.assertTrue(wrap.numCompare(1)>0);
		Assert.assertTrue(wrap.numCompare(12)<0);
		Assert.assertTrue(wrap.numCompare("1.1")>0);
	}
	
	@Test
	public void nullTest() {
		ValueWrapper wrap=new ValueWrapper(12);
		wrap.increment(null);
		Assert.assertEquals(wrap.getValue(), 12);
	}
	
	@Test(expected=RuntimeException.class)
	public void wrongVarExceptionTest() {
		ValueWrapper wrap=new ValueWrapper(12);
		wrap.increment(1.2f);
	}
	
}

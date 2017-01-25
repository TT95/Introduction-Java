package hr.fer.zemris.java.hw13;


import hr.fer.zemris.java.hw13.utility.TrigonometricUtil;
import hr.fer.zemris.java.hw13.utility.TrigonometricUtil.Trigo;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing for {@link TrigonometricUtil} class.
 * @author Teo Toplak
 *
 */
public class TrigonometricUtilTest {

	@Test
	public void testingValue1() {
		Trigo trigo = TrigonometricUtil.utility(2);
		Assert.assertTrue(trigo.getSin()==0.0349);
		Assert.assertTrue(trigo.getCos()==0.9994);
	}
	
	@Test
	public void testingValue2() {
		Trigo trigo = TrigonometricUtil.utility(14);
		Assert.assertTrue(trigo.getSin()==0.2419);
		Assert.assertTrue(trigo.getCos()==0.9703);
	}
	
	@Test
	public void testingValue3() {
		Trigo trigo = TrigonometricUtil.utility(90);
		Assert.assertTrue(trigo.getSin()==1.0);
		Assert.assertTrue(trigo.getCos()==0.0);
	}
	
	@Test
	public void testingValue4() {
		Trigo trigo = TrigonometricUtil.utility(50);
		Assert.assertTrue(trigo.getSin()==0.766);
		Assert.assertTrue(trigo.getCos()==0.6428);
	}
	
}

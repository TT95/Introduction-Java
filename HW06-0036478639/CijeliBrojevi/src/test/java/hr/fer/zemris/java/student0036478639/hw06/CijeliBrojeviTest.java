package hr.fer.zemris.java.student0036478639.hw06;

import org.junit.Assert;
import org.junit.Test;

/**
 * Klasa namijenjena testiranju klase CijeliBrojevi
 * @author Teo Toplak
 *
 */
public class CijeliBrojeviTest {

	@Test
	public void CijeliBrojeviJeNeparanTest() {
		CijeliBrojevi brojevi=new CijeliBrojevi();
		Assert.assertTrue(brojevi.jeNeparan(3));
		Assert.assertFalse(brojevi.jeNeparan(2));
	}
	
	@Test
	public void CijeliBrojeviJeParanTest() {
		CijeliBrojevi brojevi=new CijeliBrojevi();
		Assert.assertTrue(brojevi.jeParan(2));
		Assert.assertFalse(brojevi.jeParan(3));
	}
	
	@Test
	public void CijeliBrojeviJeProstTest() {
		CijeliBrojevi brojevi=new CijeliBrojevi();
		Assert.assertTrue(brojevi.jeProst(11));
		Assert.assertFalse(brojevi.jeProst(6));
	}
	
}

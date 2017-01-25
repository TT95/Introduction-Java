package hr.fer.zemris.java.hw14;


import hr.fer.zemris.java.hw14.model.Poll;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testiranje {@link Poll} klase.
 * ---------
 * Pošto stvarno nisam nigdje mogao izvuc nekakvu korist od
 * JUnit testova u ovoj zadaci (lakse mi je bilo provjeravat 
 * ispravnost sa najobicnijim syso debagiranjem), cisto
 * da zadovoljim uvjet pisanja testova napravio sam ovu klasu.  
 * ---------
 * @author Teo Toplak
 *
 */
public class PollTest {

	@Test
	public void testingValue1() {
		Poll poll =  new Poll();
		poll.setId(1);
		poll.setMessage("poll");
		poll.setTitle("anketa");
		Assert.assertTrue(poll.getId()==1);
		Assert.assertTrue(poll.getMessage().equals("poll"));
		Assert.assertTrue(poll.getTitle().equals("anketa"));
	}
	
	@Test
	public void testingValue2() {
		Poll poll =  new Poll();
		poll.setId(420);
		poll.setMessage("besmisleni test");
		poll.setTitle("servleti_u_srcu");
		Assert.assertTrue(poll.getId()==420);
		Assert.assertTrue(poll.getMessage().equals("besmisleni test"));
		Assert.assertTrue(poll.getTitle().equals("servleti_u_srcu"));
	}
}

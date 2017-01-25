
import hr.fer.zemris.java.hw15.utility.Encrypt;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing {@link Encrypt} class.
 * ---------
 * Since I didn't find use of JUnit tests in this homework
 * i created this almost useless testing just to meet homework
 * demandings.
 * ---------
 * @author Teo Toplak
 *
 */
public class EncryptTest {

	@Test
	public void testingValue1() {
		String hash = Encrypt.getHash("josko123");
		Assert.assertTrue(hash.equals("c4563e5c8f08ea9597f99357a143f23ddcf042dd"));
	}
	
	@Test
	public void testingValue2() {
		String hash = Encrypt.getHash("mamaMia");
		Assert.assertTrue(hash.equals("233257abd728600aa40e5f97ad8b41bcdb84adc5"));
	}
	
	@Test
	public void testingValue3() {
		String hash = Encrypt.getHash("amadeus12ImaTko");
		Assert.assertTrue(hash.equals("a06798aa770b81c74f75aeba7bd8dde95303bb97"));
	}
	
	@Test
	public void testingValue4() {
		String hash = Encrypt.getHash("upisiPassword");
		Assert.assertTrue(hash.equals("e53ad6112efabafbf67e099099ee8cdf3793b17c"));
	}
}

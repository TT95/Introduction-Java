package hr.fer.zemris.java.custom.scripting.exec;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class testing ObjectMultistack class.
 * @author Teo Toplak
 *
 */
public class ObjectMultistackTest {

	public ObjectMultistack multistack=new ObjectMultistack();
	
	public ObjectMultistackTest() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("year", price);
		Assert.assertEquals(multistack.peek("year"), price);
	}
	
	@Test
	public void peekTest() {
		Assert.assertEquals(multistack.peek("year").getValue(), 200.51);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void popTest() {
		multistack.pop("year");
		multistack.pop("year");
		multistack.pop("year");
	}
	
	@Test(expected=NoSuchElementException.class)
	public void peekTestException() {
		multistack.peek("josko");
	}
	
	@Test
	public void isEmptyTest() {
		Assert.assertTrue(multistack.isEmpty("josko"));
		Assert.assertFalse(multistack.isEmpty("year"));
	}
	
}

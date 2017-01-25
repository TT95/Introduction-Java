package hr.fer.zemris.java.student0036478639.hw07;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class made for testing method hextobyte in Crypto class.
 * @author Teo Toplak
 *
 */
public class HexToByteTest {

	@Test
	public void hextobyteTest1() {
		byte[] expected={
				0b00001101,
				0b00111101,
				0b01000100
		};
		String value="0d3d44";
		Assert.assertTrue(Arrays.equals(expected, Crypto.hextobyte(value)));
	}
	
	@Test
	public void hextobyteTest2() {
		byte[] expected={
				0b00100100,
				0b01000110,
				0b00011110
		};
		String value="24461e";
		Assert.assertTrue(Arrays.equals(expected, Crypto.hextobyte(value)));
	}
	
	@Test
	public void hextobyteTest3() {
		byte[] expected={
				0b00001101,
				0b00111101,
				0b01000100
		};
		String value="0d3d44";
		Assert.assertTrue(Arrays.equals(expected, Crypto.hextobyte(value)));
	}
	
	@Test
	public void hextobyteTest4() {
		byte[] expected={
				0b00100010,
				(byte) 0b10100100, //dont know why cast is needed
				0b01011000
		};
		String value="22a458";
		Assert.assertTrue(Arrays.equals(expected, Crypto.hextobyte(value)));
	}
}

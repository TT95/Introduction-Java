package hr.fer.zemris.java.hw15.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class which is used for encrypting 
 * any string provided. Contains only one method.
 * 
 * @author Teo Toplak
 *
 */
public class Encrypt {

	/**
	 * Returns enrypted value of password/string provided
	 * @param password password
	 * @return encrypted value
	 */
	public static String getHash(String password) {
		
			MessageDigest sha=null;
			try {
				sha = MessageDigest.getInstance("sha-1");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			byte[] hash = sha.digest(password.getBytes());
			return Encrypt.bytetohex(hash);
	}
	
	/**
	 * Converts array of bytes into hax value
	 * @param bytes array of bytes
	 * @return hey value
	 */
	private static String bytetohex(byte[] bytes) {
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
	    for ( int index = 0; index < bytes.length; index++ ) {
	        int v = bytes[index] & 0xFF;
	        hexChars[index * 2] = hexArray[v >>> 4];
	        hexChars[index * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars).toLowerCase();
	}
}

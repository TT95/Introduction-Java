package hr.fer.zemris.java.student0036478639.hw07;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class representing program that allows the user to encrypt/decrypt given file
 * using the AES crypto-algorithm and the 128-bit encryption key or calculate
 * and check the SHA-256 file digest. Accepts only command line arguments. There
 * are three operations possible: checksha, encrypt and decrypt. With keyword
 * checksha you will provide file for checking (i.e. checksha hw07test.bin).
 * With keywords encrypt and decrypt you will provide (1) input file (2) ouptu
 * file (i.e. encrypt hw07.pdf hw07.crypted.pdf)
 * 
 * @author Teo Toplak
 *
 */
public class Crypto {

	/**
	 * Method called when executing program.
	 * @param args read class javadoc for explanation
	 * @throws IllegalArgumentException problem with arguments input
	 */
	public static void main(String[] args) throws IOException {

		if (args[0].equals("checksha")) {
			if (args.length == 2) {
				checksha(args[1]);
			} else {
				throw new IllegalArgumentException("Wrong number of arguments!");
			}
		} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
			if (args.length == 3) {
				crypt(args[0], args[1], args[2]);
			} else {
				throw new IllegalArgumentException("Wrong arguments!");
			}
		} else {
			throw new IllegalArgumentException("Wrong arguments!");
		}

	}

	/**
	 * Method called for encrypting/decrypting.
	 * @param whichOne keyword which chooses operation
	 * @param inFile input file
	 * @param outFile output file
	 * @throws IOException when there is problem with console input
	 */
	private static void crypt(String whichOne, String inFile, String outFile) throws IOException {
		
		boolean encrypt;
		if(whichOne.equals("encrypt")) {
			encrypt=true;
		} else {
			encrypt=false;
		}
		BufferedReader readerConsole = new BufferedReader(
				new InputStreamReader(System.in)
				);
		
		System.out.print("Please provide password as hex-encoded "
				+ "text (16 bytes, i.e. 32 hex-digits): \n > ");
		String keyText=readerConsole.readLine();
		
		System.out.print("Please provide initialization vector "
				+ "as hex-encoded text (32 hex-digits): \n > ");
		String ivText=readerConsole.readLine();
		
		SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
		
		Path pIn = Paths.get(inFile);
		Path pOut = Paths.get(outFile);
		Cipher cipher;
		
		try (InputStream reader = Files.newInputStream(pIn,StandardOpenOption.READ)){
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			OutputStream writer = new FileOutputStream(pOut.toFile());
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			while(true) {
				byte[] buffer = new byte[4000];
				int numberOfBytes = reader.read(buffer);
				if(numberOfBytes < 1) {
					break;
				}
				writer.write(cipher.update(buffer,0,numberOfBytes));
			}
			byte[] finalDoc = cipher.doFinal();
			writer.write(finalDoc);
			writer.close();
		} catch (Exception e) {
			System.err.println("Problem with digesting!");
			System.exit(1);
		}
		
		
	}

	/**
	 * Method called for digesting operation.
	 * @param inFile input file
	 * @throws IOException when there is problem with console input.
	 */
	private static void checksha(String inFile) throws IOException {
		
		Path p = Paths.get(inFile);
		try(InputStream reader = Files.newInputStream(p,StandardOpenOption.READ)) {
			MessageDigest sha = MessageDigest.getInstance("sha-256");
			while(true) {
				byte[] buffer = new byte[1024];
				int numberOfBytes = reader.read(buffer);
				if(numberOfBytes < 1) {
					break;
				}
				sha.update(buffer,0,numberOfBytes);
			}
			byte[] hash = sha.digest();
			BufferedReader readerConsole = new BufferedReader(
					new InputStreamReader(System.in)
					);
			System.out.print("Please provide expected sha-256 digest for "
					+p.getFileName()+" : \n > ");
			byte[] input= hextobyte(readerConsole.readLine());
			if(Arrays.equals(input, hash)) {
				System.out.println("Digesting completed. Digest of "+ p.getFileName() +
						" matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of "+p.getFileName()
						+" does not match the expected digest.\nDigest was: " + bytetohex(hash));
			}
		} catch (Exception e) {
			System.err.println("Problem with digesting!");
			System.exit(1);
		}
		
	}
	
	/**
	 * Method which converts bytes array to string with hexadecimal values.
	 * @param bytes bytes array
	 * @return string with hexadecimal values.
	 */
	public static String bytetohex(byte[] bytes) {
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
	    for ( int index = 0; index < bytes.length; index++ ) {
	        int v = bytes[index] & 0xFF;
	        hexChars[index * 2] = hexArray[v >>> 4];
	        hexChars[index * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars).toLowerCase();
	}

	/**
	 * Method which converts string with hexadecimal values into bytes array.
	 * @param s string with hexadecimal values
	 * @return bytes array
	 */
	public static byte[] hextobyte(String s) {
		
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int index = 0; index < len; index += 2) {
			data[index / 2] = (byte) ((Character.digit(s.charAt(index), 16) << 4) + Character
					.digit(s.charAt(index + 1), 16));
		}
		return data;
	}
}

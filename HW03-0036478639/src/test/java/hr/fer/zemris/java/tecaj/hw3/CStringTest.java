package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class represents tester for class CString
 * @author Teo Toplak
 *
 */
public class CStringTest {

	@Test
	public void CStringTest1(){
		char[] test1char = {'M','a','r','k','o'};
		CString test1 = new CString(test1char,1,4);
		char [] test2char={'J','M','a','r','k','o'};
		CString test2 = new CString(test2char,2,4);
		Assert.assertTrue(test1.isEqual(test2));
	}
	
	@Test
	public void CStringTest2(){
		char[]charTest="Kara".toCharArray();
		CString test1 = new CString(charTest);
		char[] charTest2= "KaraMarko".toCharArray();
		CString novi = new CString(charTest2);
		char[] test3 = {'M','a','r','k','o'};
		CString test4 = new CString(test3,1,4);
		Assert.assertTrue(novi.endsWith(test4));
		Assert.assertTrue(novi.startsWith(test1));
	}
	
	@Test
	public void CStringTest3(){
		char[] josko="acccaaaabbaaaabbbaacccaabaaaabbbb".toCharArray();
		char[] bekavac="bb".toCharArray();
		CString akavac1 = new CString("AAAA".toCharArray());
		CString josko1 = new CString(josko);
		CString bekavac1= new CString(bekavac);
		Assert.assertEquals("acccaaaaAAAAaaaaAAAAbaacccaabaaaaAAAAAAAA",josko1.replaceAll(bekavac1, akavac1).toString());
		Assert.assertEquals("baaaaAAAAAAAA",josko1.replaceAll(bekavac1, akavac1).right(13).toString());
	}
	
	@Test
	public void CStringTest4(){
		CString test1=new CString("Josko");
		Assert.assertEquals("k",test1.substring(3, 4).toString());
		CString test2= new CString("baaaaAAAAAAAA");
		Assert.assertEquals("bTTTTAAAAAAAA",test2.replaceAll('a','T').toString());
	}
	
	@Test
	public void CStringTest5(){
		CString test1= new CString("ababa");
		CString test2= new CString("baaaaAAAAAAAA");
		Assert.assertEquals("abababaaaaAAAAAAAA",test1.add(test2).toString());
		Assert.assertEquals("baaa",test2.left(4).toString());
	}
	
	@Test
	public void CStringTest6(){
		CString test1= new CString("ababa");
		CString test2= new CString("dafgcababaffffggaf");
		Assert.assertTrue(test2.contains(test1));
	}
	
	@Test
	public void CStringTest7(){
		CString test= new CString("ababa");
		Assert.assertTrue(test.indexOf('b')==1);
	}
	
	@Test
	public void CStringTest8(){
		CString test= new CString("ababa");
		Assert.assertTrue(test.charAt(3)=='b');		
	}
	
	@Test
	public void CStringTest9(){
		CString test= new CString("abc");
		String test2="";
		for(char charTest : test.toCharArray()){
			test2+=charTest;
		}
		Assert.assertTrue(test2.equals("abc"));		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CStringTest10(){
		CString test= new CString("abc");
		test.right(-1);
		}
	
	@Test(expected=IllegalArgumentException.class)
	public void CStringTest11(){
		CString test= new CString("abc");
		test.left(-1);
		}
	
	@Test
	public void CStringTest12(){
		CString test1= new CString("abc");
		CString test2= new CString("abcabc");
		Assert.assertFalse(test1.contains(test2));		
		}
}

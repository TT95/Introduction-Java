package hr.fer.zemris.java.tecaj.hw4;

import java.util.Iterator;
import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;
import org.junit.Assert;
import org.junit.Test;


/**
 * Class represents tester for SimpleHashtable
 * @author Teo Toplak
 *
 */
public class SimpleHashtableTest {

	static SimpleHashtable hash = new SimpleHashtable(5);
	
	@Test
	public void SimpleHashtableTest1(){
		hash.put("Ivana", Integer.valueOf(2));
		hash.put("Ante", Integer.valueOf(2));
		hash.put("Jasna", Integer.valueOf(3));
		hash.put("Kristina", Integer.valueOf(5));
		hash.put("josko", Integer.valueOf(5)); 
		Assert.assertEquals(hash.get("Ivana"),2);
	}
	
	@Test
	public void SimpleHashtableTest12(){
		Assert.assertEquals(null, hash.get("darinka"));
	}
	
	@Test
	public void SimpleHashtableTest2(){
		Assert.assertTrue(hash.containsKey("josko"));
		Assert.assertFalse(hash.containsKey("osko"));
	}
	
	@Test
	public void SimpleHashtableTest3(){
		Assert.assertFalse(hash.isEmpty());
	}
	
	@Test
	public void SimpleHashtableTest4(){
		Assert.assertEquals(hash.size(),5);
	}
	
	@Test
	public void SimpleHashtableTest5(){
		Assert.assertFalse(hash.containsValue(4));
		Assert.assertTrue(hash.containsValue(2));
	}
	
	@Test
	public void SimpleHashtableTest6(){
		System.out.println(hash.toString());
		Assert.assertEquals("[ Ante=2 Ivana=2 Jasna=3 Kristina=5 josko=5 ]", hash.toString());
	}
	
	@Test
	public void SimpleHashtableTest7(){
		hash.remove("Jasna");
		Assert.assertEquals("[ Ante=2 Ivana=2 Kristina=5 josko=5 ]", hash.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void SimpleHashtableTest78(){
		hash.put(null,null);
	}
	
	@Test
	public void SimpleHashtableTest8(){
		hash.clear();
		Assert.assertTrue(hash.isEmpty());
	}
	
	@Test
	public void SimpleHashtableTest10(){
		SimpleHashtable examMarks = new SimpleHashtable();
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		String string;
		for(int i=0;i<25;i++){
			string=Integer.toString(i);
			examMarks.put(string, Integer.valueOf(5));
		}
		Iterator<SimpleHashtable.TableEntry> iter = examMarks.iterator();
		for(SimpleHashtable.TableEntry pair : examMarks) {
			if(pair.getKey().equals("Jasna")){
				Assert.assertEquals(2, pair.getValue());
			}
		}
		while(iter.hasNext()) {
			iter.next();
			iter.remove();
				
		}
	}
	
}

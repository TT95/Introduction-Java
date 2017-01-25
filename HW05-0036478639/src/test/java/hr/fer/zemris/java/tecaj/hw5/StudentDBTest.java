package hr.fer.zemris.java.tecaj.hw5;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.*;

/**
 * Class used for testing whole .hw5.db package with JUnit
 * @author Teo Toplak
 *
 */
@SuppressWarnings("deprecation")
public class StudentDBTest {

	static StudentDatabase database;
    static List<StudentRecord> list =new LinkedList<>();;
	
	@Test
	public void testStudentDatabaseForJMBAGAndFilter() {
		StudentRecord rec=new StudentRecord("0000000001","Toplak","Josko","3");
		list.add(rec);
		list.add(new StudentRecord("0000000002","Horvat","Duravko","3"));
		list.add(new StudentRecord("0000000003","Kasandra","Ivo","3"));
		list.add(new StudentRecord("0000000004","Smoljko","Boris","3"));
		list.add(new StudentRecord("0000000005","Buric","Kala","3"));
		list.add(new StudentRecord("0000000006","Trsto","Mirko","3"));
		list.add(new StudentRecord("0000000007","Buric","Anica","3"));
		list.add(new StudentRecord("0000000008","Anic","Jole","3"));
		list.add(new StudentRecord("0000000009","Komsic","Sinisa","3"));
		list.add(new StudentRecord("0000000010","Katanec","Marin","3"));
		database=new StudentDatabase(list);
		Assert.assertTrue(database.forJMBAG("0000000001").equals(rec));
		QueryFilter filter1=new QueryFilter("query lastName<\"L\"");
		QueryFilter filter2=new QueryFilter("query firstName<\"L\"");
		QueryFilter filter3=new QueryFilter("query jmbag<\"0000000004\"");
		QueryFilter filter4=new QueryFilter("query lastName>\"L\"");
		QueryFilter filter5=new QueryFilter("query lastName<=\"L\"");
		QueryFilter filter6=new QueryFilter("query lastName>=\"L\"");
		QueryFilter filter7=new QueryFilter("query lastName!=\"S*ko\"");
		QueryFilter filter8=new QueryFilter("query lastName=\"*uric\"");
		QueryFilter filter9=new QueryFilter("query lastName=\"B*\"");
		QueryFilter filter10 = new QueryFilter(
				"query jmbag=\"0000000003\" "
				+ "and lastName>\"A\" "
				+ "and jmbag=\"0000000005\" "
				+ "and jmbag=\"0000000004\"");
		filter1.writeTable(database);
		Assert.assertFalse(filter1.getJMBAG().isPresent());
		Assert.assertTrue(filter1.accepts(list.get(2)));
		Assert.assertTrue(filter2.accepts(list.get(6)));
		Assert.assertTrue(filter3.accepts(list.get(1)));
		Assert.assertTrue(filter4.accepts(list.get(5)));
		Assert.assertTrue(filter5.accepts(list.get(6)));
		Assert.assertTrue(filter6.accepts(list.get(5)));
		Assert.assertTrue(filter7.accepts(list.get(3)));
		Assert.assertTrue(filter9.accepts(list.get(6)));
		Assert.assertTrue(filter8.accepts(list.get(6)));
		Assert.assertFalse(filter10.accepts(list.get(6)));
		Assert.assertFalse(filter7.accepts(list.get(1)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testStudentDatabaseWithNull() {
		@SuppressWarnings("unused")
		StudentDatabase database=new StudentDatabase(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testStudentDatabaseWrongQeury1() {
		@SuppressWarnings("unused")
		QueryFilter filter9=new QueryFilter("query lastName=\"*a*\"");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testStudentDatabaseWrongQeury2() {
		@SuppressWarnings("unused")
		QueryFilter filter9=new QueryFilter("query lastName\"Josko\"");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testStudentDatabaseWrongQeury3() {
		@SuppressWarnings("unused")
		QueryFilter filter9=new QueryFilter("query lastName\"Josko\"");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testStudentDatabaseWrongQeury4() {
		@SuppressWarnings("unused")
		QueryFilter filter9=new QueryFilter("query LastName==\"Josko\"");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testStudentDatabaseWrongQeury5() {
		@SuppressWarnings("unused")
		QueryFilter filter9=new QueryFilter("query LastName=\"Josko\"");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testStudentDatabaseWrongQeury6() {
		@SuppressWarnings("unused")
		QueryFilter filter9=new QueryFilter("query lastName=\"Josko");
	}
	@Test
	public void testStudentDatabaseParser() {
		List<String> list=new LinkedList<>();
		list.add("0000000007	Čima	Sanjin	4");
		list.add("0000000008	Ćurić	Marko	5");
		DatabaseParser parser = new DatabaseParser(list);
		parser.makeDatabase();
	}

	
}

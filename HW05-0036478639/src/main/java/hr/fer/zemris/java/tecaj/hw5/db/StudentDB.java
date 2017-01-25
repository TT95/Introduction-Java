package hr.fer.zemris.java.tecaj.hw5.db;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Class which represents main program for querying provided database. Database
 * path is provided through command line argument, represents text file with
 * list of student info seperated by tab (jmbag,last name,first name and grade
 * respectively). Uses query as only keyword 
 * 
 * @author Teo Toplak
 *
 */
public class StudentDB {

	/**
	 * Method called when executing program.
	 * @param args path to database text file.
	 * @throws IOException when path to file is not found
	 */
	public static void main(String[] args) throws IOException {
		
		List<String> records= new LinkedList<>();
		try { 
		records = Files.readAllLines(
				Paths.get(args[0]),
				StandardCharsets.UTF_8
				);
		} catch (Exception e){
			System.out.println("Failed to set path to document");
			System.exit(-1);
		}
		
		DatabaseParser databaseParser= new DatabaseParser(records);
		StudentDatabase database= databaseParser.makeDatabase();
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in)
				);
		
		while(true){
			System.out.print("> ");
			String query= reader.readLine();
			try{
				QueryFilter queryFilter= new QueryFilter(query);
				if(queryFilter.getJMBAG().isPresent()) {
					System.out.println("Using index for record retrieval.");
				}
				queryFilter.writeTable(database);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		
	}
		
		
}
	
	


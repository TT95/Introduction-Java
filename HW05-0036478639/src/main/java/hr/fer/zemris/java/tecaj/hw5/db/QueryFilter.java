package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.comparison.*;
import hr.fer.zemris.java.tecaj.hw5.db.getters.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Class which is used for parsing query, making list of conditions based on
 * query and creating table based on database and conditions given. If during
 * parsing of the query an expression jmbag=SOMETHING (with no wildcards) is
 * found QueryFilter will remember this and offer fast record retrieval of
 * matched record in getJMBAG() method.
 * 
 * @author Teo Toplak
 *
 */
public class QueryFilter implements IFilter{

	/**
	 * String of everything after query word
	 */
	private String query;
	/**
	 * List of conditions
	 */
	private List<ConditionalExpression> listOfConditions;
	/**
	 * optional value for used for possible O(1) database search
	 */
	private String jmbag; 
	/**
	 * Flag used for telling if jmbag variable changed once already
	 */
	private boolean twoOrMoreJmbagReq;
	
	/**
	 * Constructor which receives query string (everything user entered after query keyword).
	 * @param query string which contains everything user entered after query keyword.
	 */
	public QueryFilter(String query) {
		
		twoOrMoreJmbagReq=false;
		listOfConditions=new LinkedList<>();
		String[] queryArray= query.split("query");
		if(queryArray.length!=2){
			throw new IllegalArgumentException("Wrong command input!");
		}
		this.query = queryArray[1];
		queryParser(this.query);
	}

	/**
	 * Method used for parsing query (one condition at a time) and making list of conditions.
	 * @param string one condition in query
	 */
	private void queryParser(String string) {
		
		IFieldValueGetter fieldValueGetter;
		String stringLiteral;
		IComparisonOperator comparisonOperator;
		//used for taking substring which represents literal, value depends on size of comparison operator
		int cuttingIndex;
		
		String[] queryArray= string.split("and");
		
		for(String query : queryArray) {
			//removing all blank spaces in string
			query=query.replaceAll("\\s","");
			//splitting if there is comparison operator with 2 characters
			String[] array= query.split("(?=<=|>=|!=)");
			if(array.length==2) {
				
				fieldValueGetter=whichFieldValueGetter(array[0]);
				
				String operator=array[1].substring(0,2);
				switch(operator) {
				case "<=": comparisonOperator = new SmallerEqualsCondition(); break;
				case ">=": comparisonOperator = new LargerEqualsCondition(); break;
				case "!=": comparisonOperator = new NotEqualsCondition(); break;
				default: throw new IllegalArgumentException("Wrong command input!"); 
				}
				
				cuttingIndex=2;
				
			} else {
				array= query.split("(?=<|>|=)");
				if(array.length!=2) {
					throw new IllegalArgumentException("Wrong command input!"); 
				}
				
				fieldValueGetter=whichFieldValueGetter(array[0]);
				
				String operator=array[1].substring(0,1);
				switch(operator) {
				case "<": comparisonOperator = new SmallerCondition(); break;
				case ">": comparisonOperator = new LargerCondition(); break;
				case "=": comparisonOperator = new EqualsCondition(); break;
				default: throw new IllegalArgumentException("Wrong command input!"); 
				}
				
				cuttingIndex=1;
			}
			
			// literal must start and end with "
			String literal= array[1].substring(cuttingIndex,array[1].length());
			if(!(literal.startsWith("\"") && literal.endsWith("\""))) {
				 throw new IllegalArgumentException("Wrong command input! (check legal use of \")"); 
			}
			
			//if literal contains more * throw exception
			stringLiteral=literal.substring(1,literal.length()-1);
			if(literal.lastIndexOf('*') != literal.indexOf('*')) {
				throw new IllegalArgumentException("Wrong command input! (Only one * is allowed)"); 
			}
			
			//use optional method for getting record with jmbag?
			if (!stringLiteral.contains("*")
					&& (comparisonOperator instanceof EqualsCondition)
					&& fieldValueGetter instanceof JmbagGetter) {
				if(jmbag!=null) {
					if(!jmbag.equals(stringLiteral)) {
						twoOrMoreJmbagReq=true;
						jmbag=null;
					}
				} else {
					if(!twoOrMoreJmbagReq) {
						jmbag=stringLiteral;
					}
				}
			} 
			
			listOfConditions.add(new ConditionalExpression(fieldValueGetter,
					stringLiteral, comparisonOperator));
			
		}
		
	}
	
	/**
	 * Method which accepts string with only string literal from query, and
	 * creates instance of IFieldValueGetter matching string.
	 * 
	 * @param string string with only string literal from query
	 * @return IFieldValueGetter 
	 */
	private IFieldValueGetter whichFieldValueGetter(String string) {
		
		string=string.trim();
		IFieldValueGetter fieldValueGetter;
		switch(string) {
		case "firstName": fieldValueGetter = new FirstNameGetter(); break;
		case "lastName" : fieldValueGetter = new LastNameGetter(); break;
		case "jmbag": fieldValueGetter = new JmbagGetter(); break;
		default: throw new IllegalArgumentException("Wrong command input!"); 
		}
		return fieldValueGetter;
	}

	/**
	 * Method which accepts record if it matches condtitions in conditions list.
	 * @return true if record matches condtitions
	 */
	public boolean accepts(StudentRecord record) {
		
		for(ConditionalExpression condition : listOfConditions) {
			
			IComparisonOperator comparisonOperator = condition.getComparisonOperator();
			IFieldValueGetter fieldValueGetter = condition.getFieldValueGetter();
			String stringLiteral = condition.getStringLiteral();
			
			String recordString = fieldValueGetter.get(record);
			if(stringLiteral.contains("*")) {
				//if record string is smaller than stringLiteral return false instantly
				if(recordString.length() < stringLiteral.length()-1) {
					return false;
				}
				if(stringLiteral.indexOf('*') == 0) {
					if(!recordString.endsWith(stringLiteral.substring(1,stringLiteral.length()))) {
						return false;
					}
					stringLiteral=stringLiteral.substring(1,stringLiteral.length());
				}else if(stringLiteral.indexOf('*') == stringLiteral.length()-1) {
					if(!recordString.startsWith(stringLiteral.substring(0,stringLiteral.length()-1))) {
						return false;
					}
					stringLiteral=stringLiteral.substring(0,stringLiteral.length()-1);
				} else {
					String[] array = stringLiteral.split("\\*");
					if (!(recordString.endsWith(array[1]) && recordString.startsWith(array[0]))) {
						return false;
					}
					stringLiteral=stringLiteral.substring(0,stringLiteral.indexOf('*'));
				}
				//becase i already checked if record matches literal
				if(comparisonOperator instanceof EqualsCondition) {
					return true;
				}
			}
			 
			if(!comparisonOperator.satisfied(recordString,stringLiteral)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method which returns jmbag condition (used for fast retriveal of record).
	 * @return optional string jmbag
	 */
	public Optional<String> getJMBAG() {
		if(jmbag!=null) {
			return Optional.of(jmbag);
		}
		return Optional.empty();
	}
	
	/**
	 * Method which creates table with given database.
	 * @param database database of records
	 */
	public void writeTable(StudentDatabase database) {
		
		int biggestLastName=0;
		int biggestFirstName=0;
		List<StudentRecord> list= new LinkedList<>();
		if(jmbag!=null) {
			list.add(database.forJMBAG(jmbag));
		} else { 
			list = database.filter(this);
		}
		for(StudentRecord record : list) {
			if(record.getFirstName().length()>biggestFirstName) {
				biggestFirstName=record.getFirstName().length();
			}
			if(record.getLastName().length()>biggestLastName) {
				biggestLastName=record.getLastName().length();
			}
		}
		if(list.size()!=0) {
			writeRim(biggestLastName,biggestFirstName);
		}
		for(StudentRecord record : list) {
			System.out.format("| %-10s | %-" + biggestLastName + "s | %-"
					+ biggestLastName + "s | %s |", record.getJmbag(),
					record.getLastName(), record.getFirstName(),
					record.getFinalGrade());
			System.out.println("");
		}
		if(list.size()!=0) {
			writeRim(biggestLastName,biggestFirstName);
		}
		System.out.println("Records selected: "+ list.size());
	}

	/**
	 * Method used in writeTable() method which creates rim for table.
	 * @param biggestLastName biggest last name string in table
	 * @param biggestFirstName biggest first name string in table
	 */
	private void writeRim(int biggestLastName, int biggestFirstName) {
		System.out.print("+============+=");
		for(int i=0;i<biggestLastName;i++) {
			System.out.print("=");
		}
		System.out.print("=+=");
		for(int i=0;i<biggestFirstName;i++) {
			System.out.print("=");
		}
		System.out.println("=+===+");
	}
	
}

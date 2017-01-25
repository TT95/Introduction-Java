package hr.fer.zemris.java.trazilica;

import hr.fer.zemris.java.trazilica.vector.Vector;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Search engine using TF-IDF vector strategy.
 * Accepts command line argument representing path to folder with documents to process for searching. <p>
 * Shell accepts several basic commands:<br>
 * -query: arguments are words which will be searched in provided documents<br>
 * -type: argument is number of document whose text will appear on screen<br>
 * -results: writes results of last query<br>
 * -exit: terminates shell<br>
 * <p>
 * Be aware that program can create vectors for some time; program will write current progress of vector
 * creation.
 * @author Teo Toplak
 *
 */
public class Konzola {

	/**
	 * bag of words for provided documents
	 */
	private static Set<String> vocabularSet;
	/**
	 * table with x-words from vocabular, y-documents provided, value-number of occurences
	 */
	private static HashMap<String,Map<String,Integer>> vocabular;
	/**
	 * list of stopwords
	 */
	private static List<String> stopwords;
	/**
	 * vectors created from documents given
	 */
	private static Map<String, Vector> vectors;
	/**
	 * number of documents provided
	 */
	private static double numOfArticles;
	/**
	 * result of last query
	 */
	private static String lastQuery;
	/**
	 * result of query, key is koef for similarity of vectors,
	 * value is path to document
	 */
	private static TreeMap<Double,String> result;
	
	/**
	 * Method called when executing program.
	 * Accepts command line arguments (read class javadoc)
	 * @param args path to folder with documents to process for searching
	 * @throws IOException when I/O error occures
	 */
	public static void main(String[] args) throws IOException {

		if(args.length!=1) {
			System.out.println("Wrong command line argument!");
			System.exit(1);
		}
		
		System.out.println("Go take a one minute break :)\nCreating vectors..");
		try {
			Thread.currentThread();
			Thread.sleep(4000);
		} catch (InterruptedException ignorable) { }
		initSearchEngine(args[0]);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.println("Enter command:");
			String command = reader.readLine();
			String[] arr = command.split("\\s+");
			switch (arr[0]) {
			case "exit":
				System.out.println("Exiting shell..");
				System.exit(1);
				break;
			case "query":
				queryCommand(arr);
				break;
			case "type":
				typeCommand(arr);
				break;
			case "results":
				if(lastQuery==null) {
					System.out.println("First enter query!");
					break;
				}
				System.out.println(lastQuery);
				break;
			default:
				System.out.println("Wrong command.");
				break;
			}
		}
	}

	/**
	 * Type command from shell.
	 * Writes selected text file of
	 * document presented in shell.
	 * @param arr commad in array seperated in words
	 */
	private static void typeCommand(String[] arr) {
		if(arr.length!=2) {
			System.out.println("Wrong arguments!");
			return;
		}
		if(result==null) {
			System.out.println("First enter query!");
			return;
		}
		int num = Integer.parseInt(arr[1]);
		int i=0;
		for (Map.Entry<Double, String> entry : result.entrySet()) {
			if(i==num) {
				File file = new File(entry.getValue());
				try {
					String text = new String(Files.readAllBytes(file.toPath()));
					System.out.println(text);
				} catch (IOException ignorable) { }
				break;
			}
			i++;
		};
		
	}


	/**
	 * Method called when users uses query command.
	 * It creates one new vector for words provided and 
	 * uses TF-IDF vector strategy for calculating closest matches.
	 * @param command command from user spllited to separate words 
	 */
	private static void queryCommand(String[] command) {
		List<String> words = new ArrayList<String>();
		for(int i=1;i<command.length;i++) {
			if(vocabularSet.contains(command[i])) {
				words.add(command[i].toLowerCase());
			}
			
		}
		
		System.out.println(words);
		String queryText="";
		for(String s : words) {
			queryText+=s;
		}
		
		System.out.println("Best 10 results:");
		double[] elements = new double[vocabularSet.size()];
		//filling ocurrences
		int count=0;
		double value;
		for (Map.Entry<String,Map<String,Integer>> entry : vocabular.entrySet()) {
			Map<String,Integer> map = (Map<String, Integer>) entry.getValue();
			int sum=0;
			for (Map.Entry<String,Integer> entryMap : map.entrySet()) {
				if(entryMap.getValue()>=1) {
					sum++;
				}
			};
			
			value=0;
			if(words.contains(entry.getKey())) {
				if(sum==0) {
					value= countOccurences(queryText,(String)entry.getKey());
				} else {
					value= countOccurences(queryText,(String)entry.getKey())
							*Math.log(numOfArticles/sum);
				}
			}
			elements[count] = value;
			count++;
		};
		Vector queryVector = new Vector(elements);
		result = new TreeMap<Double, String>(Collections.reverseOrder());
		for (Map.Entry<String, Vector> entry : vectors.entrySet()) {
		    Vector vec = entry.getValue();
		    Double res = vec.scalarProduct(queryVector)/(queryVector.norm()*vec.norm());
		    if(res>0) {
		    	result.put(res, entry.getKey());
		    }
		}
		count=0;
		lastQuery = "";
		for (Map.Entry<Double, String> entry : result.entrySet()) {
			lastQuery+="["+count+"]"+" ("+(Double)entry.getKey()+") " + " "+(String)entry.getValue()+"\n";
			count++;
		};
		System.out.println(lastQuery);
	}

	/**
	 * Initializes search engine.
	 * Creates TF-IDF vectors for all documents.
	 * @param arg path to directory with documents provided.
	 */
	private static void initSearchEngine(String arg) {
		
		//creating vocabular
		
		File articlesDir = new File(arg);
		if(!articlesDir.exists()) {
			System.out.println("Path to articles doesnt exist!");
		}
		try {
			stopwords = Files.readAllLines(Paths.get("vocabular/croatian_stopwords.txt"));
		} catch (IOException e1) {
			System.out.println("Problem reading stopwords!");
			System.exit(1);
		}
		File[] articles = articlesDir.listFiles();
		numOfArticles = articles.length;
		vocabularSet = new HashSet<>();
		for (File article : articles) {
			try {
				String text = new String(Files.readAllBytes(article.toPath()));
				text = text.toLowerCase();
				//splits by non-alphabetic character but leaves some strings empty
				String[] dirtVocabular = text.split("\\P{L}+");
				for(String word : dirtVocabular) {
					word = word.trim();
					word=word.toLowerCase();
					if(!word.isEmpty() && !stopwords.contains(word)) {
						vocabularSet.add(word);
					}
				}
			} catch (IOException e) {
				System.out.println("Problem creating vocabular!");
				System.exit(1);
			}
		}
		System.out.println("Size of vocabular is "+vocabularSet.size()+" words");
		
		//filling occurences of words for articles
		vocabular = new HashMap<>();
		int count=0;
		for(String s : vocabularSet) {
			count++;
			System.out.println("Creating vectors: "+vocabularSet.size()+" / "+count);
			Map<String,Integer> map = new HashMap<String, Integer>();
			for (File article : articles) {
				try {
					String text = new String(Files.readAllBytes(article.toPath()));
					map.put(article.toString(), countOccurences(text,s));
				} catch (IOException e) {
					System.out.println("Problem creating vocabular!");
					System.exit(1);
				}
			}
			vocabular.put(s, map);
		}


		//creating vectors
		vectors = new HashMap<>();
		for (File article : articles) {
			double[] elements = new double[vocabularSet.size()];
			count=0;
			for (Map.Entry<String,Map<String,Integer>> entry : vocabular.entrySet()) {
				Map<String,Integer> map = (Map<String, Integer>) entry.getValue();
				int sum=0;
				for (Map.Entry<String,Integer> entryMap : map.entrySet()) {
					if(entryMap.getValue()>=1) {
						sum++;
					}
				};
				double value;
				if(sum==0) {
					value = map.get(article.toString());
				} else {
					value = map.get(article.toString())*Math.log(numOfArticles/sum);
				}
				
				elements[count] = value;
				count++;
			};
			vectors.put(article.toString(),new Vector(elements));
		}
	}
	
	/**
	 * Counts number of occurences of given substring in string.
	 * @param str string to be searched for matched substrings
	 * @param findStr substring searched in bigger string
	 * @return number of occurences
	 */
	private static Integer countOccurences(String str, String findStr) {
	    int lastIndex = 0;
	    int count =0;
	    str = str.toLowerCase();
	    while(true){
	           lastIndex = str.indexOf(findStr,lastIndex);
	           
	           if( lastIndex != -1){
	                 count ++;
	           } else {
	        	   break;
	           }
	           lastIndex+=findStr.length();
	    }
	    return count;
	}
}

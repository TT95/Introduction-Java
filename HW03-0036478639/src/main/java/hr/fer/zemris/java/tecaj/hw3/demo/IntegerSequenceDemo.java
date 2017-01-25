package hr.fer.zemris.java.tecaj.hw3.demo;
import hr.fer.zemris.java.tecaj.hw3.IntegerSequence;

/**
 * Class demonstrates use of IntegerSequence class.
 * @author Teo Toplak
 *
 */
public class IntegerSequenceDemo {

	/**
	 * Method called when executing program.
	 * Uses short version of for loop for demonstration.
	 * @param args
	 */
	public static void main(String[] args) {
		
		IntegerSequence range = new IntegerSequence(1, 11, 2);
		for(int i : range) {
			for(int j : range) {
			System.out.println("i="+i+", j="+j);
			}
		}
	}

}

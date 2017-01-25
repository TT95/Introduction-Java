package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Teo Toplak
 * @version 1.0
 */
public class HofstadterQ {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Argumetni su objasnjeni u nastavku. 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		if(args.length==0){
			System.err.println("Unesi argument!");
			System.exit(1);	
		}
		long i= Long.parseLong(args[0]);
		if(i<0){
			System.out.println("Unesi pozitivan argument!");
			System.exit(1);	
		}
		long x=hof(i);
		System.out.println("You requested calculation of " + i +". number of Hofstadter's Q-sequence."
				+ " The requested number: " + x);
	}

	/**
	 * Metoda racuna i-ti element Hofstadter-ove Q sekvence
	 * @param i redni broj elementa sekvence koji zelimo doznati
	 * @return vraca trazeni element sekvence
	 */
	public static long hof(long i){
		if(i==1 || i==2) return 1;
		return hof(i - hof(i-1)) + hof(i - hof(i - 2));
	}
}

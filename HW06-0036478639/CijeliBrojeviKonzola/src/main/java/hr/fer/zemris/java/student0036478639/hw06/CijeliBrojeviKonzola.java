package hr.fer.zemris.java.student0036478639.hw06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Klasa koja u beskonačnoj petlji čita brojeve koje korisnik unosi (jedan po
 * retku) i ispisuje informacije o tom broju sta se tice parnosti, neparnosti i
 * proste karakteristike broja.
 * 
 * @author Teo Toplak
 *
 */
public class CijeliBrojeviKonzola {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * O samom programu detaljnije u opisu klase. 
	 * @param args argumenti komandne linije
	 * @throws IOException iznimka kada dode do greske u ocitavanju znakova
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in)
				);
		
		while(true) {
			System.out.println("Unesite broj:");
			String brojString=reader.readLine();
			int broj=Integer.parseInt(brojString);
			CijeliBrojevi brojevi= new CijeliBrojevi();
			System.out.println(
					"Paran: "+
					naHrvatski(brojevi.jeParan(broj))+
					" Neparan: "+
					naHrvatski(brojevi.jeNeparan(broj))+
					" Prost: "+
					naHrvatski(brojevi.jeProst(broj)));
					
		}
	}
	
	public static String naHrvatski(boolean bool) {
		if(bool==true) {
			return "DA";
		}
		return "NE";
	}

}

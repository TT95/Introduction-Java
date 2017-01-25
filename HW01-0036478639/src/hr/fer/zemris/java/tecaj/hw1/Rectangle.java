
package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Teo Toplak
 * @version 1.0
 */

public class Rectangle {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Argumetni su objasnjeni u nastavku. 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args)  throws IOException{
		
		double width;
		double height;
		
		if(args.length!=0){
			if(args.length!=2){
				System.err.println("Invalid number of arguments was provided.");
				System.exit(1);	
			}
			width=Double.parseDouble(args[0]);
			height=Double.parseDouble(args[1]);
		} 
		else{			
			width=uzmi("width");
			height=uzmi("height");
		}
			System.out.printf("You have specified a rectangle with width %1.1f and height %1.1f Its area "
	+ "is %1.1f and its perimeter is %1.1f. %n", width, height,  width*height, (2* width)+(2*height));			
	
	
}
	
	/**
	 * Metoda sluzi za primanje podataka o karakteristikama pravokutnika, 
	 * te provjerava valjanost unosa.
	 * @param a naziv karakteristike koja se unosi
	 * @return vrijenost unesene karakteristike pravokutnika.
	 */

	public static double uzmi(String a)  throws IOException{
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in)
				);
		double x=0;
		do{
			System.out.println("Please provide " + a + ": ");
			String ulaz= reader.readLine();
			if(ulaz == null || ulaz.trim().isEmpty()){
				System.out.println("Nothing was given.");
				continue;
			}
			x= Double.parseDouble(ulaz);
			if(x<0){
				System.out.println(a + " is negative.");
				continue;
			}
			break;
	}while(true);
	return x;	
	}
	
}
package hr.fer.zemris.java.tecaj.hw1;

/**
 * @author Teo Toplak
 * @version 1.0
 */
public class Roots {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Argumetni su objasnjeni u nastavku. Program racuna n-ti korijen 
	 * kompleksnog broja zadanog iz komandne linije.
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		
		if(args.length!=3){
			System.out.println("Krivi broj argumenata!");
			System.exit(1);
		}
		
		double x=Double.parseDouble(args[0]);
		double y=Double.parseDouble(args[1]);
		double r=Double.parseDouble(args[2]);
		double arg=Math.atan2(y,x);
		double aps=Math.hypot(x,y);
		aps= Math.pow(aps, 1/r);
		
		for(double i=0;i<r;i++){
			System.out.format("%1.0f) %1.1f + %1.1fi %n",i,aps*Math.cos((arg+2*i*Math.PI)/r), aps*Math.sin((arg+2*i*Math.PI)/r));
		}
	}

}

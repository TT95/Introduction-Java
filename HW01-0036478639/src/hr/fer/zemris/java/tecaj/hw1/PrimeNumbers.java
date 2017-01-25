package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Teo Toplak
 * @version 1.0
 */
public class PrimeNumbers {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Argumetni su objasnjeni u nastavku. Program racuna n-ti prosti broj.
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		
		if(args.length!=1){
			System.out.println("Greska sa argumentima!");
			System.exit(1);
		}
		double n=Double.parseDouble(args[0]);
		if(n<0){
			System.out.println("Unesi pozitivan argument!");
			System.exit(1);
		}
		boolean prost;
		double broj=2;
		int prostih=0;
		while(prostih!=n){
			prost=true;
			for(double i=2;i<=Math.sqrt(broj);i++){
				if(broj % i == 0){
					prost=false;
					break;
				}
			}
			if(prost==true){
				prostih++;
				System.out.format("%d. %1.0f %n",prostih, broj);
			}
			broj++;
		}
	}

}

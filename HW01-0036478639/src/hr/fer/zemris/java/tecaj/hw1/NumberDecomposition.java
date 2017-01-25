package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Teo Toplak
 * @version 1.0
 */
public class NumberDecomposition {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Argumetni su objasnjeni u nastavku. 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		if(args.length!=1){
			System.out.println("Greska sa argumentima!");
			System.exit(1);
		}
		double n=Double.parseDouble(args[0]);
		if(n<1){
			System.out.println("Unesi argument veci od 1!");
			System.exit(1);
		}
		int j=1;
		while(n!=1){
			for(double i=2;i<=n;i++){
				if(n % i == 0){
					System.out.format("%d. %1.0f %n", j,i);
					n/=i;
					j++;
					break;
				}
			}
		}

	}

}

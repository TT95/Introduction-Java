package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Teo Toplak
 * @version 1.0
 */

class ProgramListe {

	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
 }

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Argumetni su objasnjeni u nastavku. 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;

		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
	
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
 
		cvor = sortirajListu(cvor);

		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);

		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
		
 }
	
	/**
	 * Metoda se koristi za doznaju velicine zadane liste.
	 * @param cvor cvor liste ciju velicinu zelimo doznati.
	 * @return vraca velicinu liste.
	 */
	
	static int velicinaListe(CvorListe cvor) {
 
	 CvorListe pm=cvor;
	 int suma=0;
	 for(;pm!=null;pm=pm.sljedeci){
		 suma++;
	 }
	 return suma;
 }
 
	/**
	 * Metoda sluzi za ubacivanje elemenata (cvorova) u listu.
	 * @param prvi elemnt liste u koju dodajemo novi element.
	 * @param podatak podatak koji zelimo pospremiti u listu
	 * @return vraca prvi element liste
	 */
	static CvorListe ubaci(CvorListe prvi, String podatak) {
	 
	 CvorListe novi= new CvorListe();
	 novi.sljedeci=null;
	 novi.podatak=podatak;
	 if(prvi==null){
		 return novi;
	 }else{
		 CvorListe pm=prvi;
		 for(;pm.sljedeci!=null ;pm=pm.sljedeci);
		 pm.sljedeci=novi;		 
		 return prvi;
	 }
 }
 
	/**
	 * Metoda za ispisivanje elemenata liste.
	 * @param cvor prvi element liste
	 */
	static void ispisiListu(CvorListe cvor) {
 
	  for(;cvor!=null;cvor=cvor.sljedeci){
		  System.out.println(cvor.podatak);
	  }
 }

	/**
	 * Metoda za sortiranje liste.
	 * @param cvor prvi element liste
	 * @return vraca prvi element liste
	 */
	static CvorListe sortirajListu(CvorListe cvor) {

	 CvorListe pm1;
	 CvorListe pm2;
	 String pm;
	 
	 for(pm1=cvor;pm1.sljedeci!=null ;pm1=pm1.sljedeci){
		 for(pm2=pm1;pm2!=null ;pm2=pm2.sljedeci){
			 if(((pm1.podatak).compareTo(pm2.podatak))>0){
				 pm=pm1.podatak;
				 pm1.podatak=pm2.podatak;
				 pm2.podatak=pm;
			 }
		 }
	 }
	 return cvor;
 }
}

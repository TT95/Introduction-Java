package hr.fer.zemris.java.tecaj.hw1;



/**
 * 
 * @author Teo Toplak
 * @version 1.0
 */
class ProgramStabla {

	static class CvorStabla {
		CvorStabla lijevi;
		CvorStabla desni;
		String podatak;
	}
 
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Argumetni su objasnjeni u nastavku. 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		CvorStabla cvor = null;

		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		cvor = ubaci(cvor, "Anamarija");
		cvor = ubaci(cvor, "Vesna");
		cvor = ubaci(cvor, "Kristina");

		System.out.println("Ispisujem stablo inorder:");
		ispisiStablo(cvor);

		int vel = velicinaStabla(cvor);
		System.out.println("Stablo sadrzi elemenata: "+vel);

		boolean pronaden = sadrziPodatak(cvor, "Ivana");
		System.out.println("Trazeni podatak je pronaden: "+pronaden);
 }
	
	/**
	 * Metoda sluzi za provjeru prisutnosti nekog elementa u stablu
	 * @param korijen korijen stabla unutar kojeg trazimo
	 * @param podatak podatak koji trazimo
	 * @return da li stablo sadrzi ili ne odredeni element (podatak)
	 */
 static boolean sadrziPodatak(CvorStabla korijen, String podatak) {
	 if(korijen==null) return false;
	 if(podatak.equals(korijen.podatak)){
		 return true;
	 }
	 return sadrziPodatak(korijen.desni,podatak) || sadrziPodatak(korijen.lijevi,podatak);
 }

 /**
  * Metodom doznajemo velicinu stabla
  * @param cvor korijen stabla
  * @return velicina stabla
  */
 static int velicinaStabla(CvorStabla cvor) {
	 if(cvor==null) return 0;
	 return velicinaStabla(cvor.lijevi) + velicinaStabla(cvor.desni) + 1;
 }
 
 /**
  * Metoda sluzi za dodavanje elementa u stablo.
  * @param korijen korijen stabla.
  * @param podatak podatak koji zelimo ubaciti.
  * @return vraca korijen stabla
  */
 static CvorStabla ubaci(CvorStabla korijen, String podatak) {
	 
	 CvorStabla novi= new CvorStabla();
	 novi.lijevi=null;
	 novi.desni=null;
	 novi.podatak=podatak;
	 
	 if(korijen==null){
		 return novi;
	 }
	 else{
		 if(korijen.podatak.compareTo(podatak)<0){
		 korijen.desni=ubaci(korijen.desni,podatak);
		 return korijen;
		 }else{
		 korijen.lijevi= ubaci(korijen.lijevi,podatak);
		 return korijen;
		 }
	 }
	 
 }

 /**
  * Metoda koja ispisuje elemente stabla.
  * @param cvor korijen stabla
  */
 static void ispisiStablo(CvorStabla cvor) {
 
	 if(cvor!=null){
		 ispisiStablo(cvor.lijevi);
		 System.out.println(cvor.podatak);
		 ispisiStablo(cvor.desni); 
	 }
 }

}
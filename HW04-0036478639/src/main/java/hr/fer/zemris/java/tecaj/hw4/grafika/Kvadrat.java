package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Klasa koja predstavlja kvadrat.
 * @author Teo Toplak
 *
 */
public class Kvadrat extends Pravokutnik{
	
	/**
	 * staticka konstanta za stvaratelja geometrijskog lika
	 */
	public static final StvarateljLika STVARATELJ = new LikKvadratStvaratelj();
	
	/**
	 * Konstruktor koji izraduje instancu kvadrata.
	 * @param ime ime
	 * @param x x koordinata gornjeg-lijevog ugla
	 * @param y y koordinata gornjeg-lijevog ugla
	 * @param sirina sirina kvadrata
	 * @param visina visina kvadrata 
	 * @throws IllegalArgumentException
	 */
	public Kvadrat(String ime,int x, int y, int sirina, int visina) {
		
		super(ime, x, y, sirina, visina); 
		if(sirina!=visina){
			throw new IllegalArgumentException("Sirina i visina bi trebale biti jednake vrijednosti!");
		}
		
	}
	
	/**
	 * Konstruktor koji izraduje instancu kvadrata.
	 * @param ime
	 * @param ime ime
	 * @param x x koordinata gornjeg-lijevog ugla
	 * @param y y koordinata gornjeg-lijevog ugla
	 * @param duljina duljina stranice 
	 */
	public Kvadrat(String ime,int x, int y, int duljina) {

		super(ime, x, y, duljina, duljina); 

	}
	
	/**
	 * Klasa koja predtstavlja stvaratelja za geometrijski lik.
	 * Stvaratelj sluzi primarno za proizvodnju instance lika iz parametara zadanih putem stringa.
	 */
	private static class LikKvadratStvaratelj implements StvarateljLika{

		/**
		 * Metoda koja vraca naziv lika
		 */
		public String nazivLika() {
			return "KVADRAT";
		}

		/**
		 * Metoda koja sluzi za proizvodnju instance lika iz parametara zadanih putem stringa.
		 */
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			
			String[] nizStr = parametri.split("\\s+");
			if(nizStr.length!=4 && nizStr.length!=3){
				throw new IllegalArgumentException("Neispravan unos parametara!");
			}
			int[] nizInt=new int[nizStr.length];
			for(int i=0;i<nizStr.length;i++){
				nizInt[i]=Integer.parseInt(nizStr[i]);
			}
			
			if(nizInt.length==4){
				return new Kvadrat(nazivLika(),nizInt[0],nizInt[1],nizInt[2],nizInt[3]);
			}
			return new Kvadrat(nazivLika(),nizInt[0],nizInt[1],nizInt[2]);
		}
		
	}
	
}

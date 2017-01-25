package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Klasa koja predstavlja kruznicu
 * @author Teo Toplak
 */
public class Kruznica extends Elipsa{

	/**
	 * staticka konstanta za stvaratelja geometrijskog lika
	 */
	public static final StvarateljLika STVARATELJ = new LikKruznicaStvaratelj();
	
	/**
	 * Konstruktor za kruznicu koji prihvaca ime, koordinate centra te radijus kao parametre 
	 * @param ime ime kruznice 
	 * @param x x koordinata centra
	 * @param y y koordinata centra
	 * @param r radijus
	 */
	public Kruznica(String ime,int x, int y, int r){
		super(ime,x,y,r,r);
	}
	
	/**
	 * Konstruktor za kruznicu koji prihvaca koordinate centra te radijus kao parametre (zadan preko 
	   vertikalnog i horizontalnog radijusa)
	 * @param ime ime kruznice 
	 * @param x x koordinata centra
	 * @param y y koordinata centra
	 * @param a vodoravni radijus
	 * @param b okomiti radijus
	 * @throws IllegalArgumentException 
	 */
	public Kruznica(String ime,int x, int y, int a,int b){
		super(ime,x,y,a,b);
		if(a!=b){
			throw new IllegalArgumentException("Sirina i visina bi trebale biti jednake vrijednosti!");
		}
	}
	
	/**
	 * Klasa koja predtstavlja stvaratelja za geometrijski lik.
	 * Stvaratelj sluzi primarno za proizvodnju instance lika iz parametara zadanih putem stringa.
	 */
	private static class LikKruznicaStvaratelj implements StvarateljLika{

		/**
		 * Metoda koja vraca naziv lika
		 */
		public String nazivLika() {
			return "KRUZNICA";
		}

		/**
		 * Metoda koja sluzi za proizvodnju instance lika iz parametara zadanih putem stringa.
		 */
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			
			String[] nizStr = parametri.split("\\s+");
			if(nizStr.length!=3){
				throw new IllegalArgumentException("Neispravan unos parametara!");
			}
			int[] nizInt=new int[nizStr.length];
			for(int i=0;i<nizStr.length;i++){
				nizInt[i]=Integer.parseInt(nizStr[i]);
			}
			
			return new Kruznica(nazivLika(),nizInt[0],nizInt[1],nizInt[2]);
		}
		
	}
}

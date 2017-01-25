package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja pravokutnik
 * @author Teo Toplak
 *
 */
public class Pravokutnik extends GeometrijskiLik{

	/**
	 * x koordinata gornjeg-lijevog ugla
	 */
	private int vrhX;
	/**
	 * y koordinata gornjeg-lijevog ugla
	 */
	private int vrhY;
	/**
	 * sirina pravokutnika
	 */
	private int sirina;
	/**
	 * visina pravokutnika
	 */
	private int visina;
	
	/**
	 * staticka konstanta za stvaratelja geometrijskog lika
	 */
	public static final StvarateljLika STVARATELJ = new LikPravokutnikStvaratelj();

	/**
	 * Konstruktor pravokutnika.
	 * @param ime ime 
	 * @param vrhX x koordinata gornjeg-lijevog ugla
	 * @param vrhY y koordinata gornjeg-lijevog ugla
	 * @param sirina sirina pravokutnika
	 * @param visina visina pravokutnika
	 */
	public Pravokutnik(String ime, int vrhX, int vrhY, int sirina, int visina) {
		super(ime);
		this.vrhX = vrhX;
		this.vrhY = vrhY;
		this.sirina = sirina;
		this.visina = visina;
	}


//	@Override
//	public double getPovrsina() {
//		return visina * sirina;
//	}

	/**
	 * Metoda koja vraca true ako je dana tocka unutar instance geometrijskog lika.
	 * @true ako je dana tocka unutar instance geometrijskog lika
	 */
	public boolean sadrziTocku(int x, int y) {
		if (x < vrhX || x > vrhX + sirina) {
			return false;
		}

		if (y < vrhY || y > vrhY + visina) {
			return false;
		}

		return true;
	}

	/**
	 * Metoda koja popunjuje lik na zadanom rasteru.
	 */
	public void popuniLik(Slika slika) {
		for (int y = vrhY; y < vrhY + visina; y++) {
			for (int x = vrhX; x < vrhX + sirina; x++) {
				try{
					slika.upaliTocku(x, y);
				}catch(IllegalArgumentException e){
					//try-catch : mogucnost crtanja likova koji i prelaze van okvira
				}
				
			}
		}
	}
	
	/**
	 * Klasa koja predtstavlja stvaratelja za geometrijski lik.
	 * Stvaratelj sluzi primarno za proizvodnju instance lika iz parametara zadanih putem stringa.
	 */
	private static class LikPravokutnikStvaratelj implements StvarateljLika{

		
		/**
		 * Metoda koja vraca naziv lika
		 */
		public String nazivLika() {
			return "PRAVOKUTNIK";
		}

		/**
		 * Metoda koja sluzi za proizvodnju instance lika iz parametara zadanih putem stringa.
		 */
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			
			String[] nizStr = parametri.split("\\s+");
			if(nizStr.length!=4){
				throw new IllegalArgumentException("Neispravan unos parametara!");
			}
			int[] nizInt=new int[nizStr.length];
			try{
				for(int i=0;i<nizStr.length;i++){
					nizInt[i]=Integer.parseInt(nizStr[i]);
				}
			}catch(Exception e){
				throw new IllegalArgumentException("Neispravan format unosa!");
			}
			
			return new Pravokutnik(nazivLika(),nizInt[0],nizInt[1],nizInt[2],nizInt[3]);
		}
		
	}
}


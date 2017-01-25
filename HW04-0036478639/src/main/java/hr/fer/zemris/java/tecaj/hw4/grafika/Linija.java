package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja liniju.
 * @author Teo Toplak
 *
 */
public class Linija extends GeometrijskiLik{

	/**
	 * x koordinata pocetne tocke
	 */
	private int x0;
	/**
	 * y koordinata pocetne tocke
	 */
	private int y0;
	/**
	 * x koordinata zavrsne tocke
	 */
	private int x1;
	/**
	 * y koordinata zavrsne tocke
	 */
	private int y1;
	
	/**
	 * staticka konstanta za stvaratelja geometrijskog lika
	 */
	public static final StvarateljLika STVARATELJ = new LikLinijaStvaratelj();
	
	/**
	 * Konstruktor linije.
	 * @param ime ime 
	 * @param x0 x koordinata pocetne tocke
	 * @param y0 y koordinata pocetne tocke
	 * @param x1 x koordinata zavrsne tocke
	 * @param y1 y koordinata zavrsne tocke
	 */
	public Linija(String ime, int x0, int y0, int x1, int y1){
		super(ime);
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	/**
	 * Metoda koja vraca true ako se dana tocka nalazni da instanci pravca.
	 * @param x x koordinata tocke
	 * @param y y koordinata tocke
	 * @return true ako se dana tocka nalazni da instanci pravca
	 */
	private boolean nalaziNaPravcu(int x, int y){
		
		//ovime zaobilazim dijeljenje sa nulom, dok sigurno zadrzavam funkionalnost metode sadrzi tocku
		if(x1==x0){
			return true;
		}
		//koeficijent sluzi kao mjerilo preciznosti crtanja pravca (sto je veci to ce biti siri ali i gusci pravac)
		double koeficijent=0.5;
		if(Math.abs(y-y0 - ((double)(y1-y0)/(x1-x0))*(x-x0))<koeficijent){
			return true;
		}
		return false;
	}
	
	/**
	 * Metoda koja vraca true ako je dana tocka unutar instance geometrijskog lika.
	 * @true ako je dana tocka unutar instance geometrijskog lika
	 */
	public boolean sadrziTocku(int x, int y) {
		if(nalaziNaPravcu(x,y)){
			return true;
		}
		return false;
	}
	
	/**
	 * Metoda koja popunjuje lik na zadanom rasteru.
	 */
	public void popuniLik(Slika slika){
		for (int y =Math.min(y0,y1); y <Math.max(y0,y1) ; y++) {
			for (int x = Math.min(x0,x1); x <Math.max(x0,x1); x++) {
				if(sadrziTocku(x, y)){
					try{
						slika.upaliTocku(x, y);
					}catch(IllegalArgumentException e){
						//try-catch : mogucnost crtanja likova koji i prelaze van okvira
					}
				}
			}
		}
	}

	/**
	 * Klasa koja predtstavlja stvaratelja za geometrijski lik.
	 * Stvaratelj sluzi primarno za proizvodnju instance lika iz parametara zadanih putem stringa.
	 */
	private static class LikLinijaStvaratelj implements StvarateljLika{

		/**
		 * Metoda koja vraca naziv lika
		 */
		public String nazivLika() {
			return "LINIJA";
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
			for(int i=0;i<nizStr.length;i++){
				nizInt[i]=Integer.parseInt(nizStr[i]);
			}
			
			return new Linija(nazivLika(),nizInt[0],nizInt[1],nizInt[2],nizInt[3]);
		}
		
	}
}

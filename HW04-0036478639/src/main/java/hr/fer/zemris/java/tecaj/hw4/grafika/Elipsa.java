package hr.fer.zemris.java.tecaj.hw4.grafika;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja elipsu.
 * @author Teo Toplak
 *
 */
public class Elipsa extends GeometrijskiLik{

	/**
	 * x koordinata centra
	 */
	private int x;
	
	/**
	 * y koordinata centra
	 */
	private int y;
	
	/**
	 * vodoravni radijus
	 */
	private int a;
	
	/**
	 * okomiti radijus
	 */
	private int b;
	
	/**
	 * staticka konstanta za stvaratelja geometrijskog lika
	 */
	public static final StvarateljLika STVARATELJ = new LikElipsaStvaratelj();
	
	/**
	 * Konstruktor koji stvara instancu elipse sa zadanim koordinatama centra, vodoravnim te okomitim radijusom.
	 * @param ime ime elipse
	 * @param x x koordinata centra
	 * @param y y koordinata centra
	 * @param a vodoravni radijus
	 * @param b okomiti radijus
	 */
	public Elipsa(String ime,int x, int y, int a, int b) {
		super(ime);
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Metoda koja vraca kvadrat danog broja.
	 * @param a broj 
	 * @return kvadrat broja
	 */
	private double kvadrat(int a){
		return Math.pow((double)a,2);
	}

	
	/**
	 * Metoda koja vraca true ako je dana tocka unutar instance geometrijskog lika.
	 * @true ako je dana tocka unutar instance geometrijskog lika
	 */
	public boolean sadrziTocku(int x, int y) {
		if(x<this.x-a || x>this.x+a || y<this.y-b || y>this.y+b){
			return false;
		}
		if(kvadrat(b * (x-this.x)) + kvadrat(a*(y-this.y)) < kvadrat(a*b)){
			return true;
		}
		return false;
	}

	/**
	 * Metoda koja popunjuje lik na zadanom rasteru.
	 */
	public void popuniLik(Slika slika){
		for(int x=this.x-a;x<this.x+a;x++){
			for(int y=this.y-b;y<this.y+b;y++){
				if(sadrziTocku(x,y)){
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
	private static class LikElipsaStvaratelj implements StvarateljLika{

		/**
		 * Metoda koja vraca naziv lika
		 */
		public String nazivLika() {
			return "ELIPSA";
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
			
			return new Elipsa(nazivLika(),nizInt[0],nizInt[1],nizInt[2],nizInt[3]);
		}
		
	}
	
}

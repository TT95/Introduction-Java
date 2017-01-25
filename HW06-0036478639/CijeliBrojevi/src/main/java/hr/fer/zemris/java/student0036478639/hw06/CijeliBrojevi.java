package hr.fer.zemris.java.student0036478639.hw06;

/**
 * Klasa koja predstavlja neke operacije sa cijelim brojevima.
 * @author Teo Toplak
 *
 */
public class CijeliBrojevi {
	
	/**
	 * Metoda koja vraca true ako je broj neparan, false inace.
	 * @param broj broj 
	 * @return true ako je broj neparan, false inace
	 */
    public boolean jeNeparan(int broj) {
    	if(broj % 2 == 1) {
    		return true;
    	}
    	return false;
    }
    
	/**
	 * Metoda koja vraca true ako je broj paran, false inace.
	 * @param broj broj 
	 * @return true ako je broj paran, false inace
	 */
    public boolean jeParan(int broj) {
    	if(broj % 2 == 0) {
    		return true;
    	}
    	return false;
    }
    
	/**
	 * Metoda koja vraca true ako je broj prost, false inace.
	 * @param broj broj 
	 * @return true ako je broj prost, false inace
	 */
    public boolean jeProst(int broj) {
    	if(broj==1) {
    		return false;
    	}
    	for(int i=2;i<broj;i++) {
    		if(broj % i == 0) {
    			return false;
    		}
    	}
    	return true;
    }
}

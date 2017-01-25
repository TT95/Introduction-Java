package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.*;

/**
 * Klasa koja predstavlja implementaciju memorije racunala.
 * @author Teo Toplak
 *
 */
public class MemoryImpl implements Memory{
	
	/**
	 * Niz(objekata) koji predstavlja implementaciju memorije racunala.
	 */
	private Object[] memory;
	
	/**
	 * Konstruktor memorije racunala.
	 * @param size velicina memorije koju zelimo stvoriti
	 */
	public MemoryImpl(int size) {
		this.memory= new Object[size];
	}
	
	/**
	 * Metoda kojom pristupamo (unosimo vrijednost) odredenom dijelu memorije.
	 */
	@Override
	public void setLocation(int location, Object value) {
		memory[location]=value;
	}

	/**
	 * Meotda kojom pristupamo (primamo vrijednost) odredenom dijelu memorije.
	 */
	@Override
	public Object getLocation(int location) {
		return memory[location];
	}

	
}

package hr.fer.zemris.java.simplecomp.impl;
import  hr.fer.zemris.java.simplecomp.models.*;

/**
 * Klasa koja predstavlja implementaciju 'racunala'.
 * Sadrzi memoriju i registre cija se velicina/broj unosi kroz konstruktor.
 * @author Teo Toplak
 *
 */
public class ComputerImpl implements Computer {

	/**
	 * memorija racunala
	 */
	private MemoryImpl memory;
	/**
	 * registri racunala
	 */
	private RegistersImpl registers;
	
	/**
	 * Konstruktor koji stvara 'racunalo' sa argumentima koji predstavljaju
	 * velicinu/broj memorije/registara
	 * 
	 * @param size velicina memorije
	 * @param regsLen broj registara
	 */
	public ComputerImpl(int size,int regsLen) {
		this.memory=new MemoryImpl(size);
		this.registers=new RegistersImpl(regsLen);
	}
	

	/**
	 * Metoda koja vraca referencu na registre racunala.
	 */
	@Override
	public Registers getRegisters() {
		return registers;
	}

	/**
	 * Metoda koja vraca referencu na memoriju racunala.
	 */
	@Override
	public Memory getMemory() {
		return memory;
	}

	
}

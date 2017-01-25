package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.*;

/**
 * Klasa koja predstavlja implementaciju registara racnala.
 * @author Teo Toplak
 *
 */
public class RegistersImpl implements Registers{

	/**
	 * Niz registara opce namjenje.
	 */
	private Object[] registers;
	/**
	 * Programsko brojilo.
	 */
	private int programCounter;
	/**
	 * Zastavica racunala.
	 */
	private boolean flag;
	
	/**
	 * Konstruktor registara memorije. Vrijednost programskog brojila se
	 * postavlja na 0, a zastavica na false.
	 * 
	 * @param regsLen broj registara koji zelimo stvoriti u racunalu
	 */
	public RegistersImpl(int regsLen) {
		registers=new Object[regsLen];
		programCounter=0;
		flag=false; 
	}
	
	/**
	 * Metoda kojom dobavljamo vrijednost odredenog registra
	 * @param index index registra ciju vrijednost zelimo primiti
	 */
	@Override
	public Object getRegisterValue(int index) {
		return registers[index];
	}

	/**
	 * Metoda kojom se postavlja vrijednost odredenog registra
	 */
	@Override
	public void setRegisterValue(int index, Object value) {
		registers[index]=value;
	}

	/**
	 * Metoda kojom se dohvaca vrijednost programskog brojila
	 */
	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	/**
	 * Metoda kojom se odreduje vrijednost programskog brojila
	 */
	@Override
	public void setProgramCounter(int value) {
		programCounter=value;
	}

	/**
	 * Metoda kojom se povecava vrijednost programskog brojila
	 */
	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	/**
	 * Metoda kojom se dohvaca vrijednost zastavice racunala
	 */
	@Override
	public boolean getFlag() {
		return flag;
	}

	/**
	 * Metoda kojom se odreduje vrijednost zastavici racunala
	 */
	@Override
	public void setFlag(boolean value) {
		flag=value;
	}

}

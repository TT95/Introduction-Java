package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Klasa koja predstavlja operaciju ucitavanja vrijesnosti u registar (load rX,
 * memorijskaAdresa ). Uzima sadržaj memorijske lokacije i pohranjuje taj
 * sadržaj u registar rX
 * 
 * @author Teo Toplak
 *
 */
public class InstrLoad implements Instruction {

	/**
	 * Registar u koji se ucitava vrijednost
	 */
	private int register;
	/**
	 * Memorijska lokacija sa koje se ucitava vrijednost
	 */
	private int memoryAdr;
	
	/**
	 * Konstruktor instrukcije koju definira sama klasa.
	 * @param arguments lista argumenata instrukcije
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if(arguments.size()!=2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
			}
		if(!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
			}
		this.register= ((Integer)arguments.get(0).getValue()).intValue();
		this.memoryAdr=((Integer)arguments.get(1).getValue()).intValue();
	}

	/**
	 * Metoda kojom se izvrsava operacija klase nad danim 'racunalom'.
	 * @param computer 'racunalo' unutar kojeg se izvrsava instrukcija
	 */
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(register,
				computer.getMemory().getLocation(memoryAdr));
		return false;
	}

}

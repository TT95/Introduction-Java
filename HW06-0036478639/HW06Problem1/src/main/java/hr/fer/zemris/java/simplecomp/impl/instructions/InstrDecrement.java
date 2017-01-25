package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Klasa koja predstavlja operaciju umanjivanja( decrement rx ).
 * Vrijednost registra rx se umanjuje za jedan.
 * @author Teo Toplak
 *
 */
public class InstrDecrement implements Instruction {

	/**
	 * Index registra koji se umanjuje
	 */
	private int register;
	
	/**
	 * Konstruktor instrukcije koju definira sama klasa.
	 * @param arguments lista argumenata instrukcije
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if(arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
			}
		this.register= ((Integer)arguments.get(0).getValue()).intValue();
	}

	/**
	 * Metoda kojom se izvrsava operacija klase nad danim 'racunalom'.
	 * @param computer 'racunalo' unutar kojeg se izvrsava instrukcija
	 */
	@Override
	public boolean execute(Computer computer) {
		int value=(Integer) computer.getRegisters().getRegisterValue(register);
		value--;
		computer.getRegisters().setRegisterValue(register, value);
		return false;
	}

}

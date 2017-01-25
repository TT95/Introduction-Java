package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Klasa koja predstavlja operaciju skakanja na odredenu adresu(jump lokacija).
 * Programsko brojilo se postavlja na odredenu lokaciju.
 * @author Teo Toplak
 *
 */
public class InstrJump implements Instruction {

	/**
	 * Memorijska lokacija na koju se preskace
	 */
	private int location;
	
	/**
	 * Konstruktor instrukcije koju definira sama klasa.
	 * @param arguments lista argumenata instrukcije
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if(arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
			}
		this.location= ((Integer)arguments.get(0).getValue()).intValue();
	}

	/**
	 * Metoda kojom se izvrsava operacija klase nad danim 'racunalom'.
	 * @param computer 'racunalo' unutar kojeg se izvrsava instrukcija
	 */
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(location);
		return false;
	}

}

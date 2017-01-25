package hr.fer.zemris.java.simplecomp.impl.instructions;


import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Klasa koja predstavlja operaciju prekida(halt).
 * Dolaskom do operacije halt se zavrsava izvodenje programa.
 * @author Teo Toplak
 *
 */
public class InstrHalt implements Instruction {
	
	/**
	 * Konstruktor instrukcije koju definira sama klasa.
	 * @param arguments lista argumenata instrukcije
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		
	}
	
	/**
	 * Metoda kojom se izvrsava operacija klase nad danim 'racunalom'.
	 * @param computer 'racunalo' unutar kojeg se izvrsava instrukcija
	 */
	@Override
	public boolean execute(Computer computer) {
		return true;
	}
}

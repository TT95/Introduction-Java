package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Klasa koja predstavlja operaciju ispisa(echo rx).
 * Sadrzaj registra rx se ispisuje na standaran output.
 * @author Teo Toplak
 *
 */
public class InstrEcho implements Instruction {

	/**
	 * Index registra cija se vrijednost ispisuje.
	 */
	private int register;
	
	/**
	 * Konstruktor instrukcije koju definira sama klasa.
	 * @param arguments lista argumenata instrukcije
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if(arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument!");
			}
		this.register= ((Integer)arguments.get(0).getValue()).intValue();
	}

	/**
	 * Metoda kojom se izvrsava operacija klase nad danim 'racunalom'.
	 * @param computer 'racunalo' unutar kojeg se izvrsava instrukcija
	 */
	@Override
	public boolean execute(Computer computer) {
		
		System.out.print(computer.getRegisters().getRegisterValue(register));
		return false;
	}
}

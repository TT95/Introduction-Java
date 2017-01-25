package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Klasa koja predstavlja operaciju prijesnosa podatka iz registra u registar(move rx, ry).
 * Vrijednost registra ry se posprema u ry.
 * @author Teo Toplak
 *
 */
public class InstrMove implements Instruction {

	/**
	 * Index registra u koji se unosi vrijednost
	 */
	private int indexOfReg0;
	/**
	 * Index registra iz kojeg se prebacuje vrijednost
	 */
	private int indexOfReg1;
	
	/**
	 * Konstruktor instrukcije koju definira sama klasa.
	 * @param arguments lista argumenata instrukcije
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if(arguments.size()!=2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
			}
		if(!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
			}
		this.indexOfReg0= ((Integer)arguments.get(0).getValue()).intValue();
		this.indexOfReg1= ((Integer)arguments.get(1).getValue()).intValue();
	}

	/**
	 * Metoda kojom se izvrsava operacija klase nad danim 'racunalom'.
	 * @param computer 'racunalo' unutar kojeg se izvrsava instrukcija
	 */
	@Override
	public boolean execute(Computer computer) {
		
		Object value= computer.getRegisters().getRegisterValue(indexOfReg1);
		computer.getRegisters().setRegisterValue(indexOfReg0, value);
		return false;
	}

}

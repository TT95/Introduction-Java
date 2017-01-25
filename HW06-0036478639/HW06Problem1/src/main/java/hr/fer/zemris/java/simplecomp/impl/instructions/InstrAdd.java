package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Klasa koja predstavlja instrukciju dodavanja (add rx, ry, rz ).
 * U rx se posprema rezultat zbrajanja vrijednosti registara ry i rz
 * @author Teo Toplak
 *
 */
public class InstrAdd implements Instruction {

	/**
	 * index prvog registra
	 */
	private int indexOfReg0;
	/**
	 * index drugog registra
	 */
	private int indexOfReg1;
	/**
	 * index treceg registra
	 */
	private int indexOfReg2;
	
	/**
	 * Konstruktor instrukcije koju definira sama klasa.
	 * @param arguments lista argumenata instrukcije
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if(arguments.size()!=3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
			}
		if(!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
			}
		if(!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
			}
		this.indexOfReg0= ((Integer)arguments.get(0).getValue()).intValue();
		this.indexOfReg1= ((Integer)arguments.get(1).getValue()).intValue();
		this.indexOfReg2= ((Integer)arguments.get(2).getValue()).intValue();
	}

	/**
	 * Metoda kojom se izvrsava operacija klase nad danim 'racunalom'..
	 * @param computer 'racunalo' unutar kojeg se izvrsava instrukcija
	 */
	@Override
	public boolean execute(Computer computer) {
		
		Object value=(Integer)computer.getRegisters().getRegisterValue(indexOfReg1) +
				(Integer)computer.getRegisters().getRegisterValue(indexOfReg2);
		computer.getRegisters().setRegisterValue(indexOfReg0, value);
		return false;
	}

}

package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Klasa koja predstavlja operaciju usporedbe registara(testEquals rx, ry).
 * Ako su vrijednosti registara jednake onda se zastavica postavlja na true.
 * @author Teo Toplak
 *
 */
public class InstrTestEquals implements Instruction {

	/**
	 * index prvog registra
	 */
	private int indexOfReg0;
	/**
	 * index drugog registra
	 */
	private int indexOfReg1;
	
	public InstrTestEquals(List<InstructionArgument> arguments) {
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
		
		Object value1= computer.getRegisters().getRegisterValue(indexOfReg0);
		Object value2= computer.getRegisters().getRegisterValue(indexOfReg1);
		if(value1.equals(value2)) {
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}

}

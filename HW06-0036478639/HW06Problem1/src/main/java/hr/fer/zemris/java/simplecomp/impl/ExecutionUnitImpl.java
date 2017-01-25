package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * Upravljački sklop računala. Ovaj razred "izvodi"
 * program odnosno predstavlja impulse takta za sam
 * procesor.
 * @author Teo Toplak
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {
	
	/**
	 * Metoda kojom se pokrece izvrsavanje instrukcija.
	 */
	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		boolean halt=false;
		while(!halt) {
			Instruction instr=(Instruction) computer.getMemory().getLocation(
					computer.getRegisters().getProgramCounter());
			computer.getRegisters().incrementProgramCounter();
			halt=instr.execute(computer);
		}
		return true;
	}

	
}

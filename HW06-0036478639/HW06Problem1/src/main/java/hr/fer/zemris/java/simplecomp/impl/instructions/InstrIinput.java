package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Klasa koja predstavlja operaciju unosa cijelog broja(iinput lokacija).
 * Na zadanu lokaciju se upisuje vrijesnost unesena na standardnom ulazu.
 * @author Teo Toplak
 *
 */
public class InstrIinput implements Instruction {

	/**
	 * Memorijska adresa u koju se posprema unesena vrijednost
	 */
	private int memoryAdr;
	
	/**
	 * Konstruktor instrukcije koju definira sama klasa.
	 * @param arguments lista argumenata instrukcije
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if(arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
			}
		this.memoryAdr= ((Integer)arguments.get(0).getValue()).intValue();
	}

	/**
	 * Metoda kojom se izvrsava operacija klase nad danim 'racunalom'.
	 * @param computer 'racunalo' unutar kojeg se izvrsava instrukcija
	 */
	@Override
	public boolean execute(Computer computer) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean flag=true;
		int number=0;
		try {
			String numberStr= reader.readLine();
			number=  Integer.parseInt(numberStr);
		} catch (Exception e) {
			flag=false;
		}
		computer.getRegisters().setFlag(flag);
		computer.getMemory().setLocation(memoryAdr, number);
		return false;
	}

}

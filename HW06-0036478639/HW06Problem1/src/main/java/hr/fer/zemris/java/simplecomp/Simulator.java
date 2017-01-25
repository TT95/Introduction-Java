package hr.fer.zemris.java.simplecomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.*;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Klasa koja predstavlja simulaciju obavljanja instrukcija unutar prethodno
 * stvorenog racunala. Klasa mora primiti put do tekstualne datoteke u kojoj se
 * nalazi asemblerski kod ili putem naredbenog redka ili unosom u standardni
 * ulaz. Klasa prvo stvara novo racunalo zatim novi objekt pomocu kojeg parser
 * stvara primjere instrukcija, prevodi program zapisanog u tekstualnoj
 * datoteci, stvara izvrsnu jedinicu tj. sklop koji ce izvoditi program, te
 * pokretanja te izvrsne jednice.
 * @author Teo Toplak
 *
 */
public class Simulator {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args put do tekstualne datoteke u kojoj se nalazi asemblerski kod
	 * @throws IOException ako dode do pogreske u citanju linije asemblerskog koda
	 */
	public static void main(String[] args) throws IOException {
		
		String path;
		
		if(args.length>1) {
			throw new IllegalArgumentException("One argument must be provided!");
		}
		if(args.length==0) {
			BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Type path to file with asembler code: ");
			path=reader.readLine();
		} else {
			path=args[0];
		}
		
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions");
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		try {
			ProgramParser.parse(path, comp, creator);
		} catch (Exception e) {
			System.err.println("Wrong code or path to file with asembler code!");
			System.exit(1);
		}
		
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		exec.go(comp);
	}
}

package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.complex.*;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Basic support for creating fractals derived from Newton-Raphson iteration.
 * Calculations made by multithreading.
 * @author Teo Toplak
 *
 */
public class Newton {

	/**
	 * Method called when executing program
	 * @param args takes no command line arguments
	 * @throws IOException when reading console input fails
	 */
	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String input = "";
		int rootIndex = 1;
		List<Complex> rootsList = new ArrayList<Complex>();
		System.out
				.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root"
				+ " per line. Enter 'done' when done.");
		while (true) {
			System.out.print("Root " + rootIndex + "> ");
			input = reader.readLine();
			if (input.equals("done")) {
				break;
			}
			rootsList.add(Complex.parseString(input));
			rootIndex++;
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");

		FractalViewer
				.show(getParallelFractalproducer(new ComplexRootedPolynomial(
						rootsList)));
	}

	/**
	 * Creates window and shows Newton fractal.
	 * @param polynom polynom needed for fractal creation
	 * @return producer of fractal
	 */
	private static IFractalProducer getParallelFractalproducer(
			ComplexRootedPolynomial polynom) {
		
		return new FractalProducer(polynom);
	}

}

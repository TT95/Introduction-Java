package hr.fer.zemris.java.fractals;


import hr.fer.zemris.java.complex.*;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



/**
 * Basic support for creating fractals derived from Newton-Raphson iteration.
 * Calculations made sequentially by one thread.
 * @author Teo Toplak
 *
 */
public class NewtonSeq {

	/**
	 * Method called when executing program
	 * @param args takes no command line arguments
	 * @throws IOException when reading console input fails
	 */
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input="";
		int rootIndex=1;
		List<Complex> rootsList = new ArrayList<Complex>();
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root"
				+ " per line. Enter 'done' when done.");
		while(true) {
			System.out.print("Root "+rootIndex+"> ");
			input = reader.readLine();
			if(input.equals("done")) {
				break;
			}
			rootsList.add(Complex.parseString(input));
			rootIndex++;
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		FractalViewer.show(getSequentialFractalproducer(new ComplexRootedPolynomial(rootsList)));
		
		
	}
	
	/**
	 * Creates window and shows Newton fractal.
	 * @param polynom polynom needed for fractal creation
	 * @return producer of fractal
	 */
	private static IFractalProducer getSequentialFractalproducer(ComplexRootedPolynomial polynom) {
		return new IFractalProducer() {
			
			@Override
			public void produce(double reMin, double reMax, double imMin, double imMax,
					int width, int height, long requestNo,
					IFractalResultObserver observer) {
				System.out.println("Započinjem izračune...");
				int m = 16*16*16;
				short[] data = new short[width*height];
				long t0 = System.currentTimeMillis();
				calculate(reMin, reMax, imMin, imMax, width, height, m, 0, height-1, data, polynom);
				long t1 = System.currentTimeMillis();
				System.out.println(t1-t0);
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(data, (short)(polynom.order()+1), requestNo);
				System.out.println("Dojava gotova...");
			}
			
		};
	}
	
	/**
	 * Calculates Newton-Raphson iteration-based fractal. Puts adequate indexes
	 * in section of short numbers based array set by ymin and ymax.
	 * 
	 * @param reMin min value on real axis
	 * @param reMax max value on real axis
	 * @param imMin min value on imaginary axis
	 * @param imMax max value on imaginary axis
	 * @param width width of window creating fractal
	 * @param height height of window creating fractal
	 * @param m number of maximum iterations for calculating index 
	 * 			of root in polynome 
	 * @param ymin starting part of array segment filling with indexes
	 * @param ymax ending part of array segment filling with indexes
	 * @param data array for indexes
	 * @param polynom polynom 
	 */
	public static void calculate(double reMin, double reMax, double imMin, double imMax,
			int width, int height, int m, int ymin, int ymax, short[] data,
			ComplexRootedPolynomial polynom ) {
		int offset = ymin * width;
		ComplexPolynomial derivation = polynom.toComplexPolynom().derive();
		for(int y = ymin; y <= ymax; y++) {
			for(int x = 0; x < width; x++) {
				double cre = x/(width-1.0)*(reMax-reMin) + reMin;
				double cim = ((height-1)-y)/(height-1.0)*(imMax-imMin) + imMin;
				Complex zn = new Complex(cre,cim);
				Complex zn1 = new Complex();	
				Double module;
				int iter = 0;
				do {
					zn1 = zn.sub(polynom.apply(zn).divide(derivation.apply(zn)));
					module = zn1.sub(zn).module();
					zn=zn1;
					iter++;
				} while(Math.abs(module)>0.001 && iter<64);
				short index = (short) polynom.indexOfClosestRootFor(zn1, 0.002);
				if(index==-1) {
					data[offset++] = 0;
				} else {
					data[offset++] = index;
				}
			}
		}
	}

}

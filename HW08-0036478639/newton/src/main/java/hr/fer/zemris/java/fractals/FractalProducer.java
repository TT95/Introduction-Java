package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexPolynomial;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Class representing producer of Newton-Raphson iteration-based fractal.
 * @author Teo Toplak
 *
 */
public class FractalProducer implements IFractalProducer{

	/**
	 * number of threads
	 */
	private int nThreads;
	/**
	 * thread pool
	 */
	private ExecutorService pool;
	/**
	 * complex rooted polynom
	 */
	private ComplexRootedPolynomial polynom;
	
	/**
	 * Constuctor for producer.
	 * @param polynom polynom needed for fractal creation
	 */
	public FractalProducer(ComplexRootedPolynomial polynom) {

		this.polynom=polynom;
		this.nThreads = 8 * Runtime.getRuntime().availableProcessors();;
		this.pool = Executors.newFixedThreadPool(nThreads,
				new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});;
	}


	@Override
	public void produce(double reMin, double reMax, double imMin,
			double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer) {
		
		class Job implements Runnable {

			int start;
			int end;		
			short data[];
			int m;
			
			public Job(int start, int end, short[] data, int m) {
				this.m = m;
				this.start = start;
				this.end = end;
				this.data = data;
			}

			@Override
			public void run() {
					calculate(reMin, reMax, imMin, imMax, width, height, m, start, end, data, polynom);
			}		
						
		}
		long t0 = System.currentTimeMillis();
		
		System.out.println("Započinjem izračune...");
		int m = 16 * 16 * 16;
		short[] data = new short[width * height];
		List<Future<?>> list = new ArrayList<>();
		int partForOneThread =(int) Math.floor(width/nThreads);
		int partRemained = width - partForOneThread*(nThreads-1);
		
		for(int i=0;i<nThreads;i++) {
			
			if(i<nThreads-1) {
				list.add(pool.submit(new Job(i*partForOneThread,(i+1)*partForOneThread-1,data,m)));
			} else {
				list.add(pool.submit(new Job(width-partRemained	,width-1 ,data,m)));
			}
			
		}
		
		for(Future<?> x : list) {
			while(true) {
				try {
					x.get();
					break;
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
		
		long t1 = System.currentTimeMillis();
		System.out.println(t1-t0);
		
		System.out.println("Izračuni gotovi...");
		observer.acceptResult(data, (short) (polynom.order() + 1),
				requestNo);
		System.out.println("Dojava gotova...");
		//pool.shutdown(); since pogram needs to keep running (for zooming tool)
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
	public static void calculate(double reMin, double reMax, double imMin,
			double imMax, int width, int height, int m, int ymin, int ymax,
			short[] data, ComplexRootedPolynomial polynom) {
		int offset = ymin * width;
		ComplexPolynomial derivation = polynom.toComplexPolynom().derive();
		for (int y = ymin; y <= ymax; y++) {
			for (int x = 0; x < width; x++) {
				double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
				double cim = ((height - 1) - y) / (height - 1.0)
						* (imMax - imMin) + imMin;
				Complex zn = new Complex(cre, cim);
				Complex zn1 = new Complex();
				Double module;
				int iter = 0;
				do {
					zn1 = zn.sub(polynom.apply(zn).divide(derivation.apply(zn)));
					module = zn1.sub(zn).module();
					zn = zn1;
					iter++;
				} while (Math.abs(module) > 0.001 && iter < 64);
				short index = (short) polynom.indexOfClosestRootFor(zn1, 0.002);
				if (index == -1) {
					data[offset++] = 0;
				} else {
					data[offset++] = index;
				}
			}
		}
	}

}

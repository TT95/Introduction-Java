package hr.fer.zemris.java.hw13.utility;

import java.text.DecimalFormat;

/**
 * Class providing utility for calculating cos and sin
 * values of degree given.
 * @author Teo Toplak
 *
 */
public class TrigonometricUtil {

	/**
	 * Method returns {@link Trigo} which stores sin and cos values
	 * of given argument (in degrees).
	 * @param degrees degrees
	 * @return class containing sin and cos value
	 */
	public static Trigo utility(int degrees) {
		double radians = Math.toRadians(degrees);
		DecimalFormat format = new DecimalFormat("0.0000");
		return new Trigo(Double.valueOf(format.format(Math.sin(radians)).replaceAll(",", ".")),
				Double.valueOf(format.format(Math.cos(radians)).replaceAll(",", ".")));
	}
	
	/**
	 * Class containing sin and cos
	 * value for page representation
	 * @author Teo Toplak
	 *
	 */
	public static class Trigo {
		/**
		 * sin value
		 */
		private double sin;
		/**
		 * cos value
		 */
		private double cos;
		
		/**
		 * Constructor taking sin and cos
		 * values as arguments 
		 */
		public Trigo(double sin, double cos) {
			this.sin = sin;
			this.cos = cos;
		}

		/**
		 * sin value getter
		 * @return the sin
		 */
		public double getSin() {
			return sin;
		}

		/**
		 * cos value getter
		 * @return the cos
		 */
		public double getCos() {
			return cos;
		}
		
	}
}

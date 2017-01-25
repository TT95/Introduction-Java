package hr.fer.zemris.java.gui.calc.operations;

/**
 * Strategy for buttons on calculator represening operations.
 * Operation represents normal arithmetic operations on calculator.<br>
 * Example of operation buttons: <br>
 * -+ <br>
 * -- <br>
 * -* <br>
 * -/ <br>
 * @author Teo Toplak
 *
 */
public interface IOperation {

	/**
	 * Calculates result of operation with two numbers given.
	 * @param num1 first number
	 * @param num2 second number
	 * @return result of operation
	 */
	public Double calculate(double num1, double num2);
}

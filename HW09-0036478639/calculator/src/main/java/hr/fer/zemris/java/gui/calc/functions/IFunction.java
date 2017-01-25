package hr.fer.zemris.java.gui.calc.functions;

/**
 * Strategy for buttons on calculator represening functions.
 * Function represents action where only current number on screen is used
 * and replaced by result of function. <br>
 * Function buttons with inverse function following: <br>
 * -log (10^) <br>
 * -ln (e^) <br>
 * -cos (arccos) <br>
 * -sin (arcsin)<br>
 * -ctg (arcctg)<br>
 * -tg (arctg)<br>
 * -1/x <br>
 * -x^n (x^1/n) <br>
 * @author Teo Toplak
 *
 */
public interface IFunction {
	/**
	 * Default function of button.
	 * @param num argument number (from screen)
	 * @return result of function operation
	 */
	public double function(double num);
	/**
	 * Inversed function of button.
	 * @param num argument number (from screen)
	 * @return result of function operation
	 */
	public double invFunction(double num);
}

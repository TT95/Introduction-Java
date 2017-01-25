package hr.fer.zemris.java.gui.calc.operations;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.ICalculator;

/**
 * Context in which strategy {@link IOperation} is used.
 * @author Teo Toplak
 */
public class OperationUtility {

	/**
	 * Uses given concrete operation to process result in calculator.
	 * @param op operation
	 * @param calc calculator
	 */
	public static void setOperation(IOperation op, ICalculator calc) {
		if(calc.getLastNumExists() && calc.getOperationExists()) {
			JButton button = (JButton) calc.getPanel().getComponent(1);
			button.doClick();

		}
		calc.setOperationExists(true);
		calc.setOperation(op);
	}
}

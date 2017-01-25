package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.ICalculator;

/**
 * Context in which strategy {@link IFunction} is used.
 * @author Teo Toplak
 */
 public class FunctionUtility {

	/**
	 * Utility method which uses different function concrete strategys to
	 * calculate solution while also changing internal state of calculator.
	 * 
	 * @param func function concrete strategy
	 * @param calc calculator
	 */
	public FunctionUtility(IFunction func, ICalculator calc) {
		
		Double num=calc.getScreen();
		if(num.isNaN()) {
			return;
		}
		if(calc.getInv()) {
			num = func.invFunction(num);
		} else {
			num = func.function(num);
		}
		calc.setFunctionUsed(true);
		calc.setLastNumExists(false);
		calc.setOperationExists(false);
		calc.setDotActivated(false);
		calc.setNumToScreen(num);
	}
	
}

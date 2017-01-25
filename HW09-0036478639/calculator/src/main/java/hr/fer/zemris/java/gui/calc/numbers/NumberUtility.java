package hr.fer.zemris.java.gui.calc.numbers;


import hr.fer.zemris.java.gui.calc.ICalculator;

/**
 * Class representing utility which takes numbers pressed on calculator and uses
 * them for refreshing current screen state.
 * 
 * @author Teo Toplak
 *
 */
public class NumberUtility {

	/**
	 * Method which takes number pressed on calculator and uses it to refresh
	 * state of screen to match user input.
	 * 
	 * @param num number pressed
	 * @param calc calculator
	 */
	public static void addNum(Double num, ICalculator calc) {
		
		Double num2 = calc.getScreen();
		if((num2.isNaN() || num2.isInfinite() || calc.getFunctionUsed())
				&& !screenEmpty(calc)) {
			calc.setLastNumExists(false);
			calc.setNumToScreen(num);
			calc.setDotActivated(false);
			calc.setFunctionUsed(false);
			return;
		} 
		
		if(calc.getOperationExists() && !calc.getLastNumExists()) {
			calc.setLastNumExists(true);
			calc.setLastNum(num2);
			calc.setNumToScreen(num);
		} else {
			String str = calc.getScreenString();
			str += num.intValue();
			calc.setNumToScreen(Double.parseDouble(str));
		}
	}
	
	/**
	 * Returns true if screen on calculator is currently empty.
	 * @param calc calculator
	 * @return true if screen is empty
	 */
	private static boolean screenEmpty(ICalculator calc) {
		String str = calc.getScreenString();
		if(str.equals("")) {
			return true;
		}
		return false;
	}
}

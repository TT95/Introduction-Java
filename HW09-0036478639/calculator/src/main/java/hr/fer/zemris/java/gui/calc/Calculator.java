package hr.fer.zemris.java.gui.calc;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Class demonstrating calculator GUI.
 * @author Teo Toplak
 *
 */
public class Calculator {
	
	/**
	 * Method called when executing program
	 * @param args exepts no command line argument
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(()-> {
			JFrame frame = new CalculatorGUI();
			frame.setVisible(true);
			frame.pack();
		});
	}
}

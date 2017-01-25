package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.operations.IOperation;

import java.awt.BorderLayout;


import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Class representing calculator GUI. Contains internal data used for
 * operations. Implements {@link ICalculator} which allows easy communication
 * with calculator internal data. Panel containing implemented interface
 * (buttons,screen...) created with {@link CalcPanel} class.
 * 
 * @author Teo Toplak
 *
 */
public class CalculatorGUI extends JFrame implements ICalculator{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tells if decimal dot is activated or not
	 */
	private boolean dotActivated;
	/**
	 * Tells if inverse function is selected or not
	 */
	private boolean inv;
	/**
	 * Calculator stack
	 */
	private Stack<Double> stack;
	/**
	 * Calculator screen
	 */
	private String screen;
	/**
	 * Calculators current operation
	 */
	private IOperation operation;
	/**
	 * Calculators last number
	 */
	private double lastNum;
	/**
	 * Calculators panel
	 */
	private JPanel panel;
	/**
	 * Tells if last number exists in calculator internal data
	 */
	private boolean lastNumExists;
	/**
	 * Tells if operation exists in calculator internal data
	 */
	private boolean operationExists;
	/**
	 * Tells if function button was used
	 */
	private boolean functionUsed;


	/**
	 * Constructor which creates calculators GUI
	 */
	public CalculatorGUI() {
		
		stack= new Stack<>();
		screen = "";
		inv = false;
		lastNum=-1;
		operation=null;
		lastNumExists=false;
		operationExists=false;
		dotActivated=false;
		
		setLocation(100,100);
		setLocation(20,50);
		setSize(480, 350);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Initiates creation of calculator GUI (panel mostly).
	 */
	private void initGUI() {

		getContentPane().setLayout(new BorderLayout());
		panel = CalcPanel.givePanel(this);
		getContentPane().add(panel, BorderLayout.CENTER);
	
	}

	@Override
	public IOperation getOperation() {
		return operation;
	}
	
	@Override
	public boolean getInv() {
		return inv;
	}
	
	@Override
	public Stack<Double> getStack() {
		return stack;
	}

	@Override
	public Double getScreen() {
		Double num;
		try {
			num = Double.parseDouble(screen);
		} catch (Exception e) {
			return Double.NaN;
		};
		return num;
	}
	
	@Override
	public double getLastNum() {
		return lastNum;
	}
	
	@Override
	public void setInv(boolean inv) {
		this.inv = inv;	
	}

	@Override
	public void setScreen(String string) {
		JLabel l = (JLabel) panel.getComponent(0); 
		l.setText(string);
		screen = string;
		
	}

	@Override
	public void setOperation(IOperation operation) {
		this.operation=operation;
	}

	@Override
	public void setLastNum(double num) {
		lastNum = num;
		
	}

	@Override
	public void setNumToScreen(Double num) {
		if(num == num.intValue()) {
			String str = String.format("%.0f", num);
			this.setScreen(str);
		} else {
			JLabel l = (JLabel) panel.getComponent(0);
			screen = String.valueOf(num);
			l.setText(screen);
		}
		
		
	}

	@Override
	public boolean getLastNumExists() {
		return lastNumExists;
	}

	@Override
	public void setLastNumExists(boolean lastNumExists) {
		this.lastNumExists=lastNumExists;
	}

	@Override
	public boolean getOperationExists() {
		return operationExists;
	}

	@Override
	public void setOperationExists(boolean operationExists) {
		this.operationExists=operationExists;
	}

	@Override
	public boolean getDotActivated() {
		return dotActivated;
	}

	@Override
	public void setDotActivated(boolean dotActivated) {
		this.dotActivated=dotActivated;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public String getScreenString() {
		JLabel l = (JLabel) panel.getComponent(0);
		return l.getText();
	}

	@Override
	public boolean getFunctionUsed() {
		return functionUsed;
	}

	@Override
	public void setFunctionUsed(boolean functionUsed) {
		this.functionUsed = functionUsed;
	}
}

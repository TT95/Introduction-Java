package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.operations.IOperation;

import java.util.Stack;

import javax.swing.JPanel;

public interface ICalculator {
	/**
	 * Returns true if inverse is selected on calculator.
	 * False otherwise.
	 * @return true if inverse is selected on calculator.
	 */
	public boolean getInv();
	/**
	 * Getter for calculator internal stack.
	 * @return
	 */
	public Stack<Double> getStack();
	/**
	 * Returns double value of screen.
	 * If value cannot be represented as double returns NaN.
	 * @return double value
	 */
	public Double getScreen();
	/**
	 * Getter for calculator current operation.
	 * @return current operation
	 */
	public IOperation getOperation();
	/**
	 * Getter for last number in calculator.
	 * @return last number 
	 */
	public double getLastNum();
	
	/**
	 * Returns true if last number exists in internal memory of calculator.
	 * False otherwise.
	 * @return true if last number exists
	 */
	public boolean getLastNumExists();

	/**
	 * Returns true if operation exists in internal memory of calculator. False
	 * otherwise.
	 * @return true if operation exists
	 */
	public boolean getOperationExists();
	/**
	 * Returns true if decimal dot is pressed on calculator.
	 * @return true if decimal dot is pressed
	 */
	public boolean getDotActivated();
	/**
	 * Getter for calculator panel which contains all interface.
	 * @return panel of calculator
	 */
	public JPanel getPanel();
	/**
	 * Returns current string of screen on calculator.
	 * @return string
	 */
	public String getScreenString();
	/**
	 * Returns boolean which tells if function button was just used.
	 * @return true if function button was used
	 */
	public boolean getFunctionUsed();
	
	
	/**
	 * Sets boolean which tells if function button was just used.
	 * @param functionUsed boolean for functionUsed boolean
	 */
	public void setFunctionUsed(boolean functionUsed);
	/**
	 * Sets calculators dotActivaed variable to given boolean.
	 * Usually only used for deselecting decimal dot.
	 * @param dotActivated boolean to set
	 */
	public void setDotActivated(boolean dotActivated);
	/**
	 * Used to tell calculator whether operation exists or doesnt.
	 * @param operationExists sets calculators operationExists 
	 * 		value to given boolean
	 */
	public void setOperationExists(boolean operationExists);
	/**
	 * Used to tell calculator whether inverse is selected or not.
	 * @param inv boolean to tell calculator whether inverse is seleceted or not.
	 */
	public void setInv(boolean inv);
	/**
	 * Sets screen of calcualtor to given string.
	 * @param string string given
	 */
	public void setScreen(String string);
	/**
	 * Sets operation of calculator to given one.
	 * @param operation operation
	 */
	public void setOperation(IOperation operation);
	/**
	 * Sets last number of calculator to given value.
	 * @param num value of number
	 */
	public void setLastNum(double num);
	/**
	 * Sets given number to screen on calculator.
	 * @param num given number
	 */
	public void setNumToScreen(Double num);
	/**
	 * Used to tell calculator whether last number exists or not.
	 * @param lastNumExists boolean to tell calculator
	 * 		 whether last number exists or not
	 */
	public void setLastNumExists(boolean lastNumExists);
}

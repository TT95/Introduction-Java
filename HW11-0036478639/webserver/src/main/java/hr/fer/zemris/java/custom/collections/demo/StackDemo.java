package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demo program that evaluates mathematical expression in postfix-notation using StackDemo class.
 * It accepts exactly one command-line argument, a string representing expression to be evaluated.
 * Numbers in expression must be integers, and operators must be +, -, *, / or %
 * @author Teo Toplak
 *
 */
public class StackDemo {

	/**
	 * Stack that is used to evaluate the expression.
	 */
	private static ObjectStack stack = new ObjectStack();

	/**
	 * This method is called when the program starts.
	 * @param args Command-line arguments, explained in the description of the program.
	 */
	public static void main(String[] args) {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Invalid number of arguments");
		}
				
		String[] argumentList = args[0].trim().split("\\s+");
		

		for(int i = 0; i < argumentList.length; i++) {
			switch (argumentList[i]) {
			case "+":		
			case "-":
			case "*":
			case "/":
			case "%":
				executeOperation(argumentList[i]);
				break;
			default:
				int number = 0;
				try {
					number = Integer.parseInt(argumentList[i]);
				} catch (NumberFormatException e) {
					System.out.println("Expression invalid: Can not resolve number or operator.");
					System.exit(1);
				}
				stack.push(number);
				break;
			}			
		}
		
		if(stack.size() != 1) {
			System.out.println("Expression invalid: Result not a single number.");
			System.exit(1);
		}
		System.out.println(stack.pop());
		
	}
	
	/**
	 * Executes operation on last two numbers on stack, and puts result back on the stack.
	 * @param operator symbol representing operation to be executed.
	 */
	private static void executeOperation(String operator){
		
		Object object2 = null;
		Object object1 = null;
			
		int value1 = 0;
		int value2 = 0;
		
		try {
			object2 = stack.pop();
			object1 = stack.pop();
		} catch (EmptyStackException e) {
			System.out.println("Expression invalid: Stack already empty.");
			System.exit(1);
		}
		
		value2 = (Integer)object2;
		value1 = (Integer)object1;
		
		int result = 0;
		
		switch (operator) {
		case "+":
			result = value1 + value2;
			break;
		case "-":
			result = value1 - value2;
			break;
		case "*":
			result = value1 * value2;
		case "/":
			try {
				result = value1 / value2;
			} catch (ArithmeticException e){
				System.out.println("Expression invalid: Division by zero.");
				System.exit(1);
			}
			break;
		case "%":
				result = value1 % value2;
			break;
		default:
				System.out.println("Operator invalid.");
				System.exit(1);
			break;
		}		
		stack.push(result);
	}

}

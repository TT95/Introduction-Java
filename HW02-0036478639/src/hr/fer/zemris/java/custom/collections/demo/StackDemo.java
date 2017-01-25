package hr.fer.zemris.java.custom.collections.demo;
import hr.fer.zemris.java.custom.collections.*;

/**
 * Class which calculates expression in postfix representation.
 * Uses ObjectStack as collection for solving.
 * Writes solution on standard output.
 * Command-line argument is one string for example: "-1 8  3  /  +" (notice quotation marks).
 * @param args argument must be legal posfix expression represented by one string only.
 * @author Teo
 *
 */
public class StackDemo {

	/**
	 * Method called upon executing program.
	 */
	public static void main(String[] args) {
		
		ObjectStack stack= new ObjectStack();
		
		//checking for illegal argument
		if(args.length!=1){
			System.out.println("Wrong argument");
			System.exit(1);
		}
		
		//spliting string into array removing space blanks
		String[] array = args[0].split("\\s+");
		
		//going element by element in array, calculating.
		for(int count=0;count<array.length;count++){			
			if(isNumber(array[count])){
				stack.push(array[count]);
			} 
			else{
				if(stack.size()<2){
					System.err.println("Illegal argument (check quantity of operators to numbers)");
					System.exit(1);
				}
				calculate(stack, array[count]);
			}
		}
		if(stack.size()!=1){
			System.err.println("Illegal argument");
			System.exit(1);
		}
		System.out.println("Expression evaluates to " + stack.pop());	
	}
	
	/**
	 * Method that includes parser to help calculate given expression.
	 * It is expected to have atleast 2 numbers on stack (given in argument) and one string which represents operator.
	 * @param stack stack with numbers to be calculated
	 * @param string string which represents operator
	 */
	public static void calculate(ObjectStack stack, String string){
		int number1=Integer.parseInt((String)stack.pop());
		int number2=Integer.parseInt((String)stack.pop());
		switch(string){
		case "+":
			number1=number2 + number1;
			break;
		case "-":
			number1=number2 - number1;
			break;
		case "/":
			number1=number2 / number1;
			break;
		case "*":
			number1=number2 * number1;
			break;
		case "%":
			number1=number2 % number1;
			break;
		default:
			System.err.println("Illegal argument");
			System.exit(1);
		}
		stack.push(Integer.toString(number1));
	}
	/**
	 * Method for checking if a given string is integer.
	 * @param string string that represents integer number
	 * @return returns true if string is an integer, false otherwise
	 */
	public static boolean isNumber(String string)  
	{  
	  try  
	  {  
	   Integer.parseInt(string);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

}

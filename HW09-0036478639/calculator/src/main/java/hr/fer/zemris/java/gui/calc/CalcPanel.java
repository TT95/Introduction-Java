package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.RCPosition;
import hr.fer.zemris.java.gui.calc.functions.FunctionUtility;
import hr.fer.zemris.java.gui.calc.functions.IFunction;
import hr.fer.zemris.java.gui.calc.numbers.NumberUtility;
import hr.fer.zemris.java.gui.calc.operations.IOperation;
import hr.fer.zemris.java.gui.calc.operations.OperationUtility;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;




import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Class used to create {@link JPanel} for calculator. It contains all
 * implemented buttons and screen. Contains only one method which gives panel to
 * calculator after whole interface has been added to panel.
 * 
 * @author Teo Toplak
 *
 */
public class CalcPanel {


	/**
	 * Method creates panel holding whole interface for calculator, and returns it.
	 * @param calc calculator
	 * @return panel for calculator
	 */
	public static JPanel givePanel(ICalculator calc) {


		List<Component> components = new ArrayList<>();
		
		CalcLayout layout = new CalcLayout(5);
		JPanel panel = new JPanel(layout);
		
		JLabel screen = new JLabel("",SwingConstants.RIGHT);
		components.add(screen);
		screen.setOpaque(true);
	
		
		JButton equals = new JButton("=");
		equals.addActionListener(e-> {
			if(calc.getLastNumExists()) {
				Double num1 = calc.getLastNum();
				Double num2 = calc.getScreen();
				if(num2.isNaN()) {
					calc.setScreen("MATH ERROR");
					return;
				}
				calc.setOperationExists(false);
				calc.setLastNumExists(false);
				calc.setDotActivated(false);
				Double res = calc.getOperation().calculate(num1, num2);
				calc.setScreen(String.valueOf(res));
			} else {
				return;
			}
		});
		components.add(equals);
		
		JButton clr = new JButton("clr");
		clr.addActionListener(e-> {
			calc.setScreen("");
			calc.setDotActivated(false);
		});
		components.add(clr);
		
		JButton fraction = new JButton("1/x");
		fraction.addActionListener(e-> {
			IFunction func = new IFunction() {
				@Override
				public double function(double num) {
					return 1/num;
				}

				@Override
				public double invFunction(double num) {
					return 1/num;
				}
			};
			new FunctionUtility(func, calc);
		});
		components.add(fraction);
		
		JButton sin = new JButton("sin");
		sin.addActionListener(e-> {
			IFunction func = new IFunction() {
				@Override
				public double function(double num) {
					return Math.sin(num);
				}

				@Override
				public double invFunction(double num) {
					return Math.asin(num);
				}
			};
			new FunctionUtility(func, calc);
		});
		components.add(sin);
		
		JButton seven = new JButton("7");
		seven.addActionListener(e-> {
			NumberUtility.addNum(7.0, calc);
		});
		components.add(seven);
		
		JButton eight = new JButton("8");
		eight.addActionListener(e-> {
			NumberUtility.addNum(8.0, calc);
		});
		components.add(eight);
		
		JButton nine = new JButton("9");
		nine.addActionListener(e-> {
			NumberUtility.addNum(9.0, calc);
		});
		components.add(nine);
		
		JButton divide = new JButton("/");
		divide.addActionListener(e-> {
			IOperation op = (num1,num2)->{
				return num1/num2;
			};
			OperationUtility.setOperation(op,calc);
		});
		components.add(divide);
		
		JButton res = new JButton("res");
		res.addActionListener(e-> {
			calc.getStack().clear();
			calc.setLastNum(-1);
			calc.setOperation(null);
			calc.setScreen("");
			calc.setLastNumExists(false);
			calc.setOperationExists(false);
			calc.setDotActivated(false);
			calc.setFunctionUsed(false);
		});
		components.add(res);
		
		JButton log = new JButton("log");
		log.addActionListener(e-> {
			IFunction func = new IFunction() {
				@Override
				public double function(double num) {
					return Math.log10(num);
				}

				@Override
				public double invFunction(double num) {
					return Math.pow(10,num);
				}
			};
			new FunctionUtility(func, calc);
		});
		components.add(log);
		
		JButton cos = new JButton("cos");
		cos.addActionListener(e-> {
			IFunction func = new IFunction() {
				@Override
				public double function(double num) {
					return Math.cos(num);
				}

				@Override
				public double invFunction(double num) {
					return Math.acos(num);
				}
			};
			new FunctionUtility(func, calc);
		});
		components.add(cos);
		
		JButton four = new JButton("4");
		four.addActionListener(e-> {
			NumberUtility.addNum(4.0, calc);
		});
		components.add(four);
		
		JButton five = new JButton("5");
		five.addActionListener(e-> {
			NumberUtility.addNum(5.0, calc);
		});
		components.add(five);
		
		JButton six = new JButton("6");
		six.addActionListener(e-> {
			NumberUtility.addNum(6.0, calc);
		});
		components.add(six);
		
		JButton multiply = new JButton("*");
		multiply.addActionListener(e-> {
			IOperation op = (num1,num2)->{
				return num1*num2;
			};
			OperationUtility.setOperation(op,calc);
		});
		components.add(multiply);
		
		JButton push = new JButton("push");
		push.addActionListener(e-> {
			Double num = calc.getScreen();
			if(num.isNaN()) {
				System.out.println("josko");
				return;
			}
			calc.getStack().add(num);
		});
		components.add(push);
		
		JButton ln = new JButton("ln");
		ln.addActionListener(e-> {
			IFunction func = new IFunction() {
				@Override
				public double function(double num) {
					return Math.log(num);
				}

				@Override
				public double invFunction(double num) {
					return Math.pow(Math.E,num);
				}
			};
			new FunctionUtility(func, calc);	
		});
		components.add(ln);
		
		JButton tan = new JButton("tan");
		tan.addActionListener(e-> {
			IFunction func = new IFunction() {
				@Override
				public double function(double num) {
					return Math.tan(num);
				}

				@Override
				public double invFunction(double num) {
					return Math.atan(num);
				}
			};
			new FunctionUtility(func, calc);
		});
		components.add(tan);
		
		JButton one = new JButton("1");
		one.addActionListener(e-> {
			NumberUtility.addNum(1.0, calc);
		});
		components.add(one);
		
		JButton two = new JButton("2");
		two.addActionListener(e-> {
			NumberUtility.addNum(2.0, calc);
		});
		components.add(two);
		
		JButton three = new JButton("3");
		three.addActionListener(e-> {
			NumberUtility.addNum(3.0, calc);
		});
		components.add(three);
		
		JButton minus = new JButton("-");
		minus.addActionListener(e-> {
			IOperation op = (num1,num2)->{
				return num1-num2;
			};
			OperationUtility.setOperation(op,calc);
		});
		components.add(minus);
		
		JButton pop = new JButton("pop");
		pop.addActionListener(e-> {
			if(calc.getStack().isEmpty()) {
				calc.setScreen("Stack is empty");
				return;
			}
			Double num = calc.getStack().pop();
			calc.setNumToScreen(num);
			calc.setDotActivated(false);
		});
		components.add(pop);
		
		JButton power = new JButton("x^n");
		power.addActionListener(e-> {
			IOperation op;
			if(!calc.getInv()) {
				op = (num1,num2)->{
					return Math.pow(num1, num2);
				};
			} else {
				op = (num1,num2)->{
					return Math.pow(num1, 1/num2);
				};
			}
			OperationUtility.setOperation(op,calc);
		});
		components.add(power);
		
		JButton ctg = new JButton("ctg");
		ctg.addActionListener(e-> {
			IFunction func = new IFunction() {
				@Override
				public double function(double num) {
					return 1/Math.tan(num);
				}

				@Override
				public double invFunction(double num) {
					return 1/Math.atan(num);
				}
			};
			new FunctionUtility(func, calc);
		});
		components.add(ctg);
		
		JButton zero = new JButton("0");
		zero.addActionListener(e-> {
			NumberUtility.addNum(0.0, calc);
		});
		components.add(zero);
		
		JButton sign = new JButton("+/-");
		sign.addActionListener(e-> {
			IFunction func = new IFunction() {
				@Override
				public double function(double num) {
					return -1*num;
				}

				@Override
				public double invFunction(double num) {
					return -1*num;
				}
			};
			new FunctionUtility(func, calc);
		});
		components.add(sign);
		
		JButton dot = new JButton(".");
		dot.addActionListener(e-> {
			Double num = calc.getScreen();
			if(num.isNaN()) {
				return;
			}
			String str = calc.getScreenString();
			//if already got dot
			if(str.contains(".")) {
				return;
			}
			calc.setDotActivated(true);
			str += ".";
			calc.setScreen(str);
		});
		components.add(dot);
		
		JButton plus = new JButton("+");
		plus.addActionListener(e-> {
			IOperation op = (num1,num2)->{
				return num1+num2;
			};
			OperationUtility.setOperation(op,calc);
		});
		components.add(plus);
		
		JCheckBox inv = new JCheckBox("inv");
		inv.addItemListener(e-> {
			if(inv.isSelected()) {
				calc.setInv(true);
			} else {
				calc.setInv(false);
			}
		});
		components.add(inv);
		

		int cols = layout.COLS;
		int colPosition = 0;
		int rowPosition = 0;
		for(int i=0;i<components.size();i++) {
			Component c = components.get(i);
			if(i==0) {
				c.setBackground(Color.orange);
				panel.add(c,new RCPosition(1,1));
				colPosition = 5;
				continue;
			}
			if(colPosition==cols) {
				colPosition=0;
				rowPosition++;
			}
			panel.add(c,new RCPosition(rowPosition+1, colPosition+1));
			colPosition++;
		}
		return panel;
	}
	
}

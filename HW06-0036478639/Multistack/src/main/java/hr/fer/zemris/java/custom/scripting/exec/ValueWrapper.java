package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class wrapping any object and some operational method for some of them.
 * Supported classes for operation methods are primitive types, Integer, Double,
 * String and null.
 * 
 * @author Teo Toplak
 *
 */
public class ValueWrapper {

	/**
	 * value wrapped
	 */
	private Object value;

	/**
	 * Constructor which creates instance of this class with value wrapped.
	 * @param value value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	/**
	 * Method which increments current with given value.
	 * @param incValue given value
	 */
	public void increment(Object incValue) {
		Number number1= parseToNumber(value);
		Number number2=parseToNumber(incValue);
		if(number1 instanceof Double || number2 instanceof Double) {
			value= number1.doubleValue() +number2.doubleValue();
		} else {
		value= number1.intValue()+number2.intValue();
		}
	}
	
	/**
	 * Method which decrements current value with given value.
	 * @param decValue given value
	 */
	public void decrement(Object decValue) {
		Number number1= parseToNumber(value);
		Number number2=parseToNumber(decValue);
		if(number1 instanceof Double || number2 instanceof Double) {
			value= number1.doubleValue()-number2.doubleValue();
		} else {
		value= number1.intValue()-number2.intValue();
		}
	}
	
	/**
	 * Method which multiplies current value with given value.
	 * @param decValue given value
	 */
	public void multiply(Object mulValue) {
		Number number1= parseToNumber(value);
		Number number2=parseToNumber(mulValue);
		if(number1 instanceof Double || number2 instanceof Double) {
			value= number1.doubleValue()*number2.doubleValue();
		} else {
		value= number1.intValue()*number2.intValue();
		}
	}
	
	/**
	 * Method which divides current value with given value.
	 * @param decValue given value
	 */
	public void divide(Object divValue) {
		Number number1= parseToNumber(value);
		Number number2=parseToNumber(divValue);
		if(number2.equals(0)) {
			throw new ArithmeticException("Cannot divide with zero!");
		}
		if(number1 instanceof Double || number2 instanceof Double) {
			value= number1.doubleValue()/number2.doubleValue();
		} else {
		value= number1.intValue()/number2.intValue();
		}
	}
	
	/**
	 * Method which compares current value with given value (returnes negative
	 * number if current value is smaller, positive if bigger and zero if
	 * equal).
	 * 
	 * @param decValue
	 *            given value
	 * @return negative number if current value is smaller, positive if bigger
	 *         and zero if equal
	 */
	public int numCompare(Object withValue) {
		Number number1= parseToNumber(value);
		Number number2=parseToNumber(withValue);
		if(number1 instanceof Double || number2 instanceof Double) {
			return (int)(number1.doubleValue()-number2.doubleValue());
		} 
		return number1.intValue()-number2.intValue();
	}
	
	/**
	 * Method which converts object to integer or double if possible.
	 * If argument is null, method will convert it to integer with value 0.
	 * @param o object to convert	
	 * @return integer or double
	 * @throws RuntimeException if parsing is not possible
	 */
	private Number parseToNumber(Object o) {
		if(o==null) {
			return 0;
		}
		if(o instanceof String) {
			String string = (String)o;
			try{
				if(string.contains(".") || string.contains("E")) {
					return (Double)Double.parseDouble(string);
				}
				return (Integer)Integer.parseInt(string);
			} catch (Exception e) {
				throw new RuntimeException("Cannot parse string to number for appropriate action!");
			}
		}
		if(o instanceof Integer) {
			return (Integer)o;
		}
		if(o instanceof Double) {
			return (Double)o;
		}
		throw new RuntimeException("Cannot convert object to number for appropriate action!");
	}
	
	/**
	 * Value getter
	 * @return value 
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Value setter
	 * @param value value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	
}

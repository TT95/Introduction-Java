package hr.fer.zemris.java.complex;



public class Complex {

	/**
	 * Complex number with zero value
	 */
	public static final Complex ZERO = new Complex(0,0);
	/**
	 * Complex number with real part value set to 1,
	 * imaginary to 0
	 */
	public static final Complex ONE = new Complex(1,0);
	/**
	 * Complex number with real part value set to -1,
	 * imaginary to 0
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	/**
	 * Complex number with real part value set to ,
	 * imaginary to 1
	 */
	public static final Complex IM = new Complex(0,1);
	/**
	 * Complex number with real part value set to 0,
	 * imaginary to -1
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * real part of complex number
	 */
	private double real;
	/**
	 * imaginary part of complex number
	 */
	private double imaginary;
	/**
	 * magnitude of complex number
	 */
	private double magnitude;
	/**
	 * angle of complex number
	 */
	private double angle;

	/**
	 * Default constructor which creates complex number with value 0
	 */
	public Complex() {
		real=0;
		imaginary=0;
		magnitude=0;
		angle=0;
	}
	
	/**
	 * Constructor taking real and imaginary part as arguments
	 * @param real real part of number
	 * @param imaginary imaginary part of number
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		this.magnitude = Math.hypot(real,imaginary);
		if(real==0){
			if(imaginary < 0){
				this.angle=Math.PI;
			}
			else{
				this.angle=0;
			}
		}
		else{
			this.angle =Math.atan2(imaginary ,real);
		}
	}
	
	/**
	 * Parses string to match complex number
	 * @param string string		
	 * @return complex number
	 */
	public static Complex parseString(String string) {
		string = string.replaceAll(" ", "");
		String[] arr = string.split("(?=[-+])");
		if(arr.length==1) {
			if(arr[0].contains("i")) {
				arr[0]=arr[0].replaceAll("i", "");
				if(!Complex.isNumeric(arr[0])) {
					return new Complex(0, Double.parseDouble(arr[0]+"1"));
				}
				return new Complex(0, Double.parseDouble(arr[0]));
			} else {
				return new Complex(Double.parseDouble(arr[0]),0);				
			}
		}
		if(arr[1].contains("i")) {
			arr[1]=arr[1].replaceAll("i", "");
			if(!Complex.isNumeric(arr[1])) {
				return new Complex(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]+"1"));
			}
			return new Complex(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
		} else {
			arr[0]=arr[0].replaceAll("i", "");
			if(!Complex.isNumeric(arr[0])) {
				return new Complex(Double.parseDouble(arr[1]), Double.parseDouble(arr[0]+"1"));
			}
			return new Complex(Double.parseDouble(arr[1]), Double.parseDouble(arr[0]));
		}
	}

	/**
	 * Return module od complex number
	 * @return
	 */
	public double module() {
		return magnitude;
	}
	
	/**
	 * Multiplies 2 complex numbers
	 * @param c complex number to multiply with
	 * @return result of operation as complex number
	 */
	public Complex multiply(Complex c) { 
		double newReal=real*c.getReal() - imaginary*c.getImaginary();
		double newImaginary=real*c.getImaginary() + imaginary*c.getReal();
		return new Complex(newReal, newImaginary);
	}
	
	/**
	 * Divides two complex numbers
	 * @param c complex number as divisor 
	 * @return result of dividing
	 */
	public Complex divide(Complex c) {
		if(c.getMagnitude()==0) {
			throw new IllegalArgumentException();
		}
		double newReal = magnitude / c.getMagnitude() * Math.cos(angle - c.getAngle());
		double newImaginary = magnitude / c.getMagnitude() * Math.sin(angle - c.getAngle());
		return new Complex(newReal, newImaginary);
	}
	
	
	/**
	 * Adds given complex number with this one and returns result of operation.	
	 * @param c complex number added to this one
	 * @return result of operation
	 */
	public Complex add(Complex c) {
		return new Complex(real+c.getReal(),imaginary+c.getImaginary());

	}
	
	/**
	 * Subtracts given complex number with this one and returns result of operation.	
	 * @param c complex number subtracted to this one
	 * @return result of operation
	 */
	public Complex sub(Complex c) {
		return new Complex(real-c.getReal(),imaginary-c.getImaginary());

	}

	/**
	 * Negates complex number
	 * @return negative complex number
	 */
	public Complex negate() {
		return new Complex(-1*real,-1*imaginary);
	}
	
	@Override
	public String toString() {
		return real + (imaginary>=0?"+":"-") + Math.abs(imaginary) + "i";
	}
	
	/**
	 * Method which raises complex number to power of n.
	 * @param n raising power
	 * @throws IllegalArgumentException if n is less than 0
	 * @return result of operation
	 */
	public Complex power(int n){
		if(n<0){
			throw new IllegalArgumentException("Illegal argument for method \"power\" ");
		}
		double m=(double)n; //since Math.pow is not compatible with int numbers
		double newReal=Math.pow(magnitude, m) * Math.cos(m*angle);
		double newImaginary=Math.pow(magnitude, m) * Math.sin(m*angle);
		return new Complex(newReal, newImaginary);
	}
	
	/**
	 * Getter method for real part of complex number
	 * @return real part
	 */
	public double getReal(){
		return real;
	}
	
	/**
	 * Getter method for imaginary part of complex number
	 * @return imaginary part
	 */
	public double getImaginary(){
		return imaginary;
	}
	
	/**
	 * Getter method for magnitude of complex number
	 * @return magnitude
	 */
	public double getMagnitude(){
		return magnitude;
	}
	
	/**
	 * Getter method for angle of complex number
	 * @return angle
	 */
	public double getAngle(){
		return angle;
	}
	
	/**
	 * Method returns true if complex numbers are same
	 * @param c complex number
	 * @return true if complex numbers are same
	 */
	public boolean equals(Complex c) {
		if(c.getReal()==real && c.getImaginary()==imaginary) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if string is a number.
	 * Used locally in parseString() method.
	 * @param str string 
	 * @return true if string is a number
	 */
	private static boolean isNumeric(String str)  {  
		try  {  
			Double.parseDouble(str);  
		}  catch(NumberFormatException nfe)  {  
			return false;  
		}  
		return true;  
	}
}

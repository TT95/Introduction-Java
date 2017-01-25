package hr.fer.zemris.java.tecaj.hw3;

/**
 * Class represents an unmodifiable complex number.
 * Class has implemented support for working with complex numbers.
 * Each method which performs some kind of modification returns a new instance which 
   represents modified number.
 * @author Teo Toplak
 */


public class ComplexNumber {

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
	 * Constructor with possibility of setting real and imaginary part.
	 * @param real real part
	 * @param imaginary imaginary part
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		this.magnitude = Math.hypot(real,imaginary);
		if(real==0){
			if(imaginary < 0){
				this.angle=180;
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
	 * Factory method which creates comlex number with real part given.
	 * @param real real part
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromReal(double real){
		return new ComplexNumber(real,0);
	}
	
	/**
	 * Factory method which creates comlex number with imaginary part given.
	 * @param imaginary imaginary part
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromImagnary(double imaginary){
		return new ComplexNumber(0,imaginary);
	}
	
	/**
	 * Factory method which creates comlex number with magnitude and angle given.
	 * @param magnitude magnitude of complex number
	 * @param angle angle of complex number
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle){
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Factory method which creates comlex number with string given.
	 * Method wont work for string with imaginary part leading.
	 * @param s string as complex number
	 * @return ComplexNumber
	 */
	public static ComplexNumber parse(String s){
		
		//using regex to split string in real part and imaginary part while preserving both number signs 
		String[] complexArray=s.split("(?=[-+])");
		if(complexArray.length==2){
			return new ComplexNumber(Double.parseDouble(complexArray[0]),
					Double.parseDouble(complexArray[1].substring(0,complexArray[1].length()-1)));
		}
		else{
			//is it only imaginary part in string?
			if(complexArray[0].substring(complexArray[0].length()-1,complexArray[0].length()).equals("i")){
				return new ComplexNumber(0,Double.parseDouble(complexArray[0].substring(0,complexArray[0].length()-1)));
			}
			//its only real part in string
			return new ComplexNumber(Double.parseDouble(complexArray[0]),0);
		}

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
	 * Method which adds one complex number to another.
	 * @param c second complex number
	 * @return  ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber c){
		real+=c.getReal();
		imaginary+=c.getImaginary();
		return new ComplexNumber(real,imaginary);
	}
	
	/**
	 * Method which subtracts one complex number from another.
	 * @param c second complex number
	 * @return  ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c){
		real-=c.getReal();
		imaginary-=c.getImaginary();
		return new ComplexNumber(real,imaginary);
	}
	
	/**
	 * Method which multiplies one complex number with another.
	 * @param c second complex number
	 * @return  ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber c){
		double newReal=real*c.getReal() - imaginary*c.getImaginary();
		double newImaginary=real*c.getImaginary() + imaginary*c.getReal();
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Method which divides one complex number with another.
	 * @param c second complex number
	 * @return  ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber c){
		double newReal = magnitude / c.getMagnitude() * Math.cos(angle - c.getAngle());
		double newImaginary = magnitude / c.getMagnitude() * Math.sin(angle - c.getAngle());
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Method which raises complex number to power of n.
	 * @param n raising power
	 * @throws IllegalArgumentException if n is less than 0
	 * @return
	 */
	public ComplexNumber power(int n){
		if(n<0){
			throw new IllegalArgumentException("Illegal argument for method \"power\" ");
		}
		double m=(double)n; //since Math.pow is not compatible with int numbers
		double newReal=Math.pow(magnitude, m) * Math.cos(m*angle);
		double newImaginary=Math.pow(magnitude, m) * Math.sin(m*angle);
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Method which calculates n-th root of complex number with given number.
	 * @param m raising power
	 * @throws IllegalArgumentException if given number is less or equal 0
	 * @return array of ComplexNumber
	 */
	public ComplexNumber[] root(int m){
		if(m<=0){
			throw new IllegalArgumentException("Illegal argument for method \"root\" ");
		}
		double n= (double)m;//since Math.pow is not compatible with int numbers
		ComplexNumber[] complexNumbersArray=new ComplexNumber[(int)n];
		for(int k=0;k<n;k++){
			complexNumbersArray[k]=new ComplexNumber(Math.pow(magnitude, 1/n)*Math.cos((angle+2*k*Math.PI)/n)
					, Math.pow(magnitude, 1/n)*Math.sin((angle+2*k*Math.PI)/n));
		}
		return complexNumbersArray;

	}
	
	/**
	 * Method which returns complex number in form of a string.
	 * @return string string
	 */
	public String toString(){
		String string="";
		if(real!=0){
			string+=Double.toString(real);
		}
		if(imaginary<0){
			string+=Double.toString(imaginary)+"i";
		}
		if(imaginary>0){
			string+="+"+Double.toString(imaginary)+"i";
		}
		if(string.equals("")){
			return "0.0";
		}
		return string;
	}
	
}


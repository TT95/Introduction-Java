package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.comparison.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;

/**
 * Class representing conditional expression used for accepting records in database.
 * @author Teo Toplak
 *
 */
public class ConditionalExpression {

	/**
	 * IFieldValueGetter strategy
	 */
	private IFieldValueGetter fieldValueGetter;
	/**
	 * string literal
	 */
	private String stringLiteral;
	/**
	 * IComparisonOperator strategy
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor which gets a reference to IFieldValueGetter strategy, a
	 * reference to string literal and a reference to IComparisonOperator
	 * strategy, and creates conditional expression.
	 * 
	 * @param fieldValueGetter IFieldValueGetter strategy
	 * @param stringLiteral string literal
	 * @param comparisonOperator IComparisonOperator strategy
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter,
			String stringLiteral, IComparisonOperator comparisonOperator) {
		super();
		this.fieldValueGetter = fieldValueGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}
	/**
	 * IFieldValueGetter strategy getter.
	 * @return IFieldValueGetter strategy
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}
	/**
	 * String literal getter.
	 * @return string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
	/**
	 * IComparisonOperator strategy getter.
	 * @return IComparisonOperator strategy
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
	
	
}

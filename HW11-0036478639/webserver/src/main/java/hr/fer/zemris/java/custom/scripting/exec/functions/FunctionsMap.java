package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Class giving away execution methods for smart script function through map.
 * @author Teo Toplak
 *
 */
public class FunctionsMap {

	/**
	 * Map containing execution methods
	 */
	private Map<String,IFunction> map;
	
	/**
	 * Return double representation of value from
	 * multistack.
	 * @param x object
	 * @param ms multistack
	 * @return double representation
	 */
	private Double getDouble(Object x,ObjectMultistack ms) {
		if(x instanceof TokenConstantDouble) {
			TokenConstantDouble t = (TokenConstantDouble)x;
			return t.getValue();
		}
		if(x instanceof TokenConstantInteger) {
			TokenConstantInteger t = (TokenConstantInteger)x;
			return Integer.valueOf(t.getValue()).doubleValue();
		}
		if(x instanceof TokenVariable) {
			TokenVariable t = (TokenVariable)x;
			return (Double) ms.peek(t.asText()).getValue();
		}
		if(x instanceof String) {
			return Double.parseDouble((String)x);
		}
		if(x instanceof Double) {
			return (Double)x;
		}
		System.err.println("Wrong input, could not parse value to double for operation!");
		System.exit(1);
		//for warnings
		return null;
	}
	
	/**
	 * Constructor creating map with funcitons.
	 */
	public FunctionsMap() {
		map = new HashMap<>();
		map.put("sin", (ms,s,c) -> {
			s.push(Math.sin(getDouble(s.pop(), ms)*Math.PI/180));
		});
		map.put("decfmt", (ms,s,c) -> {
			Object f = s.pop();
			DecimalFormat dec = new DecimalFormat((String)f);
			Object x = s.pop();
			s.push(dec.format(getDouble(x, ms)));
		});
		map.put("dup", (ms,s,c) -> {
			Object x = s.pop();
			s.push(x);
			s.push(x);
		});
		map.put("swap", (ms,s,c) -> {
			Object x = s.pop();
			Object y = s.pop();
			s.push(x);
			s.push(y);
		});
		map.put("setMimeType", (ms,s,c) -> {
			Object x = s.pop();
			c.setMimeType((String)x);
		});
		map.put("paramGet", (ms,s,c) -> {
			Object dv = s.pop();
			Object name = s.pop();
			Object value = c.getParameter((String)name);
			s.push(value==null ? dv : value);
		});
		map.put("pparamGet", (ms,s,c) -> {
			Object dv = s.pop();
			Object name = s.pop();
			Object value = c.getPersistentParameter((String)name);
			s.push(value==null ? dv : value);
		});
		map.put("pparamSet", (ms,s,c) -> {
			Object name = s.pop();
			Object value = s.pop();
			c.setPersistentParameter((String)name,(String) value);
		});
		map.put("pparamDel", (ms,s,c) -> {
			Object name = s.pop();
			c.removePersistentParameter((String)name);
		});
		map.put("tparamGet", (ms,s,c) -> {
			Object dv = s.pop();
			Object name = s.pop();
			Object value = c.getTemporaryParameter(((String)name));
			s.push(value==null ? dv : value);
		});
		map.put("tparamSet", (ms,s,c) -> {
			Object name = s.pop();
			Object value = s.pop();
			c.setTemporaryParameter((String)name,(String) value);
		});
		map.put("tparamDel", (ms,s,c) -> {
			Object name = s.pop();
			c.removeTemporaryParameter((String)name);
		});
	}

	/**
	 * Returns smart script function from map created
	 * @param string name of smart script funciton
	 * @return smart script function
	 */
	public IFunction getMap(String string) {
		return map.get(string);
	}
}

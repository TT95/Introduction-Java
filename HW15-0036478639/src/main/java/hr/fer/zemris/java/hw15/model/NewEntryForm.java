package hr.fer.zemris.java.hw15.model;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Form for creating new entry.
 * Contains map with error which has been made
 * on site. Validates itself, contains method for
 * filling its properties from http request etc.
 * 
 * @author Teo Toplak
 *
 */
public class NewEntryForm {

	/**
	 * title
	 */
	private String title;
	/**
	 * text
	 */
	private String text;
	/**
	 * map containing errors to display to user
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Gets error by name
	 * @param name error name
	 * @return error text
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	/**
	 * Returns true if any errors exist
	 * @return true if any errors exist
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	/**
	 * Returns true if error with given name
	 * exists in map
	 * @param name error name
	 * @return true if error exist
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * validates this for, and fills 
	 * map with errors if necessary 
	 */
	public void validate() {
		errors.clear();
		
		if(title.isEmpty()) {
			errors.put("title", "Provide title!");
			return;
		}
		if(text.isEmpty()) {
			errors.put("text", "Enter text!");
			return;
		}
		
	}
	
	/**
	 * Fills form's properties with http request
	 * @param req http request
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.title = prepare(req.getParameter("title"));
		this.text = prepare(req.getParameter("text"));
	}
	/**
	 * Prepares string by trimming it and 
	 * emptying it if string is null
	 * @param s string
	 * @return prepared string
	 */
	private String prepare(String s) {
		if(s==null) return "";
		return s.trim();
	}

	/**
	 * title getter
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * title setter
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * text getter
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * text setter
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
}
	

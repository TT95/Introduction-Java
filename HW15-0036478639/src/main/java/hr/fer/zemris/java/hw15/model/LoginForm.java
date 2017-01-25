package hr.fer.zemris.java.hw15.model;

import hr.fer.zemris.java.hw15.dao.DAOProvider;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * Form for user login.
 * Contains map with error which has been made
 * on site. Validates itself, contains method for
 * filling its properties from http request etc.
 * 
 * @author Teo Toplak
 *
 */
public class LoginForm {

	/**
	 * nickname
	 */
	private String nick;
	/**
	 * password
	 */
	private String password;
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
		
		if(nick.isEmpty()) {
			errors.put("nick", "Provide nickname!!");
			return;
		}
		if(password.isEmpty()) {
			errors.put("password", "Enter password!");
			return;
		}
		if(!DAOProvider.getDAO().isDuplicateNickname(nick)) {
			errors.put("nick", "Nickname doesnt exist!");
			password="";
			return;
		}
		
		if(!DAOProvider.getDAO().legalLogin(nick, password)) {
			errors.put("password", "Wrong password!");
			password="";
		}
	}
	
	/**
	 * Fills form's properties with http request
	 * @param req http request
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.nick = prepare(req.getParameter("nick"));
		this.password = prepare(req.getParameter("password"));
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
	 * nickname getter
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}
	/**
	 * nickname setter
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	/**
	 * password getter
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * password setter
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

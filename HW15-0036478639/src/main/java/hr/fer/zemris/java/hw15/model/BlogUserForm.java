package hr.fer.zemris.java.hw15.model;


import hr.fer.zemris.java.hw15.dao.DAOProvider;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Form for creating new user.
 * Contains map with error which has been made
 * on site. Validates itself, contains method for
 * filling its properties from http request etc.
 * 
 * @author Teo Toplak
 *
 */
public class BlogUserForm {

	/**
	 * first name
	 */
	private String firstName;
	/**
	 * last name
	 */
	private String lastName;
	/**
	 * nickname
	 */
	private String nick;
	/**
	 * email
	 */
	private String email;
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
		
		if(firstName.isEmpty()) {
			errors.put("firstName", "Provide first name!");
		}
		
		if(lastName.isEmpty()) {
			errors.put("lastName", "Provide last name!");
		}

		if(this.email.isEmpty()) {
			errors.put("email", "Provide email!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("email", "Email is in wrong format!");
			}
		}
		
		if(nick.isEmpty()) {
			errors.put("nick", "Provide nickname!!");
		} else if(DAOProvider.getDAO().isDuplicateNickname(nick)) {
			errors.put("nick", "Nickname already exists!");
		}
		
		if(password.isEmpty()) {
			errors.put("password", "Enter password!");
		}
	}
	
	/**
	 * Fills form's properties with http request
	 * @param req http request
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.nick = prepare(req.getParameter("nick"));
		this.email = prepare(req.getParameter("email"));
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
	 * first name getter
	 * @return the firstName
	 * 
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * first name setter
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * last name getter
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * last name setter
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * email getter
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * email setter
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * password getter
	 * @return the passwordHash
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * password setter
	 * @param passwordHash the passwordHash to set
	 */
	public void setPassword(String passwordHash) {
		this.password = passwordHash;
	}
	
	
}

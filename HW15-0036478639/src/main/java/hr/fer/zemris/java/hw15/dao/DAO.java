package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * Interface that demands several methods for communication
 * with database from all DAO implementations.
 * 
 * @author Teo Toplak
 *
 */
public interface DAO {

	/**
	 * Gets entry with selected <code>id</code>. If such entry doesnt exist,
	 * returns <code>null</code>.
	 * 
	 * @param id entry key
	 * @return entry or <code>null</code> if it doesnt exist
	 * @throws DAOException if excpetion occurs during getting information
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Returns list of users as {@link BlogUser} class from database.
	 * 
	 * @return list of users as {@link BlogUser} class from database
	 * @throws DAOException if excpetion occurs during getting information
	 */
	public List<BlogUser> getAllUsers() throws DAOException;
	
	/**
	 * Returns true if provided nickname already exist in database.
	 * 
	 * @param name nickname of user
	 * @return true if nickname already exists in database, false otherwise
	 */
	public boolean isDuplicateNickname(String name);
	
	/**
	 * Adds user to database
	 * @param user user to be added
	 */
	public void addUser(BlogUser user);
	
	/**
	 * Checks if there exists combination of nickname and 
	 * password provided in arguments
	 * @param nick nickname
	 * @param password password
	 * @return true if combination exists
	 */
	public boolean legalLogin(String nick, String password);
	
	/**
	 * Returns user from datase based on nickname provided
	 * @param nick nickname
	 * @return user with given nickname
	 */
	public BlogUser getUser(String nick);
	/**
	 * Returns user from datase based on id provided
	 * @param id user id
	 * @return user with given nickname
	 */
	public BlogUser getUser(Long id);
	
	/**
	 * Gets all blog entries based on nickname of user provided
	 * @param nick nickname of user
	 * @return list of entries under nickname provided
	 */
	public List<BlogEntry> getBlogEntries(String nick);
	
	/**
	 * Adds comment to entry (id of user and entry must be provided)
	 * @param idLoggedInUser id of user
	 * @param text text for comment
	 * @param entryId entry id
	 */
	public void addComment(Long idLoggedInUser, String text, String entryId);
	
	/**
	 * Adds entry to database
	 * @param entry {@link BlogEntry} class
	 */
	public void addBlogEntry(BlogEntry entry);
	
	/**
	 * Updates entry with its id and new text and title provided
	 * @param id entry id
	 * @param title entry title
	 * @param text entry text
	 */
	public void updateBlogEntry(String id, String title, String text);
}

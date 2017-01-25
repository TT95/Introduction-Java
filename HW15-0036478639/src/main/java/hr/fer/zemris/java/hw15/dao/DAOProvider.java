package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Provides DAO implementation.
 * This is sort of singleton visible to all classes
 * for easy communication with database selected.
 * 
 * @author Teo Toplak
 *
 */
public class DAOProvider {

	/**
	 * Variable holding dao imlementation
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * DAO implementation getter 
	 * @return DAO implementation
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}

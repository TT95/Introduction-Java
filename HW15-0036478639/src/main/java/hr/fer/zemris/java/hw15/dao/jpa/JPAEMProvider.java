package hr.fer.zemris.java.hw15.dao.jpa;

import hr.fer.zemris.java.hw15.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Class providing connections with database.
 * Contains pool with ready connections.
 * 
 * @author Teo Toplak
 *
 */
public class JPAEMProvider {

	/**
	 * thread pool for connections with database
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * entity manager getter.
	 * Gives ready connections for communication with
	 * database.
	 * @return entity manager 
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if(ldata==null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Closes pools and breakes connections
	 * @throws DAOException when dao exception occurs
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if(ldata==null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
	/**
	 * Class wrapping entity manager
	 * @author Teo Toplak
	 *
	 */
	private static class LocalData {
		EntityManager em;
	}
	
}

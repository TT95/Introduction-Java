package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class providing entity manager factory- pool
 * with connections with ready connections with database.
 * 
 * @author Teo Toplak
 *
 */
public class JPAEMFProvider {

	/**
	 * entity manager factory
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * factory getter
	 * @return entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * factory setter
	 * @param entity manager factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}

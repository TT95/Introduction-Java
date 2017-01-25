package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

/**
 * Singleton razred koji zna koga treba vratiti kao pruzatelja
 * usluge pristupa podsustavu za perzistenciju podataka.
 * Uocite da, iako je odluka ovdje hardkodirana, naziv
 * razreda koji se stvara mogli smo dinamicki procitati iz
 * konfiguracijske datoteke i dinamicki ucitati -- time bismo
 * implementacije mogli mijenjati bez ikakvog ponovnog kompajliranja
 * koda.
 * 
 * @author marcupic
 *
 */
public class DAOProvider {

	/**
	 * dao varijabla
	 */
	private static DAO dao = new SQLDAO();
	
	/**
	 * Dohvat primjerka.
	 * 
	 * @return objekt koji enkapsulira pristup sloju za perzistenciju podataka.
	 */
	public static DAO getDao() {
		return dao;
	}
	
}

package hr.fer.zemris.java.hw14.dao;


import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

import java.util.List;

/**
 * Sucelje prema podsustavu za perzistenciju podataka.
 * 
 * @author teo toplak
 *
 */
public interface DAO {

	/**
	 * Vraca sva glasanja iz baze podataka
	 * @return listu glasackih anketa
	 * @throws DAOException kada DAO javi iznimku
	 */
	public List<Poll> getPolls() throws DAOException;
	/**
	 * Vraca listu glasackih opcija iz zadane ankete
	 * @param id id ankete
	 * @return listu glasackih opcija ankete
	 * @throws DAOException kada DAO javi iznimku
	 */
	public List<PollOption> getPollOptions(long id) throws DAOException;
	/**
	 * Poveca broj glasova zadane opcije u anketi  za jedan
	 * @param pollID id ankete
	 * @param id id opcije ankete
	 * @throws DAOException kada DAO javi iznimku
	 */
	public void updatePollOption(long pollID, long id) throws DAOException;
	
}

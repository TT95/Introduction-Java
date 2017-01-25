package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija ocekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, sto znaci da bi netko
 * prije no sto izvodenje dode do ove tocke to trebao tamo postaviti.
 * U web-aplikacijama tipicno rjesenje je konfigurirati jedan filter 
 * koji ce presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> unosi = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						Poll unos = new Poll();
						unos.setId(rs.getLong(1));
						unos.setTitle(rs.getString(2));
						unos.setMessage(rs.getString(3));
						unosi.add(unos);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new DAOException("Pogreska prilikom dohvata liste korisnika.", ex);
		}
		return unosi;
	}
	
	@Override
	public List<PollOption> getPollOptions(long id) throws DAOException {
		List<PollOption> unosi = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount"
					+ " from PollOptions where pollID="+id+" order by votesCount desc");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollOption unos = new PollOption();
						unos.setId(rs.getLong(1));
						unos.setOptionTitle(rs.getString(2));
						unos.setOptionLink(rs.getString(3));
						unos.setPollID(rs.getLong(4));
						unos.setVotesCount(rs.getString(5));
						unosi.add(unos);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new DAOException("Pogreska prilikom dohvata liste korisnika.", ex);
		}
		return unosi;
	}

	@Override
	public void updatePollOption(long pollID, long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("update PollOptions set votesCount=votesCount+1"
					+ " where pollID="+pollID+" and id="+id);
			try {
				pst.executeUpdate();
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new DAOException("Pogreska prilikom dohvata liste korisnika.", ex);
		}
	}

}

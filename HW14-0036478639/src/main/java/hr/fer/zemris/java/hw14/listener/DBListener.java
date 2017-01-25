package hr.fer.zemris.java.hw14.listener;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Listener which makes sure that default values are
 * set in database. If they are already present does nothing.
 * 
 * @author Teo Toplak
 *
 */
@WebListener
public class DBListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties prop = new Properties();
		try {
			prop.load(Files.newInputStream(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"))));
		} catch (IOException e) { }
		String host = prop.getProperty("host");
		String port = prop.getProperty("port");
		String name = prop.getProperty("name");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");

		String connectionURL = "jdbc:derby://"+host+":"+port+"/" + name + ";user="+user+";password="+password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogre�ka prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

			
		
		//creating connection
		Connection con = null;
		try {
			con = cpds.getConnection();
			
			createDefaultTables(con);

			if(pollIsEmpty(con)) {
				String path = sce.getServletContext().getRealPath("/WEB-INF");
				insertDefaultPoll(con,path);
			}
		} catch (SQLException e) {
			try {
				throw new IOException("Baza podataka nije dostupna.", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	/**
	 * Checks if there is no polls set in database
	 * @param con connection
	 * @return true if there is no default polls set in database
	 */
	private boolean pollIsEmpty(Connection con) {
		
		PreparedStatement pst = null;
		int count=0;

		try {
			pst = con.prepareStatement("SELECT * FROM Polls");
			ResultSet rset = pst.executeQuery();

			try {
				while(rset.next()) {
					count++;
				}
			} finally {
				try { rset.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				ex.printStackTrace();
			}
		} 
		
		if(count==0) {
			return true;
		}
		return false;
	}


	/**
	 * Inserts default poll in database
	 * @param con connection
	 * @param path path to folder containing default values for poll
	 */
	private void insertDefaultPoll(Connection con, String path) {
		
		//creating default poll
		Properties propPoll = new Properties();
		try {
			propPoll.load(Files.newInputStream(Paths.get(path+ "/defaultPoll.properties")));
		} catch (IOException e) { }
		String title = propPoll.getProperty("title");
		String message = propPoll.getProperty("message");
		
		PreparedStatement pst = null;
		long newID=0;
		
		try {
			pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, title);
			pst.setString(2, message);
			
			pst.executeUpdate();
			
			ResultSet rset = pst.getGeneratedKeys();
			
			try {
				if(rset != null && rset.next()) {
					newID = rset.getLong(1);
				}
			} finally {
				try { rset.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		
		//creating default options in default poll
		String fileName = path + "/defaultOptions.txt";
		List<String> list=null;
		try {
			list = Files.readAllLines(Paths.get(fileName),
			        StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Problem reading txt file containing default options for poll");
			e.printStackTrace();
		}
		for(String s : list) {
			String[] arr = s.split("\t");
			try {
				pst = con.prepareStatement("INSERT INTO PollOptions "
						+ "(OptionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				System.out.println("--------------");
				System.out.println("s");
				pst.setString(1, arr[0]);
				pst.setString(2, arr[1]);
				pst.setString(3, String.valueOf(newID));
				pst.setString(4, arr[2]);
				pst.executeUpdate();

			} catch(SQLException ex) {
				ex.printStackTrace();
			} finally {
				try { pst.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * Creates default tables if they dont already exist 
	 * in database.
	 * @param con connection
	 */
	private void createDefaultTables(Connection con) {

		Set<String> set=null;
		try {
			set = getDBTables(con);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		if(!set.contains("polls")) {
			createTablePolls(con);
		}
		if(!set.contains("polloptions")) {
			createTablePollOptions(con);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates table for polls with sql statement
	 * @param con connection
	 */
	private void createTablePolls(Connection con) {

		try {
			Statement sta = con.createStatement();
			sta.executeUpdate("CREATE TABLE Polls"
					+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ "title VARCHAR(150) NOT NULL,"
					+ "message CLOB(2048) NOT NULL)");
			sta.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}

	/**
	 * Creates table for poll options with sql statement
	 * @param con connection
	 */
	private void createTablePollOptions(Connection con) {
		try {
			Statement sta = con.createStatement();
			sta.executeUpdate("CREATE TABLE PollOptions"
					+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY"
					+ ",optionTitle VARCHAR(100) NOT NULL,"
					+ "optionLink VARCHAR(150) NOT NULL,"
					+ "pollID BIGINT,"
					+ "votesCount BIGINT,"
					+ "FOREIGN KEY (pollID) REFERENCES Polls(id))");
			sta.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Returns set of tables in database
	 * @param targetDBConn connection to database
	 * @return set of tables in database
	 * @throws SQLException sql exception
	 */
	private Set<String> getDBTables(Connection targetDBConn) throws SQLException
	{
		Set<String> set = new HashSet<String>();
		DatabaseMetaData dbmeta = targetDBConn.getMetaData();
		readDBTable(set, dbmeta, "TABLE", null);
		readDBTable(set, dbmeta, "VIEW", null);
		return set;
	}

	/**
	 * Reads database for existing tables
	 * @param set set for tables
	 * @param dbmeta database metadata
	 * @param searchCriteria search criteria
	 * @param schema schema
	 * @throws SQLException sql exception
	 */
	private void readDBTable(Set<String> set, DatabaseMetaData dbmeta, String searchCriteria, String schema)
			throws SQLException
	{
		ResultSet rs = dbmeta.getTables(null, schema, null, new String[]
				{ searchCriteria });
		while (rs.next())
		{
			set.add(rs.getString("TABLE_NAME").toLowerCase());
		}
	}

}

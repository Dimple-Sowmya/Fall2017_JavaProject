/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.iam.services.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeleteException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.IdentityUpdateException;
import fr.epita.iam.services.configuration.ConfigurationService;
import fr.epita.logger.Logger;

/**
 * <h3>Description</h3>
 * <p>
 * This class allows to ...
 * </p>
 *
 * <h3>Usage</h3>
 * <p>
 * This class should be used as follows:
 *
 * <pre>
 * <code>${type_name} instance = new ${type_name}();</code>
 * </pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 *         ${tags}
 */
public class IdentityJDBCDAO implements IdentityDAO {


	private static final Logger LOGGER = new Logger(IdentityJDBCDAO.class);
	/**
	 *
	 */
	private static final String DB_HOST = "db.host";
	private static final String DB_PWD = "db.pwd";
	private static final String DB_USER = "db.user";

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.dao.IdentityDAO#create(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void create(Identity identity) throws IdentityCreationException {

		LOGGER.info("creating that identity" + identity);
		Connection connection = null;
		try {
			connection = getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement("INSERT INTO IDENTITIES(UID, EMAIL, DISPLAY_NAME) VALUES (?, ?, ?)");
			pstmt.setString(1, identity.getUid());
			pstmt.setString(2, identity.getEmail());
			pstmt.setString(3, identity.getDisplayName());
			pstmt.execute();
			System.out.println("Successfully created Identity.");
		} catch (final Exception e) {
			// TODO: handle exception
			LOGGER.error("error while creating the identity " + identity + "got that error " + e.getMessage());
			throw new IdentityCreationException(e, identity);

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e) {
					// can do nothing here, except logging maybe?
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.dao.IdentityDAO#search(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public List<Identity> search(Identity criteria) throws IdentitySearchException {

		final List<Identity> results = new ArrayList<>();
		Connection connection = null;
		try {
			connection = getConnection();
			final String sqlString = "SELECT DISPLAY_NAME, EMAIL, UID FROM IDENTITIES " + "WHERE (? IS NULL OR DISPLAY_NAME LIKE ?) "
					+ "AND (? IS NULL OR EMAIL LIKE ?) " + "AND (? IS NULL OR UID = ?)";
			final PreparedStatement preparedStatement = connection.prepareStatement(
					sqlString);

			preparedStatement.setString(1, criteria.getDisplayName());
			preparedStatement.setString(2, criteria.getDisplayName() + "%");
			preparedStatement.setString(3, criteria.getEmail());
			preparedStatement.setString(4, criteria.getEmail() + "%");
			preparedStatement.setString(5, criteria.getUid());
			preparedStatement.setString(6, criteria.getUid());
			final ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				final Identity currentIdentity = new Identity();
				// How to select the right index?
				currentIdentity.setDisplayName(rs.getString("DISPLAY_NAME"));
				currentIdentity.setEmail(rs.getString("EMAIL"));
				currentIdentity.setUid(rs.getString("UID"));

				results.add(currentIdentity);
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error while performing search", e);
			throw new IdentitySearchException(e, criteria);
		} finally {
			try {
				connection.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}

		return results;
	}

	
	public List<Identity> searchAll() throws IdentitySearchException {

		final List<Identity> results = new ArrayList<>();
		Connection connection = null;
		try {
			connection = getConnection();
			final String sqlString = "SELECT DISPLAY_NAME, EMAIL, UID FROM IDENTITIES " ;
			final PreparedStatement preparedStatement = connection.prepareStatement(
					sqlString);

			/*preparedStatement.setString(1, criteria.getDisplayName());
			preparedStatement.setString(2, criteria.getDisplayName() + "%");
			preparedStatement.setString(3, criteria.getEmail());
			preparedStatement.setString(4, criteria.getEmail() + "%");
			preparedStatement.setString(5, criteria.getUid());
			preparedStatement.setString(6, criteria.getUid());*/
			final ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				final Identity currentIdentity = new Identity();
				// How to select the right index?
				currentIdentity.setDisplayName(rs.getString("DISPLAY_NAME"));
				currentIdentity.setEmail(rs.getString("EMAIL"));
				currentIdentity.setUid(rs.getString("UID"));

				results.add(currentIdentity);
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error while performing search", e);
			//throw new IdentitySearchException(e, criteria);
		} finally {
			try {
				connection.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}

		return results;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.dao.IdentityDAO#update(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void update(Identity identity) throws IdentityUpdateException {
		Connection connection = null;
		try {
			connection = getConnection();
			final String sqlString = "UPDATE IDENTITIES SET DISPLAY_NAME=?, EMAIL=? "+ "WHERE UID='"+identity.getUid()+"'";
			//System.out.println(sqlString);
			final PreparedStatement preparedStatement = connection.prepareStatement(
					sqlString);

			preparedStatement.setString(1, identity.getDisplayName());
			preparedStatement.setString(2, identity.getEmail());
			

			 preparedStatement.executeUpdate();
			//rs.close();
			System.out.println("Identity update successfully");
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error while performing update", e);
			throw new IdentityUpdateException(e, identity);
		} finally {
			try {
				connection.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.dao.IdentityDAO#delete(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void delete(Identity identity) throws IdentityDeleteException {
		Connection connection = null;
		try {
			connection = getConnection();
			final String sqlString = "DELETE FROM IDENTITIES "+ "WHERE UID='"+identity.getUid()+"'";
			//System.out.println(sqlString);
			final PreparedStatement preparedStatement = connection.prepareStatement(
					sqlString);

			//preparedStatement.setString(1, identity.getUid());

			 preparedStatement.executeUpdate();
			//rs.close();
			System.out.println("Identity delete successfully");
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error while performing delete", e);
			throw new IdentityDeleteException(e, identity);
		} finally {
			try {
				connection.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		// TODO make this variable through configuration

		final ConfigurationService confService = ConfigurationService.getInstance();

		final String url = confService.getConfigurationValue(DB_HOST);
		final String password = confService.getConfigurationValue(DB_PWD);
		final String username = confService.getConfigurationValue(DB_USER);

		Class.forName("org.apache.derby.jdbc.ClientDriver");

		final Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}

}

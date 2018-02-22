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
import fr.epita.iam.datamodel.User;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentitySearchException;
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
public class LoginJdbdcDAO implements LoginDAO {


	private static final Logger LOGGER = new Logger(LoginJdbdcDAO.class);
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
	public void create(User user) throws IdentityCreationException {

		LOGGER.info("creating that identity" + user);
		Connection connection = null;
		try {
			connection = getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement("INSERT INTO users(NAME, PASSWORD) VALUES (?, ?)");
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			
			pstmt.execute();
		} catch (final Exception e) {
			// TODO: handle exception
			LOGGER.error("error while creating the identity " + user + "got that error " + e.getMessage());
			//throw new IdentityCreationException(e, user);

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
	public List<User> search(User criteria) throws IdentitySearchException {

		final List<User> results = new ArrayList<>();
		Connection connection = null;
		try {
			//System.out.println("hello");
			connection = getConnection();
			final String sqlString = "SELECT NAME, PASSWORD FROM users " + "WHERE (NAME LIKE ?) "
					+ "AND (PASSWORD LIKE ?) ";
			final PreparedStatement preparedStatement = connection.prepareStatement(
					sqlString);
			preparedStatement.setString(1, criteria.getName());
			preparedStatement.setString(2, criteria.getPassword());
			
			/*
			preparedStatement.setString(1, criteria.getDisplayName());
			preparedStatement.setString(2, criteria.getDisplayName() + "%");
			preparedStatement.setString(3, criteria.getEmail());
			preparedStatement.setString(4, criteria.getEmail() + "%");
			preparedStatement.setString(5, criteria.getUid());
			preparedStatement.setString(6, criteria.getUid());*/
			final ResultSet rs = preparedStatement.executeQuery();
			//System.out.println(rs.next());
			while (rs.next()) {
				final User currentIdentity = new User();
				// How to select the right index?
				//System.out.println(rs.getString("NAME")+"-----"+rs.getString("PASSWORD"));
				currentIdentity.setName(rs.getString("name"));
				currentIdentity.setPassword(rs.getString("password"));
				

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
	/*@Override
	public void update(Identity identity) {

	}*/

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.dao.IdentityDAO#delete(fr.epita.iam.datamodel.Identity)
	 */
/*	@Override
	public void delete(Identity identity) {

	}*/

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

	@Override
	public void update(User identity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User identity) {
		// TODO Auto-generated method stub
		
	}

}

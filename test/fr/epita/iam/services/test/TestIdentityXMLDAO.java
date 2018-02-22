/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.iam.services.test;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.services.IdentityXMLDAO;
import fr.epita.iam.services.dao.IdentityDAO;

/**
 * <h3>Description</h3>
 * <p>This class allows to ...</p>
 *
 * <h3>Usage</h3>
 * <p>This class should be used as follows:
 *   <pre><code>${type_name} instance = new ${type_name}();</code></pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 * ${tags}
 */
public class TestIdentityXMLDAO {

	/**
	 * <h3>Description</h3>
	 * <p>
	 * This methods allows to ...
	 * </p>
	 *
	 * <h3>Usage</h3>
	 * <p>
	 * It should be used as follows :
	 *
	 * <pre>
	 * <code> ${enclosing_type} sample;
	 *
	 * //...
	 *
	 * sample.${enclosing_method}();
	 *</code>
	 * </pre>
	 * </p>
	 *
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 *         ${tags}
	 * @throws IdentityCreationException
	 * @throws IdentitySearchException
	 */
	public static void main(String[] args) throws IdentityCreationException, IdentitySearchException {
		// given
		final IdentityDAO dao = new IdentityXMLDAO();

		// when
		dao.create(new Identity("Thomas Broussard", "1234", "tbr@tbr.com"));

		// then
		final List<Identity> list = dao.search(new Identity("Thomas", null, null));

		if (list.isEmpty()) {
			System.out.println("failure");
		} else {
			System.out.println("success");
		}
		System.out.println(list);

	}

}

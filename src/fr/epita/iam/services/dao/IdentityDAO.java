/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.iam.services.dao;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeleteException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.IdentityUpdateException;

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
public interface IdentityDAO {

	public static final Integer number = 0;

	public void create(Identity identity) throws IdentityCreationException;

	public List<Identity> search(Identity criteria) throws IdentitySearchException;

	public void update(Identity identity)throws IdentityUpdateException;

	public void delete(Identity identity) throws IdentityDeleteException;

}

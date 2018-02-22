/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.iam.exceptions;

import fr.epita.iam.datamodel.Identity;

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
public class IdentityCreationException extends IdentityDataException {

	/**
	 * @param e
	 * @param identity
	 */
	public IdentityCreationException(Exception e, Identity identity) {
		super(e, identity);
	}

	@Override
	public String getMessage() {
		return "A problem occurred while creating that Identity : " + faultyIdentity;
	}

}

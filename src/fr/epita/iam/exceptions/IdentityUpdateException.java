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
public class IdentityUpdateException extends IdentityDataException {

	/**
	 * @param cause
	 * @param faultyIdentity
	 */
	public IdentityUpdateException(Exception cause, Identity faultyIdentity) {
		super(cause, faultyIdentity);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public String getMessage() {
		return "a problem occured while updating identity with that criteria : " + faultyIdentity;
	}
}

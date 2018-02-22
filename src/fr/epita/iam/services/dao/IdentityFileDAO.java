/**
 * Ce fichier est la propriété de l'agence de la Biomédecine Code application : Composant :
 */
package fr.epita.iam.services.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;

/**
 * <h3>Description</h3>
 * <p>
 * Cette classe permet de ...
 * </p>
 *
 * <h3>Utilisation</h3>
 * <p>
 * Elle s'utilise de la manière suivante :
 *
 * <pre>
 * <code>${type_name} instance = new ${type_name}();</code>
 * </pre>
 * </p>
 *
 * @since $${version}
 * @see Voir aussi $${link}
 * @author ${user}
 *
 *         ${tags}
 */
public class IdentityFileDAO implements IdentityDAO {

	private final String filePath;

	private final PrintWriter pw;
	private final Scanner scanner;

	/**
	 * @param filePath
	 * @throws IOException
	 */
	public IdentityFileDAO(String filePath) throws IOException {
		this.filePath = filePath;
		final File file = new File(this.filePath);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		final FileOutputStream fos = new FileOutputStream(file, true);
		pw = new PrintWriter(fos);
		scanner = new Scanner(file);

	}

	@Override
	public void create(Identity identity) {
		pw.println("------------------");
		pw.println(identity.getUid());
		pw.println(identity.getEmail());
		pw.println(identity.getDisplayName());
		pw.println("------------------");
		pw.flush();

	}

	@Override
	public List<Identity> search(Identity criteria) {
		final List<Identity> results = new ArrayList<>();

		while (scanner.hasNext()) {
			final Identity identity = new Identity();
			scanner.nextLine();
			identity.setUid(scanner.nextLine());
			identity.setEmail(scanner.nextLine());
			identity.setDisplayName(scanner.nextLine());
			scanner.nextLine();

			if (criteriaCheck(criteria, identity)) {
				results.add(identity);

			}

		}

		return results;

	}

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
	 */
	private boolean criteriaCheck(Identity criteria, Identity identity) {
		return identity.getDisplayName().contains(criteria.getDisplayName()) || identity.getEmail().contains(criteria.getEmail())
				|| identity.getUid().contains(criteria.getUid());
	}

	@Override
	public void update(Identity identity) {
		// TODO
	}

	@Override
	public void delete(Identity identity) {
		// TODO
	}

}

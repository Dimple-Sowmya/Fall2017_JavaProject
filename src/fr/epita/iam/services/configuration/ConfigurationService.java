/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.iam.services.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
public class ConfigurationService {

	private Properties properties;

	private static ConfigurationService instance;

	private ConfigurationService(String filePathToConfiguration) {
		filePathToConfiguration = "test/conf.properties";
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File(filePathToConfiguration)));
		} catch (final IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static ConfigurationService getInstance() {
		if (instance == null) {
			instance = new ConfigurationService(System.getProperty("conf"));
		}
		return instance;
	}

	public String getConfigurationValue(String propertyKey) {
		return properties.getProperty(propertyKey);
	}

}

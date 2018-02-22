/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.iam.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.services.configuration.ConfigurationService;
import fr.epita.iam.services.dao.IdentityDAO;
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
public class IdentityXMLDAO implements IdentityDAO {

	/**
	 *
	 */
	private static final String PROPERTY_NAME_DISPLAY_NAME_TEXT = "./property[@name='displayName']/text()";
	/**
	 *
	 */
	private static final String PROPERTY_NAME_UID_TEXT = "./property[@name='uid']/text()";
	/**
	 *
	 */
	private static final String PROPERTY_NAME_EMAIL_TEXT = "./property[@name='email']/text()";
	private static final XPathFactory xpathFactory = XPathFactory.newInstance();
	private static final XPath Xpath = xpathFactory.newXPath();
	private static final Logger LOGGER = new Logger(IdentityXMLDAO.class);

	/**
	 *
	 */
	private static final String PROPERTY = "property";
	/**
	 *
	 */
	private static final String IDENTITY = "identity";
	private Document document;

	/**
	 *
	 */
	public IdentityXMLDAO() {
		try {
			final ConfigurationService configuration = ConfigurationService.getInstance();
			final File file = new File(configuration.getConfigurationValue("xml.file.path"));
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			final DocumentBuilder documentBuilder = dbf.newDocumentBuilder();

			if (file.exists()) {

				document = documentBuilder.parse(new FileInputStream(file));

			} else {
				document = documentBuilder.newDocument();

				document.appendChild(document.createElement("identities"));
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {

			LOGGER.error("An error occured", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.dao.IdentityDAO#create(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void create(Identity identity) throws IdentityCreationException {
		final Element root = document.getDocumentElement();
		final Element identityElement = getNewIdentityElt();
		identityElement.appendChild(getNewPropertyElmt("displayName", identity.getDisplayName()));
		identityElement.appendChild(getNewPropertyElmt("uid", identity.getUid()));
		identityElement.appendChild(getNewPropertyElmt("email", identity.getEmail()));
		root.appendChild(identityElement);
		writeToFile();
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
	private Element getNewPropertyElmt(String propertyName, String propertyValue) {
		final Element identityProperty = getNewPropertyElt();
		identityProperty.setAttribute("name", propertyName);
		identityProperty.setTextContent(propertyValue);
		return identityProperty;
	}

	private Element getNewIdentityElt() {
		return document.createElement(IDENTITY);
	}

	private Attr getNewNameAttr() {
		return document.createAttribute("name");
	}

	private Element getNewPropertyElt() {
		return document.createElement(PROPERTY);
	}

	// /*
	// * (non-Javadoc)
	// * @see fr.epita.iam.services.dao.IdentityDAO#search(fr.epita.iam.datamodel.Identity)
	// */
	// @Override
	// public List<Identity> search(Identity criteria) throws IdentitySearchException {
	// final List<Identity> identities = new ArrayList<>();
	// final NodeList list = document.getElementsByTagName(IDENTITY);
	//
	// final int length = list.getLength();
	// for (int i = 0; i < length; i++) {
	// final Node identityNode = list.item(i);
	// if (identityNode instanceof Element) {
	// final Element identityElement = (Element) list.item(i);
	// final Identity identity = new Identity();
	// final NodeList propertiesList = identityElement.getElementsByTagName(PROPERTY);
	// final int propertiesLength = propertiesList.getLength();
	// for (int j = 0; j < propertiesLength; j++) {
	// final Node propertyNode = propertiesList.item(j);
	// if (propertyNode instanceof Element) {
	// final Element propertyElement = (Element) propertyNode;
	// final String attribute = propertyElement.getAttribute("name");
	// switch (attribute) {
	// case "displayName":
	// identity.setDisplayName(propertyElement.getTextContent());
	// break;
	// case "email":
	// identity.setEmail(propertyElement.getTextContent());
	// break;
	// case "uid":
	// identity.setUid(propertyElement.getTextContent());
	// break;
	//
	// }
	// }
	// }
	// identities.add(identity);
	// }
	// }
	// return identities;
	//
	// }


	private String documentToString() {
		String output = "";
		try {
			final TransformerFactory tf = TransformerFactory.newInstance();
			final Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			final StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(writer));
			output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		} catch (final Exception e) {
			LOGGER.error("got a problem while transforming document to string", e);
		}
		return output;
	}

	private void writeToFile() {
		final String xmlFilePath = ConfigurationService.getInstance().getConfigurationValue("xml.file.path");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(xmlFilePath), true);
			writer.println(documentToString());

		} catch (final FileNotFoundException e) {

			LOGGER.error("An error occured", e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.dao.IdentityDAO#update(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void update(Identity identity) {
		// TODO Complete this stub or remove that comment line

	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.dao.IdentityDAO#delete(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void delete(Identity identity) {
		// TODO Complete this stub or remove that comment line

	}

	private List<Element> evaluateXpathAsList(Node relativeParent, String xpath) {
		final List<Element> elements = new ArrayList<>();
		XPathExpression xPathExpression;
		try {
			xPathExpression = Xpath.compile(xpath);
			final NodeList results = (NodeList) xPathExpression.evaluate(relativeParent, XPathConstants.NODESET);
			final int length = results.getLength();
			for (int i = 0; i < length; i++) {
				final Node item = results.item(i);
				if (item instanceof Element) {
					elements.add((Element) item);
				}
			}
		} catch (final XPathExpressionException e) {
			// FIXME Use a logger to trace the following error
			// LOGGER.error("An error occured", ${exception_var})
		}
		return elements;
	}

	private String evaluateXpathAsString(Node relativeParent, String xpath) {
		XPathExpression xPathExpression;
		try {
			xPathExpression = Xpath.compile(xpath);
			return (String) xPathExpression.evaluate(relativeParent, XPathConstants.STRING);

		} catch (final XPathExpressionException e) {
			// FIXME Use a logger to trace the following error
			// LOGGER.error("An error occured", ${exception_var})
		}
		return "";
	}
	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.dao.IdentityDAO#search(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public List<Identity> search(Identity criteria) throws IdentitySearchException {
		final List<Identity> identities = new ArrayList<>();
		final String expression = "/identities/identity[starts-with(./property[@name='displayName']/text() , '" + criteria.getDisplayName()
		+ "')]";
		final List<Element> identitiesAsElement = evaluateXpathAsList(document, expression);
		for (final Element element : identitiesAsElement) {
			final Identity identity = new Identity();
			identity.setDisplayName(evaluateXpathAsString(element, PROPERTY_NAME_DISPLAY_NAME_TEXT));
			identity.setEmail(evaluateXpathAsString(element, PROPERTY_NAME_EMAIL_TEXT));
			identity.setUid(evaluateXpathAsString(element, PROPERTY_NAME_UID_TEXT));
			identities.add(identity);
		}



		return identities;
	}

}

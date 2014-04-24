package IO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import DB.ForeignKey;

public class XMLParser {

	public XMLParser(){

	}
		
	public Document getDocument(File xmlFile) throws IOException{
		Document xmlDocument = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDocument = dBuilder.parse(xmlFile);
			xmlDocument.getDocumentElement().normalize();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new IOException(e);
		}
		return xmlDocument;
	}
	
	public String getField(File f, String name) throws IOException{
		Document xmlFile = getDocument(f);
		return xmlFile.getElementsByTagName(name).item(0).getTextContent().trim();
	}
	public String getTableName(File f) throws IOException{
		return getField(f,"table");
	}
	public HashMap<String,ForeignKey> getForeignKeys(File f) throws IOException{
		Document xmlFile = getDocument(f);
		HashMap<String,ForeignKey> foreignKeys = new HashMap<String,ForeignKey>();
		NodeList foreignKeysNode = xmlFile.getElementsByTagName("foreign-key");
		for (int i = 0; i < foreignKeysNode.getLength(); i++) {
			Node node = foreignKeysNode.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String table = element.getAttribute("table");
				String matchColumn = element.getAttribute("match-column");
				String selectColumn = element.getAttribute("select");
				String name = element.getAttribute("name");
				
				ForeignKey key = new ForeignKey(selectColumn,table, matchColumn);
				foreignKeys.put(name,key);
			}
		}		
		return foreignKeys;
	}

	public HashMap<String, String> getMappings(File f) throws IOException {
		Document xmlFile = getDocument(f);
		HashMap<String,String> mappings = new HashMap<String,String>();
		NodeList foreignKeysNode = xmlFile.getElementsByTagName("mapping");
		for (int i = 0; i < foreignKeysNode.getLength(); i++) {
			Node node = foreignKeysNode.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String csvColumn = element.getAttribute("csvColumn");
				String dbColumn = element.getAttribute("dbColumn");
				mappings.put(csvColumn, dbColumn);
			}
		}		
		return mappings;
	}
}

package rec.planner;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
public class XMLReader {

    private static Element rootElement;
    public static String read(String key){
        try {
            if(rootElement == null)
                readFile();
            return getTextValue(rootElement, key);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getTextValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    private static void readFile() throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File("config.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);


        doc.getDocumentElement().normalize();


        rootElement = doc.getDocumentElement();
    }

}

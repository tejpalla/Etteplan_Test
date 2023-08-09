import java.io.FileWriter;
import java.io.IOException;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class Xml_parser {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("sma_gentext.xml");
            
            // Getting the root element
            Element root = doc.getDocumentElement();
            
            // Finding the specific element by attribute id
            String targetValue = findTargetValueById(root, "42007");
            
            if (targetValue == null) {
                System.out.println("Element with attribute id 42007 not found.");
            } else {
                // Calling the function to write the target value to a file
                writeToFile("output.txt", targetValue);
                System.out.println("Value written to output.txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String findTargetValueById(Element element, String id) {
        NodeList transUnits = element.getElementsByTagName("trans-unit");
        
        // Loop through the trans-unit tags to find the one with the required Id and extract the target value
        for (int i = 0; i < transUnits.getLength(); i++) {
            Element transUnit = (Element) transUnits.item(i);
            String unitId = transUnit.getAttribute("id");
            
            if (id.equals(unitId)) {
                Element targetElement = (Element) transUnit.getElementsByTagName("target").item(0);
                return targetElement.getTextContent();
            }
        }
        return null;
    }
    
    private static void writeToFile(String filename, String content) {
        // Writing the target value to the file
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
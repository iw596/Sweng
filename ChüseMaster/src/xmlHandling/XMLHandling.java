package xmlHandling;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import listDataStructure.List;
import listDataStructure.ListItem;

public abstract class XMLHandling {

	public List buildListFromXML(String filename){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		ArrayList<ListItem> list_array = new ArrayList<ListItem>();
		String list_type = null;
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// location of xml file - local to the project folder
			File file = new File(System.getProperty("user.dir") + filename + ".xml");
			
			// bulid doc from xml file in java
			Document document = builder.parse(file);
			
			// set nodes in xml file in to array by the node tag
			NodeList element_list = document.getElementsByTagName("item");
			
			// loop thought all nodes 
			for(int i = 0; i < element_list.getLength(); i++){
				// get node i from node array that came from xml file
				Node element = element_list.item(i);
				
				//System.out.println(element.getChildNodes().item(1).getTextContent());
				
				// take this node vales and place it in to the list array of the list
				//this.addItem(new Item(element.getChildNodes().item(1).getTextContent()));
				
				ListItem item = new ListItem(element.getChildNodes().item(1).getTextContent());
				
				list_array.add(item);
				
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(list_array.get(0).getClass() == ListItem.class) {
			list_type = "ListItem";
		}
		
		List list = new List(list_type, filename);
		list.addItemArray(list_array);
		
		return list;
		
	}
	
	
	//TODO fix errors relating to changed data structure input
	public void buildXMLFromList(){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Element root = document.createElement("items");
			document.appendChild(root);
			
			for(int i = 0; i < this.listSize(); i++) {
				root.appendChild(createItem(document, this.getItemFromIndex(i).getName()));
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transf = transformerFactory.newTransformer();
	        
	        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transf.setOutputProperty(OutputKeys.INDENT, "yes");
	        transf.setOutputProperty(OutputKeys.STANDALONE, "yes");
	        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        
	        //TODO remove in final - console only
	        //DOMSource source = new DOMSource(document);
	        
	        // file path to where its being stored
	        File myFile = new File("D:\\" + this.file_name);
	        
	        // print list with tags in console
	        ///StreamResult console = new StreamResult(System.out);
	        // write to file in given location
	        StreamResult file = new StreamResult(myFile);
	        
	        /*try {
				transf.transform(source, console);
			} catch (TransformerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
	        
/*	        try {
				transf.transform(source, file);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static Node createItem(Document document, String name) {
        
        Node user = document.createElement("item");
        
        user.appendChild(createItemElement(document, "name", name));
        
        return user;
    }
	
	 private static Node createItemElement(Document document, String name, 
	            String value) {

	        Node node = document.createElement(name);
	        node.appendChild(document.createTextNode(value));

	        return node;



	 }
	
}

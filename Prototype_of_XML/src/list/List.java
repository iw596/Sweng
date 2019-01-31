package list;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

// list of items that the user has made
// note: only text file (not used ID tag and other files)
// reading and writing file goes to D drive. change location at line 93 for read, 154 for write
public class List {
	
	// name of the list
	private String name;
	
	// file name of the xml file where the list is stored 
	private String file_name;
	
	// item list
	private ArrayList<Item> list;
	
	// constructor
	public List(String name, String file_name, int ID){
		this.setName(name);
		this.setFileName(file_name);
		list = new ArrayList<Item>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int listSize(){
		return this.list.size();
	}
	
	public void addItem(Item item){
		this.list.add(item);
	}
	
	public Item getItemFromIndex(int index){
		return this.list.get(index);
	}
	
	public void removeItem(int index) {
		this.list.remove(index);
	}
	public void displayList(){
		
		for(int i = 0; i < this.listSize(); i++){
			getItemFromIndex(i).displayItem();
		}
	}
	
	public String getFileName() {
		return file_name;
	}

	public void setFileName(String file_name) {
		this.file_name = file_name;
	}
	
	public void GetListFromXML(){
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// location of xml file
			File file = new File("D:\\" + this.file_name);
			
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
				this.addItem(new Item(element.getChildNodes().item(1).getTextContent()));
				
			
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
		
	}
	
	public void StoreListToXMLFile(){
		
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
	        
	        DOMSource source = new DOMSource(document);
	        
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
	        
	        try {
				transf.transform(source, file);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
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

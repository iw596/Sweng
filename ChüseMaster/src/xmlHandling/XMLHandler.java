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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import listDataStructure.AudioItem;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.ImageItem;
import listDataStructure.VideoItem;

public class XMLHandler {

	public static ChuseList buildListFromXML(String filename){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		ArrayList<BasicItem> list_array = new ArrayList<BasicItem>();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// location of xml file - local to the project folder
			File file = new File(System.getProperty("user.dir") + "\\" + filename + ".xml");
			
			// build doc from xml file in java
			Document document = builder.parse(file);
			
			// set nodes in xml file in to array by the node tag
			NodeList element_list = document.getElementsByTagName("multimedia");
			
			// loop thought all nodes 
			for(int i = 0; i < element_list.getLength(); i++){
				// get node i from node array that came from xml file
				Node element = element_list.item(i);
				
				//System.out.println(element.getChildNodes().item(1).getTextContent());
				
				// take this node vales and place it in to the list array of the list
				//this.addItem(new Item(element.getChildNodes().item(1).getTextContent()));
				
				//item 1 is type
				//item 3 is title
				//item 5 is location
				
				BasicItem item = null;
				
				if(element.getChildNodes().item(1).getTextContent().equals("BasicItem")) {
					item = new BasicItem(element.getChildNodes().item(3).getTextContent());
				} else if(element.getChildNodes().item(1).getTextContent().equals("ImageItem")) {
					item = new ImageItem(element.getChildNodes().item(5).getTextContent());
				} else if(element.getChildNodes().item(1).getTextContent().equals("AudioItem")) {
					item = new AudioItem(element.getChildNodes().item(5).getTextContent());
				} else if(element.getChildNodes().item(1).getTextContent().equals("VideoItem")) {
					item = new VideoItem(element.getChildNodes().item(3).getTextContent(),
							element.getChildNodes().item(5).getTextContent(),
							element.getChildNodes().item(7).getTextContent(),
							element.getChildNodes().item(9).getTextContent());
				}

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
		
		ChuseList list = new ChuseList(filename);
		list.addItemArray(list_array);
		
		return list;
		
	}
	
	//TODO fix errors relating to changed data structure input
	public static void buildXMLFromList(ChuseList list, String filename, ChuseList results){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Element id = document.createElement("id");
			document.appendChild(id);
			
			document = addPage(document, id, list);
			
			if(results != null) {
				document = addPage(document, id, results);
			}
			
/*			Element page = document.createElement("page");
			id.appendChild(page);
			
			for(int i = 0; i < list.getSize(); i++) {
				page.appendChild(createItem(document, list.get(i)));
			}*/
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transf = transformerFactory.newTransformer();
	        
	        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transf.setOutputProperty(OutputKeys.INDENT, "yes");
	        transf.setOutputProperty(OutputKeys.STANDALONE, "yes");
	        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        
	        DOMSource source = new DOMSource(document);
	        
	        // file path to where its being stored
	        File myFile = new File(System.getProperty("user.dir") + "\\" +  filename + ".xml");
	        
	        // print list with tags in console
	        ///StreamResult console = new StreamResult(System.out);
	        // write to file in given location
	        StreamResult file = new StreamResult(myFile);
	        
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
	
	
	public static void buildXMLFromList(ChuseList list, String filename) {
		buildXMLFromList(list, filename, null);
	}
	
	private static Document addPage(Document document, Element id, ChuseList list) {
		
		Element page = document.createElement("page");
		id.appendChild(page);
		
		for(int i = 0; i < list.getSize(); i++) {
			page.appendChild(createItem(document, list.get(i)));
		}
		
		return document;
		
	}
	
	public static void addResultsToXML(ChuseList results, String filename) {
		
		ChuseList page_1 = buildListFromXML(filename);
		
		XMLHandler.buildXMLFromList(page_1, filename, results);
		
	}
	
	private static Node createItem(Document document, BasicItem item) {
        
        Node multimedia = document.createElement("multimedia");
        
        multimedia.appendChild(createItemElement(document, "type", item.getType()));
        multimedia.appendChild(createItemElement(document, "title", item.getTitle()));
        
        if(item.getType() == "BasicItem") {
        	multimedia.appendChild(createItemElement(document, "path", "n/a"));
        } else if(item.getType() == "ImageItem") {
        	multimedia.appendChild(createItemElement(document, "path", item.getPath()));
        } else if(item.getType() == "AudioItem") {
        	multimedia.appendChild(createItemElement(document, "path", item.getPath()));
        } else if(item.getType() == "VideoItem") {
        	multimedia.appendChild(createItemElement(document, "path", item.getPath()));
        	multimedia.appendChild(createItemElement(document, "description", item.getMetadata().get(0)));
        	multimedia.appendChild(createItemElement(document, "channel", item.getMetadata().get(1)));
        }
        
        return multimedia;
    }
	
	 private static Node createItemElement(Document document, String name, 
	            String value) {

	        Node node = document.createElement(name);
	        node.appendChild(document.createTextNode(value));

	        return node;
	 }
	
	public static void main(String[] args) {
		
		ChuseList test_list = new ChuseList("animals");
		
		test_list.addItem(new BasicItem("Banana"));
		test_list.addItem(new BasicItem("Cake"));
		test_list.addItem(new BasicItem("Pancake"));
		test_list.addItem(new BasicItem("Tide Pods"));
		
		ChuseList result_list = new ChuseList("animal_results");
		
		result_list.addItem(new BasicItem("Tide Pods"));
		
		XMLHandler.buildXMLFromList(test_list, "new one 2");
		
		test_list = XMLHandler.buildListFromXML("new one 2");
		//test_list.printList();
		
		//XMLHandler.addResultsToXML(result_list, "new one 2");
		//test_list.printList();

		System.out.println("Writing File:");

	}

}

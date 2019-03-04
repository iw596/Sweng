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

/**
 * Class to handle the creation, reading and editing of list XML files.
 * 
 * Date created: 31/01/2019
 * Date last edited: 28/02/2019
 * Last edited by: Dan Jackson
 * 
 * @author Jack Small
 *
 */
public class XMLHandler {
	
	/**
	 *  buildListFromXML (String)
	 *  
	 *  Builds the list from the XML file with the name "String" in the local directory.
	 *  Returns a list of items in the form of a ChuseList class.
	 *  
	 * @param filename - the filename/filepath of the XML file
	 * @return list - the list created from the XML reader
	 */
	public static ChuseList buildListFromXML(String filename){
		
		// this line is need for XML handling (don't know why :) )
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		// List of items that will be used to store the content from the XML file 
		ArrayList<BasicItem> list_array = new ArrayList<BasicItem>();
		
		try {
			// this line can cause an error there for a try is need
			// this line is need for XML handling (don't know why :) )
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// location of XML file - local to the project folder
			File file = new File(System.getProperty("user.dir") + "\\" + filename + ".xml");
			
			// build doc from XML file into java
			// this line can cause an error there for a try is need
			// this line is need for XML handling (don't know why :) )
			Document document = builder.parse(file);
			
			NodeList id_check = document.getElementsByTagName("id");

			if(id_check.getLength() != 1) {
				return null;
			}
			
			for(int j=1; j < id_check.item(0).getChildNodes().getLength(); j += 2) {
				
				//System.out.println("j value:" + j);
				
				if(!id_check.item(0).getChildNodes().item(j).getNodeName().equals("page")){
					continue;
				}

				// set nodes in XML file in to array by the node tag name
				//NodeList element_list = document.getElementsByTagName("multimedia");

				NodeList element_list = id_check.item(0).getChildNodes().item(j).getChildNodes();
				
				// loop through all nodes in the node list that was copied from the XML file
				for(int i = 0; i < element_list.getLength(); i++){
					// get node i from node array that came from XML file
					Node element = element_list.item(i);
					
					if(!element.getNodeName().equals("multimedia")) {
						continue;
					}
					
					//item 1 is type
					//item 3 is title
					//item 5 is location
					
					// Temp variable for getting the item from the node 
					BasicItem item = null;
					
					// Checks if node is of class BasicItem, if not it cycles through all available 
					// class types until the right one is found. At that point it builds the item of that class.
					if(element.getChildNodes().item(1).getTextContent().equals("BasicItem")) {
						item = new BasicItem(element.getChildNodes().item(3).getTextContent());
					} else if(element.getChildNodes().item(1).getTextContent().equals("ImageItem")) {
						item = new ImageItem(element.getChildNodes().item(5).getTextContent());
					} else if(element.getChildNodes().item(1).getTextContent().equals("AudioItem")) {
						item = new AudioItem(element.getChildNodes().item(5).getTextContent());
					} else if(element.getChildNodes().item(1).getTextContent().equals("VideoItem")) {
						item = new VideoItem(element.getChildNodes().item(3).getTextContent(),
								// The XML goes through all available information of node.
								// For some reason XML counts up in odd numbers :( 
								element.getChildNodes().item(5).getTextContent(),
								element.getChildNodes().item(7).getTextContent(),
								element.getChildNodes().item(9).getTextContent());
					} else {
						continue;
					}
					
					// dumps the newly made item into the list array
					list_array.add(item);
				}
		
			}
			
		} catch (ParserConfigurationException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Creates a new "ChuseList" and sets it equal to null
		ChuseList list = null;
		
		if(list_array.size() > 0){
			//Stores array of items in a "ChuseList" with the file name of the XML file
			list = new ChuseList(filename);
			list.addItemArray(list_array);
		}

		// return the list made 
		return list;
		
	}
	
	/**
	 * buildXMLFromList(ChuseList, String, ChuseList)
	 *  
	 * build XML file from list and name it the "filename" which is passed to it  
	 * note : it will be stored in the local directory 
	 * 
	 * use : for storing the list in a ChuseList class to be accessed by "buildListFromXML" 
	 * 
	 * @param list - the list to build the XML file from
	 * @param filename - the filename to write the XML file to
	 * @param results - the results to add to the XML file
	 */
	public static void buildXMLFromList(ChuseList list, ChuseList results){
		
		// this line is need for XML handling (don't know why :) )
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			
			// this line is need for XML handling (don't know why :) )
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			// add node called "id" to the XML file
			Element id = document.createElement("id");
			document.appendChild(id);
			
			// add the page tag with all "multimedia" tags 
			// where each one hold the information of an item in the list that is being stored 
			document = addPage(document, id, list);
			
			// working on it 
			// check if the list has statistics from previous algorithm runs. 
			if(results != null) {
				// not today
				document = addPage(document, id, results);
			}
			
			// this line is need for XML handling (don't know why :) )
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transf = transformerFactory.newTransformer();
	        
	        // top tags for the XML formating  
	        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transf.setOutputProperty(OutputKeys.INDENT, "yes");
	        transf.setOutputProperty(OutputKeys.STANDALONE, "yes");
	        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        
	        // builds the XML with the document made
	        DOMSource source = new DOMSource(document);
	        
	        // file path to where its being stored
	        File myFile = new File(System.getProperty("user.dir") + "\\" +  list.getName() + ".xml");
	        
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
	
	/**
	 * Method to build an XML file from a list, but not add the results of the list to
	 * the XML file.
	 * 
	 * @param list - the list to build the XML file from
	 * @param filename - the filename to write the XML file to
	 */
	public static void buildXMLFromList(ChuseList list) {
		buildXMLFromList(list, null);
	}
	
	
	/**
	 * addPage(Document, Element, ChuseList)
	 * 
	 * Ads the page tag and creates items with the right class type via the create item method.
	 * note : called within "buildXMLFromList" method
	 * 
	 * use : used for XML handling of method "buildXMLFromList"
	 * 
	 * @param document - the XML document to edit
	 * @param id - the id XML tag element
	 * @param list - the list to add to the page
	 * @return document - the edited document
	 */
	private static Document addPage(Document document, Element id, ChuseList list) {
		
		// add page tag
		Element page = document.createElement("page");
		id.appendChild(page);
		
		// loop through all list items and add them to the XML file under the page tag
		for(int i = 0; i < list.getSize(); i++) {
			page.appendChild(createItem(document, list.get(i)));
		}
		
		// return the document which is being stored
		return document;
	}
	
	/**
	 * Work in progress.
	 * TODO update later in development for statistics
	 * 
	 * @param results
	 * @param filename
	 */
	public static void addResultsToXML(ChuseList results, String filename) {
		
		ChuseList page_1 = buildListFromXML(filename);
		XMLHandler.buildXMLFromList(page_1, results);
		
	}
	
	/**
	 * createItem(Document, BasicItem)
	 * 
	 * Creates item with the right class type and ads its information to the document.
	 * note : this is called within the "addPage" method.
	 * 
	 * use : used for XML handling of method "addPage"
	 * 
	 * @param document - the document to edit
	 * @param item - the item to add to the document
	 * @return multimedia - the multimedia XML node
	 */
	private static Node createItem(Document document, BasicItem item) {
        
		// add multimedia tag
        Node multimedia = document.createElement("multimedia");
        
        // add type tag under multimedia tag
        multimedia.appendChild(createItemElement(document, "type", item.getType()));
        // add title tag under multimedia tag
        multimedia.appendChild(createItemElement(document, "title", item.getTitle()));
        
        // Checks the class type of the item
        if(item.getType() == "BasicItem") {
        	// If class is basic item then it receives no path.
        	multimedia.appendChild(createItemElement(document, "path", "n/a"));
        } else if(item.getType() == "ImageItem") {
        	// If item type is ImageItem, it gets the stored path and adds it to document.
        	multimedia.appendChild(createItemElement(document, "path", item.getPath()));
        } else if(item.getType() == "AudioItem") {
        	//If item type is AudioItem it gets the stored path and adds it to the document.
        	multimedia.appendChild(createItemElement(document, "path", item.getPath()));
        } else if(item.getType() == "VideoItem") {
        	// If item is of type VideoItem it will get the path, the meta data and channel and add it to
        	// the document.
        	multimedia.appendChild(createItemElement(document, "path", item.getPath()));
        	multimedia.appendChild(createItemElement(document, "description", item.getMetadata().get(0)));
        	multimedia.appendChild(createItemElement(document, "channel", item.getMetadata().get(1)));
        }
        
        // Returns the new multimedia tag.
        return multimedia;
    }
	
	/**
	 * createItemElement(Document, String, String)
	 * 
	 * Adds name and value to a node(XML).
	 * note : this is called within the "createItem" method.
	 * 
	 * @param document - the document to edit
	 * @param name - the name of the node
	 * @param value - the value of the node (String)
	 * @return node - the new node
	 */
	 private static Node createItemElement(Document document, String name, 
	            String value) {
		 	
		 	// make new node with name "name"
	        Node node = document.createElement(name);
	        // add value "value" to this node with name "name" 
	        node.appendChild(document.createTextNode(value));

	        // return the node
	        return node;
	 }

}

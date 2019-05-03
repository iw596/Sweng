package xmlHandling;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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
import listDataStructure.RankingItem;
import listDataStructure.RankingList;
import listDataStructure.VideoItem;

/**
 * Class to handle the creation, reading and editing of list XML files.
 * 
 * Date created: 31/01/2019
 * Date last edited: 15/03/2019
 * Last edited by: Dan Jackson
 * 
 * Date last edited: 19/04/2019
 * Last edited by: Jack Small
 * 
 * @author Jack Small
 *
 */
public abstract class XMLHandler {
	
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
		
		NodeList id_check = null;
		
		try {
			// this line can cause an error there for a try is need
			// this line is need for XML handling (don't know why :) )
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// location of XML file - local to the project folder
			File file = new File(filename);
			
			// build doc from XML file into java
			// this line can cause an error there for a try is need
			// this line is need for XML handling (don't know why :) )
			Document document = builder.parse(file);
			
			id_check = document.getElementsByTagName("id");

			if(id_check.getLength() != 1) {
				return null;
			}
			
			if(id_check.item(0).hasAttributes()) {
				id_check.item(0).getAttributes().getNamedItem("User").getNodeValue();
			}
			
			for(int j=1; j < id_check.item(0).getChildNodes().getLength(); j += 2) {
				
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
					} else if(element.getChildNodes().item(1).getTextContent().equals("YouTubeItem")) {
						item = new VideoItem(element.getChildNodes().item(3).getTextContent(),
								// The XML goes through all available information of node.
								// For some reason XML counts up in odd numbers :( 
								element.getChildNodes().item(5).getTextContent(),
								element.getChildNodes().item(7).getTextContent(),
								element.getChildNodes().item(9).getTextContent());
					} else if(element.getChildNodes().item(1).getTextContent().equals("VideoItem")) {
						item = new VideoItem(element.getChildNodes().item(5).getTextContent());
					} else {
						continue;
					}
					
					// dumps the newly made item into the list array
					list_array.add(item);
				}
		
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Creates a new "ChuseList" and sets it equal to null
		ChuseList list = null;
		
		
		
		if(list_array.size() > 0){
			//Stores array of items in a "ChuseList" with the file name of the XML file
			list = new ChuseList();
			list.addItemArray(list_array);
			// set the author
			if(id_check.item(0).hasAttributes()) {
				list.setAuthor(id_check.item(0).getAttributes().getNamedItem("User").getNodeValue());
			}
		}

		// return the list made 
		return list;
		
	}
	
	/**
	* buildXMLFromList(ChuseList, String, RankingList)
	*  
	* build XML file from list and name it the "filename" which is passed to it
	* also will still results if the user has some to append
	* 
	* 
	* note : it will be stored in the local directory 
	* note : should only be used when saving new list for the first time
	* 
	* use : for storing the list in a ChuseList class to be accessed by "buildListFromXML" 
	* 
	* @param list - the list to build the XML file from
	* @param filename - the filename to write the XML file to
	* @param results - the results to add to the XML file
	* 
	* Date last edited: 17/04/2019
	* Last edited by: Jack Small
	* 
	*/
	public static void buildXMLFromList(ChuseList list, String file_path, RankingList results, String user){
			
			// this line is need for XML handling (don't know why :) )
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			try {
				
				// this line is need for XML handling (don't know why :) )
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.newDocument();
				
				// add node called "id" to the XML file
				Element id = document.createElement("id");
				
				id.setAttribute("User", user);
				
				document.appendChild(id);
				
				// add the page tag with all "multimedia" tags 
				// where each one hold the information of an item in the list that is being stored 
				document = addPage(document, id, list);
				
				// store results if there are some
				if(results != null) {
					document = addResults(document, id, results);
					
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
		        File myFile = new File(file_path);
		        
		        // print list with tags in console
		        ///StreamResult console = new StreamResult(System.out);
		        
		        // write to file in given location
		        StreamResult file = new StreamResult(myFile);
		        
		        try {
					transf.transform(source, file);
				} catch (TransformerException e) {
					e.printStackTrace();
				}

				
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
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
	public static void buildXMLFromList(ChuseList list, String file_path) {
		buildXMLFromList(list, file_path, null, null);
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
	 * Method to add a single set of local results to a locally stored list XML file.
	 * 
	 * @param document	the XML file document
	 * @param id		the results element
	 * @param list		the results list
	 * @return
	 */
	private static Document addResults(Document document, Element id, RankingList list) {
					
		// add results tag
		Element results = document.createElement("results");
		id.appendChild(results);
		
		// get date and time relative to UTC for time stamp
		// could use google server import for data and time?
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
					
		// add time stamp to attributes
		results.setAttribute("Time", df.format(date));
							
		// loop through all Ranked items and add them to the XML file under the page tag
		for(int i = 0; i < list.getSize(); i++) {
			// add the item 
			Node multimediaForResults = createItem(document, list.get(i).getWrappedItem());
			// append the ranking to the node
			multimediaForResults.appendChild(createItemElement(document, "points", Integer.toString(list.get(i).getRanking())));
			// append to the results tag
			results.appendChild(multimediaForResults);
		}
					
		// return the document which is being stored
		return document;
	}
	
	/**
	 * Method to append results to an XML file with n sets of results already in the file.
	 * 
	 * @param file_path	the path to the file
	 * @param results	the results to add
	 */
	public static void appendResults(String file_path, RankingList results){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
	    	
	    	Document document = builder.parse(file_path);
	    	
	    	NodeList id = document.getElementsByTagName("id");
	    	
	    	document = addResults(document, (Element)id.item(0), results);
	    	
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
	        File myFile = new File(file_path);
	        
	        // print list with tags in console
	        ///StreamResult console = new StreamResult(System.out);
	        
	        // write to file in given location
	        StreamResult file = new StreamResult(myFile);
	        
	        transf.transform(source, file);
	    	
		} catch (ParserConfigurationException | SAXException | IOException | TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
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

	/**
	 * Method to build a set of results from an list of nodes from a read XML file.
	 * 
	 * @param element_list	the list of elements in the XML file
	 * @return list			the set of results
	 */
	public static RankingList buildResultsFromXML(NodeList element_list){
		
		
		// List of items that will be used to store the content from the XML file 
		RankingList list = new RankingList();

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
			RankingItem item_2 = null; 
							
			// Checks if node is of class BasicItem, if not it cycles through all available 
			// class types until the right one is found. At that point it builds the item of that class.
			if(element.getChildNodes().item(1).getTextContent().equals("BasicItem")) {
				item = new BasicItem(element.getChildNodes().item(3).getTextContent());
				item_2 = new RankingItem(item, Integer.parseInt(element.getChildNodes().item(7).getTextContent()));
			} else if(element.getChildNodes().item(1).getTextContent().equals("ImageItem")) {
				item = new ImageItem(element.getChildNodes().item(5).getTextContent());
				item_2 = new RankingItem(item, Integer.parseInt(element.getChildNodes().item(7).getTextContent()));
			} else if(element.getChildNodes().item(1).getTextContent().equals("AudioItem")) {
				/*item = new AudioItem(element.getChildNodes().item(5).getTextContent());
				item_2 = new RankingItem(item, Integer.parseInt(element.getChildNodes().item(7).getTextContent()));*/
			} else if(element.getChildNodes().item(1).getTextContent().equals("VideoItem")) {
				item = new VideoItem(element.getChildNodes().item(3).getTextContent(),
						// The XML goes through all available information of node.
						// For some reason XML counts up in odd numbers :( 
						element.getChildNodes().item(5).getTextContent(),
						element.getChildNodes().item(7).getTextContent(),
						element.getChildNodes().item(9).getTextContent());
				item_2 = new RankingItem(item, Integer.parseInt(element.getChildNodes().item(11).getTextContent()));
			} else {
				continue;
			}
							
			// dumps the newly made item into the list array
			list.addItem(item_2);
						
		}
		
		return list;
		
	}
	
	/**
	 * Method to build a results list from an XML file.
	 * 
	 * @param filename	the path to the XML file
	 * @return
	 */
	public static ArrayList<RankingList> buildResultsListFromXML(String filename){
		
		// this line is need for XML handling (don't know why :) )
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		ArrayList<RankingList> results = new ArrayList<RankingList>();
		
			try {
			// this line can cause an error there for a try is need
			// this line is need for XML handling (don't know why :) )
			
			
				DocumentBuilder builder = factory.newDocumentBuilder();
				
				// location of XML file - local to the project folder
				File file = new File(filename);
				
				// build doc from XML file into java
				// this line can cause an error there for a try is need
				// this line is need for XML handling (don't know why :) )
				Document document = builder.parse(file);
				
				NodeList id_check = document.getElementsByTagName("id");

				if(id_check.getLength() != 1) {
					return null;
				}
				
				for(int j=1; j < id_check.item(0).getChildNodes().getLength(); j += 2) {
					
					if(!id_check.item(0).getChildNodes().item(j).getNodeName().equals("results")){
						continue;
					}

					NodeList element_list = id_check.item(0).getChildNodes().item(j).getChildNodes();
					
					results.add(buildResultsFromXML(element_list));
					
					results.get(results.size()-1).setTime(id_check.item(0).getChildNodes().item(j).getAttributes().getNamedItem("Time").getNodeValue());
				}
				
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
			
			return results;
	}	
	
	/**
	 * Method to build an XML file from a list with a set of results added.
	 * 
	 * @param file_path	the path to the local XML file
	 * @param results	the results lists
	 */
	public static void buildXMLFromListWithResults(String file_path, RankingList results) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document document = builder.newDocument();
			
			// add node called "id" to the XML file
			Element id = document.createElement("id");
			
			document.appendChild(id);
			
			//document = addPage(document, id, null);
			
			if(results != null) {
				document = addResults(document, id, results);	
			}
			
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
	        File myFile = new File(file_path);
	        
	        // print list with tags in console
	        ///StreamResult console = new StreamResult(System.out);
	        
	        // write to file in given location
	        StreamResult file = new StreamResult(myFile);
	        
	        try {
				transf.transform(source, file);
			} catch (TransformerException e) {
				e.printStackTrace();
			}

			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		
	}
}

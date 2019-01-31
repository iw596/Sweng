package list;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AdvanceList extends List{
	
	// ID tag for the list
	// this is used to see what type of list, as each type of list is stored differently
	private int ID;
	
	public AdvanceList(String name, String file_name, int ID) {
		super(name, file_name, ID);
		this.setID(ID); // used to see waht type of list it is, 
		// (100-199)txt (200-299)img (300-399)vid 
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	@Override
	public void GetListFromXML(){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			// location of files
			File folder = new File("D:\\SWengProjects\\" + this.ID);
			// location of xml file
			File file = new File(folder + "\\" + this.getFileName());
			
			// bulid doc from xml file in java
			Document document = builder.parse(file);
			// set nodes in xml file in to array by the node tag
			NodeList element_list = document.getElementsByTagName("item");
			
			// loop thought all nodes 
			for(int i = 0; i < element_list.getLength(); i++){
				// get node i from node array that came from xml file
				Node element = element_list.item(i);
				
				//System.out.println(element.getChildNodes().item(1).getTextContent());
				if(this.ID >= 100 && this.ID < 200) {
					// take this node vales and place it in to the list array of the list
					this.addItem(new Item(element.getChildNodes().item(1).getTextContent()));
				}else if(this.ID >= 200 && this.ID < 300) {

					this.addItem(new ItemImage(element.getChildNodes().item(1).getTextContent(), 
							element.getChildNodes().item(2).getTextContent()));
				}
			
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
	}
	
}

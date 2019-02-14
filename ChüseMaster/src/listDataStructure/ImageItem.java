package listDataStructure;

/**
 * ImageItem is class within the listDataStructurePackage. This class holds
 * an image item within the list. This class extends the generic ListItem 
 * superclass. This class also implements the ListInterface interface.
 * 
 * Date created: 30/01/2019
 * Date last edited: 31/01/2019
 * Last edited by: Isaac Watson & Dan Jackson
 * 
 * @author Isaac Watson & Dan Jackson
 *
 */
public class ImageItem extends BasicItem implements ListInterface {
	
	private String file_location;
	
	/**
	 * Constructor function for the image item class. Sets the items
	 * name and opens an image file with the given name.
	 * 
	 * @param image_url - the location reference of the image's storage location
	 */
	public ImageItem(String file_location) {
		super(file_location.split(".")[0]);
		this.file_location = file_location;
		this.type = "ImageItem";
	}
	
	public String getPath() {
		return this.file_location;
	}

}

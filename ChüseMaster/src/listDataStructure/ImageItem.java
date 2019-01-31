package listDataStructure;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
public class ImageItem extends ListItem implements ListInterface {

	private Image image;
	
	/**
	 * Constructor function for the image item class. Sets the items
	 * name and opens an image file with the given name.
	 * 
	 * @param image_url - the location reference of the image's storage location
	 */
	public ImageItem(String image_url) {
		super(image_url);
		
		//tries to open an image file and save it within the item
		try {
			this.image = ImageIO.read(new File(this.getName()));
		//if the image is not found, prints the stack trace and exits the
		//program
		} catch(IOException ioe) {
			ioe.printStackTrace();
			System.exit(0);
		}

	}

}

package listDataStructure;

import java.awt.Image;

/**
 * ListInterface is an interface for items within the data structure.
 * 
 * Date created: 30/01/2019
 * Date last edited: 31/01/2019
 * Last edited by: Isaac Watson & Dan Jackson
 * 
 * @author Isaac Watson & Dan Jackson
 *
 */
public interface ListInterface {
	
	/**
	 * Implementable function getImage to return the image of
	 * an item. Implements a default return of null.
	 * 
	 * @return null
	 */
	public default Image getImage() {
		return null;
	};
	
	/**
	 * Implementable function getURL to return the url of
	 * an aspect to an item. Implements a default return of null.
	 * 
	 * @return null
	 */
	public default String getURL() {
		return null;
	};

}

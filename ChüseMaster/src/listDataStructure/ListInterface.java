package listDataStructure;

import java.util.ArrayList;

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
	
	
	public default String getTitle() {
		return null;
	}
	
	/**
	 * Implementable function getURL to return the url of
	 * an aspect to an item. Implements a default return of null.
	 * 
	 * @return null
	 */
	public default String getPath() {
		return null;
	};
	
	public default String getType() {
		return null;
	}
	
	public default ArrayList<String> getMetadata() {
		return null;
	}
	
	public default void print() {
		System.out.println("No print implementation present.");
	}
	
	public default ArrayList<String> getObjectValue() {
		return null;
	}

}

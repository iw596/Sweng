package listDataStructure;

import java.util.ArrayList;

/**
 * ListInterface is an interface for items within the data structure.
 * 
 * Date created: 30/01/2019
 * Date last edited: 28/02/2019
 * Last edited by: Dan Jackson
 * 
 * @author Isaac Watson and Dan Jackson
 *
 */
public interface ListInterface {
	
	/**
	 * Prototype function to return the title of an item.
	 * @return null - default value used for error checking
	 */
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
	
	/**
	 * Prototype function to return the type of an item.
	 * @return null - default value used for error checking
	 */
	public default String getType() {
		return null;
	}
	
	/**
	 * Prototype function to return the metadata of an item.
	 * @return null - default value used for error checking
	 */
	public default ArrayList<String> getMetadata() {
		return null;
	}
	
	/**
	 * Prototype function to print an item.
	 */
	public default void print() {
		System.out.println("No print implementation present.");
	}
	
	/**
	 * Prototype function to return the actual value of an item.
	 * @return null - default value used for error checking
	 */
	public default ArrayList<String> getObjectValue() {
		return null;
	}
	
	public default void changePath(String new_path) {
		System.out.println("No change path implementation present.");
	}

}

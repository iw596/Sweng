package xmlHandling;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Abstract class used for locating files stored on the users local system.
 * 
 * Date created: 15/03/2019
 * Date last edited: 15/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public abstract class FileLocator {

	/**
	 * Method to find all xml files stored within the saves folder of the project.
	 * @return
	 */
	public static File[] locateRootFiles() {
		
    	File folder = new File(System.getProperty("user.dir") + "/saves");
    	File[] list_of_files;
    	
    	//open all files in directory with .xml file extension
    	list_of_files = folder.listFiles(new FilenameFilter() {
    		@Override
    		public boolean accept(File dir, String name) {
    			return name.toLowerCase().endsWith(".xml");
    		}
    	});
    	
    	return list_of_files;
    	
	}
	
}

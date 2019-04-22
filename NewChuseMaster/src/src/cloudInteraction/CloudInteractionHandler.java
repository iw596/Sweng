package cloudInteraction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;

import listDataStructure.ChuseList;
import xmlHandling.XMLHandler;

/**
 * Class for all of the Google Cloud Platform interaction, including cloud storage and cloud
 * database interaction.
 * 
 * Date created: 22/02/2019
 * Date last edited: 16/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class CloudInteractionHandler {
	
	private static Storage storage;
	private static Datastore datastore;
	
	private static Boolean loggedIn = false;

	private static UserAccount user = null;
	
	private static String list_access_type = "private";
	
	/**
	 * Constructor for the CloudInteractionHandler. Authorises cloud storage and database.
	 */
	public CloudInteractionHandler() {
		try {
			storage = authoriseCloudStorage(System.getProperty("user.dir") + "\\SWEng WeTech-9745fc9cfe28.json");
			datastore = authoriseCloudDatastore(System.getProperty("user.dir") + "\\SWEng WeTech-9745fc9cfe28.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor for the CloudInteractionHandler. Authorises cloud storage and database. Logs into a 
	 * user area using the email and password given.
	 * 
	 * @param email		the user account's email
	 * @param password	the user account's password
	 */
	public CloudInteractionHandler(String email, String password) {
		this();
		logIn(email, password);
	}
	
	/**
	 * MODIFIED FROM EXAMPLE CODE FROM GOOGLE
	 * Method to authorise access to the Google Cloud Storage service using the application's private key.
	 * 
	 * @param jsonPath		path to the application's private key
	 * @return
	 * @throws IOException
	 */
	private static Storage authoriseCloudStorage(String jsonPath) throws IOException {
		  // You can specify a credential file by providing a path to GoogleCredentials.
		  // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
		  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
		        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		  Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

		return storage;
	}
	
	/**
	 * MODIFIED FROM EXAMPLE CODE FROM GOOGLE
	 * Method to authorise access to the Google Cloud Datastore service using the application's private key.
	 * 
	 * @param jsonPath		path to the application's private key
	 * @return
	 * @throws IOException
	 */
	private static Datastore authoriseCloudDatastore(String jsonPath) throws IOException {
		  // You can specify a credential file by providing a path to GoogleCredentials.
		  // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
		  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
		        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		  Datastore datastore = DatastoreOptions.newBuilder().setCredentials(credentials).build().getService();

		return datastore;
	}
	
	/**
	 * Method for uploading an XML file to a specific bucket on the Google Cloud Platform.
	 * 
	 * @param bucket_name		the name of the bucket
	 * @param local_file_path	the path to the local file
	 * @param upload_file_name	the uploaded file name
	 */
	private static void uploadFileToBucket(String bucket_name, String local_file_path, String upload_file_name) {
		
		//reads all of the bytes in the local file
		byte[] file_content = readAllBytesFromFile(local_file_path);
		
		//creates a blob
		BlobId blobId = BlobId.of(bucket_name, upload_file_name);
		
		//creates the blobs info, including its type of XML
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/xml").build();
		
		//uploads the blob to the user's cloud storage area
		try {
			storage.create(blobInfo, new String(file_content).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StorageException e) {
			return;
		}
		
	}
	
	public static void uploadResults(String local_file_path, String upload_file_name) {
		uploadFileToBucket("we-tech-user-storage", local_file_path, upload_file_name);
	}
	
	/**
	 * Method to upload a media file to a specific bucket on the Google Cloud Platform. Converts the file to base 64 and then
	 * uploads it with a given file name.
	 * 
	 * @param storage - the Google Cloud Platform storage object
	 * @param bucket_name - the name of the bucket to upload the file to
	 * @param local_file_path - the path of the file on the local machine
	 * @param upload_file_name - the name of the file when uploaded
	 */
	private static void uploadMediaToBucket(String bucket_name, String local_file_path, String upload_file_name) {

		//finds the mime type of the local file
		String mime_type = getMIMEType(local_file_path);
		
		//if the mime type is null, returns from the method
		if(mime_type == null) {
			return;
		}
		
		byte[] media_content = readAllBytesFromFile(local_file_path);
		
		//encodes the bytes from the file to base 64 ready for upload using the unicode UTF-8 standard
		byte[] encoded = Base64.getEncoder().encode(media_content);
		
		//creates a blob of the encoded file to upload to the 
		BlobId blob_id = BlobId.of(bucket_name, upload_file_name);
		BlobInfo blob_info = BlobInfo.newBuilder(blob_id).setContentType(mime_type).build();
		
		//creates the blob on the google cloud
		try {
			storage.create(blob_info, new String(encoded).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StorageException e) {
			return;
		}
		
	}
	
	/**
	 * Method to read all of the bytes from a file.
	 * @param local_file_path
	 * @return
	 */
	private static byte[] readAllBytesFromFile(String local_file_path) {
		
		//a reference to the file being uploaded
		File file = new File(local_file_path);

		//creates a byte array to store the file being uploaded in
		byte[] file_content = new byte[(int) file.length()];
		
		//reads all the bytes within the file
		try {
			file_content = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file_content;
		
	}
	
	/**
	 * Method to find the MIME type of a media file given the path to the file.
	 * @param filepath - the path to the file (relative or absolute)
	 * @return mime_type - the mime type of the file in string form (eg. for text: "text/plain")
	 */
	private static String getMIMEType(String filepath) { 
		
		//gets the file extension of the file
		String extension = FilenameUtils.getExtension(filepath);
		
		String mime_type = null;
		
		//checks the extension in order to determine the MIME type of the file
		if(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg") || extension.equals("gif")) {
			mime_type = "image/" + extension;
		} else if(extension.equals("mp3") || extension.equals("m4a") || extension.equals("flac") || extension.equals("wav")) {
			mime_type = "audio/" + extension;
		}

		//returns the mime type
		return mime_type;

	}
	
	/**
	 * Method to upload an entire list of multimedia items to the Google Cloud Platform.
	 * @param filepath	the filepath to the local list XML file
	 */
	public static Boolean uploadList(String filepath) {
		
		//checks if the user is logged in
		if(!isLoggedIn()) {
			//if not returns without uploading
			System.out.println("Returning without upload.");
			return false;
		}
		
		//builds a list from the XML file
		ChuseList list = XMLHandler.buildListFromXML(filepath);
		
		//reads the filepaths of all multimedia objects
		ArrayList<String> paths = getPathsFromList(list);

		//removes any null objects in the paths array list
		paths.removeAll(Collections.singleton(null));

		//uploads the XML list file to the user's area
		uploadFileToBucket("we-tech-user-storage", filepath, user.getId() + "/" + list_access_type + "/" + FilenameUtils.getBaseName(filepath) + "/" + FilenameUtils.getName(filepath));
		
		//uploads every multimedia file contained within the XML file to the user's area
		for(String path : paths) {
			uploadMediaToBucket("we-tech-user-storage", path, user.getId() + "/" + list_access_type + "/" + FilenameUtils.getBaseName(filepath) + "/" + FilenameUtils.getName(path));
		}
		
		//resets the access type to private
		setAccessType(0);
		
		return true;

	}
	
//	public static Boolean uploadResults(String filepath, String author_username) {
//		
//	}
	
	/**
	 * Method to download a list from a given path in the user's cloud storage area.
	 * 
	 * @param cloud_list_path	the path to the folder containing all the relevant files in the user's cloud storage area
	 * @return local path		the path to the new XML file that is stored locally
	 * @throws IOException
	 */
	public static String downloadList(String cloud_list_path) throws IOException {
		
		String downloaded_file_name = null;
		
		//checks that the user is logged in
		if(!isLoggedIn()) {
			System.out.println("Not logged in.");
			return downloaded_file_name;
		}
		
		//checks that the user is logged into the correct account for the access rights, or that the list is public
		if(!Integer.toString(user.getId()).equals(cloud_list_path.split("/")[0]) && (user.getId() + "/" + cloud_list_path.split("/")[1] + "/").equals(user.getId() + "/private/")) {
			System.out.println("Not logged in to correct account.");
			return downloaded_file_name;
		}
		
		//fetches the paths to all the files contained within the path folder
		Page<Blob> blobs = storage.list("we-tech-user-storage", BlobListOption.prefix(cloud_list_path));
		
		if(blobs == null) {
			return downloaded_file_name;
		}
		
		//loops through all of the files
		for(Blob blob : blobs.iterateAll()) {
			
			System.out.println("Downloading...");
			
			System.out.println(blob.getName());
			
			
			//if it is an XML file then download and save as an XML file
			if(FilenameUtils.getExtension(blob.getName()).equals("xml")) {
				//reads the XML file's byte content
				byte[] list_xml = blob.getContent();
				//writes the XML file's byte content to a local file
				downloaded_file_name = System.getProperty("user.dir") + "/saves/" + cloud_list_path.split("/")[cloud_list_path.split("/").length - 1] + "/" + FilenameUtils.getName(blob.getName());
//				FileUtils.writeByteArrayToFile(new File(System.getProperty("user.dir") + "/saves/" + cloud_list_path.split("/")[cloud_list_path.split("/").length - 1] + "/" + FilenameUtils.getName(blob.getName())), list_xml);
				FileUtils.writeByteArrayToFile(new File(downloaded_file_name), list_xml);
				System.out.println("Downloaded file name is:");
				System.out.println(downloaded_file_name);
			//if it is not an XML file, it must be a media file, so download, decode from Base64 and then save
			} else {
				
				//read the encoded media file's content
				byte[] media_file_base64 = blob.getContent();
				
				//decode the encoded media file
				byte[] media_file = Base64.getDecoder().decode(media_file_base64);
				
				//writes the decoded media file to a local file
				FileUtils.writeByteArrayToFile(new File(System.getProperty("user.dir") + "/saves/" + cloud_list_path.split("/")[cloud_list_path.split("/").length - 1] + "/" + FilenameUtils.getName(blob.getName())), media_file);
			}

		}
		
		//rebuilds the list XML file with the paths changed to match the new media file locations
		rebuildXML(System.getProperty("user.dir") + "/saves/" + cloud_list_path.split("/")[cloud_list_path.split("/").length - 1] + "/");
		
		return downloaded_file_name;
		
	}
	
	/**
	 * Method to rebuild a list XML file with the multimedia paths changed to match the new media file locations.
	 * 
	 * @param list_download_folder	the folder where the media files are contained
	 */
	private static void rebuildXML(String list_download_folder) {
		
		System.out.println("Rebuilding XML...");
		
		//opens the folder
    	File folder = new File(list_download_folder);

    	File[] list_of_files;

    	File[] xml_files = null;
    	
    	File output_xml_file = null;
    	
    	//open all files in directory
    	list_of_files = folder.listFiles(new FilenameFilter() {
    		@Override
    		public boolean accept(File dir, String name) {
    			return true;
    		}
    	});
    	
    	//open all files in directory with .xml file extension
    	xml_files = folder.listFiles(new FilenameFilter() {
    		@Override
    		public boolean accept(File dir, String name) {
    			return name.toLowerCase().endsWith(".xml");
    		}
    	});

    	ChuseList list = null;
    	
    	//checks the XML files and ensures that they contain the list
    	for(File xml_file : xml_files) {
    		
    		//builds a list from the XML file
    		list = XMLHandler.buildListFromXML(xml_file.getAbsolutePath());
    		
    		//if they contain the list, break out of the for loop
    		if(list != null) {
    			output_xml_file = xml_file;
    			break;
    		}
    		
    	}
    	
    	System.out.println(list);
    	
    	//loops through every file in the folder
    	for(File file : list_of_files) {
    		
    		//if the file is the XML file, then skip an iteration of the loop
    		if(file.getName().toLowerCase().endsWith(".xml")) {
    			continue;
    		}
    		
    		//loop through every item in the list
    		for(int i = 0; i < list.getSize(); i++) {
    			
    			//if the path for the current item of the list contain the exact name of the media file but with a different path
    			//then change the path of the current item to the new path of the media file
    			if(list.get(i).getPath().contains(file.getName())) {
    				list.get(i).changePath(file.getAbsolutePath());
    			}
    		}
    	}
    	
    	//rebuild the XML using the updated list
    	XMLHandler.buildXMLFromList(list, output_xml_file.getAbsolutePath());
		
	}
	
	/**
	 * Method for getting the paths to all multimedia items from a list XML file and returning this
	 * list of paths as an array list of Strings.
	 * 
	 * @param list	the list of multimedia items
	 * @return
	 */
	private static ArrayList<String> getPathsFromList(ChuseList list) {
		
		ArrayList<String> paths = new ArrayList<String>();
		
		//loops through every item in the list
		for(int i=0; i < list.getSize(); i++) {
			
			//adds the path from the current item to the list
			paths.add(list.get(i).getPath());
			
		}
		
		//returns the list of paths
		return paths;
		
	}
	
	/**
	 * Method for creating an account with a given email, user name and password.
	 * 
	 * @param email		the account's desired email address
	 * @param username	the account's desired user name
	 * @param password	the account's desired password
	 */
	public static Boolean createAccount(String email, String username, String password, int age, String gender) {
		
		Boolean result = false;
		
		//get the user account object from the datastore
		Entity retrieved_account = getUserAccountEntity(email);

		//search for the username in the database
		QueryResults <Entity> results = queryUserAccountByProperty("username", username);
		
		//if no account has that email or username, then create the account
		if(retrieved_account == null && results == null) {
			
			//encrypt password using bcrypt with a salt value of 14
			String encrypted_pass = BCrypt.hashpw(password, BCrypt.gensalt(14));
			
			//create a new account on the datastore, storing the hashed email, hashed password and the username
			Entity account = Entity.newBuilder(datastore.newKeyFactory().setKind("User Account").newKey(email.hashCode()))
					.set("password", encrypted_pass)
					.set("username", username)
					.set("age", Integer.toString(age))
					.set("gender", gender)
					.build();
			datastore.put(account);
			
			result = true;
		
		//otherwise print error statements
		} else if(retrieved_account != null && results == null) {
			System.out.println("Account with this email address already exists.");
		} else if(retrieved_account == null && results != null) {
			System.out.println("Account with this username already exists.");
		} else {
			System.out.println("Account with this email address and username already exists.");
		}
		
		return result;
		
	}
	
	/**
	 * @param email		the email address associated with the account
	 * @param password	the account's password
	 * @return
	 */
	public static Boolean logIn(String email, String password) {
		
		Boolean result = false;
		
		//gets the user account object
		Entity retrieved_account = getUserAccountEntity(email);
		
		//checks if the email is valid
		if(verifyAccountId(email)) {
			//checks if the password is valid
			if(verifyAccountPassword(email, password)) {
				//creates a new local user account object using the provided details
				user = new UserAccount(email, retrieved_account.getString("username"), retrieved_account.getString("age"), retrieved_account.getString("gender"));
				loggedIn = true;
				result = true;
			}
		}
		
		//user.print();
		
		//returns true if logged in, false if not
		return result;
		
	}
	
	/**
	 * Logs out of the account
	 */
	public static void logOut() {
		user = null;
		loggedIn = false;
	}
	
	/**
	 * Method to verify that the password provided is correct
	 * 
	 * @param email		the email address for the account
	 * @param password	the password associated with the account
	 * @return			true if successful, false otherwise
	 */
	public static Boolean verifyAccountPassword(String email, String password) {
		
		Boolean result = false;
		
		//retrieve the user account object
		Entity retrieved_account = getUserAccountEntity(email);
		
		//if the user account exists
		if(retrieved_account != null) {
			//verify the password using the bcrypt check function
			if(BCrypt.checkpw(password, retrieved_account.getString("password"))) {
				result = true;
			}
		}

		//returns true if password is correct, false if not
		return result;
		
	}
	
	/**
	 * Method to check whether or not an account ID is valid.
	 * 
	 * @param email	the email address to check
	 * @return
	 */
	public static Boolean verifyAccountId(String email) {
		
		Boolean result = false;
		
		//fetch the user account with the provided email
		Entity retrieved_account = getUserAccountEntity(email);
		
		//check if the retrieved account exists, if it does set the return value to true
		if(retrieved_account != null) {
			result = true;
		}
		
		//return true if the account exists, false otherwise
		return result;
		
	}
	
	/**
	 * Method to get all public list stored on a user's profile. Uses their username to find their account.
	 * 
	 * TODO COMPLETE METHOD - NEEDS TO ADD A RETURN FOR THE XML FILE NAMES
	 * @param username	the username of the account
	 * @return
	 */
	public static ArrayList<String> getPublicProfileContent(String username) {
		
		ArrayList<String> public_lists = null;
		
		//checks if there is an account with the username given
		QueryResults<Entity> results = queryUserAccountByProperty("username", username);
		
		//if there is then perform actions
		if(results != null) {
			
			public_lists = new ArrayList<String>();
			
			//read the account
			Entity public_account = results.next();

//			System.out.println("Username: ");
//			System.out.println(username);
//			System.out.println("User account ID: ");
//			System.out.println(public_account.getKey().getId());
			
			//get all the files contained within the public profile
			Page<Blob> blobs = storage.list("we-tech-user-storage", BlobListOption.prefix(public_account.getKey().getId() + "/public/"));

//			System.out.println("Public Files: ");
//			for(Blob blob : blobs.iterateAll()) {
//				System.out.println(blob.getName());
//			}

			
			for(Blob blob : blobs.iterateAll()) {
				if(FilenameUtils.getExtension(blob.getName()).equals("xml")) {					
					public_lists.add(FilenameUtils.removeExtension(blob.getName()));
				}
			}

			
			
		}
		
		return public_lists;
		
	}
	
	/**
	 * Method to locate all accounts featuring a specific property with a specific value.
	 * @param property
	 * @param value
	 * @return
	 */
	public static QueryResults<Entity> queryUserAccountByProperty(String property, String value) {
		
		//create a query for the database for the property and its value
		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind("User Account")
				.setFilter(PropertyFilter.eq(property, value))
				.build();
		
		//run the query
		QueryResults<Entity> results = datastore.run(query);
		
		if(!results.hasNext()) {
			results = null;
		}
		
		//return the query results
		return results;
	}
	
	/**
	 * Method to get a user account entity with a given email address.
	 * @param email	the email address of the account
	 * @return
	 */
	public static Entity getUserAccountEntity(String email) {
		
		//hash the email
		int hashed_email = email.hashCode();
		
		//search the database for the hashed email
		Key user_key = datastore.newKeyFactory().setKind("User Account").newKey(hashed_email);
		
		//retrieve the user account
		Entity retrieved = datastore.get(user_key);
		
		return retrieved;
		
	}
	
	/**
	 * Method to check if the program is logged in.
	 * @return	true if logged in, false if not
	 */
	public static Boolean isLoggedIn() {
		
		if(loggedIn) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Method to get the locally stored user account.
	 * 
	 * @return
	 */
	public static UserAccount getUserAccount() {
		return user;
	}
	
	/**
	 * Method to set the access type of the list being uploaded, either private or public.
	 * 
	 * @param access_type	the type of access to grant for a list (1 for public, 0 for private)
	 */
	public static void setAccessType(int access_type) {
		
		if(access_type == 1) {
			list_access_type = "public";
		} else {
			list_access_type = "private";
		}
	}
	
}
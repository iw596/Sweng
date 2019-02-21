package cloudStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;


public class QuickstartSample {
	
	static Storage authoriseCloudStorage(String jsonPath) throws IOException {
		  // You can specify a credential file by providing a path to GoogleCredentials.
		  // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
		  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
		        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		  Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

/*		  System.out.println("Buckets:");
		  Page<Bucket> buckets = storage.list();
		  for (Bucket bucket : buckets.iterateAll()) {
		    System.out.println(bucket.toString());
		  }*/
		  
		return storage;
		
	}
	
	static Datastore authoriseCloudDatastore(String jsonPath) throws IOException {
		  // You can specify a credential file by providing a path to GoogleCredentials.
		  // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
		  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
		        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

		  Datastore datastore = DatastoreOptions.newBuilder().setCredentials(credentials).build().getService();

		  
		return datastore;
		
	}
	
	private static void uploadFileToBucket(Storage storage, String bucket_name, String local_file_path, String upload_file_name) {

		File file = new File(local_file_path);
		FileInputStream file_input;
		file_input = null;
		
		try {
			file_input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] file_content = new byte[(int) file.length()];
		
		try {
			file_input.read(file_content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BlobId blobId = BlobId.of(bucket_name, upload_file_name);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
		
		try {
			storage.create(blobInfo, new String(file_content).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Uploaded file with name: " + file.getName());
		
	}
	
	
	public static void uploadList(Storage storage, String bucket_name, String user_name, String file_path) {
		
		File file = new File(file_path);
		
		String file_name = file.getName().substring(0, file.getName().lastIndexOf('.'));
		
		String directory = user_name + "/" + file_name;
		
		Bucket bucket = storage.get(bucket_name);
		
		List<String> blobs = new ArrayList<String>();
		
		blobs.add(directory + "/" + file.getName());
		
		Boolean alreadyExists = false;
		
		for(Blob blob: bucket.get(blobs)) {
			if(blob != null) {
				System.out.println("File already exists.n");
				alreadyExists = true;
			}
		}
		
		if(!alreadyExists) {
			uploadFileToBucket(storage, bucket_name, file_path, directory + "/" + file.getName());
		}

	}
	
	public static void createAccount(Datastore datastore, String email, String password) {
		
		int hashed_email = email.hashCode();
		
		Key user_key = datastore.newKeyFactory().setKind("User Account").newKey(hashed_email);
		
		Entity retrieved_account = datastore.get(user_key);
		
		if(retrieved_account == null) {
			String encrypted_pass = BCrypt.hashpw(password, BCrypt.gensalt(14));
			
			Entity account = Entity.newBuilder(user_key)
					.set("password", encrypted_pass)
					.build();
			
			datastore.put(account);
		} else {
			System.out.println("Account with this email address already exists.");
		}
	}
	
	public static Boolean verifyAccountPassword(Datastore datastore, String email, String password) {
		
		Boolean result = false;
		
		int hashed_email = email.hashCode();
		
		Key user_key = datastore.newKeyFactory().setKind("User Account").newKey(hashed_email);
		
		Entity retrieved = datastore.get(user_key);
		
		if(retrieved != null) {
			if(BCrypt.checkpw(password, retrieved.getString("password"))) {
				result = true;
			}
		}

		return result;
		
	}
	
	public static Boolean checkAccountId(Datastore datastore, String hashed_email) {
		
		Boolean result = false;
		
		Key user_key = datastore.newKeyFactory().setKind("User Account").newKey(hashed_email);
		
		Entity retrieved = datastore.get(user_key);
		
		if(retrieved != null) {
			result = true;
		}
		
		return result;
		
	}
	
	public static void main(String... args) throws Exception {
		
		
		Storage storage = authoriseCloudStorage(System.getProperty("user.dir") + "\\My First Project-29d37f5d03f4.json");
		Datastore datastore = authoriseCloudDatastore(System.getProperty("user.dir") + "\\My First Project-29d37f5d03f4.json");
		
		Scanner scanner = new Scanner(System.in);
		String email = "";
		
		System.out.println("Do you want to create an account? (y/n)");
		String result = scanner.nextLine();
		
		if(result.equals("y")) {
			System.out.println("Enter your email address: ");
			email = scanner.nextLine();
			System.out.println("Enter a new password:");
			String password = scanner.nextLine();
			createAccount(datastore, email, password);
		}

		Boolean success = false;
		
		while(!success) {
			System.out.println("Please log in.");
			System.out.println("Email address: ");
			email = scanner.nextLine();
			System.out.println("Password");
			String password = scanner.nextLine();
			
			success = verifyAccountPassword(datastore, email, password);
			
			if(success) {
				System.out.println("Successfully logged in.");
				break;
			} else {
				System.out.println("Failed to log in. Incorrect email or password.");
			}
		}
		
		UserAccount user = new UserAccount(email.hashCode(), true);
		
		uploadList(storage, "staging.western-throne-231914.appspot.com", Integer.toString(user.getId()), System.getProperty("user.dir") + "\\List with Spaces.xml");

	}
}
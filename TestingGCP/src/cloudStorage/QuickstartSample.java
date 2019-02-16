package cloudStorage;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
//Imports the Google Cloud client library
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;



public class QuickstartSample {
	
	static Storage authExplicit(String jsonPath) throws IOException {
		  // You can specify a credential file by providing a path to GoogleCredentials.
		  // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
		  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
		        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		  Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

		  System.out.println("Buckets:");
		  Page<Bucket> buckets = storage.list();
		  for (Bucket bucket : buckets.iterateAll()) {
		    System.out.println(bucket.toString());
		  }
		  
		return storage;
		
		}
	
	public static void main(String... args) throws Exception {
		
		Storage storage = authExplicit(System.getProperty("user.dir") + "\\My First Project-183cd58ef5b2.json" );
		
		//Page<Bucket> buckets = storage.list();
		
		BlobId blobId = BlobId.of("staging.western-throne-231914.appspot.com", "test_1.txt");
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
		storage.create(blobInfo, "I am a test blob created by Dan!".getBytes(Charset.forName("UTF-8")));
		
		/*storage.crea
		
		Blob downloaded = storage.get(BlobId.of("staging.western-throne-231914.appspot.com", "test_1"));
		
		downloaded.downloadTo(Paths.get(System.getProperty("user.dir") + "\\" + downloaded.getName()));*/
		
	}
}
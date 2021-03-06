package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;

import cloudInteraction.CloudInteractionHandler;
import listDataStructure.ChuseList;
import xmlHandling.XMLHandler;


/**
 * Class for unit testing all of the functionality of the Cloud Interaction Handler, which interfaces
 * between the Chuse app and the Google Cloud Platform.
 * 
 * Date created: 16/04/2019
 * Date last edited: 17/04/2019
 * Last edited by: Dan Jackson
 * @author Dan Jackson
 *
 */
//sorts the methods by ascending order as the order they are completed in is essential
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CloudInteractionHandlerTest {
	
	@Test
	/**
	 * Method to test creating a new account.
	 */
	public void A_createNewAccount() {
		
		System.out.println(System.getProperty("user.dir") + "\\SWEng WeTech-9745fc9cfe28.json");
		
		new CloudInteractionHandler();
		
		CloudInteractionHandler.createAccount("test@gmail.com", "test_account", "testing123", 21, "Male");
		
		CloudInteractionHandler.logIn("test@gmail.com", "testing123");
		
		ArrayList<String> public_lists = CloudInteractionHandler.getRandomPublicLists();
		
		for(String list : public_lists) {
			
			System.out.println(list);
			
		}
		
//		QueryResults<Entity> results = CloudInteractionHandler.queryUserAccountByProperty("username", "test_account");
//		
//		if(results == null) {
//			return;
//		}
//		
//		Entity user = results.next();
//		
//		System.out.println(user.getString("gender"));
//		System.out.println(user.getString("age"));
		
//		ArrayList<String> public_lists = CloudInteractionHandler.getAllPublicLists();
//
//		Collections.shuffle(public_lists);
//		
//		for(String path : public_lists) {
//			//CloudInteractionHandler.downloadList(path);
//			//System.out.println(path.split("/")[0]);
//			System.out.println("-**-**-**-**-**-**-**-**-**-**-**-**-**-**-**-**-**-");
//			Entity account = CloudInteractionHandler.getUserAccountEntity(Integer.parseInt(path.split("/")[0]));
//			System.out.println("List Name: " + path.split("/")[2]);
//			System.out.println("List Owner: " + account.getString("username"));
//			System.out.println("Path: " + path);
//		}
//		
//		System.out.println("-**-**-**-**-**-**-**-**-**-**-**-**-**-**-**-**-**-");
//		
//		ArrayList<String> account_lists = CloudInteractionHandler.getAllProfileContent("AAtest_account");
//		
//		if(account_lists != null) {
//			System.out.println("Successfully pulled account content.");
//			
//			for(String list : account_lists) {
//				System.out.println("List: " + list);
//			}
//			
//		} else {
//			System.out.println("Not logged into correct account, could not pull account content.");
//		}
//		
//		account_lists = CloudInteractionHandler.getAllProfileContent("test_account");
//		
//		if(account_lists != null) {
//			System.out.println("Successfully pulled account content.");
//			
//			for(String list : account_lists) {
//				System.out.println("List: " + list);
//			}
//			
//		} else {
//			System.out.println("Not logged into correct account, could not pull account content.");
//		}
		
		//assertEquals(true, CloudInteractionHandler.verifyAccountId("test@gmail.com"));
		
	}
	
//	@Test
//	/**
//	 * Method to test creating a new account.
//	 */
//	public void A_createNewAccount() {
//		
//		System.out.println(System.getProperty("user.dir") + "\\SWEng WeTech-9745fc9cfe28.json");
//		
//		new CloudInteractionHandler();
//		
//		CloudInteractionHandler.createAccount("test@gmail.com", "test_account", "testing123", 21, "Male");
//		
//		assertEquals(true, CloudInteractionHandler.verifyAccountId("test@gmail.com"));
//		
//	}
	
//	@Test
//	/**
//	 * Method to test creating a new account.
//	 */
//	public void AA_createNewAccount() {
//		
//		System.out.println(System.getProperty("user.dir") + "\\SWEng WeTech-9745fc9cfe28.json");
//		
//		new CloudInteractionHandler();
//		
//		CloudInteractionHandler.createAccount("AAtest@gmail.com", "AAtest_account", "testing123", 21, "Male");
//		
//		assertEquals(true, CloudInteractionHandler.verifyAccountId("AAtest@gmail.com"));
//		
//	}
//	
//	@Test
//	/**
//	 * Method to test creating a new account with an email already used.
//	 */
//	public void B_createDuplicateEmailAccount() {
//		
//		new CloudInteractionHandler();
//		
//		CloudInteractionHandler.createAccount("test@gmail.com", "different_test_account", "testing123", 21, "Male");
//
//		assertEquals(null, CloudInteractionHandler.queryUserAccountByProperty("username", "different_test_account"));
//		
//	}
//	
//	@Test
//	/**
//	 * Method to test creating a new account with a username already used.
//	 */
//	public void C_createDuplicateUsernameAccount() {
//		
//		new CloudInteractionHandler();
//		
//		CloudInteractionHandler.createAccount("another_test@gmail.com", "test_account", "testing123", 21, "Male");
//		
//		assertEquals(false, CloudInteractionHandler.verifyAccountId("another_test@gmail.com"));
//		
//	}
//	
//	@Test
//	/**
//	 * Method to test logging into a valid account.
//	 */
//	public void D_logInToValidAccount() {
//		
//		new CloudInteractionHandler();
//		
//		assertEquals(true, CloudInteractionHandler.logIn("test@gmail.com", "testing123"));
//
//	}
//	
//	@Test
//	/**
//	 * Method to test logging into a valid account with an incorrect password.
//	 */
//	public void E_logInWithWrongPassword() {
//		
//		new CloudInteractionHandler();
//		
//		assertEquals(false, CloudInteractionHandler.logIn("test@gmail.com", "testing12345"));
//
//	}
//	
//	@Test
//	/**
//	 * Method to test logging into an invalid account.
//	 */
//	public void F_logInToInvalidAccount() {
//		
//		new CloudInteractionHandler();
//		
//		assertEquals(false, CloudInteractionHandler.logIn("test@hotmail.co.uk", "testing123"));
//		
//	}
//	
//	@Test
//	/**
//	 * Method to test logging out of an account.
//	 */
//	public void G_logOut() {
//		
//		new CloudInteractionHandler();
//		CloudInteractionHandler.logIn("test@gmail.com", "testing123");
//		CloudInteractionHandler.logOut();
//		
//		assertEquals(null, CloudInteractionHandler.getUserAccount());
//		
//	}
//	
//	@Test
//	/**
//	 * Method to test uploading & downloading a private list of basic items.
//	 */
//	public void H_uploadPrivateBasicList() {
//		new CloudInteractionHandler();
//		CloudInteractionHandler.logIn("test@gmail.com", "testing123");
//		CloudInteractionHandler.uploadList("C:\\Users\\Dan\\Sweng\\NewChuseMaster\\saves\\Cloud Testing\\BasicItem List.xml");
//		
//		try {
//			CloudInteractionHandler.downloadList(Integer.toString("test@gmail.com".hashCode()) + "/private/BasicItem List/");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		ChuseList downloaded_list = XMLHandler.buildListFromXML(System.getProperty("user.dir") + "\\saves\\BasicItem List\\BasicItem List.xml");
//		
//		assertEquals("Oranges", downloaded_list.get(0).getTitle());
//		
//	}
//	
////	@Test
////	/**
////	 * Method to test uploading & downloading a public list of audio items
////	 */
////	public void I_uploadPublicAudioList() {
////		new CloudInteractionHandler();
////		CloudInteractionHandler.logIn("test@gmail.com", "testing123");
////		CloudInteractionHandler.setAccessType(1);
////		CloudInteractionHandler.uploadList("C:\\Users\\Dan\\Sweng\\NewChuseMaster\\saves\\Cloud Testing\\AudioItem List.xml");
////		CloudInteractionHandler.logOut();
////		
////		CloudInteractionHandler.logIn("AAtest@gmail.com", "testing123");
////		
////		try {
////			CloudInteractionHandler.downloadList(Integer.toString("test@gmail.com".hashCode()) + "/public/AudioItem List/");
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		
////		ChuseList downloaded_list = XMLHandler.buildListFromXML(System.getProperty("user.dir") + "/saves/AudioItem List/AudioItem List.xml");
////		
////		assertEquals("Adult Diversion", downloaded_list.get(0).getTitle());
////		
////	}
//	
//	@Test
//	/**
//	 * Method to test uploading & downloading a public list of audio items
//	 */
//	public void IA_uploadPublicAudioList() {
//		new CloudInteractionHandler();
//		CloudInteractionHandler.logIn("test@gmail.com", "testing123");
//		CloudInteractionHandler.setAccessType(1);
//		CloudInteractionHandler.uploadList("C:\\Users\\Dan\\Sweng\\NewChuseMaster\\saves\\Cloud Testing\\ImageItem List.xml");
//		CloudInteractionHandler.logOut();
//		
//		CloudInteractionHandler.logIn("AAtest@gmail.com", "testing123");
//		
//		System.out.println("*********************************");
//		System.out.println("---------------------------------");
//		System.out.println("*********************************");
//		
//		CloudInteractionHandler.getPublicProfileContent("test_account");
//		
//		try {
//			CloudInteractionHandler.downloadList(Integer.toString("test@gmail.com".hashCode()) + "/public/ImageItem List/");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		ChuseList downloaded_list = XMLHandler.buildListFromXML(System.getProperty("user.dir") + "/saves/ImageItem List/ImageItem List.xml");
//		
//		assertEquals("glyph-logo_May2016.png", downloaded_list.get(0).getTitle());
//		
//		System.out.println("*********************************");
//		System.out.println("---------------------------------");
//		System.out.println("*********************************");
//		
//	}
//	
//	@Test
//	/**
//	 * Method to test uploading & downloading a private list of image items.
//	 */
//	public void J_uploadPrivateImageList() {
//		new CloudInteractionHandler();
//		CloudInteractionHandler.logIn("test@gmail.com", "testing123");
//		CloudInteractionHandler.uploadList("C:\\Users\\Dan\\Sweng\\NewChuseMaster\\saves\\Cloud Testing\\ImageItem List.xml");
//		
//		try {
//			CloudInteractionHandler.downloadList(Integer.toString("test@gmail.com".hashCode()) + "/private/ImageItem List/");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		ChuseList downloaded_list = XMLHandler.buildListFromXML(System.getProperty("user.dir") + "/saves/ImageItem List/ImageItem List.xml");
//		
//		assertEquals("glyph-logo_May2016.png", downloaded_list.get(0).getTitle());
//	}
//	
//	@Test
//	/**
//	 * Method to test uploading & downloading a private list of video items.
//	 */
//	public void K_uploadPrivateVideoList() {
//		new CloudInteractionHandler();
// 		CloudInteractionHandler.uploadList("C:\\Users\\Dan\\Sweng\\NewChuseMaster\\saves\\Cloud Testing\\VideoItem List.xml");
//		
//		try {
//			CloudInteractionHandler.downloadList(Integer.toString("test@gmail.com".hashCode()) + "/private/VideoItem List/");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		ChuseList downloaded_list = XMLHandler.buildListFromXML(System.getProperty("user.dir") + "\\saves\\VideoItem List\\VideoItem List.xml");
//		
//		assertEquals("Learn HTML in 12 Minutes", downloaded_list.get(0).getTitle());
//	}
//	
//
//}
	
}

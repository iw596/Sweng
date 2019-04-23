package test;

import org.junit.Test;

import backEnd.BackEndContainer;
import cloudInteraction.CloudInteractionHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import listDataStructure.StatisticsDataStructure;

public class StatisticsTest {
	
	@Test
	public void genarl(){
		
		new CloudInteractionHandler();
		
		//CloudInteractionHandler cloud = new CloudInteractionHandler();
		//cloud.createAccount("email", "username", "password", 1, "gender");
		//BackEndContainer backend = new BackEndContainer();
		//backend.startCloudHandler();
		//backend.createAccount("Jack@mail.com", "Jack", "password", 22, "Male");
		
		
		StatisticsDataStructure aa = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
	
		//aa.setTitleFromFilePath(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
		//System.out.println(StatisticsDataStructure.getResultsFilesExtension(System.getProperty("user.dir") + "\\saves", "jeffrey"));
		
		System.out.println(aa.getList().getName());
		System.out.println("******************");
		for (int i = 0; i < 3; i++){
			System.out.println(aa.getResultList().get(i).getUser().getUsername());
			System.out.println(aa.getResultList().get(i).getUser().getAge());
			System.out.println(aa.getResultList().get(i).getUser().getGender());
			for (int j = 0; j < 1; j++){
				System.out.println(aa.getResultList().get(i).getRankingList(j).getTime());
				System.out.println("----------------------");
				for (int k = 0; k < 4; k++ ){
					aa.getResultList().get(i).getRankingList(j).get(k).print();
				}
			}
			System.out.println("********************");
		}
		System.out.println("Hi");
	}
}

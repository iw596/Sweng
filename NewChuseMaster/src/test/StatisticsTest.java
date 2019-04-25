package test;

import org.junit.Test;

import backEnd.BackEndContainer;
import cloudInteraction.CloudInteractionHandler;

import static org.junit.Assert.*;

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
		
		StatisticsDataStructure aa = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
		
		System.out.println(aa.getList().getName());
		System.out.println("******************");
		for (int i = 0; i < 3; i++){
			switch(i){
				case 0:
					assertEquals(aa.getResultList().get(i).getUser().getUsername(), "Jack");
					assertEquals(aa.getResultList().get(i).getUser().getAge(), 22);
					assertEquals(aa.getResultList().get(i).getUser().getGender(), "Male");
					for (int j = 0; j < aa.getResultList().get(i).getRankingList().size(); j++){
						switch(j){
							case 0:
								assertEquals(aa.getResultList().get(i).getRankingList().get(j).getTime(), "2019-04-22 11:51:06");
							case 1:
								assertEquals(aa.getResultList().get(i).getRankingList().get(j).getTime(), "2019-04-22 11:51:06");
						}
					}
					break;
				case 1:
					assertEquals(aa.getResultList().get(i).getUser().getUsername(), "AAtest_account");
					assertEquals(aa.getResultList().get(i).getUser().getAge(), 21);
					assertEquals(aa.getResultList().get(i).getUser().getGender(), "Male");
					assertEquals(aa.getResultList().get(i).getRankingList().get(0).getTime(), "2019-04-22 11:51:06");
					break;
				case 2:
					assertEquals(aa.getResultList().get(i).getUser().getUsername(), "danjackson");
					assertEquals(aa.getResultList().get(i).getUser().getAge(), 21);
					assertEquals(aa.getResultList().get(i).getUser().getGender(), "Male");
					assertEquals(aa.getResultList().get(i).getRankingList().get(0).getTime(), "2019-04-22 11:51:06");
					break;
			}
			
			System.out.println(aa.getResultList().get(i).getUser().getUsername());
			System.out.println(aa.getResultList().get(i).getUser().getAge());
			System.out.println(aa.getResultList().get(i).getUser().getGender());
			for (int j = 0; j < aa.getResultList().get(i).getRankingList().size(); j++){
				System.out.println(aa.getResultList().get(i).getRankingList().get(j).getTime());
				System.out.println("----------------------");
				for (int k = 0; k < 4; k++ ){
					aa.getResultList().get(i).getRankingList().get(j).get(k).print();
				}
			}
			System.out.println("********************");
		}
	}
}

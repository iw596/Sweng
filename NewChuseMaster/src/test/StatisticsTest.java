package test;

import org.junit.Test;

import cloudInteraction.CloudInteractionHandler;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import listDataStructure.StatisticsDataStructure;

public class StatisticsTest {
	
	@Test
	public void genarl(){
		// Needed to access the cloud
		new CloudInteractionHandler();
		// Creates the data structure
		StatisticsDataStructure aa = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
		
		
		/*
		 * This does JUnit testing to automatically check that the expected information is stored.
		 * 
		 */
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
								break;
							case 1:
								assertEquals(aa.getResultList().get(i).getRankingList().get(j).getTime(), "2019-04-26 11:51:06");
								break;
						}
					}
					break;
				case 1:
					assertEquals(aa.getResultList().get(i).getUser().getUsername(), "AAtest_account");
					assertEquals(aa.getResultList().get(i).getUser().getAge(), 21);
					assertEquals(aa.getResultList().get(i).getUser().getGender(), "Male");
					assertEquals(aa.getResultList().get(i).getRankingList().get(0).getTime(), "2019-04-19 11:51:06");
					break;
				case 2:
					assertEquals(aa.getResultList().get(i).getUser().getUsername(), "multithread");
					assertEquals(aa.getResultList().get(i).getUser().getAge(), 100);
					assertEquals(aa.getResultList().get(i).getUser().getGender(), "Female");
					assertEquals(aa.getResultList().get(i).getRankingList().get(0).getTime(), "2019-04-27 11:51:06");
					break;
			}
			
			/*
			 * This prints out all of the information for visual checking
			 * 
			 */
			/*System.out.println(aa.getList().getName());
			System.out.println("******************");
			System.out.println(aa.getResultList().get(i).getUser().getUsername());
			System.out.println(aa.getResultList().get(i).getUser().getAge());
			System.out.println(aa.getResultList().get(i).getUser().getGender());
			for (int j = 0; j < aa.getResultList().get(i).getRankingList().size(); j++){
				System.out.println("----------------------");
				System.out.println(aa.getResultList().get(i).getRankingList().get(j).getTime());
				System.out.println("----------------------");
				for (int k = 0; k < 4; k++ ){
					aa.getResultList().get(i).getRankingList().get(j).get(k).print();
				}
			}
			System.out.println("********************");*/
		}
	}
	
	@Test
	public void ageComparisonTest(){
		new CloudInteractionHandler();
		StatisticsDataStructure bb = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
		StatisticsDataStructure aa = bb.getDataForGivenAgeRange(10, 25);
		/*for (int i = 0; i < 2; i++){
			System.out.println(aa.getList().getName());
			System.out.println("******************");
			System.out.println(aa.getResultList().get(i).getUser().getUsername());
			System.out.println(aa.getResultList().get(i).getUser().getAge());
			System.out.println(aa.getResultList().get(i).getUser().getGender());
			for (int j = 0; j < aa.getResultList().get(i).getRankingList().size(); j++){
				System.out.println("----------------------");
				System.out.println(aa.getResultList().get(i).getRankingList().get(j).getTime());
				System.out.println("----------------------");
				for (int k = 0; k < 4; k++ ){
					aa.getResultList().get(i).getRankingList().get(j).get(k).print();
				}
		}
		System.out.println("********************");
		}
		System.out.println("Banana won: " + aa.getNumberOfWins("Banana") + " times");
		System.out.println("Tide pods won: " + aa.getNumberOfWins("Tide pods") + " times");
		System.out.println("Pancake won: " + aa.getNumberOfWins("Pancake") + " times");
		System.out.println("Cake won: " + aa.getNumberOfWins("Cake") + " times");*/
}
	@Test
	public void genderComparisonTest(){
		new CloudInteractionHandler();
		StatisticsDataStructure bb = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
		StatisticsDataStructure aa = bb.getDataForGivenGender("Male");
		/*for (int i = 0; i < 2; i++){
			System.out.println(aa.getList().getName());
			System.out.println("******************");
			System.out.println(aa.getResultList().get(i).getUser().getUsername());
			System.out.println(aa.getResultList().get(i).getUser().getAge());
			System.out.println(aa.getResultList().get(i).getUser().getGender());
			for (int j = 0; j < aa.getResultList().get(i).getRankingList().size(); j++){
				System.out.println("----------------------");
				System.out.println(aa.getResultList().get(i).getRankingList().get(j).getTime());
				System.out.println("----------------------");
				for (int k = 0; k < 4; k++ ){
					aa.getResultList().get(i).getRankingList().get(j).get(k).print();
				}
			}
			System.out.println("********************");
		}
		System.out.println("Banana won: " + aa.getNumberOfWins("Banana") + " times");
		System.out.println("Tide pods won: " + aa.getNumberOfWins("Tide pods") + " times");
		System.out.println("Pancake won: " + aa.getNumberOfWins("Pancake") + " times");
		System.out.println("Cake won: " + aa.getNumberOfWins("Cake") + " times");*/
}
	
	@Test
	public void timeComparisonTest(){
		new CloudInteractionHandler();
		StatisticsDataStructure bb = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
        Date date2;
        
        try {
			date1 = sdf.parse("2019-04-15");
			date2 = sdf.parse("2019-04-24");
			StatisticsDataStructure aa = bb.getDataForGivenTimeRange(date1, date2);
			for (int i = 0; i < 2; i++){
				System.out.println(aa.getList().getName());
				System.out.println("******************");
				System.out.println(aa.getResultList().get(i).getUser().getUsername());
				System.out.println(aa.getResultList().get(i).getUser().getAge());
				System.out.println(aa.getResultList().get(i).getUser().getGender());
				for (int j = 0; j < aa.getResultList().get(i).getRankingList().size(); j++){
					System.out.println("----------------------");
					System.out.println(aa.getResultList().get(i).getRankingList().get(j).getTime());
					System.out.println("----------------------");
					for (int k = 0; k < 4; k++ ){
						aa.getResultList().get(i).getRankingList().get(j).get(k).print();
					}
				}
			System.out.println("********************");
			}
			System.out.println("Banana won: " + aa.getNumberOfWins("Banana") + " times");
			System.out.println("Tide pods won: " + aa.getNumberOfWins("Tide pods") + " times");
			System.out.println("Pancake won: " + aa.getNumberOfWins("Pancake") + " times");
			System.out.println("Cake won: " + aa.getNumberOfWins("Cake") + " times");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void winCountTest(){
    	new CloudInteractionHandler();
		StatisticsDataStructure bb = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
		/*
		System.out.println("Banana won: " + bb.getNumberOfWins("Banana") + " times");
		System.out.println("Tide pods won: " + bb.getNumberOfWins("Tide pods") + " times");
		System.out.println("Pancake won: " + bb.getNumberOfWins("Pancake") + " times");
		System.out.println("Cake won: " + bb.getNumberOfWins("Cake") + " times");*/
    }
	
}
package test;

import org.junit.Test;

import listDataStructure.StatisticsDataStructure;

public class StatisticsTest {
	
	@Test
	public void genarl(){
		
		StatisticsDataStructure aa = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\hello");
	
		aa.setTitleFromFilePath(System.getProperty("user.dir") + "\\saves\\hello");
		
	}
}

package test;

import java.io.IOException;
import java.util.ArrayList;

import algorithms.TournamentAlgorithms;
import apiHandlers.YouTubeAPIHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.RankingList;
import xmlHandling.XMLHandler;

public class DataStructuresVideoTest extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		//Initialises file browser and sets file handler
		ArrayList<BasicItem> video_array = new ArrayList<BasicItem>();
    	ChuseList videos = null;
		try {
			videos = YouTubeAPIHandler.getPlaylistData("https://www.youtube.com/watch?v=q6EoRBvdVPQ&list=PLFsQleAWXsj_4yDeebiIADdH5FMayBiJo");
		} catch (IOException e1) {
			// TODO Auto-generated catch block;
			e1.printStackTrace();
		}
		
		//Add videos to a ChuseList of Basic Items
    	for(int i = 0; i < videos.getSize(); i++) {
    		video_array.add(videos.get(i));
    	}
		
		//Initialisation of Chuse list, adding items gotten from file
		ChuseList list = new ChuseList("name");
        list.addItemArray(video_array);
        list.printList();
        XMLHandler.buildXMLFromList(list, "new xml video file");
       
        //Test to check the size to confirm the right amount of items and right type
        int size = list.getSize();
        System.out.println(size);
		for(int i = 0; i < list.getSize(); i++) {
			System.out.println(list.get(i).getType());
			System.out.println(list.get(i).getPath());
		} 
		
		//Ranking algorithm
        ArrayList<ChuseList> result = TournamentAlgorithms.rankingAlgorithm(list);
        RankingList ranks = new RankingList(result);
     
		list = TournamentAlgorithms.findLosers(result);
		result = TournamentAlgorithms.rankingAlgorithm(list);
		ranks.addRankedResults(result);
	}
	
	//Launches file chooser
	public static void main(String[] args) {
		launch(args);
	}

}

package test;

import java.util.ArrayList;

import algorithms.TournamentAlgorithms;
import javafx.application.Application;
import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.RankingList;
import mediaFileImportHandling.TextFileHandler;
import xmlHandling.XMLHandler;

public class DataStructuresTextTest extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		//Setup for the individual file choosers
		ArrayList<BasicItem> text = TextFileHandler.openTextFile(stage);	//Changed to my own function for test purposes.
    	
    	//Mixed list with every type of data structure currently implemented.
        ChuseList list = new ChuseList("name");
        list.addItemArray(text);
        list.printList();
        XMLHandler.buildXMLFromList(list, "new xml text file");

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
	


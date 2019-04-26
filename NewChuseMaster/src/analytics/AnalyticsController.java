package analytics;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import cloudInteraction.CloudInteractionHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Text;
import listDataStructure.StatisticsDataStructure;


public class AnalyticsController implements Initializable {

    @FXML
    private BarChart<?, ?> barchart;
    
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private AreaChart<?, ?> areachart;

    @FXML
    private BarChart<?, ?> barchart3;

    @FXML
    private BarChart<?, ?> barchart4;

    
    @FXML
    private CategoryAxis barchart_x;

    @FXML
    private NumberAxis barchart_y;
  
    @Override
    public void initialize (URL url, ResourceBundle rb) {
    
    // Needed to access the cloud
    new CloudInteractionHandler();	
    // Creates the data structure
 	StatisticsDataStructure aa = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
    
    
    //Gives object to store data
    XYChart.Series set1 = new XYChart.Series<>();
    //ABCD = Name and number is value.
    int i;
    for(i = 0; i < aa.getList().getSize(); i++){
    	
    	set1.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), aa.getAverageScore(aa.getList().get(i).getTitle())));
    	
    }
    
    //Displays a title at the top of the chart.
    barchart.setTitle("Average score");
    //Gives name to data set, this name will be in the legend on the bar chart.
    set1.setName("Score");
    //bc.getData connects the data to the bar chart.
    barchart.getData().addAll(set1);
    barchart.setLegendSide(Side.RIGHT);
    
    //Trying to add animation
    //barchart_y.setAnimated(true);
    
    
//--------------------------------------------------------------------------
   
    //AREA CHART 
	XYChart.Series series1 = new XYChart.Series();  
	series1.setName("Male"); 
	XYChart.Series series2 = new XYChart.Series(); 
	series2.setName("Female"); 
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date1;
    Date date2;
    
    /*try {
		
		date2 = new Date();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		
		
		StatisticsDataStructure Result = aa.getDataForGivenTimeRange(date1, date2);
    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	
	series1.getData().add(new XYChart.Data("Monday", 3)); 
	series1.getData().add(new XYChart.Data("Tuesday", 4)); 
	series1.getData().add(new XYChart.Data("Wednesday", 3)); 
	series1.getData().add(new XYChart.Data("Thursday", 5)); 
	series1.getData().add(new XYChart.Data("Friday", 4)); 
	series1.getData().add(new XYChart.Data("Saturday", 10)); 
	series1.getData().add(new XYChart.Data("Sunday", 12));  
	
	
	series2.getData().add(new XYChart.Data("Monday", 1)); 
	series2.getData().add(new XYChart.Data("Tuesday", 3)); 
	series2.getData().add(new XYChart.Data("Wednesday", 4));  
	
	series2.getData().add(new XYChart.Data("Thursday", 3)); 
	series2.getData().add(new XYChart.Data("Friday", 3)); 
	series2.getData().add(new XYChart.Data("Saturday", 5)); 
	series2.getData().add(new XYChart.Data("Sunday", 4)); 
	
	areachart.getData().addAll(series1,series2); 
	areachart.setLegendSide(Side.RIGHT);
	areachart.setTitle("Popularity over last 7 days:");
	
    //------------------
	
	XYChart.Series series4 = new XYChart.Series(); 
	series4.setName("Female"); 
	
	XYChart.Series series3 = new XYChart.Series(); 
	series3.setName("Male"); 
	
	StatisticsDataStructure FemalesResults = aa.getDataForGivenGender("Female");
	StatisticsDataStructure MaleResults = aa.getDataForGivenGender("Male");
	
	for(i = 0; i < aa.getList().getSize(); i++){
		series4.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), FemalesResults.getAverageScore(FemalesResults.getList().get(i).getTitle())));
		series3.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), MaleResults.getAverageScore(MaleResults.getList().get(i).getTitle()))); 
		
	}
	
	barchart3.setLegendSide(Side.RIGHT);
	barchart3.getData().addAll(series4,series3);
	barchart3.setTitle("Favourite fruit by gender");
	
	//---------------------------------------
	
	StatisticsDataStructure under18Results = aa.getDataForGivenAgeRange(0, 18);
	StatisticsDataStructure Results18_24 = aa.getDataForGivenAgeRange(18, 24);
	StatisticsDataStructure Results25_34 = aa.getDataForGivenAgeRange(25, 34);
	StatisticsDataStructure Results35_44 = aa.getDataForGivenAgeRange(35, 44);
	StatisticsDataStructure Results45_54 = aa.getDataForGivenAgeRange(45, 54);
	StatisticsDataStructure Results55_64 = aa.getDataForGivenAgeRange(55, 64);
	StatisticsDataStructure Results65over = aa.getDataForGivenAgeRange(65, 200);
	
	
	XYChart.Series series5 = new XYChart.Series();  
	series5.setName("Under 18"); 
	XYChart.Series series6 = new XYChart.Series(); 
	series6.setName("18-24"); 
	XYChart.Series series7 = new XYChart.Series(); 
	series7.setName("25-34"); 
	XYChart.Series series8 = new XYChart.Series(); 
	series8.setName("35-44"); 
	XYChart.Series series9 = new XYChart.Series(); 
	series9.setName("45-54"); 
	XYChart.Series series10 = new XYChart.Series(); 
	series10.setName("55-64"); 
	XYChart.Series series11 = new XYChart.Series(); 
	series11.setName("Over 65"); 
	
	System.out.println(Results18_24.getAverageScore(Results18_24.getList().get(0).getTitle()));
	
	for(i = 0; i < aa.getList().getSize(); i++){
		series5.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), under18Results.getAverageScore(under18Results.getList().get(i).getTitle())));
		series6.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), Results18_24.getAverageScore(Results18_24.getList().get(i).getTitle())));
		series7.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), Results25_34.getAverageScore(Results25_34.getList().get(i).getTitle())));
		series8.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), Results35_44.getAverageScore(Results35_44.getList().get(i).getTitle())));
		series9.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), Results45_54.getAverageScore(Results45_54.getList().get(i).getTitle())));
		series10.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), Results55_64.getAverageScore(Results55_64.getList().get(i).getTitle())));
		series11.getData().add(new XYChart.Data(aa.getList().get(i).getTitle(), Results65over.getAverageScore(Results65over.getList().get(i).getTitle())));
	}
	
	
	
	barchart4.setLegendSide(Side.RIGHT);
	barchart4.getData().addAll(series5,series6, series7,series8,series9, series10,series11);
	barchart4.setTitle("Favourite fruit by age");
	barchart4.setCategoryGap(20);
	

    //---------------------------------------------
    
    scroll.setFitToHeight(true);
    scroll.setFitToWidth(true);

 
   }
    

    

    
    

}

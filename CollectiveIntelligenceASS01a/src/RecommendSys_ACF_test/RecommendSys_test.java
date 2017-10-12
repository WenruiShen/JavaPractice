package RecommendSys_ACF_test;

/**************************************************************************************
 * Project Name: 	Recommend System
 * Course Info:		Collective Intelligence(COMP30490) Assignment_1
 * Author: 			Shen Wenrui
 * Student No:		15210671
 * College:			UCD.School of CS
 * Data: 			2017-02-08
 * Email: 			wenrui.shen@ucdconnect.ie
 * Description:		Task_1: Read data set file(csv), format it into compressed matrix.
 * 					Task_2:
 * 					Task_3: 
 * 					Task_4:  
 **************************************************************************************/

import java.io.IOException;
import java.util.HashMap;

public class RecommendSys_test {
	final static String file_name = "100k.dat";
	public static void main(String[] args) throws IOException {
		System.out.println("Now the test start!");
		RecommendSys recommend_system = new RecommendSys(file_name);
	}
}

/******************************************************
 * Class Name: 		RecommendSys
 * Constructor:		RecommendSys(String file_name);
 * Last change:		2012-02-08
 * Description:		Main body of the recommend system.
 ******************************************************/
class RecommendSys{
	/**************************************************
	 * Main Constructor of the Recommend System Class.
	 **************************************************/
	public RecommendSys(String file_name) throws IOException{
		ResysHashMap resysHashMap = new ResysHashMap();
		Ratings_Count ratings_Count = new Ratings_Count();
		Display display = new Display();
		
		FileProcess fileProcess = new FileProcess();
		fileProcess.File_reader(file_name, resysHashMap, ratings_Count);
		//check_data_map(userMap, display);// Output all data from hashmap.

		/* Task_1: Make statistics.*/
		System.out.println("<Task_1> start:");
		MakeStatistic(resysHashMap, ratings_Count);
		
		/* Task_2: Leave-One-Out Testing Loop*/ 
		System.out.println("<Task_2> start:");
		new LoopControl().LOOLoopTest(resysHashMap);
		
		// Task_3: 
		System.out.println("<Task_3> start:");
		float[][] DistanceMatrix = new MatrixPrepare().PrepareDistanceMatrix(resysHashMap.get_userMap(), resysHashMap.get_itemListMap());
		display.DisplayResults(DistanceMatrix, "Task3_PrepareDistanceMatrix.txt");
		DiatanceSimCollaborativeFilterTest(resysHashMap, DistanceMatrix);
		
		// Task_4: 
		System.out.println("<Task_4> start:");
		//ResnickPrediction(resysHashMap, DistanceMatrix);
		System.out.println("RecommendSys Run over!");
	}

	/*******************************************************************
	 * Task_1:	Main Body.
	 *******************************************************************/
	private void MakeStatistic(ResysHashMap resysHashMap, Ratings_Count ratings_Count){
		DataStatistic dataStatistic = new DataStatistic();
		dataStatistic.make_stata(resysHashMap, ratings_Count);
		int user_id = 25;	// [1, 943]
		int item_id = 5;	// [1, 1682]
		float predict_rating = dataStatistic.mean_item_rating(resysHashMap, user_id, item_id);
		if(predict_rating <= 0)
			System.out.println("\tCannot predict this user-item pair!");
		else
			System.out.println("\tPrediction(user:" + Integer.toString(user_id) + ", item:" + 
								Integer.toString(item_id) + ")=" + String.format("%.2f", predict_rating));
		dataStatistic.cal_coverage(resysHashMap);
	}
	
	
	/*******************************************************************
	 * Task_3:	Collaborative Filtering 1, Distance-Based Similarities.
	 *******************************************************************/
	private void DiatanceSimCollaborativeFilterTest(ResysHashMap resysHashMap, float[][] DistanceMatrix){
		LoopControl loopControl = new LoopControl();
		loopControl.LOOLoopTest(resysHashMap, "Distance", DistanceMatrix);
		loopControl.LOOLoopTest(resysHashMap, "Size", DistanceMatrix);
		//LOOLoopTest(resysHashMap, "Overlap", DistanceMatrix);
	}
	
	
	/**************************************
	 * Task_4:	Resnick's Prediction
	 **************************************/
	private void ResnickPrediction(ResysHashMap resysHashMap, float[][] DistanceMatrix){
		LoopControl loopControl = new LoopControl();
		loopControl.LOOLoopTest_Resnick(resysHashMap, "Distance", DistanceMatrix);
		loopControl.LOOLoopTest_Resnick(resysHashMap, "Size", DistanceMatrix);
	}
	
}


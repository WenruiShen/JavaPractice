package RecommendSys_ACF_test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class LoopControl {

	Integer N_plusStepCtrl(Integer n){
		if(n < 10)
			n += 1;
		else if(n < 20)
			n += 5;
		else if(n < 100)
			n += 10;
		else if(n < 200)
			n += 20;
		else
			n += 50;
		return n;
	}
	
	int start_N = 5;
	int stop_N = 20;
	
	/**************************************
	 * Task_2:  Leave-One-Out Testing Loop.
	 **************************************/
	void LOOLoopTest(ResysHashMap resysHashMap){
		Display display = new Display();
		HashMap<Integer, HashMap<Integer, Integer>> userMap = resysHashMap.get_userMap();
		
		String log_file_name = "task_2_predictions.csv";
        //set_log_out_to_file(log_file_name);
		display.init_log_file(log_file_name);
		display.change_log_file(log_file_name);
		System.out.println(String.format("\tUser_id\tItem_id\tActual_rating\tpredict_rating\tRMSE"));  
        Integer users_count = userMap.size();
        // Count all failed predict user-item-pair for average RMES coverage.
        Integer total_failed_predict_count = 0;
        // Create a array to store all user-rows' RMES.
        double[] Users_RMES_Array = new double[users_count];
        long startTime_ms = System.currentTimeMillis();
		Set<Integer> user_set = userMap.keySet(); 
		// Iterate each user, and calculate each user's root-mean-squared-error(RMSE).
		int user_array_id = 0;
        for (Iterator<Integer> user_iterator = user_set.iterator(); user_iterator.hasNext();) {
        	Integer User_id = user_iterator.next();
        	HashMap<Integer, Integer> itemMap = userMap.get(User_id);
        	Integer items_count = itemMap.size();
        	// Create a array to store temple predict rating for this user-row. 
        	float[] user_item_pair_predrat_array = new float[items_count];
        	
        	Set<Integer> item_set = itemMap.keySet();
            // Iterate each item in the certain user.
            int item_array_id = 0;
            int failed_predict_count = 0;
            for (Iterator<Integer> item_iterator = item_set.iterator(); item_iterator.hasNext();) {
            	Integer Item_id = item_iterator.next();
            	Integer Rating = itemMap.get(Item_id);
            	float predict_rating = new DataStatistic().mean_item_rating(resysHashMap, User_id, Item_id);
            	double user_item_pairRMES = 0;
            	if(predict_rating <= 0){
            		// Escape itself or failed to predict this user-item-pair.
            		//System.out.println(String.format("Failed to predict: (User_id:%d, \tItem_id:%d)", User_id, Item_id));
            		user_item_pair_predrat_array[item_array_id++] = -1;
            		failed_predict_count++;
            		continue;
            	}
            	else
            	{
            		user_item_pair_predrat_array[item_array_id++] = predict_rating;
            		user_item_pairRMES = Math.pow((predict_rating - Rating), 2);
            		Users_RMES_Array[user_array_id] += user_item_pairRMES;
            		System.out.println(String.format("\t%d,\t%d,\t%d,\t%f\t%f", 
										User_id, Item_id, Rating, predict_rating, Math.sqrt(user_item_pairRMES)));        		
            	}
            }
            
            // Check whether this user's items are all cannot be predicted.
            if(items_count == failed_predict_count){
            	System.out.println(String.format("--XXX---Failed to predict this user:%d ", User_id));
            	//failed_predict_user_count++;
            	Users_RMES_Array[user_array_id] = -1;	// Mark this user-row cannot be predicted.
            }
            else{
            	 // Single RMES for this user-row.
            	Users_RMES_Array[user_array_id] /= (item_array_id - failed_predict_count);
            	Users_RMES_Array[user_array_id] = Math.sqrt(Users_RMES_Array[user_array_id]);
                System.out.println(String.format("--------User_id:%d, \tfailed_predict:%d, \tRMES:%f", 
    												User_id, failed_predict_count, Users_RMES_Array[user_array_id]));
            }
           
            user_array_id++;
            total_failed_predict_count += failed_predict_count;
        }
        
        // Calculate the average RMES for all users.
        double average_RMES = 0;
        Integer RMES_count = 0;
        for(double user_RMSE : Users_RMES_Array)
        {
        	if(user_RMSE >= 0){
        		average_RMES += user_RMSE;
        		RMES_count++;
        	}
        }
        average_RMES /= RMES_count;
        float overall_coverage = (float) RMES_count / users_count;
        
        long stopTime_ms = System.currentTimeMillis();
        double totalRunTome_ms = (double)(stopTime_ms - startTime_ms);
        double averagrunTime_ms = totalRunTome_ms / users_count;
        
        System.out.println(String.format("--------User_num:%d, \tSuccesed_pre_count:%d, \toverall_coverage:%f",
        											users_count, RMES_count, overall_coverage));
        System.out.println(String.format("--------Average RMES:%f", average_RMES));
        System.out.println(String.format("--------Total LOO_Test Run Time:%.4f ms", totalRunTome_ms));
        System.out.println(String.format("--------Average Run Time:%.4f ms", averagrunTime_ms));
        display.set_log_out_to_console();

        // Print in console.
        System.out.println(String.format("\tUser_num:%d, \tSuccesed_pre_count:%d, \toverall_coverage:%f",
													users_count, RMES_count, overall_coverage));
        System.out.println(String.format("\tAverage RMES:%f", average_RMES));
        System.out.println(String.format("\tTotal LOO_Test Run Time:%.4f ms", totalRunTome_ms));
        System.out.println(String.format("\tAverage Run Time:%.4f ms", averagrunTime_ms));
	}
	
	
	/**********************************************************************
	 *  Task_3:  Over Loading the function of Leave-One-Out Testing Loop. 
	 **********************************************************************/
	FinalResults LOOLoopTest(ResysHashMap resysHashMap, String NeighbourSelectMethod, float[][][] DistanceMatrix, Ratings_Count ratings_Count){
		long predict_startTime_ms = System.currentTimeMillis();
		System.out.println("LOOLoopTest: \"" + NeighbourSelectMethod + "\" Start!");
		
		Display display = new Display();
		HashMap<Integer, HashMap<Integer, Integer>> userMap = resysHashMap.get_userMap();
		HashMap<Integer, HashMap<Integer, Integer>> itemListMap = resysHashMap.get_itemListMap();
		
		FinalResults finalResults = finalResultsInit(start_N, stop_N, userMap, itemListMap);
		
		finalResults.NeighbourSelectApproach = NeighbourSelectMethod;
		finalResults.n_evaluationMap = new HashMap<Integer, N_Evaluation>();								//<Integer n, N_Evaluation>
		finalResults.UsersPredictResultsMap = new HashMap<Integer, HashMap<Integer, UserNPredictResult>>();	//<Integer User_id, HashMap<Integer n, SingleUserPredictResult>>
		
		// Optimization: Prepare all neighbours' closing correlation ahead into a 2-D matrix.
		MatrixPrepare matrixPrepare = new MatrixPrepare();
		float[][][] neighbourIDList = matrixPrepare.NeighbourIDInit(userMap, NeighbourSelectMethod, DistanceMatrix);
		display.DisplayResults(neighbourIDList, "Task3_PrepareNeighbourMatrix_"+ NeighbourSelectMethod + ".txt");
		
		String predictResultLogFileHead = "task_3_predictions_";
		display.init_log_file("Task3_SortedNeighbourIDList_"+ NeighbourSelectMethod + ".txt");
		for (Integer n = finalResults.n_start; n <= finalResults.n_stop;)
		{
			display.init_log_file(predictResultLogFileHead + NeighbourSelectMethod + "_n" + Integer.toString(n) + ".csv");
			display.change_log_file(predictResultLogFileHead + NeighbourSelectMethod + "_n" + Integer.toString(n) + ".csv");
			System.out.println("User_id\titems_num\tPredict_Rating\tActualRating\tRMES");
			n = N_plusStepCtrl(n);
		}
		display.set_log_out_to_console();
		
		int processBarCtrl = 0;
		int processBarCount = userMap.size() / 100;
		int processBarNum = 0;
		
		// Iterate each user and make predictions. 	(Loop-level:1, O(x)=943)
		Set<Integer> user_set = userMap.keySet(); 
        for (Iterator<Integer> user_iterator = user_set.iterator(); user_iterator.hasNext();) {
        	Integer User_id = user_iterator.next();
        	HashMap<Integer, UserNPredictResult> UserNPredictResultMap = finalResults.UsersPredictResultsMap.get(User_id);
        	if(UserNPredictResultMap == null) {
        		UserNPredictResultMap = new HashMap<Integer, UserNPredictResult>();
        		finalResults.UsersPredictResultsMap.put(User_id, UserNPredictResultMap);
        	}
        	
        	if(processBarCtrl++ >=processBarCount)
        	{
        		display.set_log_out_to_console();
        		if(processBarNum == 0)
        		{
        			System.out.println("Now Processing!");
        			System.out.println(Integer.toString(processBarNum) + "%");
        		}
        		else if(processBarNum % 5 == 0)
        			System.out.println(Integer.toString(processBarNum) + "%");
        		processBarNum++;
        		processBarCtrl = 0;
        	}
        	
        	// Prepare (User_2,item_k) similarity matrix for this User_id.	(Loop-level:2, O(x)=942)
        	float[][] User1_SimMatrix = matrixPrepare.PrepareUserSimMatrix(User_id, userMap, itemListMap, DistanceMatrix);
        	
        	display.change_log_file("Task3_SortedNeighbourIDList_"+ NeighbourSelectMethod + ".txt");
        	List<Map.Entry<Integer, Float>> neighbourIDSortedList = matrixPrepare.NeighbourSelect(User_id, neighbourIDList);
        	
        	// Check first n neighbours from start_n to stop_n.	(Loop-level:2, O(x)=stop_n-start_n)
        	for (Integer n = finalResults.n_start; n <= finalResults.n_stop;){
        		// Predict from n closest neighbours.
        		UserNPredictResult userPredictResult = new Prediction().predictFromNNeighbour(User_id, n, neighbourIDSortedList, 
        																					userMap, User1_SimMatrix, NeighbourSelectMethod, 
        																					predictResultLogFileHead);
        		if(userPredictResult == null) {
        			continue;		// n failed process required, debug, swr ???????????????
        		}
        		if(UserNPredictResultMap.get(n) == null) {
        			UserNPredictResultMap.put(n, userPredictResult);
        		}else{
        			UserNPredictResultMap.replace(n, userPredictResult);
        		}	
        		n = N_plusStepCtrl(n);
        	}
        	finalResults.UsersPredictResultsMap.replace(User_id, UserNPredictResultMap);
        	//set_log_out_to_console();
        	//System.out.println(String.format("\tUser_id:%d", User_id));
        }
        display.set_log_out_to_console();
        long predict_stopTime_ms = System.currentTimeMillis();
        double predict_totalRunTome_ms = (double)(predict_stopTime_ms - predict_startTime_ms);
        finalResults.TotalRunTime = predict_totalRunTome_ms;
        Evaluation evaluation = new Evaluation();
        evaluation.OverallEvaluation(finalResults, NeighbourSelectMethod, "Task3_OverallEvaluation_", ratings_Count);
        System.out.println(String.format("\tLOOLoopTest \"%s\" Over, Total predict Time:%.4f ms, user_num:%d, n_num:%d", 
        									NeighbourSelectMethod, predict_totalRunTome_ms, 
        									finalResults.UsersPredictResultsMap.size(), finalResults.n_num));
        return finalResults;
	}

	
	/**********************************************************************
	 *  Task_4:  Resnick's Prediction Leave-One-Out Testing Loop. 
	 **********************************************************************/
	FinalResults LOOLoopTest_Resnick(ResysHashMap resysHashMap, String NeighbourSelectMethod, float[][][] DistanceMatrix, Ratings_Count ratings_Count){
		long predict_startTime_ms = System.currentTimeMillis();
		System.out.println("LOOLoopTest: \"" + NeighbourSelectMethod + "\" Start!");
		
		Display display = new Display();
		HashMap<Integer, HashMap<Integer, Integer>> userMap = resysHashMap.get_userMap();
		HashMap<Integer, HashMap<Integer, Integer>> itemListMap = resysHashMap.get_itemListMap();
		
		FinalResults finalResults = finalResultsInit(start_N, stop_N, userMap, itemListMap);
		
		finalResults.NeighbourSelectApproach = NeighbourSelectMethod;
		finalResults.n_evaluationMap = new HashMap<Integer, N_Evaluation>();								//<Integer n, N_Evaluation>
		finalResults.UsersPredictResultsMap = new HashMap<Integer, HashMap<Integer, UserNPredictResult>>();	//<Integer User_id, HashMap<Integer n, SingleUserPredictResult>>
		
		// Optimization: Prepare all neighbours' closing correlation ahead into a 2-D matrix.
		MatrixPrepare matrixPrepare = new MatrixPrepare();
		float[][][] neighbourIDList = matrixPrepare.NeighbourIDInit(userMap, NeighbourSelectMethod, DistanceMatrix);
		display.DisplayResults(neighbourIDList, "Task4_PrepareNeighbourMatrix_"+ NeighbourSelectMethod + ".txt");
		
		HashMap<Integer, Float> usesAverageRatingMap = matrixPrepare.UsesAverageRatingInit(userMap);
		
		String predictResultLogFileHead = "task_4_predictions_";
		display.init_log_file("Task4_SortedNeighbourIDList_"+ NeighbourSelectMethod + ".txt");		
		//System.out.println("*Debug:n_start:" + Integer.toString(finalResults.n_start) + ",\tn_stop:" + Integer.toString(finalResults.n_stop));
		for (Integer n = finalResults.n_start; n <= finalResults.n_stop;)
		{
			display.init_log_file(predictResultLogFileHead + NeighbourSelectMethod + "_n" + Integer.toString(n) + ".csv");
			display.change_log_file(predictResultLogFileHead + NeighbourSelectMethod + "_n" + Integer.toString(n) + ".csv");
			System.out.println("User_id\titems_num\tPredict_Rating\tActualRating\tRMES");
			n = N_plusStepCtrl(n);
		}
		display.set_log_out_to_console();
		
		int processBarCtrl = 0;
		int processBarCount = userMap.size() / 100;
		int processBarNum = 0;
		
		// Iterate each user and make predictions. 	(Loop-level:1, O(x)=943)
		Set<Integer> user_set = userMap.keySet(); 
        for (Iterator<Integer> user_iterator = user_set.iterator(); user_iterator.hasNext();) {
        	Integer User_id = user_iterator.next();
        	HashMap<Integer, UserNPredictResult> UserNPredictResultMap = finalResults.UsersPredictResultsMap.get(User_id);
        	if(UserNPredictResultMap == null) {
        		UserNPredictResultMap = new HashMap<Integer, UserNPredictResult>();
        		finalResults.UsersPredictResultsMap.put(User_id, UserNPredictResultMap);
        	}
        	if(processBarCtrl++ >=processBarCount)
        	{
        		display.set_log_out_to_console();
        		if(processBarNum == 0)
        		{
        			System.out.println("Now Processing!");
        			System.out.println(Integer.toString(processBarNum) + "%");
        		}
        		else if(processBarNum % 5 == 0)
        			System.out.println(Integer.toString(processBarNum) + "%");
        		processBarNum++;
        		processBarCtrl = 0;
        	}
        	
        	// Prepare (User_2,item_k) similarity matrix for this User_id.	(Loop-level:2, O(x)=942)
        	float[][] User1_SimMatrix = matrixPrepare.PrepareUserSimMatrix(User_id, userMap, itemListMap, DistanceMatrix);
        	display.change_log_file("Task4_SortedNeighbourIDList_"+ NeighbourSelectMethod + ".txt");
        	List<Map.Entry<Integer, Float>> neighbourIDSortedList = matrixPrepare.NeighbourSelect(User_id, neighbourIDList);
        	
    		// Check first n neighbours from start_n to stop_n.	(Loop-level:2, O(x)=stop_n-start_n)
        	for (Integer n = finalResults.n_start; n <= finalResults.n_stop;){
        		// Predict from n closest neighbours.
        		UserNPredictResult userPredictResult = new Prediction().ResnickpredictFromNNeighbour(User_id, n, neighbourIDSortedList, 
        																								userMap, User1_SimMatrix, 
        																								usesAverageRatingMap, 
        																								NeighbourSelectMethod, 
        																								predictResultLogFileHead);
        		if(userPredictResult == null) {
        			continue;
        		}
        		if(UserNPredictResultMap.get(n) == null) {
        			UserNPredictResultMap.put(n, userPredictResult);
        		}else{
        			UserNPredictResultMap.replace(n, userPredictResult);
        		}
        		n = N_plusStepCtrl(n);
        	}
        	finalResults.UsersPredictResultsMap.replace(User_id, UserNPredictResultMap);
        	//set_log_out_to_console();
        	//System.out.println(String.format("\tUser_id:%d", User_id));
        }	
        display.set_log_out_to_console();
        long predict_stopTime_ms = System.currentTimeMillis();
        double predict_totalRunTome_ms = (double)(predict_stopTime_ms - predict_startTime_ms);
        finalResults.TotalRunTime = predict_totalRunTome_ms;
        Evaluation evaluation = new Evaluation();
        evaluation.OverallEvaluation(finalResults, NeighbourSelectMethod, "Task4_OverallEvaluation_", ratings_Count);
        System.out.println(String.format("\tLOOLoopTest \"%s\" Over, Total predict Time:%.4f ms, user_num:%d, n_num:%d", 
        									NeighbourSelectMethod, predict_totalRunTome_ms, 
        									finalResults.UsersPredictResultsMap.size(), finalResults.n_num));
        return finalResults;
	}
	
	
	FinalResults finalResultsInit(Integer start_n, Integer stop_n, HashMap<Integer, HashMap<Integer, Integer>> userMap, 
											HashMap<Integer, HashMap<Integer, Integer>> itemListMap){
		if(start_n > stop_n)
			return null;
		FinalResults finalResults = new FinalResults();
		finalResults.n_start = start_n;
		finalResults.n_stop = stop_n;
		finalResults.n_num = stop_n + 1 - start_n;
		finalResults.users_num = userMap.size();
		finalResults.items_num = itemListMap.size();		
		return finalResults;
	}
	
	
}

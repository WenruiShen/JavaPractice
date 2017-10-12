package RecommendSys_ACF_test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Prediction {
	
	/*  Task_3: Predict the user's items from the n closest neighbours. */
	UserNPredictResult predictFromNNeighbour(Integer User_id, Integer n, 
										List<Map.Entry<Integer, Float>> neighbourIDSortedList, 
										HashMap<Integer, HashMap<Integer, Integer>> userMap, 
										float[][] DistanceMatrix, String NeighbourSelectMethod, String predictResultLogFileHead){
		long startTime_ms = System.currentTimeMillis();
		Display display = new Display();
		// Used to return predict result.
		UserNPredictResult userPredictResult = new UserNPredictResult();
		// <Integer Item_ID, ItemPredictResult> Used to calculate & store items predicted results.
		HashMap<Integer, ItemPredictResult> itempredictresultsmap = new HashMap<Integer, ItemPredictResult>();
		//userPredictResult.itempredictresultsmap = new HashMap<Integer, ItemPredictResult>();
		HashMap<Integer, Integer> ActualUserItemsMap = userMap.get(User_id);
		if(ActualUserItemsMap == null)
			return null;
		// Find the largest difference among neighbours.
		float difference_max = 16;
		
		display.change_log_file(predictResultLogFileHead+ NeighbourSelectMethod + "_n" + Integer.toString(n) + ".csv");
		// Iterate n closest neighbours. (Loop-level:3, O(x)=n)
		Iterator<Map.Entry<Integer, Float>> neighbour_iterator = neighbourIDSortedList.iterator();
		Integer loopTimes = n;
		for (Integer neighbour_id = 1; neighbour_id <= loopTimes; neighbour_id++){
 
			if(neighbour_iterator.hasNext()){
				Integer user2_id = neighbour_iterator.next().getKey();
				if (user2_id == User_id){
					loopTimes++;
					continue;
				}
				
				float weight_user_2 = 1- (DistanceMatrix[User_id - 1][user2_id - 1] / difference_max) ;
				
				HashMap<Integer, Integer> User_2_ItemsMap = userMap.get(user2_id);
				// Iterate user_2's each items. (Loop-level:4, O(x)<1682)
				Set<Integer> user2_items_set = User_2_ItemsMap.keySet();
				for (Iterator<Integer> item_iterator = user2_items_set.iterator(); item_iterator.hasNext();) {
	            	Integer user2_Item_id = item_iterator.next();
	            	Integer user2_Item_Rating = User_2_ItemsMap.get(user2_Item_id);
	            	ItemPredictResult item_predict_stat = itempredictresultsmap.get(user2_Item_id);
	            	if(item_predict_stat != null){
	            		// This item has already been added into User_Item_Predict_Stat_Map
	            		item_predict_stat.sum_rating += weight_user_2 * user2_Item_Rating;
	            		item_predict_stat.weight_sum += weight_user_2;
	            		item_predict_stat.count++;
	            		itempredictresultsmap.replace(user2_Item_id, item_predict_stat);
	            	}
	            	else{
	            		// Need to add a new item stat into this map.
	            		item_predict_stat = new ItemPredictResult();
	            		item_predict_stat.sum_rating += weight_user_2 * user2_Item_Rating;
	            		item_predict_stat.weight_sum += weight_user_2;
	            		item_predict_stat.count++;
	            		itempredictresultsmap.put(user2_Item_id, item_predict_stat);
	            	}
				}
			}
			else{// No enough neighbours.
				break;
			}
		}
    	
		int UserRMES_count = 0;
		// Iterate each predicted items.	(Loop-level:3, O(x)<1682)
		Set<Integer>  User_Item_Predict_Stat_set = itempredictresultsmap.keySet();
		for (Iterator<Integer> item_iterator = User_Item_Predict_Stat_set.iterator(); item_iterator.hasNext();) {
        	Integer predict_Item_id = item_iterator.next();
        	ItemPredictResult predict_Item_Result = itempredictresultsmap.get(predict_Item_id);
        	// Calculate the predicted rating.
        	if(predict_Item_Result.weight_sum != 0)
        		predict_Item_Result.PredictRating = (float) predict_Item_Result.sum_rating / predict_Item_Result.weight_sum;
    		// Calculate the single RMES for this item pair.
        	Integer actual_rating = ActualUserItemsMap.get(predict_Item_id);
        	if (actual_rating == null)
        	{
        		actual_rating = 0;
        	}
        	else{
        		predict_Item_Result.single_RMES = Math.pow((actual_rating - predict_Item_Result.PredictRating), 2);
            	userPredictResult.UserRMES += predict_Item_Result.single_RMES;
            	UserRMES_count++;
        	}
        	itempredictresultsmap.replace(predict_Item_id, predict_Item_Result);
        	System.out.println(String.format("%d,\t%d,\t%d,\t%.4f,\t%.4f", 
										User_id, predict_Item_id, actual_rating, predict_Item_Result.PredictRating, predict_Item_Result.single_RMES));
		}
		
		if(UserRMES_count != 0)
		{
			userPredictResult.UserRMES /= UserRMES_count;
			userPredictResult.UserRMES = (float)Math.sqrt(userPredictResult.UserRMES);
		}
		else
			userPredictResult.UserRMES = 0;

		long stopTime_ms = System.currentTimeMillis();
	    userPredictResult.UserRunTime = (double)(stopTime_ms - startTime_ms); 
	    //System.out.println(String.format("\tUser_id:%d,\tStepRunTime:%.4f", User_id, (double)(System.currentTimeMillis() - startTime_ms)));
	    userPredictResult.predicetItemsCount =  itempredictresultsmap.size();
	    
	    /*System.out.println(String.format("User_id:%d,\titems_num:%d,\tn:%d,\tUserRunTime:%f,\tUserRMES:%f,\tResults: %s", 
				User_id, itempredictresultsmap.size(), n, userPredictResult.UserRunTime, userPredictResult.UserRMES, 
				itempredictresultsmap.toString()));*/	    
		return userPredictResult;
	}
	
	
	/*  Task_4: Resnick's Predict the user's items from the n closest neighbours. */
	UserNPredictResult ResnickpredictFromNNeighbour(Integer User_id, Integer n, 
										List<Map.Entry<Integer, Float>> neighbourIDSortedList, 
										HashMap<Integer, HashMap<Integer, Integer>> userMap, float[][] DistanceMatrix, 
										HashMap<Integer, Float> usesAverageRatingMap, String NeighbourSelectMethod, 
										String predictResultLogFileHead){
		long startTime_ms = System.currentTimeMillis();
		Display display = new Display();
		// Used to return predict result.
		UserNPredictResult userPredictResult = new UserNPredictResult();
		// <Integer Item_ID, ItemPredictResult> Used to calculate & store items predicted results.
		HashMap<Integer, ItemPredictResult> itempredictresultsmap = new HashMap<Integer, ItemPredictResult>();
		//userPredictResult.itempredictresultsmap = new HashMap<Integer, ItemPredictResult>();
		HashMap<Integer, Integer> ActualUserItemsMap = userMap.get(User_id);
		if(ActualUserItemsMap == null)
			return null;
		Float user1_AverageRating = usesAverageRatingMap.get(User_id);
		if(user1_AverageRating == null)
			return null;
		// Find the largest difference among neighbours.
		float difference_max = 16;
		
		display.change_log_file(predictResultLogFileHead+ NeighbourSelectMethod + "_n" + Integer.toString(n) + ".csv");
		// Iterate n closest neighbours. (Loop-level:3, O(x)=n)
		Iterator<Map.Entry<Integer, Float>> neighbour_iterator = neighbourIDSortedList.iterator();
		Integer loopTimes = n;
		for (Integer neighbour_id = 1; neighbour_id <= loopTimes; neighbour_id++){
 
			if(neighbour_iterator.hasNext()){
				Integer user2_id = neighbour_iterator.next().getKey();
				if (user2_id == User_id){
					loopTimes++;
					continue;
				}
				
				Float user2_AverageRating = usesAverageRatingMap.get(user2_id);
				if(user1_AverageRating == null)
					continue;
				
				float weight_user_2 = 1- (DistanceMatrix[User_id - 1][user2_id - 1] / difference_max) ;
				
				HashMap<Integer, Integer> User_2_ItemsMap = userMap.get(user2_id);
				// Iterate user_2's each items. (Loop-level:4, O(x)<1682)
				Set<Integer> user2_items_set = User_2_ItemsMap.keySet();
				for (Iterator<Integer> item_iterator = user2_items_set.iterator(); item_iterator.hasNext();) {
	            	Integer user2_Item_id = item_iterator.next();
	            	Float user2_Item_Rating = (float)User_2_ItemsMap.get(user2_Item_id);
	            	user2_Item_Rating -= user2_AverageRating;
	            	ItemPredictResult item_predict_stat = itempredictresultsmap.get(user2_Item_id);
	            	if(item_predict_stat != null){
	            		// This item has already been added into User_Item_Predict_Stat_Map
	            		item_predict_stat.sum_rating += weight_user_2 * user2_Item_Rating;
	            		item_predict_stat.weight_sum += weight_user_2;
	            		item_predict_stat.count++;
	            		itempredictresultsmap.replace(user2_Item_id, item_predict_stat);
	            	}
	            	else{
	            		// Need to add a new item stat into this map.
	            		item_predict_stat = new ItemPredictResult();
	            		item_predict_stat.sum_rating += weight_user_2 * user2_Item_Rating;
	            		item_predict_stat.weight_sum += weight_user_2;
	            		item_predict_stat.count++;
	            		itempredictresultsmap.put(user2_Item_id, item_predict_stat);
	            	}
				}
			}
			else{// No enough neighbours.
				break;
			}
		}
    	
		int UserRMES_count = 0;
		// Iterate each predicted items.	(Loop-level:3, O(x)<1682)
		Set<Integer>  User_Item_Predict_Stat_set = itempredictresultsmap.keySet();
		for (Iterator<Integer> item_iterator = User_Item_Predict_Stat_set.iterator(); item_iterator.hasNext();) {
        	Integer predict_Item_id = item_iterator.next();
        	ItemPredictResult predict_Item_Result = itempredictresultsmap.get(predict_Item_id);
        	// Calculate the predicted rating.
        	if(predict_Item_Result.weight_sum != 0)
        		predict_Item_Result.PredictRating = (float) predict_Item_Result.sum_rating / predict_Item_Result.weight_sum + user1_AverageRating;
    		// Calculate the single RMES for this item pair.
        	Integer actual_rating = ActualUserItemsMap.get(predict_Item_id);
        	if (actual_rating == null)
        	{
        		actual_rating = 0;
        	}
        	else{
        		predict_Item_Result.single_RMES = Math.pow((actual_rating - predict_Item_Result.PredictRating), 2);
            	userPredictResult.UserRMES += predict_Item_Result.single_RMES;
            	UserRMES_count++;
        	}
        	itempredictresultsmap.replace(predict_Item_id, predict_Item_Result);
        	System.out.println(String.format("%d,\t%d,\t%d,\t%.4f,\t%.4f", 
        									User_id, predict_Item_id, actual_rating, predict_Item_Result.PredictRating, predict_Item_Result.single_RMES));
		}
		
		if(UserRMES_count != 0)
		{
			userPredictResult.UserRMES /= UserRMES_count;
			userPredictResult.UserRMES = (float)Math.sqrt(userPredictResult.UserRMES);
		}
		else
			userPredictResult.UserRMES = 0;

		long stopTime_ms = System.currentTimeMillis();
	    userPredictResult.UserRunTime = (double)(stopTime_ms - startTime_ms); 
	    //System.out.println(String.format("\tUser_id:%d,\tStepRunTime:%.4f", User_id, (double)(System.currentTimeMillis() - startTime_ms)));
	    userPredictResult.predicetItemsCount =  itempredictresultsmap.size();
	    
	    /*System.out.println(String.format("User_id:%d,\titems_num:%d,\tn:%d,\tUserRunTime:%f,\tUserRMES:%f,\tResults: %s", 
				User_id, itempredictresultsmap.size(), n, userPredictResult.UserRunTime, userPredictResult.UserRMES, 
				itempredictresultsmap.toString()));*/
	    return userPredictResult;
	}
	
}

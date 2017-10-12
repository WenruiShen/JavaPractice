package RecommendSys_ACF_test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

class MatrixPrepare {
	
	/* Task_3: Prepare the Distance Matrix in HashMap form, in order to save time.
	 * 			size: float[943][943][2]; 
	 * 			float[i][j][0] = Sum((rating_i - rating_j)^2).
	 * 			float[i][j][1] = num_of_overlapping_items.
	 * */
	float[][][] PrepareDistanceMatrix(HashMap<Integer, HashMap<Integer, Integer>> userMap, 
								HashMap<Integer, HashMap<Integer, Integer>> itemListMap){
		/* User_ID:	1,	2,		3,		4,	......,	n-2,	n-1,	n.
		 * 		1 :--	(1,2)	(1,3)	(1,4)	...	(1,n-2)	(1,n-1)	(1,n)
		 * 		2 :--	--		(2,3)	(2,4)	...	(2,n-2)	(2,n-1)	(2,n)
		 * 		3 :--	--		--		(3,4)	...	(3,n-2)	(3,n-1)	(3,n)
		 * 		4 :--	--		--		--		...	(4,n-2)	(4,n-1)	(4,n)
		 * 	......:
		 * 	  n-2 :--	--		--		--		...	--	   (n-2,n-1)(n-2,n)
		 * 	  n-1 :--	--		--		--		...	--		--		(n-1,n)
		 * 	  n	  :--	--		--		--		...	--		--		 --
		 */
		float[][][] DistanceMatrix = new float[userMap.size()][userMap.size()][2];
		long startTime_ms = System.currentTimeMillis();
		int total_user_count = userMap.size();
		for(int user_1 = 1; user_1 <= total_user_count - 1; user_1++){
			HashMap<Integer, Integer> user_1_itemMap = userMap.get(user_1);
			if(user_1_itemMap == null)
			{
				System.out.println("Failed to get user_1_itemMap!");
				continue;
			}
			
			for(int user_2 = user_1 + 1; user_2 <= total_user_count; user_2++){
				int overlapping_items_count = 0;
				// Calculate the distance between user_1 and user_2.
				HashMap<Integer, Integer> user_2_itemMap = userMap.get(user_2);
				if(user_2_itemMap == null)
				{
					DistanceMatrix[user_1 - 1][user_2 - 1][0] = -1;
					System.out.println("Failed to get user_2_itemMap!");
					continue;
				}				
				// Calculate the distance in dimensions of user_2's and user_1's overlapping items.
				Set<Integer> user_2_items_set = user_2_itemMap.keySet();
				for (Iterator<Integer> user_2_item_iterator = user_2_items_set.iterator(); user_2_item_iterator.hasNext();) {
	            	Integer user2_Item_id = user_2_item_iterator.next();
	            	Integer user2_Rating = user_2_itemMap.get(user2_Item_id);
	            	Integer user1_Rating = user_1_itemMap.get(user2_Item_id);
	            	if(user1_Rating == null)
	            	{	// Escape.
	            		continue;
	            	}
	            	overlapping_items_count++;
	            	float SingleDistanceInItemDimension = (float)Math.pow(user1_Rating - user2_Rating, 2);
					DistanceMatrix[user_1 - 1][user_2 - 1][0] += SingleDistanceInItemDimension;
				}
				
				//DistanceMatrix[user_1 - 1][user_2 - 1] = (float)Math.sqrt(DistanceMatrix[user_1 - 1][user_2 - 1]);
				DistanceMatrix[user_1 - 1][user_2 - 1][1] = overlapping_items_count; 
				// Fill the another symmetrical half empty triangle distances matrix.
				DistanceMatrix[user_2 - 1][user_1 - 1] = DistanceMatrix[user_1 - 1][user_2 - 1];
			}
		}
		long stopTime_ms = System.currentTimeMillis();
        double totalRunTome_ms = (double)(stopTime_ms - startTime_ms);
        System.out.println(String.format("\tDistance Matrix Prepare Over, Run Time:%.4f ms", totalRunTome_ms));
        return DistanceMatrix;
	}
	
	
	/* Task_3:	Prepare (User_2,item_k) similarity matrix for single User_1.
	 */
	float[][] PrepareUserSimMatrix(Integer User_id, HashMap<Integer, HashMap<Integer, Integer>> userMap, 
									HashMap<Integer, HashMap<Integer, Integer>> itemListMap, 
									float[][][] DistanceMatrix) 
	{
		/* Item_id:	1,	2,		3,		4,	......,	n-2,	n-1,	n.
		 * User_ID
		 * 		1 :--	(1,2)	(1,3)	(1,4)	...	(1,n-2)	(1,n-1)	(1,n)
		 * 		2 :--	--		(2,3)	(2,4)	...	(2,n-2)	(2,n-1)	(2,n)
		 * 		3 :--	--		--		(3,4)	...	(3,n-2)	(3,n-1)	(3,n)
		 * 		4 :--	--		--		--		...	(4,n-2)	(4,n-1)	(4,n)
		 * 	......:
		 * 	  n-2 :--	--		--		--		...	--	   (n-2,n-1)(n-2,n)
		 * 	  n-1 :--	--		--		--		...	--		--		(n-1,n)
		 * 	  n	  :--	--		--		--		...	--		--		 --
		 */
		
		float[][] User1_SimMatrix = new float[userMap.size()][itemListMap.size()];
		//long startTime_ms = System.currentTimeMillis();
		int total_user_count = userMap.size();
		HashMap<Integer, Integer> user_1_itemMap = userMap.get(User_id);
		if (user_1_itemMap == null) {
			System.out.println("Failed to get user_1_itemMap!");
			return null;
		}

		for (int user_2 = 1; user_2 <= total_user_count; user_2++) {
			if(User_id == user_2)
				continue;
			// Calculate similarities between user_1 and user_2.
			HashMap<Integer, Integer> user_2_itemMap = userMap.get(user_2);
			if (user_2_itemMap == null) {
				continue;
			}
			Set<Integer> user_1_items_set = user_1_itemMap.keySet();
			for (Iterator<Integer> user_1_item_iterator = user_1_items_set.iterator(); user_1_item_iterator.hasNext();) {
				Integer user1_Item_id = user_1_item_iterator.next();
				Integer user1_Rating = user_1_itemMap.get(user1_Item_id);
				Integer user2_Rating = user_2_itemMap.get(user1_Item_id);
				if (user2_Rating == null) {
					// This item_k not overlap in User_2.
					if(DistanceMatrix[User_id - 1][user_2 - 1][1] > 0)
					{
						User1_SimMatrix[user_2 - 1][user1_Item_id - 1] = DistanceMatrix[User_id - 1][user_2 - 1][0] / 
								DistanceMatrix[User_id - 1][user_2 - 1][1]; 
					}else{
						User1_SimMatrix[user_2 - 1][user1_Item_id - 1] = 0;
					}
					
				} else {	
					// This item_k overlap in User_2.
					if(DistanceMatrix[User_id - 1][user_2 - 1][1] > 1)
					{
						float tempDiffSim = (float) Math.pow(user1_Rating - user2_Rating, 2);
						User1_SimMatrix[user_2 - 1][user1_Item_id - 1] = 
										( DistanceMatrix[User_id - 1][user_2 - 1][0] - tempDiffSim ) / 
										( DistanceMatrix[User_id - 1][user_2 - 1][1] - 1);
					}else{
						User1_SimMatrix[user_2 - 1][user1_Item_id - 1] = 0;
					}
				}
			}
		}
			
		/*long stopTime_ms = System.currentTimeMillis();
        double totalRunTome_ms = (double)(stopTime_ms - startTime_ms);
        System.out.println(String.format("\tSimilarity Matrix Prepare Over, Run Time:%.4f ms", totalRunTome_ms));*/
		return User1_SimMatrix;
	}
	
	
	/* return form: float[userMap.size()][userMap.size()][2];
	 * float[i][j][0] : useful value;
	 * float[i][j][1] : redundant value;
	 */
	float[][][] NeighbourIDInit(HashMap<Integer, HashMap<Integer, Integer>> userMap, 
												String NeighbourSelectMethod, float[][][] DistanceMatrix){
		long startTime_ms = System.currentTimeMillis();
		float[][][] NeighbourIDInit = null;
		
		switch (NeighbourSelectMethod) {
		case "Size":
			System.out.println("\tUse \"Size\" as neighbour selecting method.");
			NeighbourIDInit = new float[userMap.size()][userMap.size()][2];
			int total_user_count = userMap.size();
			for(int user_1 = 1; user_1 <= total_user_count - 1; user_1++){
				HashMap<Integer, Integer> user_1_itemMap = userMap.get(user_1);
				if(user_1_itemMap == null)
				{
					System.out.println("Failed to get user_1_itemMap!");
					continue;
				}
				Integer user_1_item_num = user_1_itemMap.size();
				for(int user_2 = user_1 + 1; user_2 <= total_user_count; user_2++){
					// Calculate the size-difference between user_1 and user_2.
					HashMap<Integer, Integer> user_2_itemMap = userMap.get(user_2);
					if(user_2_itemMap == null)
					{
						NeighbourIDInit[user_1 - 1][user_2 - 1][0] = -1;
						System.out.println("Failed to get user_2_itemMap!");
						continue;
					}
					Integer user_2_item_num = user_2_itemMap.size();
					NeighbourIDInit[user_1 - 1][user_2 - 1][0] = Math.abs(user_1_item_num - user_2_item_num);
					// Fill the another symmetrical half empty triangle distances matrix.
					NeighbourIDInit[user_2 - 1][user_1 - 1] = NeighbourIDInit[user_1 - 1][user_2 - 1];	
				}
			}
			
			break;
		case "Distance":
			System.out.println("\tUse \"Distance\" as neighbour selecting method.");
			NeighbourIDInit = DistanceMatrix;
			break;
		case "Overlap":
			System.out.println("\tUse \"Overlap\" as neighbour selecting method.");
			
			break;
		default:
			System.out.println("Error: Invallied NeighbourSelectMethod:" + NeighbourSelectMethod);
			return null;
		}
		
		long stopTime_ms = System.currentTimeMillis();
        double totalRunTome_ms = (double)(stopTime_ms - startTime_ms);
        System.out.println(String.format("\tInit Neighbour Matrix Prepare Over, Run Time:%.4f ms", totalRunTome_ms));
		return NeighbourIDInit;
	}
	

	List<Map.Entry<Integer, Float>> NeighbourSelect(Integer User_id, float[][][] neighbourIDList){
		List<Map.Entry<Integer, Float>> neighbourIDSelectedList = new ArrayList<Entry<Integer, Float>>();
		if(User_id > neighbourIDList.length){
			System.out.println("\tERROR: NeighbourSelect wrong User_id!");
			return null;
		}
		Integer neighbour_id = new Integer(1);
		for(; neighbour_id <= neighbourIDList[User_id - 1].length; neighbour_id++){
			float neighbour_diff_value;
			if(!User_id.equals(neighbour_id)){
				neighbour_diff_value = neighbourIDList[User_id - 1][neighbour_id - 1][0];	
				Map.Entry<Integer,Float> entry = new AbstractMap.SimpleEntry<Integer, Float>(neighbour_id, neighbour_diff_value);
				neighbourIDSelectedList.add(entry);
			}
			else{
				//System.out.println(String.format("\tUser_id:%d Escape neighbour_id:%d", User_id, neighbour_id));
			}
		}
		Collections.sort(neighbourIDSelectedList, new Comparator<Map.Entry<Integer, Float>>() {
			@Override
			public int compare(Map.Entry<Integer, Float> o1, Map.Entry<Integer, Float> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		System.out.println("User_id:" + Integer.toString(User_id) + " Neighbour Sorted List: " + neighbourIDSelectedList.toString());
		return neighbourIDSelectedList;
	}
	
	
	/* Task_4: Resnick's Prediction Preparation. */
	HashMap<Integer, Float> UsesAverageRatingInit(HashMap<Integer, HashMap<Integer, Integer>> userMap){
		HashMap<Integer, Float> UsesAverageRatingMap = new HashMap<Integer, Float>();
		Set<Integer> user_set = userMap.keySet(); 
        for (Iterator<Integer> user_iterator = user_set.iterator(); user_iterator.hasNext();) {
        	Integer User_id = user_iterator.next();
        	HashMap<Integer, Integer> user_itemMap = userMap.get(User_id);
        	if(user_itemMap == null)
        		continue;
        	Float averageRting = UsesAverageRatingMap.get(User_id);
        	if(averageRting == null)
        	{
        		averageRting = (float) 0;
        		UsesAverageRatingMap.put(User_id, averageRting);
        	}
        	
        	Set<Integer> items_set = user_itemMap.keySet();
			for (Iterator<Integer> item_iterator = items_set.iterator(); item_iterator.hasNext();) {
            	Integer Item_id = item_iterator.next();
            	averageRting += user_itemMap.get(Item_id);
			}
			averageRting /= user_itemMap.size();
			UsesAverageRatingMap.replace(User_id, averageRting);
        }
		
		return UsesAverageRatingMap;
	}
	
}

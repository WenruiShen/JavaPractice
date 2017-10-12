package RecommendSys_ACF_test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

class DataStatistic {
	
	void total_rating_count(Ratings_Count ratings_Count){
		ratings_Count.total_count = ratings_Count.one_star_count + 
									ratings_Count.two_star_count +
									ratings_Count.three_star_count + 
									ratings_Count.four_star_count + 
									ratings_Count.five_star_count; 
	}
	
	/****************************
	 * Task_1: Make statistics. 
	 ****************************/
	void make_stata(ResysHashMap resysHashMap, Ratings_Count ratings_Count){
		System.out.println("\tNow make statistics:");
		
		HashMap<Integer, HashMap<Integer, Integer>> userMap = resysHashMap.get_userMap();
		HashMap<Integer, HashMap<Integer, Integer>> itemListMap = resysHashMap.get_itemListMap();
		HashMap<Integer, User_Stata> userStataMap = resysHashMap.get_userStataMap();
		HashMap<Integer, Item_Stata> itemStataMap = resysHashMap.get_itemStataMap();
		
		// 1.Total number of users, items, ratings.
		int sum_users = userMap.size();
		System.out.println("\tTotal number of users: " + Integer.toString(sum_users));
		int sum_items = itemListMap.size();
		System.out.println("\tTotal number of items: " + Integer.toString(sum_items));
		// Calculate the mean rating for each item.
		//item_mean_rating_cal(itemListMap);	
		total_rating_count(ratings_Count);
		System.out.println("\tTotal number of ratings: " + Integer.toString(ratings_Count.total_count));
		
		// 2.Calculate a ratings density metric.
		float ratings_density = (float)ratings_Count.total_count / (sum_users * sum_items);
		System.out.println(String.format("\tRatings density metric: %f", ratings_density));
		// 3.Mean,median,standard deviation, max, min ratings per user.
		make_stata_per_user(userMap, userStataMap);
		// 4.Mean,median,standard deviation, max, min ratings per item.
		make_stata_per_item(itemListMap, itemStataMap);
		// 5.Total number of ratings for each of the 5 ratings classes, 
		//    that is how many 1-stars, 2-stars, ..., 5-stars ratings are there.
		System.out.println("\t1 Star:" + Integer.toString(ratings_Count.one_star_count));
		System.out.println("\t2 Star:" + Integer.toString(ratings_Count.two_star_count));
		System.out.println("\t3 Star:" + Integer.toString(ratings_Count.three_star_count));
		System.out.println("\t4 Star:" + Integer.toString(ratings_Count.four_star_count));
		System.out.println("\t5 Star:" + Integer.toString(ratings_Count.five_star_count));
	}
	
	/* Calculate mean,median,standard deviation, max, min ratings per user. */
	void make_stata_per_user(HashMap<Integer, HashMap<Integer, Integer>> userMap, 
										HashMap<Integer, User_Stata> userStataMap){
		String log_file_name = "task1_test_log_stata_per_user.txt";
        //set_log_out_to_file(log_file_name);
		Display display = new Display(); 
		display.init_log_file(log_file_name);
		display.change_log_file(log_file_name);
		Set<Integer> user_set = userMap.keySet(); 
		// Iterate each user, and calculate each user's data.
        for (Iterator<Integer> user_iterator = user_set.iterator(); user_iterator.hasNext();) {  
        	Integer User_id = user_iterator.next();
        	User_Stata user_stata = userStataMap.get(User_id);
        	if(user_stata == null)
    		{
        		user_stata = new User_Stata();
        		userStataMap.put(User_id, user_stata);
    		}        	
        	
        	HashMap<Integer, Integer> itemMap = userMap.get(User_id);
            user_stata.items_count = itemMap.size();
            Integer[] items_ratings_array = new Integer[user_stata.items_count];
            Set<Integer> item_set = itemMap.keySet();
            // Iterate each item in the certain user.
            int array_id = 0;
            for (Iterator<Integer> item_iterator = item_set.iterator(); item_iterator.hasNext();) {
            	Integer Item_id = item_iterator.next();
            	Integer Rating = itemMap.get(Item_id);
            	items_ratings_array[array_id++] = Rating;
            	// Calculate max, min ratings per user.
            	user_stata.sum_rating += Rating;
            	if(user_stata.max_rating < Rating || user_stata.max_rating == 0)
            		user_stata.max_rating = Rating;
    			if(user_stata.min_rating > Rating || user_stata.min_rating == 0)
    				user_stata.min_rating = Rating;
            }
            // Calculate mean ratings per user.
            user_stata.mean_rating = (float)user_stata.sum_rating / user_stata.items_count;
            // Calculate median ratings per user.
            Arrays.sort(items_ratings_array);
            int middle_id = user_stata.items_count / 2;
            if(user_stata.items_count % 2 == 1)
            	user_stata.median_rating = items_ratings_array[middle_id];
            else
            	user_stata.median_rating = (items_ratings_array[middle_id - 1] + items_ratings_array[middle_id]) / 2;
            // Calculate standard deviation ratings per user.
            for(Integer i : items_ratings_array)
            	user_stata.std_deviation += Math.pow(i - user_stata.mean_rating, 2);
            user_stata.std_deviation /= user_stata.items_count;
            user_stata.std_deviation = Math.sqrt(user_stata.std_deviation);
            userStataMap.replace(User_id, user_stata);
            
            System.out.println("User_ID:" + Integer.toString(User_id) + 
            						"\tItem_count:" + Integer.toString(user_stata.items_count));
	    	System.out.println(String.format("\t\tmean:%.2f, median:%.2f, max:%d, min:%d, std_dev:%f", 
	    												user_stata.mean_rating, user_stata.median_rating, 
	    												user_stata.max_rating, user_stata.min_rating, user_stata.std_deviation));
            System.out.println("\t\t" + Arrays.asList(items_ratings_array));
        }
        display.set_log_out_to_console();
        System.out.println("\tmake_stata_per_user over, pls check results in " + log_file_name);

	}
	
	/* Calculate mean,median,standard deviation, max, min ratings per item. */
	void make_stata_per_item(HashMap<Integer, HashMap<Integer, Integer>> itemListMap, 
										HashMap<Integer, Item_Stata> itemStataMap){
        String log_file_name = "task1_test_log_stata_per_item.txt";
        //set_log_out_to_file(log_file_name);
        Display display = new Display();
        display.init_log_file(log_file_name);
        display.change_log_file(log_file_name);
		Set<Integer> item_set = itemListMap.keySet(); 
		// Iterate each item, and calculate each item's data.
		for (Iterator<Integer> item_iterator = item_set.iterator(); item_iterator.hasNext();) {
			Integer item_id = item_iterator.next();
			Item_Stata item_stata = itemStataMap.get(item_id);
			if(item_stata == null)
    		{
				item_stata =  new Item_Stata();
				itemStataMap.put(item_id, item_stata);
    		}
			HashMap<Integer, Integer> userinitemMap = itemListMap.get(item_id);
			item_stata.user_count = userinitemMap.size();
            Integer[] users_ratings_array = new Integer[item_stata.user_count];
            
            Set<Integer> user_set = userinitemMap.keySet();
            // Iterate each user in the certain item.
            int array_id = 0;
            for (Iterator<Integer> user_iterator = user_set.iterator(); user_iterator.hasNext();) {
            	Integer user_id = user_iterator.next();
            	Integer Rating = userinitemMap.get(user_id);
            	users_ratings_array[array_id++] = Rating;
            	// Calculate max, min ratings per item.
            	item_stata.sum_rating += Rating;
            	if(item_stata.max_rating < Rating || item_stata.max_rating == 0)
            		item_stata.max_rating = Rating;
    			if(item_stata.min_rating > Rating || item_stata.min_rating == 0)
    				item_stata.min_rating = Rating;
            }
            // Calculate mean ratings per item.
            item_stata.mean_rating = (float)item_stata.sum_rating / item_stata.user_count;
            // Calculate median ratings per item.
            Arrays.sort(users_ratings_array);
            int middle_id = item_stata.user_count / 2;
            if(item_stata.user_count % 2 == 1)
            	item_stata.median_rating = users_ratings_array[middle_id];
            else
            	item_stata.median_rating = (users_ratings_array[middle_id - 1] + users_ratings_array[middle_id]) / 2;
            // Calculate standard deviation ratings per item.
            for(Integer i : users_ratings_array)
            	item_stata.std_deviation += Math.pow(i - item_stata.mean_rating, 2);
            item_stata.std_deviation /= item_stata.user_count;
            item_stata.std_deviation = Math.sqrt(item_stata.std_deviation);
            itemStataMap.replace(item_id, item_stata);
            System.out.println("Item_id:" + Integer.toString(item_id) + 
            						"\tuser_count:" + Integer.toString(item_stata.user_count));
            System.out.println(String.format("\t\tmean:%.2f, median:%.2f, max:%d, min:%d, std_dev:%f", 
            					item_stata.mean_rating, item_stata.median_rating, 
            					item_stata.max_rating, item_stata.min_rating, item_stata.std_deviation));
            System.out.println("\t\t" + Arrays.asList(users_ratings_array));
		}
		display.set_log_out_to_console();
        System.out.println("\tmake_stata_per_item over, pls check results in " + log_file_name);
	}
	
	/* Task_1: Get the average rating prediction through certain user_id & item_id. */
	float mean_item_rating(ResysHashMap resysHashMap, Integer user_id, Integer item_id){
		//System.out.println("Now predict:(user:" + Integer.toString(user_id) + ", item:" + Integer.toString(item_id) + ")");
		// When make a prediction, it must expect user's own data if the user has this item's rating.
		if(resysHashMap.get_userMap().get(user_id) == null)
		{
			System.out.println("\tError: Illegal user_id:" + Integer.toString(user_id));
			return -1;
		}
		HashMap<Integer, Integer> userinitemMap = resysHashMap.get_itemListMap().get(item_id);
		if(userinitemMap == null)
		{
			System.out.println("\tError: Illegal item_id:" + Integer.toString(item_id));
			return -1;
		}
		
		Item_Stata item_stata = new Item_Stata();
		item_stata.user_count = userinitemMap.size();
		Set<Integer> user_set = userinitemMap.keySet();
        // Iterate each user in this item.
        for (Iterator<Integer> user_iterator = user_set.iterator(); user_iterator.hasNext();) {
        	Integer user_id_in_list = user_iterator.next();
        	if(user_id_in_list == user_id)
        	{
        		/*System.out.println("\tuser:" + Integer.toString(user_id) + ", item:" + 
											Integer.toString(item_id) + " has existed");*/
        		item_stata.user_count--;
        	}
        	else
        		item_stata.sum_rating += userinitemMap.get(user_id_in_list);
        }
        if(item_stata.user_count == 0)
		{
			//System.out.println("\tNo enough data to predict(user:" + Integer.toString(user_id) + ", item:" + Integer.toString(item_id) + ")");
			return -1;
		}
        item_stata.mean_rating = (float)item_stata.sum_rating / item_stata.user_count;
		
		return item_stata.mean_rating;
	}
	
	/* Task_1: Calculate the coverage of the mean_item_rating approach 
	 * 			which the percentage of ratings can be generated. */
	void cal_coverage(ResysHashMap resysHashMap){
		HashMap<Integer, HashMap<Integer, Integer>> userMap = resysHashMap.get_userMap();
		HashMap<Integer, HashMap<Integer, Integer>> itemListMap = resysHashMap.get_itemListMap();
		HashMap<Integer, User_Stata> userStataMap = resysHashMap.get_userStataMap();
		HashMap<Integer, Item_Stata> itemStataMap = resysHashMap.get_itemStataMap();
		
		Integer User_sum = userMap.size();
		Integer Item_sum = itemListMap.size();
		Integer connot_predict_pair_count = 0;
		Set<Integer> item_set = itemListMap.keySet(); 
		// Iterate each item, and calculate each item's data.
		for (Iterator<Integer> item_iterator = item_set.iterator(); item_iterator.hasNext();) {
			Integer item_id = item_iterator.next();
			HashMap<Integer, Integer> userinitemMap = itemListMap.get(item_id);
			if(userinitemMap.size() == 1)
				connot_predict_pair_count++;
			else if(userinitemMap.size() == 0)
				connot_predict_pair_count += User_sum;
		}
		float coverage = (float)(User_sum * Item_sum - connot_predict_pair_count) / (User_sum * Item_sum);
		System.out.println("\tCoverge is:" + String.format("%f", coverage));
	}
}

package RecommendSys_ACF_test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

class FileProcess {
	void File_reader(String file_name, ResysHashMap resysHashMap, Ratings_Count ratings_Count) throws IOException{
		BufferedReader BufferReadLineString = null;
		try {
			BufferReadLineString = new BufferedReader(new FileReader(file_name));
			System.out.println("Now read: " + file_name);
			String TempStringLine = null;
			int line_num = 0;
			
			/* Read the data set by line.
			 * Sample of one line:	"115,265,2,881171488"
			 *	 		User_id:	115
			 *			Item_id:	256
			 *			Rating:		2
			 *			Timestamp:	881171488
			 */
			while((TempStringLine = BufferReadLineString.readLine()) != null){
				line_num ++;
				/*if(line_num >= 10)
					continue;
				System.out.println(TempStringLine);*/
				String[] row_item = TempStringLine.split(",");
				/*System.out.println("User_id: " + row_item[0] + 
								"\tItem_id:" + row_item[1] + 
								"\tRating:" + row_item[2] + 
								"\tTimestamp:" + row_item[3]);	
				*/
				data_put_itemListMap(resysHashMap.get_itemListMap(), Integer.parseInt(row_item[0]),
											Integer.parseInt(row_item[1]), 
											Integer.parseInt(row_item[2]));
				data_put_userMap(resysHashMap.get_userMap(), Integer.parseInt(row_item[0]), 
											Integer.parseInt(row_item[1]), 
											Integer.parseInt(row_item[2]));
				rating_count_updata(ratings_Count, Integer.parseInt(row_item[2]));
			}
			BufferReadLineString.close();
			System.out.println("File read over, total lines: " + Integer.toString(line_num));
		} catch (FileNotFoundException e1){
			System.out.println("File not find: " + file_name);
		} catch(IOException e2) {
			e2.printStackTrace();
		} finally {
			if(BufferReadLineString != null){
				BufferReadLineString.close();
			}
		}
	}
	
	/******************************************************
	 * Create a HashMap with <Item_Id, <User_Id, Rating>>;
	 ******************************************************/
	void data_put_itemListMap(HashMap<Integer, HashMap<Integer, Integer>> itemListMap, 
										Integer User_id, Integer Item_id, Integer Rating){
		HashMap<Integer, Integer> userinitemMap = itemListMap.get(Item_id);
		// Check existing item.
		if(userinitemMap == null)
		{
			userinitemMap = new HashMap<Integer, Integer>();
			itemListMap.put(Item_id, userinitemMap);
		}
		userinitemMap.put(User_id, Rating);

	}
	
	/* Create a HashMap with <User_Id, <Item_Id, Rating>>*/
	void data_put_userMap(HashMap<Integer, HashMap<Integer, Integer>> userMap, 
									Integer User_id, Integer Item_id, Integer Rating){
		HashMap<Integer, Integer> itemMap = userMap.get(User_id);
		// Check existing user.
		if(itemMap == null)
		{
			itemMap = new HashMap<Integer, Integer>();
			userMap.put(User_id, itemMap);
		}
		itemMap.put(Item_id, Rating);
	}

	void rating_count_updata(Ratings_Count ratings_Count, int rating){
		switch(rating){
		case 1:
			ratings_Count.one_star_count++;
			break;
		case 2:
			ratings_Count.two_star_count++;
			break;
		case 3:
			ratings_Count.three_star_count++;
			break;
		case 4:
			ratings_Count.four_star_count++;
			break;
		case 5:
			ratings_Count.five_star_count++;
			break;
		default:
			System.out.println("Wrong rating: " + Integer.toString(rating));
		}
	}
	
	/* Iterate the map */
	private void check_data_map(HashMap<Integer, HashMap<Integer, Integer>> userMap, Display display) throws IOException{
		// Set the output stream into file.
		//set_log_out_to_file("test_log_userMap.txt");
		display.init_log_file("task0_test_log_userMap.txt");
		display.change_log_file("task0_test_log_userMap.txt");
		Set<Integer> user_set = userMap.keySet();  
        for (Iterator<Integer> user_iterator = user_set.iterator(); user_iterator.hasNext();) {  
        	Integer User_id = user_iterator.next();  
            HashMap<Integer, Integer> itemMap = userMap.get(User_id);  
            
            Set<Integer> item_set = itemMap.keySet();  
            for (Iterator<Integer> item_iterator = item_set.iterator(); item_iterator.hasNext();) {
            	Integer Item_id = item_iterator.next();  
            	Integer Rating = itemMap.get(Item_id);
            	System.out.println(String.format("%d, %d, %d", User_id,Item_id,Rating));
            }
        }
		// Set the output stream into default console.
        display.set_log_out_to_console();
	}
}

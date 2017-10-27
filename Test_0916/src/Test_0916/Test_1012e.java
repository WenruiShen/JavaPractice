package Test_0916;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Collections;

public class Test_1012e{
/*
无向图 遍历：

第一行： n, m  代表城市数量，路径熟练
第2 - m+1行， 代表每条路径的“起点城市”，“终点城市”，“长度” (路径为双向)
当可以找到一条循环路径时输出YES，不要求全部遍历；
不行时输出最长路径。

5,4
0,1,100
3,4,100
2,1,100
3,2,100
样例输出
400

5,4
0,1,100
3,4,100
2,1,100
0,2,100
输出
YES

 */
	
	List<Integer> city_start_list = new ArrayList<Integer>();  
    List<Integer> city_end_list = new ArrayList<Integer>();  
    List<Integer> road_len_list = new ArrayList<Integer>();  
    
    int cycle_mark = 0;
    int max_len = 0;
    
	int road_iterator(int road_id, List<Integer> road_record_list, int star_end){
		List<Integer> road_record_list_new = new ArrayList<Integer>();
		Collections.copy(road_record_list_new, road_record_list);
		road_record_list_new.add(road_id);
		
		for(int next_road_id = 0; next_road_id < city_start_list.size(); next_road_id++){
			//if(road_id == next_road_id) 
			//	continue;
			if(road_record_list_new.contains(next_road_id)){
				continue;
			}
			else{
				if(star_end == 0){
					if(city_start_list.get(road_id) == city_start_list.get(next_road_id)){
						road_iterator(next_road_id, road_record_list_new, 0);
					}
					else if(city_start_list.get(road_id) == city_end_list.get(next_road_id)){
						road_iterator(next_road_id, road_record_list_new, 1);
					}
				}
				else if(star_end == 1){
					
				}
			}
		}
		
		return -1;
	}

    //public static 
	void function_body()  throws Exception, IOException {
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        String [] line_1_list = bf.readLine().toString().split(",");
		int n = Integer.parseInt(line_1_list[0]);
        int m = Integer.parseInt(line_1_list[1]);        
        
	    for (int road_id=0; road_id<m;road_id++){
            String []raw_strings=bf.readLine().split(",");
	    	city_start_list.add(Integer.parseInt(raw_strings[0]));
            city_end_list.add(Integer.parseInt(raw_strings[1]));
            road_len_list.add(Integer.parseInt(raw_strings[2]));
	    }

        // iterate from each road.
	    List<Integer> road_record_list = new ArrayList<Integer>(); 
        for(int road_id = 0; road_id < n; road_id++){
        	road_iterator(road_id, road_record_list, 0);
        	road_iterator(road_id, road_record_list, 1);
            
        // judge cycle
		
        
        // judge max length
        }
        
        
        
        System.out.println("YES");
        
    }
}





/*
5
-8,5,-1,3,-9
 */

/*
public class Test_1012e {
    //public static 
	void function_body()  throws Exception, IOException {
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(bf.readLine().toString());
        String []vi_strings=bf.readLine().split(",");
		
	    List<Integer> vi_list = new ArrayList<Integer>();  
	    for (int i=0; i<n;i++){
	    	vi_list.add(Integer.parseInt(vi_strings[i]));
	    }
	    
        
        int max_vi = 0;
        for (int i=0; i<n;i++){
            int temp_max_vi = 0;
            int temp_sum_vi = 0;
            if(vi_list.get(i) > 0){
                for (int j=i; j<n;j++){
                    temp_sum_vi += vi_list.get(j);
                    if(temp_sum_vi > temp_max_vi){
                        temp_max_vi = temp_sum_vi;
                    }
                }
            }
            if(temp_max_vi > max_vi){
                max_vi = temp_max_vi;
            }
        }
        System.out.println(max_vi);
        
    }
}
*/
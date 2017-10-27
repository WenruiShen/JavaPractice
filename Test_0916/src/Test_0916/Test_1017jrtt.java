package Test_0916;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/*
3 1
6 5 1
out: 4 4 4
 */
public class Test_1017jrtt {
	void function_body(){
		Scanner sc = new Scanner(System.in);
        int n_room = sc.nextInt();
        int x_room = sc.nextInt();
        
        List<Integer> num_list = new ArrayList<Integer>();  
        List<Integer> ori_num_list = new ArrayList<Integer>();
        
        StringBuffer ori_num_str = new StringBuffer();
        
	    for (int i=0; i<n_room;i++){
	    	num_list.add(sc.nextInt());
	    }
        
	    for(int i = 1;  i<=n_room; i++){
	    	int m_N = num_list.get(i-1);// - x_room - (n_room - i - 1);
	    	
	    	int j = 0;
	    	for(j = 1;  j<=n_room; j++){
	    		if(j == i) continue;
	    		int n_j_ori = num_list.get(j-1) - m_N;
	    		
	    		if(j >= i) n_j_ori--;
	    		else if(j <= x_room) n_j_ori--;
	    		
	    		if (n_j_ori < 0) break;
	    		
	    	}
	    	if (j > n_room){
	    		for(int ori_i = 1;  ori_i<=n_room; ori_i++){
	    			int ori_num = 0;
	    			if(i == ori_i)
	    				ori_num = 0;
	    			else {
	    				ori_num = num_list.get(ori_i-1) - m_N;
	    				if(ori_i >= i) ori_num--;
	    	    		else if(ori_i <= x_room) ori_num--;
	    			}
	    				
	    			ori_num_list.add(ori_num);
	    		}
	    		
	    		int ori_num_i = num_list.get(i-1);
	    		for(int ori_i = 1;  ori_i<=n_room; ori_i++){
	    			if(i != ori_i){
	    				ori_num_i += (num_list.get(ori_i-1) - ori_num_list.get(ori_i-1));
	    			}
	    				
	    		}
	    		ori_num_list.set(i - 1,ori_num_i);
	    		//for(Integer num : ori_num_list)
	    		//	ori_num_str.append(String.valueOf(num) + " ");
	    		for (int nnn = 0; nnn < ori_num_list.size(); nnn++){
	    			ori_num_str.append(String.valueOf(ori_num_list.get(nnn)));
	    			if (nnn < ori_num_list.size() - 1)
	    				ori_num_str.append(" ");
	    		}
	    		System.out.print(ori_num_str);
	    		return;
	    	}
	    }
	    
	}
}

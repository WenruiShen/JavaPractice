package Test_0916;

import java.util.Scanner;
import java.lang.*;

public class Test_1012f {
	boolean isRepeat(int new_code, int N){
		String new_code_str = Integer.valueOf(new_code).toString();
		int new_num_len = new_code_str.length();
    	char[] char_arry = new_code_str.toCharArray();
    	for (int i = 0; i < char_arry.length - 1; i++) {
    		for (int j = i+1; j < char_arry.length; j++) {
    			if(char_arry[i] == char_arry[j]){
    				return true;
    			}
    		}
    	}
    	if(new_num_len < N){
    		for (int i = 0; i < char_arry.length; i++) {
    			if(char_arry[i] == '0'){
    				return true;
    			}
    		}
    	}
    	
    	return false;
	}
	
	void function_body(){
		Scanner sc = new Scanner(System.in);
        int ori_num = sc.nextInt();
        int N = Integer.valueOf(ori_num).toString().length();
        int min_num = (int)Math.pow(10, N - 1);
        int max_num = (int)Math.pow(10, N) - 1;
        int temp_max_diff = 0;
        int temp_max_code = 0;
        
        for(int new_code = min_num; new_code <= max_num; new_code++){
        	if(isRepeat(new_code, N)) continue;
        	
        	int diff_abs = Math.abs(new_code - ori_num);
        	int temp_diff = Math.min(diff_abs, max_num + 1 - diff_abs);
        	if(temp_diff > temp_max_diff){
        		temp_max_diff = temp_diff;
        		temp_max_code = new_code;
        	}
        	else if(temp_diff == temp_max_diff){
        		if(new_code < temp_max_code){
        			temp_max_code = new_code;
        		}
        	}
        }
        
        String final_new_code = Integer.valueOf(temp_max_code).toString();
        int final_len = final_new_code.length();
        if(final_len < N){
        	System.out.println("0" + final_new_code);
        }
        else{
        	System.out.println(final_new_code);
        }
        
	}
}

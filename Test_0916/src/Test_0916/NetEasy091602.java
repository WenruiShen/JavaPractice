package Test_0916;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



class wordChecker{
	int check_word(char[][]ch_2d_list, int x_i, int y_i, String target_word){
		int res_count = 0;
		int row_num = ch_2d_list.length;
		int com_num = ch_2d_list[0].length;
		int word_len = target_word.length();

		if (y_i <= com_num-word_len){
			String x_dir = "";
			for (int ch_i = 0; ch_i<word_len; ch_i++){
				x_dir = x_dir + String.valueOf(ch_2d_list[x_i][y_i+ch_i]);
			}
			if (target_word.equals(x_dir)){
				res_count++;
			}
		}
		
		// check xy->:
		if (x_i <= row_num-word_len && y_i <= com_num-word_len){
			String xy_dir = "";
			for (int ch_i = 0; ch_i<word_len; ch_i++){
				xy_dir = xy_dir + String.valueOf(ch_2d_list[x_i+ch_i][y_i+ch_i]);
			}
			if (target_word.equals(xy_dir)){
				res_count++;
			}
		}
		
		// check y->:
		if (x_i <= row_num-word_len){
			String y_dir = "";
			for (int ch_i = 0; ch_i<word_len; ch_i++){
				y_dir = y_dir + String.valueOf(ch_2d_list[x_i+ch_i][y_i]);
			}
			if (target_word.equals(y_dir)){
				res_count++;
			}
		}
		return res_count;
	}
}

public class NetEasy091602 {
	void function_body(){
		 Scanner sc = new Scanner(System.in);
	        int test_n = sc.nextInt();      
	        int[] res_sum_list = new int[test_n];
	        
	        for (int test_i=0; test_i<test_n;test_i++){
	        	int x_num = sc.nextInt();
	        	int y_num = sc.nextInt();
	            
	            char[][] ch_2d_list = new char[x_num][];
	            
	            for (int x_i = 0; x_i < x_num;){
	                String x_str = sc.next();
	                char[] x_ch_list = x_str.toCharArray();
	                ch_2d_list[x_i] = x_ch_list;
	                x_i++;
	            }
	            
	            String target_word = sc.next();
	            int word_len = target_word.length();
	            int sum_result = 0;
	            wordChecker wordchecker = new wordChecker();
	            
	            for (int x_i = 0; x_i < x_num; x_i++){
	            	for (int y_i = 0; y_i < y_num; y_i++){
	            		sum_result += wordchecker.check_word(ch_2d_list, x_i, y_i, target_word);
	            	}
	            }
	            res_sum_list[test_i] = sum_result;
	        }
	        
	        for (int sum : res_sum_list){
	        	System.out.println(sum);
	        }
	}
}



/*
1
4 4
ABCD
EFGH
IJKL
MNOP
 */

/*
1
10 10
AAAAAADROW
WORDBBBBBB
OCCCWCCCCC
RFFFFOFFFF
DHHHHHRHHH
ZWZVVVVDID
ZOZVXXDKIR
ZRZVXRXKIO
ZDZVOXXKIW
ZZZWXXXKIK
WORD
 */
package Test_0916;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
3
11:51:11
72:22:99
35:93:73
 */

class time_Convetor{
	int changeToMinHour(int ori_Hour){
		// HH: 00-23
		int new_Hour = 0;
		int posTen = ori_Hour / 10;
		int posOne = ori_Hour % 10;
		if (ori_Hour > 23){
			if (posTen>2){
				posTen=0;
			}
		}
		new_Hour = posTen * 10 + posOne;
		return new_Hour;
	}
	
	int changeToMinHourAndSec(int ori_MinOrSec){
		// MM/SS: 00-59
		int new_MinOrSec = 0;
		int posTen = ori_MinOrSec / 10;
		int posOne = ori_MinOrSec % 10;
		if (ori_MinOrSec > 59){
			if (posTen>5){
				posTen=0;
			}
		}
		new_MinOrSec = posTen * 10 + posOne;
		return new_MinOrSec;
	}
	
	String get_target_time(String ori_time){
		// HH:MM:SS
		// HH: 00-23
		// MM/SS: 00-59
		String[] str_time_list = ori_time.split("\\:");
		
		int ori_Hour = Integer.valueOf(str_time_list[0]).intValue();
		int ori_Minute = Integer.valueOf(str_time_list[1]).intValue();
		int ori_Second = Integer.valueOf(str_time_list[2]).intValue();
		
		int new_Hour = changeToMinHour(ori_Hour);
		int new_Minute = changeToMinHourAndSec(ori_Minute);
		int new_Second = changeToMinHourAndSec(ori_Second);
		
		String newtime = String.format("%02d:%02d:%02d", new_Hour, new_Minute, new_Second);	
		return newtime;
	}
}

public class NetEasy091601 {
	void function_body(){
		Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<String> ori_time_list = new ArrayList<String>();  
        for (int i=0; i<n;i++){
        	ori_time_list.add(sc.next());
        }
        
        time_Convetor time_convetor = new time_Convetor();
        for(String ori_time : ori_time_list){
        	//System.out.println(ori_time);
        	String target_time = time_convetor.get_target_time(ori_time);
        	System.out.println(target_time);
        }
	}
}

package Test_0916;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class nvida02 {
	void function_body()  throws Exception, IOException {
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		long std_time_sec = Long.parseLong(bf.readLine().toString());
		
		Calendar calendar2 = Calendar.getInstance();  
        calendar2.setTimeInMillis(std_time_sec * 1000);     
        
        int std_year = calendar2.get(Calendar.YEAR);  
        int std_month = calendar2.get(Calendar.MONTH);  
        int std_day = calendar2.get(Calendar.DAY_OF_MONTH);  
        int std_hour = calendar2.get(Calendar.HOUR_OF_DAY);//24Ð¡Ê±ÖÆ  
        int std_minute = calendar2.get(Calendar.MINUTE);  
        int std_second = calendar2.get(Calendar.SECOND);  
        /*
        System.out.println(std_year);
        System.out.println(std_month);
        System.out.println(std_day);
        System.out.println(std_hour);
        */
        long runnian_secs_diff = 366 * 24 * 60 * 60 * 2;
        long runnian_secs = std_time_sec;
        for(int year = 1970; year < std_year; year++){
        	if((year%4==0&&year%100!=0)||year%400==0){
        		// is run nian
        		//System.out.println(year);
        		runnian_secs -= runnian_secs_diff;
        	}
        }
        
        // thie yesr
        if((std_year%4==0&&std_year%100!=0)||std_year%400==0){
        	// this year is runnian
        	Calendar calendar3 = Calendar.getInstance(); 
        	calendar3.set(std_year, 0, 1, 0, 0, 0);  
        	long old_std_year_secs_base = calendar3.getTimeInMillis() / 1000;
        	runnian_secs -= (std_time_sec - old_std_year_secs_base)*2;
        }
        System.out.println(runnian_secs);
        
    }
}

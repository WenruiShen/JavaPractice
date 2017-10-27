package Test_0916;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class nvida01 {
	void function_body()  throws Exception, IOException {
		String std_addr_163="\\w+(\\.\\w)*@+163.com";
		String std_addr_sina="\\w+(\\.\\w)*@+sina.com";
		String std_addr_qq="\\w+(\\.\\w)*@+qq.com";
		String std_addr_gmail="\\w+(\\.\\w)*@+gmail.com";
		
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(bf.readLine().toString());
        
		List<String> line_addr_str_list = new ArrayList<String>();
		for (int line_id=0; line_id<n; line_id++){
			String line_addr_str = bf.readLine().toString();
			line_addr_str_list.add(line_addr_str);
		}
		
	    for (String line_addr_str : line_addr_str_list){
            
            if (line_addr_str.matches(std_addr_163))   
                System.out.println("Netease");
            else if (line_addr_str.matches(std_addr_qq))   
                System.out.println("Tencent");
            else if (line_addr_str.matches(std_addr_sina))   
                System.out.println("Sina");
            else if (line_addr_str.matches(std_addr_gmail))   
                System.out.println("Google");
            else
            	System.out.println("Invalid");
	    }     
        
    }
}

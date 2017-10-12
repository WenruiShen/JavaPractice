package Test_0916;
import java.util.Scanner;

public class Ctrip092101 {
	String change(int m, int n) {
        StringBuffer output_str = new StringBuffer("");
        int temp_x = 0;
        int temp_m = m;
        /*if (m < 0){
        	temp_m = -temp_m;
        	
        }*/
        while(true){
            temp_x = temp_m % n;
            output_str.append(String.format("%x", temp_x));
            temp_m = temp_m / n;
            System.out.println(String.valueOf(temp_x) + ", " + String.valueOf(temp_m));
            if(temp_m == 0){
            	break;
            }
        }
        /*if (m < 0){
        	temp_m = temp_m;
        	
        }*/
		return output_str.reverse().toString();
    }
	
    void function_body(){
    	Scanner in = new Scanner(System.in);
        String res;
            
        int _m;
        _m = Integer.parseInt(in.nextLine().trim());
        
        int _n;
        _n = Integer.parseInt(in.nextLine().trim());
  
        res = change(_m, _n);
        System.out.println(res);
    }
}

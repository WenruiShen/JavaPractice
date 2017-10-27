package Test_0916;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 设有n个正整数，将他们连接成一排，组成一个最大的多位整数。
 * 如:n=3时，3个整数13,312,343,连成的最大整数为34331213。
 * 如:n=4时,4个整数7,13,4,246连接成的最大整数为7424613。
 */

public class Test_1012c {
    //public static 
    void main(String[] args) throws Exception, IOException{
          
        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
          
        while(true){
            int N=Integer.parseInt(bf.readLine().toString());
            String []strings=bf.readLine().split(" ");
            Arrays.sort(strings,new Comparator<String>() {
  
                @Override
                public int compare(String arg0, String arg1) {
                    String s1=arg0+arg1;
                    String s2=arg1+arg0;
                    return s1.compareTo(s2);
                }
                    
            });
   
            StringBuffer sb=new StringBuffer();
            for(int i=strings.length-1;i>=0;i--){
                sb.append(strings[i]);
            }
            System.out.print(sb);
        } 
          
    }
}
package Test_0916;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 * ����n�������������������ӳ�һ�ţ����һ�����Ķ�λ������
 * ��:n=3ʱ��3������13,312,343,���ɵ��������Ϊ34331213��
 * ��:n=4ʱ,4������7,13,4,246���ӳɵ��������Ϊ7424613��
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
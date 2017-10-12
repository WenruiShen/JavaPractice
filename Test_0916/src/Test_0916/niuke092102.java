package Test_0916;
import java.math.BigDecimal;
import java.util.Scanner;
/*
 * 一个由小写字母组成的字符串可以看成一些同一字母的最大碎片组成的。例如,"aaabbaaac"是由下面碎片组成的:'aaa','bb','c'。牛牛现在给定一个字符串,请你帮助计算这个字符串的所有碎片的平均长度是多少。
 * 输入描述:输入包括一个字符串s,字符串s的长度length(1 ≤ length ≤ 50),s只含小写字母('a'-'z')
 * 输出描述:输出一个整数,表示所有碎片的平均长度,四舍五入保留两位小数。
 * 如样例所示: s = "aaabbaaac"
 * 所有碎片的平均长度 = (3 + 2 + 3 + 1) / 4 = 2.25
 * 输入例子1:aaabbaaac
 * 输出例子1:2.25
 */
public class niuke092102 {
	void function_body(){
		//System.out.println("Please input a num:");
		Scanner sc = new Scanner(System.in);
        String ori_str = sc.next();
        int ori_str_len = ori_str.length();
        int part_count = 1;
        char temp_char = ori_str.charAt(0);
        for (int id = 1; id < ori_str_len; id++){
        	char iterate_char = ori_str.charAt(id);
        	if (iterate_char != temp_char){
        		temp_char = iterate_char;
        		part_count++;
        	}
        }
        float average_len = (float)ori_str_len / part_count;
        BigDecimal b = new BigDecimal(average_len); 
        System.out.println(b.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		
	}
}






class Main000 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int len = in.nextInt();
            int[] arr = new int[len];
            for (int i = 0; i < len; ++i) {
                arr[i] = in.nextInt();
            }
            if (len < 3) {
                System.out.println("0");
            } else {
                int[][] dp = new int[len][len];
                int[] acc = new int[len];
                dp[0][0] = 0 - Math.abs(arr[1] - arr[0]);
                for (int i = 1; i < len; ++i) {
                    acc[i] = acc[i - 1] + Math.abs(arr[i] - arr[i - 1]);
                    dp[i][i - 1] = acc[i - 1];
                    for (int j = 0; j < i - 1; ++j) {
                        dp[i][j] = dp[i - 1][j] + acc[i] - acc[i - 1];
                        dp[i][i - 1] = Math.min(dp[i][i - 1], dp[i - 1][j] + Math.abs(arr[i] - arr[j]));
                    }
                }
                int min = Integer.MAX_VALUE;
                for (int j = 0; j < len - 1; ++j) {
                    min = Math.min(min, dp[len - 1][j]);
                }
                System.out.println(min);
            }
        }
    }
}


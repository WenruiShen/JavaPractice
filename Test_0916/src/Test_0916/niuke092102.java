package Test_0916;
import java.math.BigDecimal;
import java.util.Scanner;
/*
 * һ����Сд��ĸ��ɵ��ַ������Կ���һЩͬһ��ĸ�������Ƭ��ɵġ�����,"aaabbaaac"����������Ƭ��ɵ�:'aaa','bb','c'��ţţ���ڸ���һ���ַ���,���������������ַ�����������Ƭ��ƽ�������Ƕ��١�
 * ��������:�������һ���ַ���s,�ַ���s�ĳ���length(1 �� length �� 50),sֻ��Сд��ĸ('a'-'z')
 * �������:���һ������,��ʾ������Ƭ��ƽ������,�������뱣����λС����
 * ��������ʾ: s = "aaabbaaac"
 * ������Ƭ��ƽ������ = (3 + 2 + 3 + 1) / 4 = 2.25
 * ��������1:aaabbaaac
 * �������1:2.25
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


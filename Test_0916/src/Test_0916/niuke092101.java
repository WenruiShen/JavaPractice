package Test_0916;

import java.util.Scanner;
/*
 * Ϊ�˵õ�һ������"�෴��",���ǽ������������˳��ߵ�,Ȼ���ټ���ԭ�ȵ����õ�"�෴��"������,Ϊ�˵õ�1325��"�෴��",�������ǽ�����������˳��ߵ�,���ǵõ�5231,֮���ټ���ԭ�ȵ���,���ǵõ�5231+1325=6556.����ߵ�֮���������ǰ׺��,ǰ׺�㽫�ᱻ���ԡ�����n = 100, �ߵ�֮����1. 
 * ��������:�������һ������n,(1 �� n �� 10^5)
 * �������:���һ������,��ʾn���෴��
 * ��������1:1325
 * �������1:6556
 */
public class niuke092101 {
	void function_body(){
		//System.out.println("Please input a num:");
		Scanner sc = new Scanner(System.in);
        int ori_num = sc.nextInt();
        StringBuffer ori_num_str = new StringBuffer(String.valueOf(ori_num));
        String reverse_num_str = ori_num_str.reverse().toString();
        int reverse_num = Integer.valueOf(reverse_num_str);
		int final_num = ori_num + reverse_num;
		System.out.println(final_num);
	}
}

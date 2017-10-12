package Test_0916;

import java.util.Scanner;

/*
 * С��׼��ȥħ�������ɹ�ħ������,����ħ��������Ҫʹ��ħ����,����С������һöħ���Ҷ�û��,����С������̨ħ����������ͨ��Ͷ��x(x����Ϊ0)��ħ���Ҳ��������ħ���ҡ�
 * ħ������1:���Ͷ��x��ħ����,ħ�������Ὣ���Ϊ2x+1��ħ����
 * ħ������2:���Ͷ��x��ħ����,ħ�������Ὣ���Ϊ2x+2��ħ����
 * С�ײɹ�ħ�������ܹ���Ҫn��ħ����,����С��ֻ��ͨ����̨ħ����������ǡ��n��ħ����,С����Ҫ��������һ��Ͷ�뷽��ʹ�����ǡ��ӵ��n��ħ���ҡ� 
 * ��������:
 * 		�������һ��,����һ��������n(1 �� n �� 10^9),��ʾС����Ҫ��ħ����������
 * �������:
 * 		���һ���ַ���,ÿ���ַ���ʾ�ô�С��ѡȡͶ���ħ������������ֻ�����ַ�'1'��'2'��
 * ��������1:10
 * �������1:122
 */

class Magic_Machine{
	int magic_machine_1(int target_maney, int coins, StringBuffer machine_seq_str){
		int creat_coins = 2 * coins + 1;
		// find a solution:
		if (creat_coins == target_maney){
			return 1;
		}
		else if (creat_coins < target_maney){
			// need try again:
			return choose_magic_machine(target_maney, creat_coins, machine_seq_str);
		}
		else if (creat_coins > target_maney){
			// beyond the range:
			return 0;
		}
		return 0;
	}
	
	int magic_machine_2(int target_maney, int coins, StringBuffer machine_seq_str){
		int creat_coins = 2 * coins + 2;
		// find a solution:
		if (creat_coins == target_maney){
			return 1;
		}
		else if (creat_coins < target_maney){
			// need try again:
			return choose_magic_machine(target_maney, creat_coins, machine_seq_str);
		}
		else if (creat_coins > target_maney){
			// beyond the range:
			return 0;
		}
		return 0;
	}
	
	int choose_magic_machine(int target_maney, int coins,StringBuffer machine_seq_str){
		if (magic_machine_1(target_maney, coins, machine_seq_str) != 0){
			machine_seq_str.append("1");
			return 1;
		}
		else if (magic_machine_2(target_maney, coins, machine_seq_str) != 0) {
			machine_seq_str.append("2");
			return 1;
		}
		return 0;
	}
}

public class niuke091701 {
	void function_body(){
		//System.out.println("Please input a num:");
		Scanner sc = new Scanner(System.in);
        int target_maney = sc.nextInt();
        StringBuffer machine_seq_str = new StringBuffer("");
		Magic_Machine magic_machine = new Magic_Machine();
		if(0 != magic_machine.choose_magic_machine(target_maney, 0, machine_seq_str)){
			System.out.println(machine_seq_str.reverse().toString());	
		}
		
	}
}

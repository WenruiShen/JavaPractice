package Test_0916;

import java.util.Scanner;

/*
 * 小易准备去魔法王国采购魔法神器,购买魔法神器需要使用魔法币,但是小易现在一枚魔法币都没有,但是小易有两台魔法机器可以通过投入x(x可以为0)个魔法币产生更多的魔法币。
 * 魔法机器1:如果投入x个魔法币,魔法机器会将其变为2x+1个魔法币
 * 魔法机器2:如果投入x个魔法币,魔法机器会将其变为2x+2个魔法币
 * 小易采购魔法神器总共需要n个魔法币,所以小易只能通过两台魔法机器产生恰好n个魔法币,小易需要你帮他设计一个投入方案使他最后恰好拥有n个魔法币。 
 * 输入描述:
 * 		输入包括一行,包括一个正整数n(1 ≤ n ≤ 10^9),表示小易需要的魔法币数量。
 * 输出描述:
 * 		输出一个字符串,每个字符表示该次小易选取投入的魔法机器。其中只包含字符'1'和'2'。
 * 输入例子1:10
 * 输出例子1:122
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

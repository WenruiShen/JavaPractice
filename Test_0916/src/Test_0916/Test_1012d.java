package Test_0916;
import java.util.HashMap;
import java.util.Scanner;

/*
������һ�úϷ��Ķ����������Ľڵ㶼�������ֱ�ʾ�����ڸ�������������еĸ��ӹ�ϵ����������ĸ߶�
��������:
����ĵ�һ�б�ʾ�ڵ�ĸ���n��1 �� n �� 1000���ڵ�ı��Ϊ0��n-1����ɣ�
������n-1�У�ÿ����������������һ������ʾ���ڵ�ı�ţ��ڶ�������ʾ�ӽڵ�ı��
�������:������ĸ߶ȣ�Ϊһ������
ʾ��1
	����
		5
		0 1
		0 2
		1 3
		1 4
	���
		3
 */

public class Test_1012d {
    //public static 
    void main(String[] args) {
 
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String result = "";
        // �������Map���ڵ㺢������Map
        HashMap<Integer, Integer> deep = new HashMap<>();
        HashMap<Integer, Integer> childNum = new HashMap<>();
        deep.put(0, 1);
        childNum.put(0, 0);
        // Ĭ���������Ϊ1
        int max = 1;
        int myDeep = 0;
        for (int i = 0; i < n - 1; i++) {
            int parent = scanner.nextInt();
            int num = scanner.nextInt();
            // ���������ڵ���ߺ�����Ŀ����������������
            if (!deep.containsKey(parent) || childNum.get(parent) >= 2) {
                continue;
            }
            // ������ȼ�һ
            myDeep = deep.get(parent) + 1;
            // �ӽڵ���������
            deep.put(num, myDeep);
            // �游�ڵ㣬���ӽڵ��������һ
            childNum.put(parent, (childNum.get(parent) + 1));
            // ���ӽڵ㣬���ӽڵ�����Ϊ0
            childNum.put(num, 0);
            if (myDeep > max) {
                max = myDeep;
            }
        }
        System.out.println(max);
    }
}
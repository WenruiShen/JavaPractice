package test003_array;

import java.util.Arrays;

public class test003_array {
	public static void main(String[] args) {
		EhrlichSieve ehrlichsieve = new EhrlichSieve();
		ehrlichsieve.EhrlichSieveMethod();
	}
}

class MethodPara{
	public static final int LAST_NUM = 20000;	// 20000
	public static final int START_NUM = 2;
	public static final int TOTAL_NUM = LAST_NUM - START_NUM + 1; // 2~20000
}

class  EhrlichSieve{
	int[] NumList = new int[MethodPara.TOTAL_NUM];
	
	public void EhrlichSieveMethod(){
		NumListInit(NumList);
		int[] PrimeNumArray = NumProcess(NumList);
		int result_count = print_result(PrimeNumArray);
		System.out.printf("There are %d prime numbers from %d to %d.\n", result_count, MethodPara.START_NUM, MethodPara.LAST_NUM);
	}

	
	private void NumListInit(int[] NumList){
		int NumLen = NumList.length;
		int FirstNum = MethodPara.START_NUM;
		for(int NumIndex = 0; NumIndex < NumLen; NumIndex++){
			NumList[NumIndex] = FirstNum++;
		}
		//System.out.println(Arrays.toString(NumList));
	}
	
	private int[] NumProcess(int[] NumList){
		// Set all Non-Prime Elements to 0
		int NumLen = NumList.length;
		int[] PrimeNumArray = new int[MethodPara.TOTAL_NUM];
		
		for(int DevideNum = MethodPara.START_NUM; DevideNum < MethodPara.LAST_NUM; DevideNum++){
			// Tranverse all int num smaller than the largest num as denominator.
			int index_count = 0;
			for(int NumElement : NumList){ 
				// Check all elements as numerator.
				index_count++;
				if(NumElement != 0 && NumElement <= DevideNum){
					// If StartNum bigger than target elements.
					continue;
				}
				if(NumElement == 0){
					continue;
				}
				if(NumElement != 0 && NumElement % DevideNum == 0){
					//System.out.println("Remove " + Integer.toString(NumElement));
					NumList[index_count-1] = 0;
				}				
			}
		}
		//System.out.println(Arrays.toString(NumList));
		int PrimeArrayOffest = 0;
		for(int PrimeNumElement : NumList){
			if(PrimeNumElement != 0){
				PrimeNumArray[PrimeArrayOffest++] = PrimeNumElement;
			}
		}
		//System.out.println(Arrays.toString(PrimeNumArray));		
		return PrimeNumArray;
	}
	
	private int print_result(int[] PrimeNumArray){
		int result_count = 0;
		for(int PrimeNum : PrimeNumArray){
			if(PrimeNum == 0)
				break;
			System.out.printf("%d\t", PrimeNum);
			result_count++;
			if(result_count % 10 == 0){
				System.out.printf("\n");
			}
		}
		System.out.printf("\n");
		return result_count;
	}
	
}
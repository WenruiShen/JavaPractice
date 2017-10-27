package Test_0916;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/*
 * Practice for java list's iterate with deleting elements.
 * Delete all elements appears more than once in the Integer array.
 * input: 13 
 *        1 2 3 4 3 2 1 5 5 6 5 9 9
 */

public class Test_1012 {
	void function_body(){
		Scanner sc = new Scanner(System.in);
	    int n = sc.nextInt();
	    List<Integer> num_list = new ArrayList<Integer>();  
	    for (int i=0; i<n;i++){
	    	//num_list.add(Integer.valueOf(sc.nextInt()));
	    	num_list.add(sc.nextInt());
	    }
	    
	    Iterator<Integer> it = num_list.iterator();
	    while(it.hasNext()){
	    	// delete element with Iterator's remove method.
	    	Integer this_num = it.next();
	    	if(this_num.equals(5) ){
	    		it.remove();
	    	}
	    }
	    System.out.println(num_list);
	}
}

package RecommendSys_ACF_test;

import java.util.HashMap;

class ResysHashMap {
	HashMap<Integer, HashMap<Integer, Integer>> userMap = new HashMap<Integer, HashMap<Integer, Integer>>();
	HashMap<Integer, HashMap<Integer, Integer>> itemListMap = new HashMap<Integer, HashMap<Integer, Integer>>();
	HashMap<Integer, User_Stata> userStataMap = new HashMap<Integer, User_Stata>();
	HashMap<Integer, Item_Stata> itemStataMap = new HashMap<Integer, Item_Stata>();
	
	HashMap<Integer, HashMap<Integer, Integer>> get_userMap(){
		return userMap;
	}
	
	HashMap<Integer, HashMap<Integer, Integer>> get_itemListMap(){
		return itemListMap;
	}
	
	HashMap<Integer, User_Stata> get_userStataMap(){
		return userStataMap;
	}
	
	HashMap<Integer, Item_Stata> get_itemStataMap(){
		return itemStataMap;
	}
}

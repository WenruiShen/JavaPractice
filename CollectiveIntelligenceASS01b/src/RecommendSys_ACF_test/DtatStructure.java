package RecommendSys_ACF_test;

import java.util.HashMap;


	class Ratings_Count{
		int one_star_count;
		int two_star_count;
		int three_star_count;
		int four_star_count;
		int five_star_count;
		int total_count;
	}
	
	class User_Stata{
		int items_count;
		int sum_rating;
		float mean_rating;
		float median_rating;
		double std_deviation;
		int max_rating;
		int min_rating;
	}
	
	class Item_Stata{
		int sum_rating;
		float mean_rating;
		float median_rating;
		double std_deviation;
		int max_rating;
		int min_rating;
		int user_count;
	}
	
	class ItemPredictResult{
		float PredictRating;
		float sum_rating;
		float weight_sum;
		//float mean_rating;
		double single_RMES;
		int count;
	}
	class UserNPredictResult{
		float UserRMES;
		double UserRunTime;
		int predicetItemsCount;
		int unpredicetItemsCount;
		//HashMap<Integer, ItemPredictResult> itempredictresultsmap;		//<Integer Item_ID, ItemPredictResult>
	}
	class N_Evaluation{
		int predictedItemsCount;
		float averageCoverage;
		float averageRunTime;
		float averageRMES;
	}
	
	/* Task_3: Structured predicting results formats. */
	class FinalResults{
		String NeighbourSelectApproach;
		Integer n_num;
		Integer n_start;
		Integer n_stop;
		Integer users_num;
		Integer items_num;
		Double	TotalRunTime;
		HashMap<Integer, N_Evaluation> n_evaluationMap;									//<Integer n, N_Evaluation>
		HashMap<Integer, HashMap<Integer, UserNPredictResult>> UsersPredictResultsMap;	//<Integer User_id, HashMap<Integer n, UserNPredictResult>>
	}
	

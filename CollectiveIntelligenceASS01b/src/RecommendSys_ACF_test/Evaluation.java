package RecommendSys_ACF_test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

class Evaluation {
	void OverallEvaluation(FinalResults finalResults, String NeighbourSelectMethod, String logFileName, Ratings_Count ratings_Count){
		Display display = new Display();
		
		finalResults.n_evaluationMap = new HashMap<Integer, N_Evaluation>();
		// Iterate each user. (Loop-level:1, O(x)=943)
		Set<Integer> user_set = finalResults.UsersPredictResultsMap.keySet();
		for (Iterator<Integer> user_iterator = user_set.iterator(); user_iterator.hasNext();) {
			Integer User_id = user_iterator.next();
			HashMap<Integer, UserNPredictResult> userNPredictResultMap = finalResults.UsersPredictResultsMap.get(User_id);
			Integer n_num = userNPredictResultMap.size();
			// Iterate each n.
			Set<Integer> n_set = userNPredictResultMap.keySet();
			for (Iterator<Integer> n_iterator = n_set.iterator(); n_iterator.hasNext();) {
				Integer n = n_iterator.next();
				UserNPredictResult userPredictResult = userNPredictResultMap.get(n);
				N_Evaluation n_Evaluation = finalResults.n_evaluationMap.get(n);
				if(n_Evaluation == null){
					n_Evaluation = new N_Evaluation();
					finalResults.n_evaluationMap.put(n, n_Evaluation);
				}
				n_Evaluation.predictedItemsCount += userPredictResult.predicetItemsCount;
				n_Evaluation.averageRunTime += userPredictResult.UserRunTime;
				n_Evaluation.averageRMES += userPredictResult.UserRMES;		
				finalResults.n_evaluationMap.replace(n, n_Evaluation);
			}
		}
		
		Integer eva_n_num = finalResults.n_evaluationMap.size();
		Integer users_num = finalResults.UsersPredictResultsMap.size();
		Integer items_num = finalResults.items_num;
		Integer blocks_num = ratings_Count.total_count;
		if(users_num == 0 || eva_n_num == 0 || items_num == 0)
			return;
		// Iterate each n.
		Set<Integer> evaluation_set = finalResults.n_evaluationMap.keySet();
		for (Iterator<Integer> evaluation_iterator = evaluation_set.iterator(); evaluation_iterator.hasNext();) {
			Integer n = evaluation_iterator.next();
			N_Evaluation n_Evaluation = finalResults.n_evaluationMap.get(n);
			if(n_Evaluation == null)
				continue;
			n_Evaluation.averageRMES /= users_num;
			n_Evaluation.averageRunTime /= users_num;
			n_Evaluation.averageCoverage = (float)n_Evaluation.predictedItemsCount / blocks_num;
			finalResults.n_evaluationMap.replace(n, n_Evaluation);
		}
		display.DisplayResults(finalResults, logFileName + NeighbourSelectMethod + ".txt");
	}
}

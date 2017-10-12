package RecommendSys_ACF_test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

class Display {
	final static PrintStream screen_out = System.out;
	
	void init_log_file(String filename){
		try {
			File test_log = new File(filename);
			if(!test_log.exists()){
				
					test_log.createNewFile();
				
			}else{
				test_log.delete();
				test_log.createNewFile();
			}
			
			//PrintStream out = new PrintStream(new FileOutputStream(test_log));
			//System.setOut(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void change_log_file(String filename){
		try {
			File test_log = new File(filename);
			if(test_log.exists()){
				PrintStream out = new PrintStream(new FileOutputStream(test_log, true));
				System.setOut(out);
			}
			else{
				System.out.println("ERROR:LOG_FILE DOES NOT EXIST:" + filename);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void set_log_out_to_console(){
		System.setOut(screen_out);
	}	
	
	/*  Task_3: Display functions: Ovarloading.*/
	void DisplayResults(FinalResults finalResults, String log_file_name){
		init_log_file(log_file_name);
		change_log_file(log_file_name);
		System.out.println(String.format("user_num:%d,\titem_num:%d,\tn_start:%d,\tn_stop:%d,\tn_num:%d,\tMethod:%s,\tTotalRunTime:%.4fms\n", 
											finalResults.users_num, finalResults.items_num, finalResults.n_start, 
											finalResults.n_stop, finalResults.n_num,finalResults.NeighbourSelectApproach, finalResults.TotalRunTime));
		System.out.println(String.format("n\taverage_coverage\taverage_time(ms)\taverage_RMES"));
		// Iterate each n.
		/*Set<Integer> evaluation_set = finalResults.n_evaluationMap.keySet();
		for (Iterator<Integer> evaluation_iterator = evaluation_set.iterator(); evaluation_iterator.hasNext();) {
			Integer n = evaluation_iterator.next();
			N_Evaluation n_Evaluation = finalResults.n_evaluationMap.get(n);
			if (n_Evaluation == null)
				continue;
			System.out.println(String.format("%d,\t\t%.4f,\t\t%.2f,\t\t%.4f", 
												n, n_Evaluation.averageCoverage, n_Evaluation.averageRunTime, n_Evaluation.averageRMES));
		}*/
		for(Integer n = finalResults.n_start; n <= finalResults.n_stop; n++){
			N_Evaluation n_Evaluation = finalResults.n_evaluationMap.get(n);
			if (n_Evaluation == null)
				continue;
			System.out.println(String.format("%d,\t\t%.4f,\t\t%.2f,\t\t%.4f", 
					n, n_Evaluation.averageCoverage, n_Evaluation.averageRunTime, n_Evaluation.averageRMES));
		}
		set_log_out_to_console();
	}
	
	
	void DisplayResults(float[][][] DistanceMatrix, String log_file_name){
        init_log_file(log_file_name);
	    change_log_file(log_file_name);
        for(float[][] distancesrows : DistanceMatrix){
        	for(float[] user_user : distancesrows){
        		System.out.print(String.format("\t%.4f,%.1f;  ", user_user[0], user_user[1]));	
        	}
        	System.out.print("\n");
        }
        set_log_out_to_console();
	}
	
	Display(){
		
	}
}

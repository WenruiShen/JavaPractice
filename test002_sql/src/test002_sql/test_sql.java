package test002_sql;
import java.sql.*;

public class test_sql {
	public static void main(String[] args) {
		// Table-1: Poker Cards ASS-1
		MySQL_Poker_test Poker_sql = new MySQL_Poker_test(); 
		
		// Table-2: Poker Hands ASS-2
		MySQL_Poker_hands_test Poker_hands_sql = new MySQL_Poker_hands_test(); 
	}
}

class MySQL_Poker_test{
	
	String database = "ucd_test_20170126";
	String table_poker_cards = "poker_cards";
	
	String driver = "com.mysql.jdbc.Driver";
	String url_ser = "jdbc:mysql://localhost:3306/?useSSL=false";
	String url_db = "jdbc:mysql://localhost:3306/" + database + "?useSSL=false";
	String user = "root";
	String password = "SWRMYSQL";
	
	public MySQL_Poker_test(){
		System.out.println("MySQL_Poker_test Begin!");
		
		try{
			// Load driver
			Class.forName(driver);
			
			// Connect SQL Database
			Connection conn = DriverManager.getConnection(url_ser, user, password);
			if(!conn.isClosed())
				System.out.println("Successed connecting to the database server");

			// Statements allow to issue SQL queries to the database
			Statement statement = conn.createStatement();
			PreparedStatement preparedStatement = null;
			
			// Create this daatabase firstly, if database not exist.
			String sql_db_Create = "create database if not exists " + database;
			conn.createStatement().execute(sql_db_Create);
			System.out.println(sql_db_Create);

			// Choose this database.
			String sql_db_choose = "use " + database;
			conn.createStatement().execute(sql_db_choose);
			System.out.println(sql_db_choose);

			// Delete existing table if it has existed.
			String sql_table_delete = "drop table if exists " + table_poker_cards;
			conn.createStatement().execute(sql_table_delete);
			System.out.println(sql_table_delete);
			
			// Create table, if it does not exist.
			String sql_table_Create = "create table if not exists " + table_poker_cards 
					+ "(CardName char(4) ,"
					+ "Face tinyint(4) ,"
					+ "Type char(4) ," 
					+ "Suit char(4) ," 
					+ "FaceValue tinyint(4) ," 
					+ "GameValue tinyint(4) ," 
					+ "primary key(CardName) )";
			conn.createStatement().execute(sql_table_Create);
			System.out.println(sql_table_Create);
			
			// Result set get the result of the SQL query
			String sql = "select * from " + database + '.' + table_poker_cards;
			ResultSet rs = statement.executeQuery(sql);
			// Display Query results
			writeResultSet(rs);
			
			// Create 52 poker cards in the table
			poker_create(statement, preparedStatement, conn);
			
			rs = statement.executeQuery(sql);
			writeResultSet(rs);
			
			rs.close();
			conn.close();	
			
		} catch(ClassNotFoundException e){
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {	
			e.printStackTrace();
		}
		
	}
	
	private void writeResultSet(ResultSet rs) throws SQLException {
		// Display Query results
		String format_column = "%-10s %-10s %-10s %-10s %-10s %-10s \n";
		String format_row = "%-10s %-10d %-10s %-10s %-10d %-10d \n";
		System.out.format(format_column, "CardName", "Face", "Type", "Suit", "FaceValue", "GameValue");
		while(rs.next()){
			String CardName = rs.getString("CardName");
			int Face = rs.getInt("Face");
			String Type = rs.getString("Type");
			String Suit = rs.getString("Suit");
			int FaceValue = rs.getInt("FaceValue");
			int GameValue = rs.getInt("GameValue");
			System.out.format(format_row, CardName, Face, Type, Suit, FaceValue, GameValue);
			
		}
	}
	
	private void poker_create(Statement statement, PreparedStatement preparedStatement, Connection conn)
			throws SQLException {
		int cards_sum = 52;
		
		int cards_types_sum = 13;
		String cards_type_list[] = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
		
		int cards_suits_sum = 4;
		String cards_suit_list[] = {"H","S","D","C",};
		
		System.out.println("Now Creating a Poker!");
		for(int cards_suits_no = 0; cards_suits_no < cards_suit_list.length; cards_suits_no++){
			
			int card_face = (cards_suits_no == 0 | cards_suits_no == 1) ? 1 : 0;
			for(int cards_types_no = 0; cards_types_no < cards_type_list.length; cards_types_no++){
				
				String card_name = cards_type_list[cards_types_no] + cards_suit_list[cards_suits_no];
				String card_type = cards_type_list[cards_types_no];
				String card_suit = cards_suit_list[cards_suits_no];
				int face_value = cards_types_no + 1;
				int game_value = cards_types_no == 0 ? 14 : cards_types_no + 1;
				
				// check existing rows, avoiding duplicate
				String sql = "select * from " + database + '.' + table_poker_cards + " where CardName=\"" + card_name + "\"";
				ResultSet rs_check = statement.executeQuery(sql);				
				
				if(rs_check.next()) {
					System.out.println("deleting " + card_name);
					// delete existing record
					preparedStatement = conn.prepareStatement(
							"delete from " + database + '.' + table_poker_cards + " where CardName=\"" + card_name + "\"");
					preparedStatement.executeUpdate();
				}
				
				preparedStatement = conn.prepareStatement(
						"insert into " + database + '.' + table_poker_cards + " values (?, ?, ?, ? , ?, ?)");
				preparedStatement.setString(1, card_name);
				preparedStatement.setInt(2, card_face);
				preparedStatement.setString(3, card_type);
				preparedStatement.setString(4, card_suit);
				preparedStatement.setInt(5, face_value);
				preparedStatement.setInt(6, game_value);
				
				preparedStatement.executeUpdate();
			}
		}
		
		
	}
}

class MySQL_Poker_hands_test{
	String database = "ucd_test_20170126";	//"ucd_test_20170202";
	String table_poker_hands = "poker_hands";
	
	String driver = "com.mysql.jdbc.Driver";
	String url_ser = "jdbc:mysql://localhost:3306/?useSSL=false";
	String url_db = "jdbc:mysql://localhost:3306/" + database + "?useSSL=false";
	String user = "root";
	String password = "SWRMYSQL";
	
	public MySQL_Poker_hands_test(){
		System.out.println("MySQL_Poker_hands_test Begin!");
		
		try{
			// Load driver
			Class.forName(driver);
			
			// Connect SQL Database
			Connection conn = DriverManager.getConnection(url_ser, user, password);
			if(!conn.isClosed())
			{
				System.out.println("Successed connecting to the database server");
			}
			else
			{
				System.out.println("Failed to connect the database server");
			}

			// Statements allow to issue SQL queries to the database
			Statement statement = conn.createStatement();
			PreparedStatement preparedStatement = null;
			
			// Delete existing database if it has existed.
			/*String sql_db_delete = "drop database " + database;
			conn.createStatement().execute(sql_db_delete);
			System.out.println(sql_db_delete);*/
			
			// Create this daatabase firstly, if database not exist.	
			String sql_db_Create = "create database if not exists " + database;
			conn.createStatement().execute(sql_db_Create);
			System.out.println(sql_db_Create);
			
			// Choose this database. 
			String sql_db_choose = "use " + database;
			conn.createStatement().execute(sql_db_choose);
			System.out.println(sql_db_choose);
			
			// Delete existing table if it has existed.
			String sql_table_delete = "drop table if exists " + table_poker_hands;
			conn.createStatement().execute(sql_table_delete);
			System.out.println(sql_table_delete);
			
			// Create table, if it does not exist. 
			String sql_table_Create = "create table if not exists "
					+ table_poker_hands  
					+ "(Player_ID char(12) ,"
					+ "Game_ID char(12) ," 	// PRIMARY KEY,"
					+ "R1 char(4) ,"
					+ "R2 char(4) ,"
					+ "R3 char(4) ,"
					+ "R4 char(4) ,"
					+ "R5 char(4) ,"
					+ "primary key(Player_ID, Game_ID) )";
			conn.createStatement().execute(sql_table_Create);
			System.out.println(sql_table_Create);
	
			// Create 6 poker hands in the table
			table_insert_row(conn, "12789", "'17MET'", "'QH'", "'QS'", "'3D'", "'3C'", "'3H'");
			table_insert_row(conn, "12789", "'23WSA'", "'KH'", "'10H'", "'7H'", "'3H'", "'2H'");
			table_insert_row(conn, "90734", "'82SAT'", "'7C'", "'4S'", "'4D'", "'4C'", "'3H'");
			table_insert_row(conn, "84643", "'78GUV'", "'9H'", "'7C'", "'5H'", "'5C'", "'2S'");
			table_insert_row(conn, "56347", "'65YOB'", "'QH'", "'JH'", "'8C'", "'8S'", "'8D'");
			
			// ASS-WEEK-2 add 2 hands whose cards are not in order.
			//table_insert_row(conn, "90734", "'17MET'", "'2S'", "'5H'", "'9H'", "'5C'", "'7C'");
			//table_insert_row(conn, "12789", "'82SAT'", "'8S'", "'10C'", "'JH'", "'QH'", "'8D'");
			
			conn.close();	
			
		} catch(ClassNotFoundException e){
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {	
			e.printStackTrace();
		}
		
	}
	
	private void table_insert_row(Connection conn, String Player_ID, String Game_ID, String R1, String R2, 
									String R3, String R4, String R5)throws SQLException {
		String sql_table_insert =  "insert into " + database + '.' + table_poker_hands 
				+ " values (" + Player_ID + ", " + Game_ID + ", " + R1 + ", " + R2 + ", " + R3 + ", " + R4 + ", " + R5 + ")";
		conn.createStatement().execute(sql_table_insert);
		System.out.println(sql_table_insert);
	}	
}


class MySQL_ASS2_test{
	String database = "ucd_test_20170202";
	String table_poker_hands = "poker_hands";
	
	String driver = "com.mysql.jdbc.Driver";
	String url_ser = "jdbc:mysql://localhost:3306/?useSSL=false";
	String url_db = "jdbc:mysql://localhost:3306/" + database + "?useSSL=false";
	String user = "root";
	String password = "SWRMYSQL";
	
	public void MySQL_ASS2_test(){
		
	}
	
	
}

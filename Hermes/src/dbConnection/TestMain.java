package dbConnection;

import java.sql.*;

public class TestMain {

	public static void main(String[] args) {
		
		try {
			
			// connect to database
			//jdbc:<driver protocol>:<driver connection detils><database name>, username ,password
			//local
			Connection myConn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshop", "user", "pass");
			
			
			
			// statement
			
			Statement myStmt = myConn.createStatement();
			
			// prepared statements, set SQl paramete, prevents injection attaxks, may improve performance sine sql statement is pre-compiled
			
			PreparedStatement preMyStmt = myConn.prepareStatement("SELECT * FROM kund WHERE fornamn = ? ");
			
			// set parameters (?), using preMyStmt.set<type>(<index>, <value>)
			preMyStmt.setString(1, "Jake");
			
			// call stored procedures; här kan man använda ? också som tidigare samt set<type>(<index>, <value>).
			
			CallableStatement myCall = myConn.prepareCall("{CALL show_produkt_info()}");
			
			CallableStatement myCall2 = myConn.prepareCall("{CALL show_orderrad_for_order(?)}");

			
			
			// execute the statement with SQL query
			
			ResultSet myRs = myStmt.executeQuery("Select * from kund");
			
			// execute the prepared statement
			
			ResultSet preMyRs = preMyStmt.executeQuery();
			
			// execute callableStatement, om det finns mer än ett result set, använd loop, myCall.execute(), loop { callMys.getResultset()  }
			// https://stackoverflow.com/questions/9696572/queries-returning-multiple-result-sets
			ResultSet callMyRs = myCall.executeQuery();
			
			myCall2.setInt(1, 2);
			ResultSet call2MyRs = myCall2.executeQuery();
			
			
			// process result set
			
		
			while(myRs.next()) {
				System.out.println(myRs.getString("fornamn") + " "+ (myRs.getString("efternamn")));
				
			}
			
			while(preMyRs.next()) {
				System.out.println(preMyRs.getString("fornamn") + " "+ (preMyRs.getString("efternamn")));
				
			}
			
			while(callMyRs.next()) {
				System.out.println(callMyRs.getString("beskrivning") + " "+ (callMyRs.getString("pris")));
				
			}
			
			System.out.println("id  antal  beskrivning");
			while(call2MyRs.next()) {
				
				System.out.println(call2MyRs.getInt("produktId") + " "+ call2MyRs.getInt("antal") + " " + call2MyRs.getString("beskrivning"));
			}
		
		// test
			System.out.println();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

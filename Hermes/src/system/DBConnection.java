package system;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;

public class DBConnection {


	private String userName;
	private String PW;
	private Connection myConn;

	DBConnection() {

		try {
			myConn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/hermes", "user", "pass");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPW() {
		return PW;
	}

	public void setPW(String pW) {
		PW = pW;
	}

	public Connection getMyConn() {
		return myConn;
	}

	public void setMyConn(Connection myConn) {
		this.myConn = myConn;
	}

	DBConnection(String userName){
		this.userName = userName;
	}

	public String getPW(String userName) throws SQLException {

		CallableStatement myCall = myConn.prepareCall("{CALL get_pass(?)}");

		myCall.setString(1, userName);

		ResultSet myRs = myCall.executeQuery();
		myRs.next();

		String password = myRs.getString("password");



		return password;

	}

	public void stampIn(int uId) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL stamp_in(?)}");
			myCall.setInt(1, uId);

			myCall.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void stampOutn(int uId) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL stamp_out(?)}");
			myCall.setInt(1, uId);

			myCall.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void reportAbscence(int uId, String abscence) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL report_abscence(?, ?)}");
			myCall.setInt(uId, 1);
			myCall.setString(2, abscence);

			myCall.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * adding a user, then returning the id for that user
	 */

	public int userCreate(int classId, int shiftId, int hourlyPay,int managerId, String firstName, String lastName, String adress, String phone, String socialSecurityNumber) {
		int userId = 0;

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL user_create(?, ? , ?, ? , ?, ?, ? , ?, ?, ?)}");
			myCall.setInt(1, classId);
			myCall.setInt(2, shiftId);
			myCall.setInt(3, hourlyPay);
			myCall.setInt(4, managerId);
			myCall.setString(5, firstName);
			myCall.setString(6, lastName);
			myCall.setString(7, adress);
			myCall.setString(8, phone);
			myCall.setString(9, socialSecurityNumber);
			myCall.registerOutParameter(10, Types.INTEGER);
			myCall.setInt(10, userId);

			myCall.execute();

			userId = myCall.getInt(10);



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return userId;
	}


	public ArrayList <String []> getUsernames() {
		ArrayList <String []> usernames = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_usernames()}");


			ResultSet myRs = myCall.executeQuery();
			usernames = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		return usernames;
	}

	public void loginCreate(int uId, String username, String password) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL login_create(?, ?, ?)}");
			myCall.setInt(1, uId);
			myCall.setString(2, username);
			myCall.setString(3, password);

			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	/**
	 * 
	 * @param uId, which is the userId
	 * @return String [] is a row, in this case,
	 * start index 0 is userid, 1 name, 2 adress, 3 number, 4 socials, 5 shift, 6 role, 7 mangerid, 8 mangerName
	 *
	 */
	public String [] getUserInfo(int uId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_user_info(?)}");
			myCall.setInt(1, uId);

			ResultSet myRs = myCall.executeQuery();
			info = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		return info.get(0);
	}

	public void addScheduledPass(int uId, String start, String stop, String currentDate) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL add_scehduled_pass(?, ?, ?, ?)}");
			myCall.setInt(1, uId);
			myCall.setString(2, start);
			myCall.setString(3, stop);
			myCall.setString(4, currentDate);

			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


	public void editTimeReport(String start, String stop, String currentDate, int timeId) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL add_scehduled_pass(?, ?, ?, ?)}");
			myCall.setInt(4, timeId);
			myCall.setString(1, start);
			myCall.setString(2, stop);
			myCall.setString(3, currentDate);

			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public ArrayList <String []> getTimeReport(int uId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_time_report(?)}");
			myCall.setInt(1, uId);

			ResultSet myRs = myCall.executeQuery();
			info = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		return info;
	}


	public ArrayList <String []> getScheduledPass(int uId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_scheduled_pass(?)}");
			myCall.setInt(1, uId);

			ResultSet myRs = myCall.executeQuery();
			info = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		return info;
	}

	public ArrayList <String []> getToDateScheduledPass(int uId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_to_date_scheduled_pass(?)}");
			myCall.setInt(1, uId);

			ResultSet myRs = myCall.executeQuery();
			info = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		return info;
	}

	public void projectCreate(String start, String stop, String goal, int budg, String status) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL project_create(?, ?, ?, ?, ?)}");
			myCall.setInt(4, budg);
			myCall.setString(1, start);
			myCall.setString(2, stop);
			myCall.setString(3, goal);
			myCall.setString(5, status);

			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public void addUserToProject(int projectId, int userId) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL project_add_user(?, ?)}");
			myCall.setInt(1, userId);
			myCall.setInt(2, projectId);

			

			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	public void addScheduledActivities(int projectId, String starts, String stops, String currDate) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL scheduled_activities_add(?, ?, ?, ?)}");
			myCall.setInt(1, projectId);
			myCall.setString(2, starts);
			myCall.setString(3, stops);
			myCall.setString(4, currDate);

			

			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	
	public ArrayList <String []> getProcjectActivities(int projectId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_project_activities(?)}");
			myCall.setInt(1, projectId);

			ResultSet myRs = myCall.executeQuery();
			info = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		return info;
	}
	
	public ArrayList <String []> getProcjectActivitiesForUser(int userId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_project_activities_user(?)}");
			myCall.setInt(1, userId);

			ResultSet myRs = myCall.executeQuery();
			info = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		return info;
	}
	
	public int getUserIdByUsername(String username) {
		int userId = -1;
		ArrayList <String []> info = new ArrayList<String []>();

		
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_user_id_by_username(?)}");
			myCall.setString(1, username);
			ResultSet myRs = myCall.executeQuery();
			info = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		userId = Integer.parseInt(info.get(0)[0]);

		return userId;
	};
	
	


	private static ArrayList <String []>  getAllAsList(ResultSet rS) {
		ArrayList <String []> resultList = new ArrayList<String []>();
		String [] ColumnNames = getColumnNames(rS);



		try {
			while(rS.next()) {
				String [] row = new String [ColumnNames.length]; 
				for(int i = 0; i < ColumnNames.length; i++) {
					row[i] = rS.getString(ColumnNames[i]);
					if(i != ColumnNames.length) {

					}

				}
				resultList.add(row);


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}




		return resultList;

	}

	private static String [] getColumnNames(ResultSet rS) {

		ResultSetMetaData meta;
		int nColumns;
		String [] columnNames ;
		try {
			meta = rS.getMetaData();
			nColumns = meta.getColumnCount();
			columnNames  = new String [nColumns];
			for (int i = 1; i <= nColumns; i++ ) {
				columnNames[i-1] = meta.getColumnName(i);
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return columnNames;

	}


	//databas måste implimenteras först
	public int getEmployeeType() throws NumberFormatException, SQLException{
		int userType = Integer.valueOf(String.valueOf(myConn.prepareStatement("SELECT classificationID FROM user WHERE userName = " + this.userName))); // 1=Worker, 2=ProjectManager & 3=DepartmentManager
		//(?= CALL procedure_employeeType);
		return userType;
	}
}





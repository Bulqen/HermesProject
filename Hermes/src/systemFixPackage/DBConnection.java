package systemFixPackage;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;

public class DBConnection {


	private String userName;
	private String PW;
	private Connection myConn;

	//Kan �ndra till icke public sen
	public DBConnection() {

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
	/**
	 *
	 * @param userName
	 * @return String, password
	 * the function is not case-sensitive , USeR and user will result in same password
	 * @throws SQLException
	 */

	public String getPW(String userName) throws SQLException {
		String password = null;

		CallableStatement myCall = myConn.prepareCall("{CALL get_pass(?)}");

		myCall.setString(1, userName);

		ResultSet myRs = myCall.executeQuery();
		if(myRs.next()) {
			password = myRs.getString("password");
		}

		return password;

	}
	/**
	 *
	 * @param uId
	 * Will stamp in userId att currentTime att currentDate, can be triggered more than one time
	 */

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

	/**
	 *
	 * @param uId
	 * Will stamp out current userId where it has stamped in already, finishes the open passes
	 */

	public void stampOut(int uId) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL stamp_out(?)}");
			myCall.setInt(1, uId);

			myCall.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 *
	 * @param uId
	 * @param abscence
	 * @param comment
	 * Will report an abscence on current day!, can´t be executed twice the same day!
	 */

	public void reportAbscence(int uId, String abscence, String comment) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL report_abscence(?, ?, ?)}");
			myCall.setInt(uId, 1);
			myCall.setString(2, abscence);
			myCall.setString(3, comment);

			myCall.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 *
	 * @param classId
	 * @param shiftId
	 * @param hourlyPay
	 * @param managerId
	 * @param firstName
	 * @param lastName
	 * @param adress
	 * @param phone
	 * @param socialSecurityNumber
	 * @return the userId that user has
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

	/**
	 *
	 * @return all usernames that is currently in database in ArrayList <String []>
	 */
	public ArrayList <String []> getUsernames() {
		ArrayList <String []> usernames = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_usernames()}");


			ResultSet myRs = myCall.executeQuery();
			if(this.check(myRs))
				usernames = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return usernames;
	}
	/**
	 *
	 * @param uId
	 * @param username
	 * @param password
	 * Adds new login to database, will be error if username already exists, since username is PK
	 */

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
	 * start index 0 is userid, 1 name, 2 adress, 3 number, 4 socials, 5 shift, 6 role, 7 mangerid, 8 mangerName, 9 hourlypay, 10 classificationId
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
	/**
	 *
	 * @param uId
	 * @param start
	 * @param stop
	 * @param currentDate
	 * adding a pass for a certain employee
	 */

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
	/**
	 *
	 * @param tId
	 * Removes a time report from database by providing it´s id
	 */

	public void deleteTimeReport(int tId) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL delete_time_report(?)}");
			myCall.setInt(1, tId);


			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *
	 * @param uId
	 * @param start
	 * @param stop
	 * @param absence
	 * @param date
	 * @param comment
	 * Adding a new time report, probably used for backdating
	 */
	public void addTimeReport(int uId, String start, String stop, String absence, String date, String comment ) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL add_time_report(?, ?, ?, ?, ?, ?)}");
			myCall.setInt(1, uId);
			myCall.setString(2, start);
			myCall.setString(3, stop);
			myCall.setString(4, absence);
			myCall.setString(5, date);
			myCall.setString(6, comment);

			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 13:44:33 (start, stop) g�r en lista anv�ndaren kan �ndra i
	/**
	 *
	 * @param start
	 * @param stop
	 * @param currentDate
	 * @param timeId
	 * @param comment
	 * Edit current timereport by providing information and the ID of that particular time report
	 */

	public void editTimeReport(String start, String stop, String currentDate, int timeId, String comment) {
		try {
			//Ska det verkligen va add_scheduled_pass h�r?
			CallableStatement myCall = myConn.prepareCall("{CALL add_scehduled_pass(?, ?, ?, ?, ?)}");
			myCall.setInt(5, timeId);
			myCall.setString(1, start);
			myCall.setString(2, stop);
			myCall.setString(3, currentDate);
			myCall.setString(4, comment);

			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param uId
	 * @return ArrayList <String []> of all time reports on that specific user
	 * [0] id, [1] userId, [2] inTime, [3] outTime, [4] abscence, [5] currentDate, [6] comment
 	 */
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

	/**
	 *
	 * @param uId
	 * @return ArrayList <String []>
	 *  [0] userId, [1] inTime, [2] outTime, [3] currentDate
	 */

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
	/**
	 *
	 * @param uId
	 * @return ArrayList <String []>
	 * only returns all scheduled passes from today and forth
	 *  [0] userId, [1] inTime, [2] outTime, [3] currentDate
	 */

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
	/**
	 *
	 * @param start
	 * @param stop
	 * @param goal
	 * @param budg
	 * @param status
	 * Creates a project
	 */

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
	/**
	 *
	 * @param projectId
	 * @param userId
	 * Adds a user to a specific project
	 */

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

	/**
	 *
	 * @param projectId
	 * @param starts
	 * @param stops
	 * @param currDate
	 * Add a scheduled activity for a specific project
	 */

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

	/**
	 *
	 * @param projectId
	 * @return ArrayList <String []>
	 * [0] id, [1] startDate, [2] endDate, [3] goals, [4] budget, [5] status
	 */

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

	/**
	 *
	 * @param projectId
	 * @return ArrayList <String []>
	 * get all project scheduled activities for one user
	 * [0] userId, [1] projectId, [2] startTime , [3] stoppTime, [4] theDate
	 */

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

	/**
	 *
	 * @param username
	 * @return [] String
	 * all usernames in the database
	 */
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
	/**
	 *
	 * @param userId
	 * @return [] String
	 * [0] totalHours, [1] hourlyPay, [2] YYYY-MM, [3] pay, [4] namn, (funkar inte för nattskift just nu...)
	 */

	public String [] generateSalaryslip(int userId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL generate_salaryslip(?)}");
			myCall.setInt(1, userId);

			ResultSet myRs = myCall.executeQuery();
			info = getAllAsList(myRs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return info.get(0);
	}




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


	private boolean check(ResultSet res) {

		if(res != null)
			return true;
		else
			return false;

	}


	//databas måste implimenteras först
	public int getEmployeeType() throws NumberFormatException, SQLException{
		int userType = Integer.valueOf(String.valueOf(myConn.prepareStatement("SELECT classificationID FROM user WHERE userName = " + this.userName))); // 1=Worker, 2=ProjectManager & 3=DepartmentManager
		//(?= CALL procedure_employeeType);
		return userType;
	}
}





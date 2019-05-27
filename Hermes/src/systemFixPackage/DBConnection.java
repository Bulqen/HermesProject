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
	 * @param cId
	 * @param shiftId
	 * @param hourlypay
	 * @param manId
	 * @param firstName
	 * @param lastName
	 * @param adress
	 * @param phone
	 * @param socialSec
	 */
	public void editUser(int uId, int cId,int shiftId,int hourlypay,int manId, String firstName, String lastName, String adress, String phone, String socialSec  ) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL edit_user(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			myCall.setInt(1, uId);
			myCall.setInt(2, cId);
			myCall.setInt(3, shiftId);
			myCall.setInt(4, hourlypay);
			myCall.setInt(5, manId);
			myCall.setString(6, firstName);
			myCall.setString(7, lastName);
			myCall.setString(8, adress);
			myCall.setString(9, phone);
			myCall.setString(10, socialSec);



			myCall.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 *
	 * @param uId
	 * deleting user, login, time_reports and removes from projects
	 */

	public void deletUser(int uId) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL delete_user(?)}");
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
			myCall.setInt(1, uId);
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
	 * @param uId
	 * @param start
	 * @param end
	 * @param comment
	 * will report vacation between the dates (including)
	 */
	
	public void applyVacationDates(int uId, String start, String end, String comment) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL apply_vacation_dates(?, ?, ?, ?)}");
			myCall.setInt(4, uId);
			myCall.setString(1, start);
			myCall.setString(2, end);
			myCall.setString(3, comment);

			myCall.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param uId
	 * @param newPw
	 * Changing pw of user.
	 */

	public void changePW(int uId, String newPw) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL change_pw(?, ?)}");
			myCall.setInt(1, uId);
			myCall.setString(2, newPw);

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
	 * start index 0 is userid, 1 name, 2 adress, 3 number, 4 socials, 5 shift, 6 role, 7 mangerid, 8 mangerName, 9 hourlypay, 10 classificationId, 11 shiftId
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
	 * @param projectId
	 * @return
	 */
	public String [] getProjectInfo(int projectId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {

			CallableStatement myCall = myConn.prepareCall("{CALL get_project(?)}");
			myCall.setInt(1, projectId);

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
	 *
	 * @return String [] is a row, in this case,
	 * start index 0 is userid, 1 name, 2 adress, 3 number, 4 socials, 5 shift, 6 role, 7 mangerid, 8 mangerName, 9 hourlypay, 10 classificationId, 11 shiftId
	 *
	 */

	public ArrayList <String []> getAllUsers() {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_all_users()}");


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
	 *
	 * @return String [] is a row, in this case,
	 * start index 0 is userid, 1 name, 2 adress, 3 number, 4 socials, 5 shift, 6 role, 7 mangerid, 8 mangerName, 9 hourlypay, 10 classificationId, 11 shiftId
	 *
	 */
	
	public ArrayList <String []> getUsersByManager(int managerId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_users_by_manager(?)}");
			myCall.setInt(1, managerId);

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
	 * String [] is a row, in this case,
	 * index 0 is userid, 1 name, 2 adress, 3 number, 4 socials, 5 shift, 6 role, 7 mangerid, 8 mangerName, 9 hourlypay, 10 classificationId, 11 shiftId
	 */
	public ArrayList <String []> getUsersByProject(int projectId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_users_by_project(?)}");
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
	 * @return users not in project id, namn
	 */
	
	public ArrayList <String []> getUsersNotInProject(int projectId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_users_not_in_project(?)}");
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
	 * @param managerId
	 * @return ArrayList <String []> 
	 * String [] is a row, in this case,
	 * index 0 is userid, 1 name, 2 adress, 3 number, 4 socials, 5 shift, 6 role, 7 mangerid, 8 mangerName, 9 hourlypay, 10 classificationId, 11 shiftId
	 */
	
	public ArrayList <String []> getUsersByProjectManager(int managerId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_users_by_project(?)}");
			myCall.setInt(1, managerId);

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
	 * @param start
	 * @param stop
	 * @param currentDate
	 * adding a pass for a certain employee
	 */

	public void addScheduledPass(int uId, String start, String stop, String currentDate, String description) {
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL add_scehduled_pass(?, ?, ?, ?, ?)}");
			myCall.setInt(1, uId);
			myCall.setString(2, start);
			myCall.setString(3, stop);
			myCall.setString(4, currentDate);
			myCall.setString(5, description);

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
			CallableStatement myCall = myConn.prepareCall("{CALL edit_time_report(?, ?, ?, ?, ?)}");
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
	 * @return ArrayList <String []> of all time reports on that specific user.
	 * [0] id, [1] userId, [2] inTime, [3] outTime, [4] abscence, [5] currentDate, [6] comment, [7] hours
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
	 * @return ArrayList <String []> of all time reports on that specific user, within intervall (including intervall)
	 * [0] id, [1] userId, [2] inTime, [3] outTime, [4] abscence, [5] currentDate, [6] comment, [7] hours
	 * 
	 */
	
	public ArrayList <String []> getTimeReportIntervall(int uId, String start, String end) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_time_report_intervall(?,?,?)}");
			myCall.setInt(1, uId);
			myCall.setString(2, start);
			myCall.setString(3, end);

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

	public void projectCreate(String start, String stop, String goal, int budg, String status, String namn, int managerId) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL project_create(?, ?, ?, ?, ?, ?, ?)}");
			myCall.setInt(4, budg);
			myCall.setString(1, start);
			myCall.setString(2, stop);
			myCall.setString(3, goal);
			myCall.setString(5, status);
			myCall.setString(6, namn);
			myCall.setInt(7, managerId);

			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 
	 * @param managerId
	 * @return projectId
	 */
	public int getProjectByManager(int managerId) {
		int projectId = -2;
		ArrayList <String []> info = new ArrayList<String []>();
		try {
			CallableStatement myCall = myConn.prepareCall("{CALL get_project_by_manager(?)}");
			myCall.setInt(1, managerId);
			

			
			ResultSet myRs = myCall.executeQuery();
			info = getAllAsList(myRs);
			if(!info.isEmpty())
				projectId = Integer.parseInt(info.get(0)[0]);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return projectId;
		
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
	 * @param userId
	 */
	
	public void removeUserToProject(int projectId, int userId) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL remove_from_project(?, ?)}");
			myCall.setInt(1, projectId);
			myCall.setInt(2, userId);
	


			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 
	 * @param projectId
	 * @param start
	 * @param stop
	 * @param goal
	 * @param budg
	 * @param status
	 * @param namn
	 * @param managerId
	 */
	
	public void editProject(int projectId, String start, String stop, String goal, int budg, String status, String namn, int managerId) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL project_edit(?, ?, ?, ?, ?, ?, ?, ?)}");
			myCall.setInt(4, budg);
			myCall.setString(1, start);
			myCall.setString(2, stop);
			myCall.setString(3, goal);
			myCall.setString(5, status);
			myCall.setString(6, namn);
			myCall.setInt(7, managerId);
			myCall.setInt(8, projectId);

			myCall.executeUpdate();
			
			



			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void finishProject(int projectId) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL finish_Project(?)}");
			
			myCall.setInt(1, projectId);

			myCall.executeUpdate();
			
			



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
	 * @param starts
	 * @param stops
	 * @param currDate
	 * @param scheduleId
	 * editar activiteten med scheduleid.
	 */
	
	public void editScheduledActivities(int projectId, String starts, String stops, String currDate, int scheduleId, String description) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL edit_scheduled_activities(?, ?, ?, ?, ?, ?)}");
			myCall.setInt(1, scheduleId);
			myCall.setString(2, starts);
			myCall.setString(3, stops);
			myCall.setString(4, currDate);
			myCall.setInt(5, projectId);
			myCall.setString(6, description);



			myCall.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void removeScheduledActivities(int scheduleId) {

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL remove_scheduled_activities(?)}");
			myCall.setInt(1, scheduleId);
			



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
	 * [0] id, [1] startDate, [2] endDate, [3] currentDate, [4] projectID, [5] description
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
	 * [0] userId, [1] projectId, [2] startTime , [3] stoppTime, [4] theDate, [5] id
	 */

	public ArrayList <String []> getProjectActivitiesForUser(int userId) {
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
	 * [0] totalHours, [1] hourlyPay, [2] YYYY-MM, [3] pay, [4] namn, 
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
	/**
	 * 
	 * @param userId
	 * @return [] String
	 * [0] startTime, [1] endTime, [2] startDay, [3] endDay, [4] type-shift , ex( [0] "08:00",[1]"17:00", [2] "6",[3] "7", [4] "helg"), 1-7, mån-sön
	 */
	
	public String [] generateSchedule(int userId) {
		ArrayList <String []> info = new ArrayList<String []>();

		try {
			CallableStatement myCall = myConn.prepareCall("{CALL generate_schedule(?)}");
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





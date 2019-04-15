package system;

import java.util.ArrayList;

public class Project {

	//ta bort
	private String start;
	private String stop;
	private String goal; 
	private int budg;
	private String status;
	private int projectID;
	
	public Project(String start, String stop, String goal, int budg, String status, int projectID){
		this.start = start;
		this.stop = stop;
		this.goal = goal;
		this.budg = budg;
		this.status = status;
	}
	
	public Project(int userID){
		DBConnection dbc = new DBConnection();
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public int getBudg() {
		return budg;
	}

	public void setBudg(int budg) {
		this.budg = budg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void saveProject(){
		DBConnection dbc = new DBConnection();
		dbc.projectCreate(start, stop, goal, budg, status);
	}
	
	public void addUser(int userID){
		DBConnection dbc = new DBConnection();
		dbc.addUserToProject(projectID, userID);
	}
	
	public void addActivity(String startTime, String endTime, String date){
		DBConnection dbc = new DBConnection();
		dbc.addScheduledActivities(projectID, startTime, endTime, date);
	}
	
	public ArrayList<String[]> getActivities(){
		DBConnection dbc = new DBConnection();
		return dbc.getProcjectActivities(projectID);
	}
	
	
}

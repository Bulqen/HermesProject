package system;

import java.sql.Date;
import java.util.ArrayList;

public abstract class User{

	private int userId;
	private String name;
	private String adress;
	private int number;
	private String socials;
	private int shiftId;
	private String role;
	private String managerName;
	private double hourlySalary;
	private int classificationID;

	public User(int userId, String name, String adress, int number,String socials, int shiftId, int role,
			String managerName, double hourlySalary,int classificationID){
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getSocials() {
		return socials;
	}

	public void setSocials(String socials) {
		this.socials = socials;
	}

	public int getShiftId() {
		return shiftId;
	}

	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getClassificationID() {
		return classificationID;
	}

	public void setClassificationID(int classificationID) {
		this.classificationID = classificationID;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getuserName() {
		return name;
	}

	public void setUserName(String userName) {
		this.name = userName;
	}

	public double getHourlySalary() {
		return hourlySalary;
	}

	public void setHourlySalary(double salary) {
		this.hourlySalary = salary;
	}
	
	public String[] getSalarySlip(){
		
		DBConnection DBC = new DBConnection();
		ArrayList <String []> info = DBC.getTimeReport(this.userId);
		
		double totalSalary = 0;
		
		for (int i=0; i<info.size(); i++){
			Date inDate = Date.parse(info[i][2]);
			Date outDate = 
					
					
		}
		
	}


}

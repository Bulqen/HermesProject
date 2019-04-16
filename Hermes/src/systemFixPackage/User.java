package systemFixPackage;

public abstract class User{

	private int userId;
	private String name;
	private String adress;
	private String number;
	private String socials;
	private String shiftId;
	private String role;
	private String managerName;
	private String managerID;
	private String hourlySalary;
	private String classificationID;
	private String userName;

	public User(int userId, String name, String adress, String number, String socials, String shiftId, String role,
			String managerName, String hourlySalary, String classificationID,String managerID, String userName) {
		this.userId = userId;
		this.name = name;
		this.adress = adress;
		this.number = number;
		this.socials = socials;
		this.shiftId = shiftId;
		this.role = role;
		this.managerName = managerName;
		this.hourlySalary = hourlySalary;
		this.classificationID = classificationID;
		this.managerID = managerID;
		this.userName = userName;
	}

	public User(int userId, String name, String adress, String number, String socials, String shiftId, String role,
			String managerName, String hourlySalary, String classificationID) {
		this.userId = userId;
		this.name = name;
		this.adress = adress;
		this.number = number;
		this.socials = socials;
		this.shiftId = shiftId;
		this.role = role;
		this.managerName = managerName;
		this.hourlySalary = hourlySalary;
		this.classificationID = classificationID;
	}

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSocials() {
		return socials;
	}

	public void setSocials(String socials) {
		this.socials = socials;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getClassificationID() {
		return classificationID;
	}

	public void setClassificationID(String classificationID) {
		this.classificationID = classificationID;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHourlySalary() {
		return hourlySalary;
	}

	public void setHourlySalary(String salary) {
		this.hourlySalary = salary;
	}


}

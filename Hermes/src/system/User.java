package system;

public abstract class User{
	
	private int userId;
	private String name;
	private String adress;
	private int number;
	private String socials;
	private int shiftId;
	private int role;
	private String managerName;
	private double hourlySalary; // finns i databasen men jakes metod hämtar inte denna ännu
	
	public User(int userId, String name, String aress, int number,String socials, int shiftId, int role,
			String managerName, double hourlySalary){
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

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
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
	
	
}

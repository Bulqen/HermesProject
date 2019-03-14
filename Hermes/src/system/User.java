package system;

public class User{
	
	private String userName;
	private String password;
	private double HourlySalary;
	
	public User(){
		
	}
	
	public User(String userName){
		this.userName = userName;
	}

	public String getuserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getHourlySalary() {
		return HourlySalary;
	}

	public void setHourlySalary(double salary) {
		this.HourlySalary = salary;
	}
	
	
}

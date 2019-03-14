package system;

public class UserFactory {
	
	private String userName;
	
	public UserFactory(String userName){
		this.userName = userName;
	}
	
	public User createUser(){
		User theUser = new User(this.userName);
		return theUser;
	}
	
	public int getUserType(){
		
		DBConnection DB = new DBConnection();
		return DB.getEmployeeType(); // 1=Worker, 2=ProjectManager & 3=DepartmentManager
	}
	
	
}

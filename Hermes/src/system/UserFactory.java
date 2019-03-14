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
	
}

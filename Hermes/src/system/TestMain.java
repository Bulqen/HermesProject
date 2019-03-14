package system;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UserFactory theFactory = new UserFactory("felixdrangel");
		
		User theUser = theFactory.createUser();
		System.out.println(theUser.getuserName());
		
		
	}

}

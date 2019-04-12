package system;

import java.util.ArrayList;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		UserFactory theFactory = new UserFactory("felixdrangel");
		
//		User theUser = theFactory.createUser();
//		System.out.println(theUser.getuserName());
		
		DBConnection DB = new DBConnection();
		
//		int id = DB.userCreate(1, 1, 2, 1, "Kalle", "kommunist"," MOSKVA", "9874289982", "9781237982");
//		
//		System.out.println(id);
//		
		ArrayList <String []> resultList = new ArrayList<String []>();
		
		resultList = DB.getUsernames();
		
		
		display(resultList);
		
		String [] row = DB.getUserInfo(1);
		
		
		for(String val : row) {
			System.out.print(val+ " ");
		}
		
		
//fsdds
		
		//System.out.println(theFactory.getUserType().toString());
		//commit
		
		
		Worker a = new Worker();
		
	}
	
	public static void display (ArrayList <String []> resultList) {
		for(int i = 0; i < resultList.size(); i++) {
			for(int k = 0; k < resultList.get(i).length; k++) {
				System.out.print(resultList.get(i)[k] +" ");
			}
			System.out.println();
		}
	}

}

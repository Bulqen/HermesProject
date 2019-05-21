package refactor;

import systemFixPackage.timeReport;

public class AddOrEditActivityToProjectController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (AddOrEditActivityToProject)");
	}

	/*
	 * Your code should be below this
	 */
}

package refactor;

import systemFixPackage.timeReport;

public class ApplyVacationController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (ApplyVacation)");
	}

	/*
	 * Your code should be below this
	 */
}
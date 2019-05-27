package refactor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import systemFixPackage.timeReport;

public class ShowScheduleController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (ShowSchedule)");
	}


	@FXML
    private GridPane grid;
	public void test(){

		for(int i = 0; i <24; i++){
			Label time = new Label();
			time.setFont(new Font("System", 12));
			time.setTextFill(Color.web("#87cefa"));
			String text = i+":00";
			time.setText(text);
			grid.add(time,0,i);
		}



	}
}

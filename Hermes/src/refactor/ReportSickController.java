package refactor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import systemFixPackage.timeReport;

public class ReportSickController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
	}

    @FXML
    private Pane reportSick;

    @FXML
    private Button CallInSickButton;

    @FXML
    private TextArea commentOnWhySick;

    public void setStatusOfSickDayButton(){
    	commentOnWhySick.setWrapText(true);
		CallInSickButton.setDisable((CallInSickIsTrue()));
		commentOnWhySick.clear();
	}

	private boolean CallInSickIsTrue(){
		ArrayList <String []> info = this.timeReporter.getTimeReport(mainC.getUser().getUserId());
		boolean isTrue = false;

		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

		for(int i = 0; i<info.size(); i++){
			if(info.get(i)[5].equals(ft.format(dNow)) && info.get(i)[4] != null){
				isTrue = true;
			}
			if(!mainC.getStatusOfStampOutButton()){
				isTrue = false;
			}
			/*
			else if(info.get(i)[4] == null){
				isTrue = false;
			}
			else{
				isTrue = true;
			}
			*/
		}

		return isTrue;
	}

	@FXML
	private void callInSick(ActionEvent event){
		if(this.commentOnWhySick.getText().length() > 200){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("Messages has to be shorter then 200 characters");
			enterAlert.showAndWait();
			return;
		}
		else{
			this.timeReporter.recordAbsence(mainC.getUser().getUserId(), "Sick", commentOnWhySick.getText());
			commentOnWhySick.clear();
			setStatusOfSickDayButton();
		}
	}
}

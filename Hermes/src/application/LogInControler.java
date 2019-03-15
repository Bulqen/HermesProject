package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogInControler implements Initializable{

	@FXML
	private AnchorPane parent;

	@FXML
	private HBox top;

	@FXML
	private Pane content;

	@FXML
	private TextField userName;

	@FXML
	private PasswordField password;


	private double xOffSet = 0;
	private double yOffSet = 0;

	private Node node;
	private Stage stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		makeStageDragable();

	}

	private void makeStageDragable(){
		parent.setOnMousePressed((event) -> {
			node = (Node) event.getSource();
	        stage = (Stage) node.getScene().getWindow();
			xOffSet = event.getSceneX();
			yOffSet = event.getSceneY();
		});

		parent.setOnMouseDragged((event) -> {
	        stage.setX(event.getScreenX() - xOffSet);
	        stage.setY(event.getScreenY() - yOffSet);
	        stage.setOpacity(0.8f);
		});

		parent.setOnDragDone((event) -> {
	        stage.setOpacity(1.0f);
		});

		parent.setOnMouseReleased((event) -> {
	        stage.setOpacity(1.0f);
		});


	}

	@FXML
	private void minimize_stage(MouseEvent event){
        //stage.setMaximized(true);
        stage.setIconified(true);
	}

	@FXML
	private void close_app(MouseEvent event){
		System.exit(0);

	}

	@FXML
	private void handle_login(ActionEvent event){


		/*
		 * test
		String enteredUserName = userName.getText();
		String enteredPassword = password.getText();
		*/

		String enteredUserName = "Erik";
		String enteredPassword = "12";

		String user = "Erik";
		String pass = "12";

		if(enteredUserName.isEmpty() || enteredPassword.isEmpty()){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("Not all information is entered");
			enterAlert.showAndWait();
		}
		else{
			if(enteredUserName.equals(user) && enteredPassword.equals(pass))
			{
				Alert enterAlert = new Alert(AlertType.INFORMATION);
				enterAlert.setHeaderText(null);
				enterAlert.setContentText("Successfully logged in");
				enterAlert.showAndWait();

				//add you loading or delays - ;-)
                node = (Node) event.getSource();
                stage = (Stage) node.getScene().getWindow();

                //stage.setMaximized(true);
                stage.close();

				try {
					/*
					Stage stage1 = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("menuGui.fxml"));
					Scene scene = new Scene(root);
	    			stage1.initStyle(StageStyle.UNDECORATED);
	    			stage1.setScene(scene);
	    			stage1.show();
	    			*/

					FXMLLoader loader = new FXMLLoader(getClass().getResource("menuGui.fxml"));
					Parent root = (Parent) loader.load();

					menuController nextStep = loader.getController();
					nextStep.setUserName(enteredUserName);

					Stage stage1 = new Stage();
					stage1.initStyle(StageStyle.UNDECORATED);
	    			stage1.setScene(new Scene(root));
	    			stage1.show();


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}




			}
			else{
				Alert enterAlert = new Alert(AlertType.ERROR);
				enterAlert.setHeaderText(null);
				enterAlert.setContentText("The entered credentials did not match");
				enterAlert.showAndWait();
			}
		}
	}

}

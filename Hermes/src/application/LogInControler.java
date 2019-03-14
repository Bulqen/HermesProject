package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LogInControler implements Initializable{

	@FXML
	private AnchorPane parent;

	@FXML
	private HBox top;

	@FXML
	private Pane content;

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
	private void handle_login(MouseEvent event){
		System.out.println("Erik");
	}

}

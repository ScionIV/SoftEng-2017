package userpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;



public class Controller implements Initializable
{
	@FXML
	private Button logAsAdmin;
	@FXML
	private ImageView imageViewMap;
	@FXML
	public VBox contentVBox;
	@FXML
	public HBox contentHBox;
	@FXML
	public AnchorPane contentAnchor = new AnchorPane();
	@FXML
	private Slider floorSlider;

	Image map4;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Add map
		map4 = new Image("/4_thefourthfloor.png");
		imageViewMap.setImage(map4);
	}

	@FXML
	private void logAsAdminClicked() throws IOException, InvocationTargetException {
		Parent loginPrompt = (AnchorPane)FXMLLoader.load(getClass().getResource("/LoginPrompt.fxml"));
		System.out.print("here");
		Scene loginPromptScene = new Scene(loginPrompt);
		Stage loginPromptStage = new Stage();
		loginPromptStage.setScene(loginPromptScene);


		loginPromptStage.showAndWait();


		//primaryStage.setScene(login_prompt_scene);
		//primaryStage.show();

	}

	//this would be called with canLogin
//	private void loginSuccessful() throws Exception{
//
//		Parent admin_UI_parent = FXMLLoader.load(getClass().getResource("AdminUI.fxml"));
//		Scene admin_UI_scene = new Scene(admin_UI_parent);
//		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		app_stage.setScene(admin_UI_scene);
//		app_stage.show();
//
//
//	}

//	@FXML
//	private void changeFloor()throws Exception{
//
//		int newFloor = (int) floorSlider.getValue();
//		imageViewMap.setImage()
//	}

	@FXML
	private void mapClicked() {
		//System.out.print("Map Clicked");
		imageViewMap.setPickOnBounds(true);


		imageViewMap.setOnMouseClicked(e -> {
			System.out.println("["+e.getX()+", "+e.getY()+"]");
			//Paint something at that location
			paintOnLocation(e.getX(), e.getY());


		});
	}


	public void paintOnLocation(double x, double y) {
		Circle circ;
		circ = new Circle(x,y,5, Color.web("0x0000FF") );

		contentAnchor.getChildren().add(circ);
		circ.setVisible(true);

	}


}

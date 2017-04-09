package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class LoginController {
	@FXML
	private Label errorLabel;
	@FXML
	private Button cancelBtn;




	@FXML
	public void loginClicked() throws IOException, InvocationTargetException {
		boolean success = true;

		if(success) {
			// TODO: Review
			Parent adminUI = (BorderPane) FXMLLoader.load(this.getClass().getResource("/AdminUI.fxml"));
			errorLabel.getScene().setRoot(adminUI);


		} else {
			this.errorLabel.setText("Incorrect Username or Password");
			// They didn't login successfully so they should probably be punished in some way
		}

	}

	@FXML
	public void cancelBtnClicked() {

	}


}
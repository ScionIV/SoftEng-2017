package ui.admin;

import entities.Account;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

import static main.ApplicationController.getDirectory;


public class AccountPopupController
		implements Initializable
{
	@FXML private Button doneBtn;
	@FXML private TableView<Account> accountTableView;
	@FXML private TableColumn usernameCol;
	@FXML private TableColumn passwordCol;
	@FXML private TableColumn permissionsCol;
	@FXML private Label existsError;

	Account selectedAccount;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.accountTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
			@Override
			public void changed(ObservableValue<? extends Account> observable, Account oldValue, Account newValue) {
//				accountTableView = newValue;
			}
		});

		accountTableView.getItems().setAll(getDirectory().getAccounts().values());

		Callback<TableColumn, TableCell> cellFactory = p -> new EditingCell();

		permissionsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Account, String> cdf) {
				return new SimpleStringProperty(cdf.getValue().getPermissions());
			}
		});

		passwordCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Account, String> cdf) {
				return new SimpleStringProperty("*****");
			}
		});

		usernameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Account, String> cdf) {
				return new SimpleStringProperty(cdf.getValue().getUsername());
			}
		});

//		usernameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
		usernameCol.setCellFactory(cellFactory);
		passwordCol.setCellFactory(cellFactory);
		permissionsCol.setCellFactory(cellFactory);

		//Modifying the firstName property
		usernameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Account, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Account, String> t) {
				if(!getDirectory().getAccounts().values().stream().anyMatch(a->a.getUsername().equals(t.getNewValue()))) {
					existsError.setVisible(false);
					((Account) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUsername(t.getNewValue());
				}else{
					existsError.setVisible(true);
				}
			}
		});

		//Modifying the firstName property
		passwordCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Account, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Account, String> t) {
				((Account) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPassword(t.getNewValue());
			}
		});

		//Modifying the firstName property
		permissionsCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Account, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Account, String> t) {
				((Account) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPermission(t.getNewValue());
			}
		});

		this.accountTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
			@Override
			public void changed(ObservableValue<? extends Account> observable, Account oldValue, Account newValue) {
				selectedAccount = newValue;
			}
		});
	}

	@FXML
	public void onAddAccountBtnClicked(){
		Account newAccount = getDirectory().addAccount("newuser","newpassword","newpermissions");
		accountTableView.getItems().add(newAccount);
	}

	@FXML
	public void onRemoveAccountBtnClicked(){
		getDirectory().deleteAccount(selectedAccount.getUsername());
		accountTableView.getItems().remove(selectedAccount);
	}

	@FXML
	public void ondoneBtnClick(){
		getDirectory().deleteAccount("newuser");
		doneBtn.getScene().getWindow().hide();
	}

}

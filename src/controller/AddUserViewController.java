package controller;

import java.util.ArrayList;

import application.PhotoAlbum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
/**
* The AddUserViewController program is used in tandem with
* its FXML counterpart as well as AdminViewController.
*
* Its main purpose is to allow the Administrator to add a user
* to the directory.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class AddUserViewController {
	@FXML
	private TextField userName;
	@FXML
	private TextField passWord;
	@FXML
	private Button confirm;
	@FXML
	private Button cancel;
	@FXML
	private Label errorMessage;

	private Stage stage;

	private ArrayList<User> users;

	@FXML
	private void initialize(){
		users = PhotoAlbum.getUsers();
	}

	public void start(Stage stage){
		this.stage = stage;
	}
	/**
	 * This function adds a User object to the current
	 * list of users with the given username and/or password.
	 */
	@FXML
	private void handleConfirmClick(){
		try{
			String username = userName.getText();
			String password = passWord.getText();
			for (int i = 0; i < users.size(); i++){
				if (username.equalsIgnoreCase(users.get(i).getUserName())){
					errorMessage.setText("Username already exits.");
					return;
				}
			}
			users.add(new User(username, password));
			stage.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This function closes the Add User pop up window and
	 * reverts back to the previous screen.
	 */
	@FXML
	private void handleCancelClick(){
		try{
			stage.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

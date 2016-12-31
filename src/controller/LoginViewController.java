package controller;


import java.util.ArrayList;
import application.PhotoAlbum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
/**
* The LoginViewController program is used in tandem with
* its FXML counterpart as well as various other controllers,
* depending on the PhotoAlbum's current state.
*
* Its main purpose is to provide a certain amount of access,
* depending on the user who is trying to login.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class LoginViewController {

	private ArrayList<User> users;

	@FXML
	private Button login;
	@FXML
	private Button quit;
	@FXML
	private TextField userName;
	@FXML
	private PasswordField passWord;

	private Stage primaryStage;

	private static User user;

	@FXML
	private void initialize(){
		this.users = PhotoAlbum.getUsers();
	}

	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	/**
	 * This function will open a new window with either
	 * UserViewController or AdminViewController, depending
	 * on the username, after the user clicks the Login button.
	 */
	@FXML
	private void handleLoginClick(ActionEvent event){
		try{
			String name = userName.getText();
			FXMLLoader loader = new FXMLLoader();
			if (name.equals("admin")){
			    loader.setLocation(getClass().getResource("/view/AdminView.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				AdminViewController controller = loader.getController();
				controller.start(primaryStage);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			else{
				if (users != null){
					for (int i = 0; i < users.size(); i++){
						if (users.get(i).getUserName().equals(name)){
							user = users.get(i);
							loader.setLocation(getClass().getResource("/view/UserView.fxml"));
							AnchorPane root = (AnchorPane)loader.load();
							Scene scene = new Scene(root);
							UserViewController controller = loader.getController();
							controller.start(primaryStage);
							primaryStage.setScene(scene);
							primaryStage.show();
							break;
						}
					}
				}
			}
    	}catch (Exception e){
    		e.printStackTrace();
    	}
	}
	/**
	 * This function saves the program's current state and
	 * completely exits after the event that the user clicks
	 * on either the log out button or the close window button
	 * that is provided by the OS.
	 */
	@FXML //exits program
	private void handleQuitClick(ActionEvent event){
		try{
			PhotoAlbum.saveUsers();
    		primaryStage.close();
    	}catch (Exception e){
    		e.printStackTrace();
    	}
	}
	/**
	 * The purpose of this function is to give other
	 * controllers a reference to the current user.
	 *
	 * @return returns the current user
	 */
	public static User getUser(){
		return user;
	}
}
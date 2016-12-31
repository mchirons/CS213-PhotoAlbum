package controller;

import java.util.ArrayList;

import application.PhotoAlbum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
/**
* The AdminViewController program is used in tandem with
* its FXML counterpart as well as various other controllers,
* depending on the PhotoAlbum's current state.
*
* Its main purpose is to provide the Administrator with a current
* list of users and administrative properties such as add/delete
* user(s).
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class AdminViewController {
	@FXML
	private MenuItem logout;
	@FXML
	private MenuItem close;
	@FXML
	private MenuItem addUser;
	@FXML
	private MenuItem deleteSelectedUser;
	@FXML
	private ListView<User> listView;

	private ObservableList<User> usersObservable;

	private ArrayList<User> users;

	private Stage primaryStage;

	@FXML
	private void initialize(){
		this.users = PhotoAlbum.getUsers();
		usersObservable = FXCollections.observableArrayList();
		usersObservable.addAll(users);
		listView.setItems(usersObservable);
        listView.getSelectionModel().select(0);

	}

	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	/**
	 * This function gets called after the user clicks
	 * on the Logout option from the File menu. The program
	 * then reverts back to the Login Screen.
	 */
	@FXML	//go back to LoginView
	private void handleLogoutClick(ActionEvent event){
		try{
			PhotoAlbum.saveUsers();
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("/view/LoginView.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			LoginViewController controller = loader.getController();
			controller.start(primaryStage);
			primaryStage.setScene(scene);
			primaryStage.show();
    	}catch (Exception e){
    		e.printStackTrace();
    	}
	}
	/**
	 * This function saves the current list of users on the
	 * event of a window close.
	 */
	@FXML	//add save functionality
	private void handleCloseClick(ActionEvent event){
		try{
			PhotoAlbum.saveUsers();
			primaryStage.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This function brings up the dialog box to
	 * add a user on the event that the administrator
	 * clicks on the Add User button from the edit menu.
	 */
	@FXML
	private void handleAddUserClick(ActionEvent event){
		try{
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("/view/AddUserView.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			AddUserViewController controller = loader.getController();
			controller.start(stage);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Add New User");
			stage.showAndWait();
			usersObservable.setAll(users);
			listView.setItems(usersObservable);

		}catch (Exception e){
			e.printStackTrace();
		}

	}
	/**
	 * This function removes the selected user from the current list
	 * of users, and thereby also removing the user's albums.
	 */
	@FXML
	private void handleDeleteSelectedUserClick(ActionEvent event){
		try{
			if (users.size() > 0){
				users.remove(listView.getSelectionModel().getSelectedItem());
				usersObservable.setAll(users);
				listView.setItems(usersObservable);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
package controller;

import java.util.ArrayList;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Album;
import model.User;
/**
* The AddAlbumViewController program is used in tandem with
* its FXML counterpart as well as various other controllers,
* depending on the PhotoAlbum's current state.
*
* Its main purpose is to allow the current user to add an album
* to his/her list of albums.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class AddAlbumViewController {
	@FXML
	private TextField albumTitle;
	@FXML
	private Button confirm;
	@FXML
	private Button cancel;
	@FXML
	private Label errorMessage;

	private Stage stage;

	private User user;

	@FXML
	private void initialize(){
		user = UserViewController.getUser();
	}

	public void start(Stage stage){
		this.stage = stage;
	}
	/**
	 * Launches a pop up window that allows the user to
	 * input the name of the new album that they wish to
	 * add.
	 */
	@FXML
	private void handleConfirmClick(){
		try{
			ArrayList<Album> albums = user.getAlbums();
			String albumname = albumTitle.getText();
			for (int i = 0; i < albums.size(); i++){
				if (albumname.equalsIgnoreCase(albums.get(i).getAlbumName())){
					errorMessage.setText("Album name already exits.");
					return;
				}
			}
			user.addAlbum(new Album(albumname));
			stage.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This function closes the Add Album pop up window and reverts back
	 * to previous screen.
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
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
* The RenameAlbumViewController program is used in tandem with
* its FXML counterpart as well as the UserViewController,
* depending on the PhotoAlbum's current state.
*
* Its main purpose is to simply allow for the renaming of a selected album.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class RenameAlbumViewController {
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
	 * This function will change the currently selected album's
	 * title to the one specified by the user after he/she clicks on
	 * the Confirm button if the album name does not already exist.
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
			user.renameAlbum(UserViewController.getAlbum(), albumname);
			stage.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This function simply exits out of the current window
	 * and reverts back to the list of Albums.
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

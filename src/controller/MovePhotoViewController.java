package controller;

import java.util.ArrayList;

import application.PhotoAlbum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
/**
* The MovePhotoViewController program is used in tandem with
* its FXML counterpart as well as the AlbumViewController,
* depending on the PhotoAlbum's current state.
*
* Its main purpose is to simply allow for a photo to be moved
* from one album to another.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class MovePhotoViewController {
	@FXML
	private Button addToSelected;
	@FXML
	private Button close;
	@FXML
	private ListView<Album> listView;

	private ArrayList<Album> albums;

	private Photo photo;

	private ObservableList<Album> albumsObservable;

	private Stage stage;

	private Album album;

	@FXML
	private void initialize(){
		this.albums = UserViewController.getUser().getAlbums();
		this.album = UserViewController.getAlbum();
		this.photo = AlbumViewController.getPhoto();
		albumsObservable = FXCollections.observableArrayList();
		albumsObservable.addAll(albums);
		listView.setItems(albumsObservable);
        listView.getSelectionModel().select(0);
	}

	public void start(Stage stage){
		this.stage = stage;
	}
	/**
	 * This function adds the currently selected photo
	 * to another album, as selected by the user.
	 */
	@FXML
	private void handleAddToSelectedClick(){
		Album selected = listView.getSelectionModel().getSelectedItem();
		selected.addPhoto(photo);
		if (!album.equals(selected)){
			album.deletePhoto(photo);
		}
	}
	/**
	 * This function saves the current state and closes the window,
	 * reverting back AlbumView.
	 */
	@FXML
	private void handleCloseClick(){
		PhotoAlbum.saveUsers();
		stage.close();
	}
}
package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
/**
* The PhotoViewController program is used in tandem with
* its FXML counterpart as well as the AlbumViewController and
* SearchViewController, depending on the PhotoAlbum's current state.
*
* Its main purpose is to simply allow for the displaying of photos as a slideshow.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class PhotoViewController {
	@FXML
	private ImageView imageView;
	@FXML
	private Button last;
	@FXML
	private Button next;
	@FXML
	private Label caption;
	@FXML
	private Label date;
	@FXML
	private Label tags;
	@FXML
	private VBox vbox;

	private Stage stage;

	private Album album;

	private Photo photo;

	private boolean isFromSearchSelection;

	@FXML
	private void initialize(){
		this.album = UserViewController.getAlbum();
		this.photo = AlbumViewController.getPhoto();
		displayPhoto();
	}

	public void start(Stage stage, boolean isFromSearchSelection){
		this.stage = stage;
		this.isFromSearchSelection = isFromSearchSelection;
	}
	/**
	 * This function displays the previously shown photo when
	 * the user clicks on the Last button.
	 */
	@FXML
	private void handleLastClick(){
		ArrayList<Photo> photos = null;
		if (isFromSearchSelection){
			photos = SearchSelectionViewController.getPhotos();
		}
		else{
			photos = album.getPhotos();
		}
		int index = 0;
		for (int i = 0; i < photos.size(); i++){
			if (photo.equals(photos.get(i))){
				break;
			}
			else{
				index++;
			}
		}
		if (index == 0){
			return;
		}
		else {
			index--;
			this.photo = photos.get(index);
			displayPhoto();
		}
	}
	/**
	 * This function displays the next photo in the list when
	 * the user clicks on the Next button.
	 */
	@FXML
	private void handleNextClick(){
		ArrayList<Photo> photos = null;
		if (isFromSearchSelection){
			photos = SearchSelectionViewController.getPhotos();
		}
		else{
			photos = album.getPhotos();
		}
		int index = 0;
		for (int i = 0; i < photos.size(); i++){
			if (photo.equals(photos.get(i))){
				break;
			}
			else{
				index++;
			}
		}
		if (index == photos.size() - 1){
			return;
		}
		else {
			index++;
			this.photo = photos.get(index);
			displayPhoto();
		}
	}
	/**
	 * The purpose of this function is to display the
	 * selected photo in a larger size with its appropriate
	 * attributes.
	 */
	@FXML
	private void displayPhoto(){
		Image image = new Image(photo.getURI());
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setFitWidth(874);
		caption.setText(photo.getCaption());
		date.setText(new SimpleDateFormat("yyyy-MM-dd").format(photo.getDate()));
		String whatever = "";
		for(int i = 0; i < photo.getTags().size(); i++)
		{
			if(i == photo.getTags().size()-1)
			{
				whatever += photo.getTags().get(i).getValue();
			}
			else
			{
				whatever += photo.getTags().get(i).getValue() + ", ";
			}
		}
		tags.setText(whatever);
	}
}

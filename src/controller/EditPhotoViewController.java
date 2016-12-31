package controller;

import java.util.ArrayList;

import application.PhotoAlbum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
/**
* The EditViewController program is used in tandem with
* its FXML counterpart as well as the AlbumViewController,
* depending on the PhotoAlbum's current state.
*
* Its main purpose is to view/edit the current photo of an album.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class EditPhotoViewController {
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private	TextField values;
	@FXML
	private TextField caption;
	@FXML
	private	Button add1;
	@FXML
	private Button add2;
	@FXML
	private	Button close2;
	@FXML
	private	Button close3;
	@FXML
	private Button deleteSelected;
	@FXML
	private ListView<Tag> listView;

	private ObservableList<Tag> tagsObservable;

	private Photo photo;

	private ArrayList<Tag> tags;

	private Stage stage;

	@FXML
	private void initialize(){
		this.photo = AlbumViewController.getPhoto();
		this.tags = photo.getTags();
		comboBox.getItems().addAll("Location", "Name", "Other");
		tagsObservable = FXCollections.observableArrayList();
		tagsObservable.addAll(tags);
		listView.setItems(tagsObservable);
        listView.getSelectionModel().select(0);
	}

	public void start(Stage stage){
		this.stage = stage;
	}
	/**
	 * The purpose of this function is to add a tag
	 * to the currently selected photo.
	 */
	//adding tags
	@FXML
	private void handleAdd1Click(){
		try{
			ArrayList<Tag> existingTags = photo.getTags();
			String type = comboBox.getValue();
			if (type != null){
				String[] enteredValues = values.getText().split(" ");
				if (enteredValues.length > 0){
					for (int i = 0; i < enteredValues.length; i++){
						if (enteredValues[i].trim().length() > 0){
							Tag tag = new Tag(type, enteredValues[i].toLowerCase());
							if (!existingTags.contains(tag)){
								photo.addTag(tag);
							}
						}

					}
				}
			tagsObservable.setAll(tags);
			listView.setItems(tagsObservable);}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * The purpose of this function is to add a caption
	 * to the currently selected photo.
	 */
	//adding caption
	@FXML
	private void handleAdd2Click(){
		try{
			String enteredCaption = caption.getText();
			if (enteredCaption != null){
				photo.setCaption(enteredCaption);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This function will save the changes that the user
	 * made to the currently selected photo and revert back
	 * to the Album View.
	 */
	@FXML
	private void handleCloseClick(){
		try{
			PhotoAlbum.saveUsers();
			stage.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * The purpose of this function is to delete an
	 * attribute from the currently selected photo.
	 */
	@FXML
	private void handleDeleteSelectedClick(){
		try{

			if (tags.size() > 0){
				photo.deleteTag(listView.getSelectionModel().getSelectedItem());
				tagsObservable.setAll(tags);
				listView.setItems(tagsObservable);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
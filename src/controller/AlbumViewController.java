package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import application.PhotoAlbum;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
/**
* The AlbumViewController program is used in tandem with
* its FXML counterpart as well as various other controllers,
* depending on the PhotoAlbum's current state.
*
* Its main purpose is to view/edit the current set of photos
* for an album.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class AlbumViewController {
	@FXML
	private MenuItem logout;
	@FXML
	private MenuItem close;
	@FXML
	private MenuItem addPhoto;
	@FXML
	private MenuItem removeSelectedPhoto;
	@FXML
	private MenuItem deleteSelectedPhoto;
	@FXML
	private MenuItem editSelectedPhoto;
	@FXML
	private MenuItem moveSelectedTo;
	@FXML
	private Button back;
	@FXML
	private TilePane tilePane;

	private VBox selectedTile;

	private static Album album;

	private static Photo photo;

	private Stage primaryStage;

	@FXML
	private void initialize(){
		AlbumViewController.album = UserViewController.getAlbum();
		ArrayList<Photo> photos = album.getPhotos();

	    for (int i = 0; i < photos.size(); i++) {
           VBox vbox = createVBox(photos.get(i));
	       tilePane.getChildren().add(vbox);
	    }
	    tilePane.setStyle("-fx-background-color: white;");
	    tilePane.setPadding(new Insets(15, 15, 15, 15));
        tilePane.setHgap(15);
	    tilePane.setVgap(15);
	}

	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
	}

	/**
	 * The purpose of this function is to save any
	 * modifications that were done to the current album
	 * and go back to the Login View.
	 */
	@FXML	//save and go back to LoginView
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
    	}catch (Exception d){
    		d.printStackTrace();
    	}
	}
	/**
	 * This function will close the window and save the current
	 * user's photos and any modifications that were made to them.
	 */
	@FXML	//add save functionality
	private void handleCloseClick(ActionEvent event){
		try{
			PhotoAlbum.saveUsers();
			primaryStage.close();
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * This function will open the current machine's
	 * explorer window to add one or more photos on the
	 * event that the user clicked on the Add One or More
	 * Photos button.
	 */
	@FXML
	private void handleAddPhotoClick(ActionEvent event){
		try{
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Image files");
			List<File> pictures = fileChooser.showOpenMultipleDialog(stage);
			if (pictures != null){
				for (int i = 0; i < pictures.size(); i++){

					BufferedImage image = ImageIO.read(pictures.get(i));
					if(image != null)
					{
						Photo photo = new Photo(pictures.get(i));
						album.addPhoto(photo);
					}
				}
			}
			selectedTile = null;
			tilePane.getChildren().clear();
			for (int i = 0; i < album.getPhotos().size(); i++) {
		           VBox vbox = createVBox(album.getPhotos().get(i));
			       tilePane.getChildren().add(vbox);
			    }


		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * The purpose of this function is to delete the selected photo
	 * from the current album.
	 */
	@FXML
	private void handleRemoveSelectedPhotoClick(ActionEvent event){
		try{
			ArrayList<Photo> photos = album.getPhotos();
			if (selectedTile != null){
				String uri = ((Label) selectedTile.getChildren().get(3)).getText();
				for (int i = 0; i < photos.size(); i++){
					if (uri.equals(photos.get(i).getURI())){
						album.deletePhoto(photos.get(i));
						break;
					}
				}
			}
			selectedTile = null;
			tilePane.getChildren().clear();
		    for (int i = 0; i < photos.size(); i++) {
	           VBox vbox = createVBox(photos.get(i));
		       tilePane.getChildren().add(vbox);
		    }
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * This function opens a pop up window to edit the selected photo
	 * on the event that the user clicked on the Edit Selected Photo
	 * button.
	 */
	@FXML
	private void handleEditSelectedPhotoClick(ActionEvent event){
		try{
			ArrayList<Photo> photos = album.getPhotos();
			if (selectedTile != null){
				String uri = ((Label) selectedTile.getChildren().get(3)).getText();
				for (int i = 0; i < photos.size(); i++){
					if (uri.equals(photos.get(i).getURI())){
						AlbumViewController.photo = photos.get(i);
						break;
					}
				}
				Stage stage = new Stage();
				PhotoAlbum.saveUsers();
				FXMLLoader loader = new FXMLLoader();
			    loader.setLocation(getClass().getResource("/view/EditPhotoView.fxml"));
				TabPane root = (TabPane)loader.load();
				Scene scene = new Scene(root);
				EditPhotoViewController controller = loader.getController();
				controller.start(stage);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("Edit Photo");
				stage.showAndWait();
			}

			selectedTile = null;
			tilePane.getChildren().clear();
		    for (int i = 0; i < photos.size(); i++) {
	           VBox vbox = createVBox(photos.get(i));
		       tilePane.getChildren().add(vbox);
		    }
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * This function saves any changes that the user made to
	 * their album and reverts back to their list of albums in
	 * the UserViewController.
	 */
	@FXML	//go back to UserView
	private void handleBackClick(ActionEvent event){
		try{

			PhotoAlbum.saveUsers();
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("/view/UserView.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			UserViewController controller = loader.getController();
			controller.start(primaryStage);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * This function will open a pop up window after the user
	 * clicks on the Move Selected Photo To button to then choose
	 * a new album for said photo.
	 */
	@FXML
	private void handleMoveSelectedToClick(){
		try{
			ArrayList<Photo> photos = album.getPhotos();
			if (selectedTile != null){

				String uri = ((Label) selectedTile.getChildren().get(3)).getText();
				for (int i = 0; i < photos.size(); i++){
					if (uri.equals(photos.get(i).getURI())){
						AlbumViewController.photo = photos.get(i);
						break;
					}
				}
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/MovePhotoView.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				MovePhotoViewController controller = loader.getController();
				controller.start(stage);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("Move Photo to Album");
				stage.showAndWait();
			}
			selectedTile = null;
			tilePane.getChildren().clear();
			for (int i = 0; i < photos.size(); i++) {
				VBox vbox = createVBox(photos.get(i));
				tilePane.getChildren().add(vbox);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This function creates a container that gets displayed in the
	 * Album View where each photo will have its associated caption,
	 * date, and any tags that it may have shown.
	 */
	public VBox createVBox(Photo photo){
		Image image = new Image(photo.getURI());
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setFitWidth(135);
		Label uri = new Label(photo.getURI());
		uri.managedProperty().bind(uri.visibleProperty());
		uri.setVisible(false);
	    Label title = new Label("Caption: " + photo.getCaption());
	    Label date = new Label("Date: " + new SimpleDateFormat("MM.dd.YYYY").format(photo.getDate()));
	    //Label tags = new Label("Tags: " + photo.getTags());
	    VBox vbox = new VBox(imageView, title, date, uri);
	    title.setAlignment(Pos.BOTTOM_LEFT);
	    date.setAlignment(Pos.BOTTOM_LEFT);

	    vbox.setOnMousePressed(new EventHandler<MouseEvent>(){
	    	@Override
	    	public void handle(MouseEvent e){
	    		ObservableList<Node> nodes = tilePane.getChildren();
	    		for (int i = 0; i < nodes.size(); i++){
	    			nodes.get(i).setStyle("-fx-background-color: white;");
	    		}
	    		vbox.setStyle("-fx-background-color: #ccffff;");
	    		selectedTile = vbox;
	    	}
	    });

	    //double clicking
	    vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent m) {
	            if(m.getButton().equals(MouseButton.PRIMARY)){
	                if(m.getClickCount() == 2){
	                	try{
	                		ArrayList<Photo> photos = album.getPhotos();
	            			if (selectedTile != null){
	            				String uri = ((Label) selectedTile.getChildren().get(3)).getText();
	            				for (int i = 0; i < photos.size(); i++){
	            					if (uri.equals(photos.get(i).getURI())){
	            						AlbumViewController.photo = photos.get(i);
	            						break;
	            					}
	            				}
	            			}
	            			Stage stage = new Stage();
	                		FXMLLoader loader = new FXMLLoader();
		        		    loader.setLocation(getClass().getResource("/view/PhotoView.fxml"));
		        			AnchorPane root = (AnchorPane)loader.load();
		        			Scene scene = new Scene(root);
		        			PhotoViewController controller = loader.getController();
		        			controller.start(stage, false);
		        			stage.setScene(scene);
		        			stage.setResizable(false);
		        			stage.setTitle("Photo Viewer");
		        			stage.showAndWait();
	                	}catch (Exception e){
	                		e.printStackTrace();
	                	}

	                }
	            }
	        }
	    });
	    return vbox;
	}
	/**
	 * The purpose of this function is to give other controllers
	 * a reference to the current selected photo.
	 *
	 * @return returns currently selected photo
	 */
	public static Photo getPhoto(){
		return photo;
	}
}

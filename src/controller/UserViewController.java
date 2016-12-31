package controller;

import java.io.File;
import java.util.ArrayList;

import application.PhotoAlbum;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.User;
/**
* The UserViewController program is used in tandem with
* its FXML counterpart as well as various other controllers,
* depending on the PhotoAlbum's current state.
*
* Its purpose is to display the current set of albums and allow
* for modifications to the albums as well as searching for a set
* of photos.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class UserViewController {

	@FXML
	private MenuItem logout;
	@FXML
	private MenuItem close;
	@FXML
	private MenuItem createNewAlbum;
	@FXML
	private MenuItem renameSelectedAlbum;
	@FXML
	private MenuItem deleteSelectedAlbum;
	@FXML
	private MenuItem photoSearch;
	@FXML
	private TilePane tilePane;

	private VBox selectedTile;

	private static User user;

	private static Album album;

	private Stage primaryStage;

	@FXML
	private void initialize(){
		UserViewController.user = LoginViewController.getUser();
		ArrayList<Album> albums = user.getAlbums();
		//System.out.println("username: " + user.getUserName());
		if (albums == null){
			System.out.println("albums is null");
		}
	    for (int i = 0; i < albums.size(); i++) {
           VBox vbox = createVBox(albums.get(i));
	       tilePane.getChildren().add(vbox);
	    }
	    tilePane.setStyle("-fx-background-color: white;");
	}

	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	/**
	 * The purpose of this function is to save the current
	 * state of the program and to revert back to the Login View
	 * on the action that the user clicks on the Log Out button.
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
	 * This function saves the program's current state and
	 * completely exits out on the action that the user clicks
	 * the close button as provided by the OS.
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
	 * This function allows for the user to create a new album.
	 * It passes a call to the AddAlbumViewController after the user
	 * clicks on the Create Album button.
	 */
	@FXML
	private void handleCreateNewAlbumClick(ActionEvent event){
		try{
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("/view/AddAlbumView.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			AddAlbumViewController controller = loader.getController();
			controller.start(stage);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Create New Album");
			stage.showAndWait();

			tilePane.getChildren().clear();
			ArrayList<Album> albums = user.getAlbums();
		    for (int i = 0; i < albums.size(); i++) {
	           VBox vbox = createVBox(albums.get(i));
		       tilePane.getChildren().add(vbox);
		    }
		    selectedTile = null;
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * The purpose of this function is to simply rename a selected album.
	 */
	@FXML
	private void handleRenameSelectedAlbumClick(ActionEvent event){
		try{
			ArrayList<Album> albums = user.getAlbums();
			if (selectedTile != null){
				String title = ((Label) selectedTile.getChildren().get(1)).getText();
				title = title.substring(7);
				for (int i = 0; i < albums.size(); i++){
					if (title.equals(albums.get(i).getAlbumName())){
						UserViewController.album = albums.get(i);
						break;
					}
				}
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
			    loader.setLocation(getClass().getResource("/view/RenameAlbumView.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				RenameAlbumViewController controller = loader.getController();
				controller.start(stage);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("Rename Album");
				stage.showAndWait();

				tilePane.getChildren().clear();
			    for (int i = 0; i < albums.size(); i++) {
		           VBox vbox = createVBox(albums.get(i));
			       tilePane.getChildren().add(vbox);
			    }
			}

		    selectedTile = null;
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * The purpose of this function is to delete a selected album
	 * in the event that the user clicks on the Delete Selected Album button.
	 */
	@FXML
	private void handleDeleteSelectedAlbumClick(ActionEvent event){
		try{
			ArrayList<Album> albums = user.getAlbums();
			if (selectedTile != null){
				String title = ((Label) selectedTile.getChildren().get(1)).getText();
				title = title.substring(7);
				for (int i = 0; i < albums.size(); i++){
					if (title.equals(albums.get(i).getAlbumName())){
						user.deleteAlbum(albums.get(i));
					}
				}
			}
			tilePane.getChildren().clear();
		    for (int i = 0; i < albums.size(); i++) {
	           VBox vbox = createVBox(albums.get(i));
		       tilePane.getChildren().add(vbox);
		    }
		    selectedTile = null;
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * This function will call the SearchSelectionViewController
	 * in the event that he or she selects the Photo Search button.
	 */
	@FXML
	private void handlePhotoSearchClick(ActionEvent event){
		try{
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("/view/SearchSelectionView.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			SearchSelectionViewController controller = loader.getController();
			controller.start(stage);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Photo Search");
			stage.showAndWait();

			if (!controller.wasClosed()){
				loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/SearchView.fxml"));
				AnchorPane root1 = (AnchorPane)loader.load();
				Scene scene1 = new Scene(root1);
				SearchViewController controller1 = loader.getController();
				controller1.start(primaryStage);
				primaryStage.setScene(scene1);
				primaryStage.show();
			}
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * The purpose of this function is to give other
	 * controllers a reference to the current user.
	 */
	public static User getUser(){
		return user;
	}
	/**
	 * This function creates a container that gets displayed in the
	 * User View where each album will be displayed as a folder icon
	 * with a reference to the number of photos as well as the range dates
	 * of the photos.
	 */
	public VBox createVBox(Album album){
		ImageView imageView = new ImageView(new File ("src/folderIcon.png").toURI().toString());
	    Label title = new Label("Title: " + album.getAlbumName());
	    Label date = new Label(album.getDateRange());
	    Label num = new Label("Size: " + album.getPhotos().size());
	    VBox vbox = new VBox(imageView, title, num, date);

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
	                	StackPane pane = new StackPane();
	                	Label loading = new Label("Loading...");
	                	pane.getChildren().add(loading);
	                	pane.setAlignment(Pos.CENTER);
	                	Scene scene1 = new Scene(pane,1024,767);
	                	//stage.initStyle(StageStyle.UNDECORATED);
	                	primaryStage.setScene(scene1);
	                	primaryStage.show();


	                	try{
	                		ArrayList<Album> albums = user.getAlbums();
	            			if (selectedTile != null){
	            				String title = ((Label) selectedTile.getChildren().get(1)).getText();
	            				title = title.substring(7);
	            				for (int i = 0; i < albums.size(); i++){
	            					if (title.equals(albums.get(i).getAlbumName())){
	            						UserViewController.setAlbum(album);
	            						break;
	            					}
	            				}
	            			}

	                		FXMLLoader loader = new FXMLLoader();
		        		    loader.setLocation(getClass().getResource("/view/AlbumView.fxml"));
		        			AnchorPane root = (AnchorPane)loader.load();
		        			Scene scene = new Scene(root);
		        			AlbumViewController controller = loader.getController();
		        			controller.start(primaryStage);
		        			primaryStage.setScene(scene);
		        			primaryStage.show();

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
	 * This function is used by the AddAlbumViewController to include
	 * any new albums that may have been added.
	 */
	public static void setAlbum(Album album){
		UserViewController.album = album;
	}
	/**
	 * The purpose of this function is to provide a reference to the currently
	 * selected album to other controllers.
	 */
	public static Album getAlbum(){
		return album;
	}
}
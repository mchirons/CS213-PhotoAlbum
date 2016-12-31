package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Photo;
import model.User;
/**
* The SearchViewController program is used in tandem with
* its FXML counterpart as well as the SearchSelectionViewController,
* depending on the PhotoAlbum's current state.
*
* Its purpose is to display the search results as photos with the option to
* create a new album from the results.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class SearchViewController {
	@FXML
	private MenuItem logout;
	@FXML
	private MenuItem close;
	@FXML
	private MenuItem createNewAlbumFromSearch;
	@FXML
	private MenuItem photoSearch;
	@FXML
	private Button back;
	@FXML
	private TilePane tilePane;

	private Stage primaryStage;
	private ArrayList<Photo> photos;
	private VBox selectedTile;
	private User user;
	private static Photo photo;

	@FXML
	private void initialize()
	{
		this.user = LoginViewController.getUser();
		photos = SearchSelectionViewController.getPhotos();

		for (int i = 0; i < photos.size(); i++) {
	           VBox vbox = createVBox(photos.get(i));
		       tilePane.getChildren().add(vbox);
		    }
		    tilePane.setStyle("-fx-background-color: white;");
		    tilePane.setPadding(new Insets(15, 15, 15, 15));
	        tilePane.setHgap(15);
		    tilePane.setVgap(15);

	}

	public void start(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
	}
	/**
	 * This function creates a container that gets displayed in the
	 * Search View where each photo will have its associated caption,
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
	    Label date = new Label("Date: " + new SimpleDateFormat("MM-dd-YYYY").format(photo.getDate()));
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
	                		//ArrayList<Photo> photos = photos;
	            			if (selectedTile != null){
	            				String uri = ((Label) selectedTile.getChildren().get(3)).getText();
	            				for (int i = 0; i < photos.size(); i++){
	            					if (uri.equals(photos.get(i).getURI())){
	            						SearchViewController.photo = photos.get(i);
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
		        			controller.start(stage, true);
		        			stage.setScene(scene);
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
	 * This function saves the current state of the program
	 * and reverts back to the login view after the user clicks
	 * on the Log Out button.
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
	 * This function saves the current state of the program
	 * and completely exits out after the user clicks on the
	 * close button as provided by the OS.
	 */
	@FXML
	private void handleCloseClick(ActionEvent event){
		try{
			PhotoAlbum.saveUsers();
			primaryStage.close();
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * This function will open a window with a text field
	 * so that the user may name the album that he or she
	 * wants to add the current results to after clicking on
	 * the Create New Album button.
	 */
	@FXML
	private void handleCreateNewAlbumFromSearchClick(ActionEvent event){
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

			int newest = user.getAlbums().size() - 1;
			user.getAlbums().get(newest).getPhotos().addAll(photos);
		}catch (Exception d){
			d.printStackTrace();
		}
	}
	/**
	 * This function brings loads the SearchSelectionViewController
	 * once again.
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
	 * This function gets called when the user clicks
	 * on the Back button. It will revert back the to the
	 * list of albums.
	 */
	@FXML
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
}

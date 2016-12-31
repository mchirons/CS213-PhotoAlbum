/**
* The PhotoAlbum program launches an application that
* allows the user to login with his or her credentials
* and create/edit photo albums.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/

package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import controller.LoginViewController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.Photo;
import model.User;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class PhotoAlbum extends Application {

	private static File saveFile;
	private static ArrayList<User> users;
	private boolean dataExists;

	@Override
	public void start(Stage primaryStage) {
		try {
			dataExists();
			loadUsers();
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("/view/LoginView.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			LoginViewController controller = loader.getController();
			controller.start(primaryStage);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Photo Album");
			primaryStage.setResizable(false);



			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	               PhotoAlbum.saveUsers();
	            }
	        });

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//god
	public void loadUsers(){

	  	try {


			saveFile = new File("src/userData.txt");
			BufferedReader br = new BufferedReader(new FileReader("src/userData.txt"));
			String line = br.readLine();
			if (line == null && dataExists) {
				//System.out.println("line is null and test exists");
				 	FileInputStream fis = new FileInputStream("data/testData.txt");
				    ObjectInputStream ois = new ObjectInputStream(fis);
				    users = (ArrayList<User>)ois.readObject();
				    ois.close();
				    loadSampleUsers(new File("data/photos"));
			}
			else if (line != null && dataExists){
				//System.out.println("line is NOT null and test exists");
				FileInputStream fis = new FileInputStream("src/userData.txt");
			    ObjectInputStream ois = new ObjectInputStream(fis);
			    users = (ArrayList<User>)ois.readObject();
			    ois.close();
			}
			else if (line != null && !dataExists){
				//System.out.println("line is NOT null and test DOESNT exists");
				FileInputStream fis = new FileInputStream("src/userData.txt");
			    ObjectInputStream ois = new ObjectInputStream(fis);
			    users = (ArrayList<User>)ois.readObject();
			    ois.close();
			}
			else {
				//System.out.println("line is null and test exists");
				users = new ArrayList<User>();
			}

		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

	public static void saveUsers(){
		ArrayList<User> temp = new ArrayList<User>();

		temp.addAll(users);
		try {
		    FileOutputStream fos = new FileOutputStream(saveFile);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(temp);
		    oos.flush();
		    oos.close();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

	public static ArrayList<User> getUsers(){
		return users;
	}

	private void dataExists(){


		File testData = new File("data/testData.txt");

		if (testData.exists()){
			dataExists = true;
		}
		else {
			dataExists = false;
		}

	}

	private void loadSampleUsers(File sample){

		try{
			File[] files = sample.listFiles();

			for (int i = 0; i < files.length; i++){
				//System.out.println(files[i].getName());
			}

			ArrayList<Photo> franksPhotos = users.get(0).getPhotos();
			for (int i = 0; i < 10; i++){
				franksPhotos.get(i).setURI(files[i].toURI().toString());
			}

			ArrayList<Photo> donnasPhotos = users.get(1).getPhotos();

			for (int i = 0; i < 10; i++){
				donnasPhotos.get(i).setURI(files[i + 10].toURI().toString());
			}

			ArrayList<Photo> bradsPhotos = users.get(2).getPhotos();

			for (int i = 0; i < 10; i++){
				bradsPhotos.get(i).setURI(files[i + 20].toURI().toString());
			}
		} catch (Exception e){
			try{
				System.out.println("Exception");
				PrintWriter writer = new PrintWriter(sample);
				writer.print("");
				writer.close();
			}catch(Exception d){
				d.printStackTrace();
			}
		}




	}

	public static void main(String[] args) {
		launch(args);
	}
}


package controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import application.PhotoAlbum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
/**
* The SearchSelectionViewController program is used in tandem with
* its FXML counterpart as well as the UserViewController,
* depending on the PhotoAlbum's current state.
*
* Its purpose is to search for photos based on tags, dates, or both.
* The user may also create a new album based on the search results.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class SearchSelectionViewController
{
	@FXML
	private Button cancel;
	@FXML
	private Button ok;
	@FXML
	private ComboBox<String> searchComboBox;
	@FXML
	private DatePicker dateSearchFrom;
	@FXML
	private DatePicker dateSearchTo;
	@FXML
	private TextField tagSearch;

	private Stage stage;
	private Date dateFrom;
	private Date dateTo;
	private Tag tag;
	private static ArrayList<Photo> photos;
	private User user;
	private boolean wasClosed;
	//stuffs
	public void start(Stage stage){
		this.stage = stage;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	try{
        			wasClosed = true;
        			PhotoAlbum.saveUsers();
        			stage.close();
        		}catch(Exception e){
        			e.printStackTrace();
        		}
            }
        });
	}

	@FXML
	private void initialize()
	{
		searchComboBox.getItems().addAll("Location", "Name", "Other");
		photos = new ArrayList<Photo>();
		user = LoginViewController.getUser();
		wasClosed = false;
	}
	/**
	 * This function will call upon the SearchViewController
	 * if the user's input is valid for a search after he/she
	 * clicks on the OK button.
	 */
	@FXML
	private void handleOkClick(ActionEvent event)
	{
		//If user didn't input anything, why bother continuing?
		if (((dateSearchFrom.getValue() == null) || (dateSearchTo.getValue() == null)) && tagSearch.getText().toLowerCase().equals("")){
			return;
		}

		if(!((dateSearchFrom.getValue() == null) || (dateSearchTo.getValue() == null))){

			//Need LocalDate object to get the value from the user.
			LocalDate localDateFrom = dateSearchFrom.getValue();
			LocalDate localDateTo = dateSearchTo.getValue();
			dateFrom = Date.from(localDateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant());
			dateTo = Date.from(localDateTo.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Calendar c = Calendar.getInstance();
			c.setTime(dateTo);
			c.add(Calendar.DATE, 1);
			dateTo = c.getTime();
		}

		tag = new Tag(searchComboBox.getValue(), tagSearch.getText().toLowerCase());

		ArrayList<Album> albums = user.getAlbums();
		ArrayList<Photo> temp = new ArrayList<Photo>();


		//Adding all of the photos from the user's albums into one ArrayList.
		for(int i = 0; i < albums.size(); i++)
		{
			temp.addAll(albums.get(i).getPhotos());
		}

		//Checking against the information provided by the user.
		for(int i = 0; i < temp.size(); i++)
		{
			if(dateFrom != null && dateTo != null)
			{
				if(temp.get(i).getDate().after(dateFrom) &&
						temp.get(i).getDate().before(dateTo))
				{
					if (!photos.contains(temp.get(i))){
						photos.add(temp.get(i));
					}
				}
			}
			else if(temp.get(i).getTags().contains(tag))
			{
				if (!photos.contains(temp.get(i))){
					photos.add(temp.get(i));
				}

			}
			else if(temp.get(i).getTags().contains(tag) && temp.get(i).getDate().after(dateFrom) &&
					temp.get(i).getDate().before(dateTo))
			{
				if (!photos.contains(temp.get(i))){
					photos.add(temp.get(i));
				}
			}
		}
		PhotoAlbum.saveUsers();
		stage.close();
	}
	/**
	 * The purpose of this function is to provide other controllers
	 * with a reference to the current list of photos from the search.
	 *
	 * @return returns an ArrayList of photos
	 */
	public static ArrayList<Photo> getPhotos()
	{
		return photos;
	}
	/**
	 * This function closes the current window.
	 */
	@FXML
	private void handleCloseClick(){
		try{
			wasClosed = true;
			PhotoAlbum.saveUsers();
			stage.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This function is a helper method that is used by
	 * SearchViewController for easier handling.
	 *
	 * @returns a boolean value depicting whether or not this window was closed
	 */
	public boolean wasClosed(){
		return wasClosed;
	}
}

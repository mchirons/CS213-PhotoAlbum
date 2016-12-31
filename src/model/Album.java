package model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
* The Album program is used in tandem with various other controllers,
* depending on the PhotoAlbum's current state.
*
* Its purpose is to simply be a data structure whose variables consist of
* an album name and a reference to a list of photos.
*
* @author  Andre Pereira
* @author  Mark Hirons
* @version 1.0
* @since   2016-04-11
*/
public class Album implements java.io.Serializable
{
	private String albumName;
	private ArrayList<Photo> photos;

	public Album(){

	}
	/**
	 * This is the default constructor. It accepts a String as a
	 * parameter and sets that equal to the album's name.
	 *
	 * @param albumName
	 */
	public Album(String albumName){
		this.albumName = albumName;
		this.photos = new ArrayList<Photo>();
	}
	/**
	 * The purpose of this function is to add a photo
	 * to the current album.
	 */
	public void addPhoto(Photo photo)
	{
		if (photo != null && !photos.contains(photo)){
			photos.add(photo);
		}
	}

	public boolean deletePhoto(Photo photo)
	{
		if(photos.contains(photo))
		{
			photos.remove(photo);
			return true;
		}
		else return false;
	}

	public ArrayList<Photo> getPhotos()
	{
		return photos;
	}

	public String getAlbumName(){
		return albumName;
	}

	public void setAlbumName(String newName){
		this.albumName = newName;
	}

	private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
	{
        stream.writeObject(albumName);
        stream.writeObject(photos);
    }

	private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
		albumName = (String) stream.readObject();
        photos = (ArrayList<Photo>)stream.readObject();
    }

	public String toString(){
		return albumName;
	}

	public String getDateRange(){
		Date oldest = null;
		Date youngest = null;
		if (photos.size() > 0){
			youngest = photos.get(0).getDate();
			oldest = photos.get(0).getDate();
		}
		for (int i = 1; i < photos.size(); i++){
			if (photos.get(i).getDate().before(youngest)){
				youngest = photos.get(i).getDate();
			}
		}
		for (int i = 1; i < photos.size(); i++){
			if (photos.get(i).getDate().after(oldest)){
				oldest = photos.get(i).getDate();
			}
		}
		if (youngest == null || oldest == null){
			return "";
		}
		else{
			return (new SimpleDateFormat("MM.dd.YYYY").format(youngest)) + "-"  + (new SimpleDateFormat("MM.dd.YYYY").format(oldest));
		}



	}

}

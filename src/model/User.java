package model;

import java.io.IOException;
import java.util.ArrayList;

public class User implements Comparable<User>, java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private String userName, passWord;
	private ArrayList<Album> albums;
	//private ArrayList<Photo> photos;

	public User(){

	}

	public User(String userName, String passWord)
	{
		this.userName = userName;
		this.passWord = passWord;
		this.albums = new ArrayList<Album>();
	}

	private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
	{
        stream.writeObject(userName);
        stream.writeObject(passWord);
        stream.writeObject(albums);
    }

	private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        userName = (String) stream.readObject();
        passWord = (String) stream.readObject();
        albums = (ArrayList<Album>) stream.readObject();
    }

	public String getUserName()
	{
		return userName;
	}

	public void addAlbum(Album album)
	{
		albums.add(album);
	}

	public void renameAlbum(Album album, String newName){
		album.setAlbumName(newName);
	}

	public boolean deleteAlbum(Album album)
	{
		if(albums.contains(album))
		{
			albums.remove(album);
			return true;
		}
		else return false;
	}

	public ArrayList<Album> getAlbums(){
		return albums;
	}

	
	public ArrayList<Photo> getPhotos()
	
	{
		ArrayList<Photo> photos = new ArrayList<Photo>();
		for(int i = 0; i < albums.size(); i++)
		{
			photos.addAll(albums.get(i).getPhotos());
		}
		return photos;
	}
	


	@Override
	public String toString()
	{
		return userName;
	}

	@Override
	public int compareTo(User user)
	{
		if(user.getUserName().compareTo(this.userName) == 0)
			return 0;
		else if(user.getUserName().compareTo(this.userName) > 0)
			return 1;
		else return -1;
	}

}

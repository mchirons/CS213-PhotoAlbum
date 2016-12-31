package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;



public class Photo implements Comparable<Photo>, java.io.Serializable
{

	private static final long serialVersionUID = 2L;
	private BufferedImage image;
	private Date date;
	private String caption;
	private String URI;
//fghhh
	private Calendar calendar;

	private ArrayList<Tag> tags;

	public Photo(File file){
		try{
				BufferedImage image = ImageIO.read(file);
				if (image != null){
					this.URI = file.toURI().toString();
					this.tags = new ArrayList<Tag>();
					this.caption = "";
					long l = file.lastModified();
					//System.out.println(l);
					this.date = new Date(l);
					//System.out.println(date);
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.set(Calendar.MILLISECOND, 0);
					this.date = calendar.getTime();
					//System.out.println(calendar.getTime());
				}
		}catch (Exception e){

		}
	}

	public void addTag(Tag tag)
	{
		tags.add(tag);
	}

	public boolean deleteTag(Tag tag)
	{
		if(tags.contains(tag))
		{
			tags.remove(tag);
			return true;
		}
		else return false;
	}

	public String getURI()
	{
		return URI;
	}

	public String getCaption(){
		return caption;
	}

	public void setCaption(String caption){
		this.caption = caption;
	}

	public Date getDate(){
		return date;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public ArrayList<Tag> getTags(){
		return tags;
	}

	private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
	{
        stream.writeObject(URI);
        stream.writeObject(date);
        stream.writeObject(caption);
        stream.writeObject(tags);

    }
	
	public void setURI(String URI){
		this.URI = URI;
	}

	private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
		URI = (String) stream.readObject();
        date = (Date)stream.readObject();
        caption = (String)stream.readObject();
        tags = (ArrayList<Tag>)stream.readObject();
    }
	/*
	 * Simply for oredering based on date.
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Photo photo)
	{

		if(photo.getDate().compareTo(this.date) == 0) return 0;
		else if(photo.getDate().compareTo(this.date) < 0) return 1;
		else return -1;
	}
	/*
	 * This is to check if the actual images are equal.
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o)
	{
		/*
		if( !(o instanceof Photo)) return false;
		else
		{
			Photo photo = (Photo)o;
			if(photo.getImage().getWidth() == this.image.getWidth() && photo.getImage().getHeight() == this.image.getHeight())
			{
				int width, height;
				width = this.image.getWidth();
				height = this.image.getHeight();

				for(int y = 0; y < height; y++)
				{
					for(int x = 0; x < width; x++)
					{
						if (this.image.getRGB(x, y) != photo.getImage().getRGB(x, y))
						{
					          return false;
					    }
					}
				}
			}
			else return false;
		}

		return true;
		*/

		if (!(o instanceof Photo)) return false;
		else{
			Photo photo = (Photo)o;
			if (photo.getURI().equals(this.getURI())){
				return true;
			}
			else
			{
				return false;
			}
		}
	}

}

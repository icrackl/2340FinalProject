package todolist.wireandlights;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date end;
	private String shortDescription;
	private String type;
	private String longDescription;
	private String location;
	private boolean reoccuring,completed;
	
	public Item(String shortDescription){
		this.shortDescription=shortDescription;
		completed = false;
		type = "Add Type";
		longDescription = "Default";
		location = "No Location";
		
	}
	public Item(String shortDescription, String longDescription, Date end, String type, boolean reoccuring, int reoccuringtype){
		this.shortDescription = shortDescription;
		if(longDescription !=null)
			this.longDescription = longDescription;
		if(end!=null)
			this.end = end;
		if(type!=null)
			this.type=type;
	}
	public void setShortDescription(String shortDescription){
		this.shortDescription=shortDescription;
	}
	public void setLongDescription(String longDescription){
		this.longDescription=longDescription;
	}
	public void setDate(Date end){
		this.end=end;
	}
	public void setCompleted(){
		if(completed==true)
			completed = false;
		else
			completed = true;
	}
	public String getShortDescription(){
		return shortDescription;
	}
	public String getType(){
		return "" + type;
	}
	public String getLongDescription(){
		return "" + longDescription;
	}
	public Date getDate(){
		return end;
	}
	public boolean getCompleted(){
		return completed;
	}
	public String getLocation(){
		return location;
	}
	public void setLocation(String string) {
		// TODO Auto-generated method stub
		location = string;
	}
	public void setType(String string) {
		// TODO Auto-generated method stub
		type = string;
		
	}


}

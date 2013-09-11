package todolist.wireandlights;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userEmail,name,userPW,username;
	private ArrayList<Item> itemList;
	private ArrayList<String> categories;
	public User(String name, String username, String userpw, String useremail){
		this.userEmail=useremail;
		this.name=name;
		this.userPW=userpw;
		this.username=username;
		categories = new ArrayList<String>();
		categories.add("Personal");
		categories.add("School");
		categories.add("Work");
		itemList = new ArrayList<Item>();
	}
	public String getName(){
		return name;
	}
	public String getUserPw(){
		return userPW;
	}
	public String getUserName(){
		return username;
	}
	public String getUserEmail(){
		return userEmail;
	}
	public void  addName(String id){
		 name=id;
	}
	public void  addUserPW(String pw){
		userPW=pw;
	}
	public void  addUserEmail(String email){
		 userEmail=email;
	}
	public void  addUserName(String usr){
		 username=usr;
	}
	public ArrayList getItemList(){
		return itemList;
	}
	public boolean add(Item item) {
		// TODO Auto-generated method stub
		boolean boop = true;
		for(int i = 0; i < itemList.size() && boop == true;i++){
			if(item.getShortDescription().equals(itemList.get(i).getShortDescription()))
				return false;
		}
		itemList.add(item);
		return true;
	}
	public void remove(Item item) {
		// TODO Auto-generated method stub
		for(int i = 0;i<itemList.size();i++)
			if(item.getShortDescription().equals(itemList.get(i).getShortDescription()))
				itemList.remove(i);
		
	}
	public ArrayList<String> getCategories(){
		return categories;
	}
	public void setCompleted(Item item) {
		// TODO Auto-generated method stub
		for(int i = 0; i < itemList.size();i++){
			if(item.getShortDescription().equals(itemList.get(i).getShortDescription())){
				itemList.get(i).setCompleted();
			}
		}
	}
	
}

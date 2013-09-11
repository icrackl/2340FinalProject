package todolist.wireandlights;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Syst implements Serializable {
	/**
	 * Manager of all of the users saved onto the device. 
	 */
	private static final long serialVersionUID = 1L;
	private User currentUser;
	private ArrayList<User> users;

	public Syst(String username) {
		users = new ArrayList<User>();
	}

	public boolean addUser(String name, String username, String userpw,
			String useremail) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserName().equals(username))
				return false;
		}
		users.add(new User(name, username, userpw, useremail));
		return true;

	}
	public boolean addUser(User user){
		users.add(user);
		if(users.contains(user))
			return true;
		return false;
	}
	public boolean attemptLogin(String username, String userpw){
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserName().equals(username)&& users.get(i).getUserPw().equals(userpw)) {
				return true;
			}
		}
		return false;
		
	}
	public void setCurrentUser(String currentUser){
		for(int i = users.size()-1;i>=0;i--){
			if(users.get(i).getUserName().equals(currentUser)){
				this.currentUser = users.get(i);
			}
		}
	}

	public ArrayList getItemList() {
		// TODO Auto-generated method stub
		return currentUser.getItemList();
		
	}

	public boolean addItem(Item item) {
		// TODO Auto-generated method stub
		if(currentUser.add(item))
			return true;
		return false;
	}

	public void remove(Item item) {
		// TODO Auto-generated method stub
		currentUser.remove(item);
		
	}
	public ArrayList<String> getCategories(){
		return currentUser.getCategories();
	}

	public void addCategory(String string) {
		// TODO Auto-generated method stub
		boolean zooop =true;
		for(int i = 0; i < currentUser.getCategories().size();i++)
			if(currentUser.getCategories().get(i).equals(string))
				zooop=false;
		if(zooop)
			currentUser.getCategories().add(string);
	}

	public void setCompleted(Item item) {
		// TODO Auto-generated method stub
		currentUser.setCompleted(item);
		
	}

}

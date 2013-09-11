package todolist.wireandlights;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class MyApp extends Application {
	private Syst userManager;
	private static MyApp INSTANCE;
	public static MyApp getInstance(){
		return INSTANCE;
	}
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		INSTANCE=this;
		userManager=new Syst(null);
	}
	public void loadBinary() throws IOException {
		ObjectInputStream oos = null;
		try {
			//we are using external storage, so we need to get the location
			//be sure you have the correct privledges 
			File file = new File(Environment.getExternalStorageDirectory() + "/data.bin");
			oos = new ObjectInputStream(new FileInputStream(file));
			userManager = (Syst)oos.readObject();
		} catch (StreamCorruptedException e) {
			Log.e("APPLICATION FACADE", "Corrupted binary file");
		} catch (FileNotFoundException e) {
			Log.e("APPLICATION FACADE", "Binary File not found");
		} catch (IOException e) {
			Log.e("APPLICATION FACADE", "General IO Error");
		} catch (ClassNotFoundException e) {
			Log.e("APPLICATION FACADE", "Class not found");
		} finally {
			if (oos != null)
				oos.close();
		}
		
	}
	public void saveBinary() throws IOException {
		ObjectOutputStream oos = null;
		try {
			File file = new File(Environment.getExternalStorageDirectory() + "/data.bin");
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(userManager);
			oos.flush();
		} catch (IOException e) {
			Log.e("APPLICATION FACADE", "Binary file input general error");
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
		
	}
	public boolean addUser(String name, String username, String userpw, String useremail){
		if(userManager.addUser(name, username, userpw, useremail))
			return true;
		return false;
	}
	public boolean attemptLogin(String username, String userpw){
		if(userManager.attemptLogin(username,userpw))
			return true;
		return false;
	}
	public void setUser(String user){
		userManager.setCurrentUser(user);
	}
	public ArrayList<Item> getItemList(){
		return userManager.getItemList();
	}
	public boolean addItem(Item item){
		if(userManager.addItem(item))
			return true;
		return false;
		
	}
	public void remove(Item item) {
		userManager.remove(item);
		// TODO Auto-generated method stub
		
	}
	public void addCategory(String string) {
		// TODO Auto-generated method stub
		userManager.addCategory(string);
		
	}
	public ArrayList<String> getCategories(){
		return userManager.getCategories();
	}
	public void setCompleted(Item item) {
		// TODO Auto-generated method stub
		userManager.setCompleted(item);
		
	}
	
}

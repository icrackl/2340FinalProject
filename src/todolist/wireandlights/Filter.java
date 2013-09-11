package todolist.wireandlights;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Filter {

	
	
	public static ArrayList<Item> filterCategory(String categoryName, ArrayList <Item> items){
		ArrayList <Item> itemsCat= new ArrayList <Item>();
		for (int x=0; x<items.size(); x++){
			if(items.get(x).getType().compareTo(categoryName)==0){
				itemsCat.add(items.get(x));
			}
		}
		return itemsCat;
	}
	
	public static ArrayList<Item> filterDate(Date date, ArrayList <Item> items){
		ArrayList <Item> itemsDate= new ArrayList <Item>();
		for (int i=0; i<items.size(); i++){
			if(items.get(i).getDate()==null)
				break;
			if(items.get(i).getDate().compareTo(date) >=0){
				itemsDate.add(items.get(i));
			}
		}
		return itemsDate;
	}
	public static ArrayList<Item> filterDefault(ArrayList<Item> items){
		items = moveDate(items);
		int k = items.size()-1;
		if(k<0)
			return items;
		while(k>0){
			if(items.get(k).getDate()==null)
				k--;
			else
				break;
		}
		for (int i = 0; i <= k; i++) {
			int j = i;
			Item item = items.get(j);
			while ((j > 0) && (items.get(j - 1).getDate().compareTo(item.getDate())) > 0) {
				items.set(j,items.get(j-1));
				j--;
			}
			items.set(j, item);
		}
		return items;
	}
	
	public static ArrayList<Item> filterCompletion(ArrayList <Item> items){
		ArrayList <Item> itemsComp= new ArrayList <Item>();
		for (int i=0; i<items.size(); i++){
			if(items.get(i).getCompleted()== true){
				itemsComp.add(items.get(i));
			}
		}
		return itemsComp;
	}
	public static ArrayList<Item> filterUnCompleted(ArrayList<Item> items){
		ArrayList <Item> itemsComp= new ArrayList <Item>();
		for (int i=0; i<items.size(); i++){
			if(items.get(i).getCompleted()== false){
				itemsComp.add(items.get(i));
			}
		}
		return itemsComp;
	}
	public static ArrayList<Item> moveDate(ArrayList<Item> items){
		ArrayList<Item> temp = new ArrayList<Item>();
		for(int i = 0;i<items.size();i++){
			if(items.get(i).getDate()==null){
				temp.add(items.remove(i));
				i--;
			}
		}
		items.addAll(temp);
		return items;
	}
}

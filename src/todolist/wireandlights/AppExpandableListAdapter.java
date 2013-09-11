package todolist.wireandlights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppExpandableListAdapter extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<Item> items;
	private MyApp facade;
	private String chosenUser;
	private String filtercategory;
	private Date filterdate;
	private int currentFilter;

	public AppExpandableListAdapter(Context context, String chosenUser) {
		this.context = context;
		facade = MyApp.getInstance();
		try {
			facade.loadBinary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.chosenUser = chosenUser;
		facade.setUser(chosenUser);
		items = Filter.filterDefault(facade.getItemList());
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Item item = items.get(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.listrows, null);
		}
		TextView tv1 = (TextView) convertView.findViewById(R.id.editTextLongDescription);
		if(item.getLongDescription()!=null||!item.getLongDescription().equals(null))
			tv1.setText(item.getLongDescription().toString());
		Button b1 = (Button) convertView.findViewById(R.id.mapButton);
		Button b2 = (Button) convertView.findViewById(R.id.editItem);
		Button b3 = (Button) convertView.findViewById(R.id.deleteItem);
		b1.setOnClickListener(new MyClickHandler(item));
		b2.setOnClickListener(new MyClickHandler(item));
		b3.setOnClickListener(new MyClickHandler(item));
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (items.get(groupPosition) != null)
			return 1;
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return items.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Item item = items.get(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.listgrouprows, null);
		}
		TextView tv1 = (TextView) convertView.findViewById(R.id.tvItemDescript);
		tv1.setText(item.getShortDescription().toString());
		TextView tv2 = (TextView) convertView.findViewById(R.id.tvDate);
		tv2.setText("Boop");
		if(item.getDate()!=null){
			tv2.setText(item.getDate().getMonth() + 1 + "\n"+item.getDate().getDate()+"\n"+item.getDate().getYear());
		}
		TextView tv3 = (TextView) convertView.findViewById(R.id.tvCategory);
		tv3.setText("Default");
		if(item.getType()!=null||!(item.getType().equals("null")))
			tv3.setText(item.getType());
		LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.linearViewRows);
		if (item.getCompleted()){
			tv1.setTextColor(Color.DKGRAY);
			tv2.setTextColor(Color.DKGRAY);
			tv3.setTextColor(Color.DKGRAY);
		}else{
			tv1.setTextColor(Color.WHITE);
			tv2.setTextColor(Color.WHITE);
			tv3.setTextColor(Color.WHITE);
		}
		layout.setOnTouchListener(new MyTouchHandler(item));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean setList(Item item) {
		// TODO Auto-generated method stub
		if(facade.addItem(item)){
			//items.add(item);
			try {
				facade.saveBinary();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				facade.loadBinary();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			facade.setUser(chosenUser);
			switch(currentFilter){
			case 1:
				filterDate(filterdate);
				break;
			case 2:
				filterCategory(filtercategory);
				break;
			case 3:
				filterCompleted();
				break;
			case 4: 
				filterUnCompleted();
				break;
			default:
				items = Filter.filterDefault(facade.getItemList());
				break;
					
			}
				
			notifyDataSetChanged();
			return true;
			
		}else{
			return false;
		}
		

	}
	public void filterDate(Date date){
		filterdate=date;
		items = Filter.filterDefault(facade.getItemList());
		items = Filter.filterDate(date, items);
		currentFilter = 1;
		notifyDataSetChanged();
	}
	public void filterCategory(String category){
		items = Filter.filterDefault(facade.getItemList());
		items = Filter.filterCategory(category, items);
		filtercategory=category;
		currentFilter = 2;
		notifyDataSetChanged();
	}
	public void noFilter(){
		currentFilter = 0;
		items = Filter.filterDefault(facade.getItemList());
		notifyDataSetChanged();
	}
	public void filterCompleted(){
		items = Filter.filterDefault(facade.getItemList());
		items = Filter.filterCompletion(items);
		currentFilter = 3;
		notifyDataSetChanged();
	}
	public void filterUnCompleted(){
		items = Filter.filterDefault(facade.getItemList());
		items = Filter.filterUnCompleted(items);
		currentFilter = 4;
		notifyDataSetChanged();
	}
	public ArrayList<String> returnCategories(){
		return facade.getCategories();
	}
	public void resetAdapter(){
		try {
			facade.loadBinary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		facade.setUser(chosenUser);
		items = Filter.filterDefault(facade.getItemList());
		notifyDataSetChanged();
	}

	private class MyClickHandler implements View.OnClickListener {
		private Item item;

		public MyClickHandler(Item item) {
			this.item = item;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId()==R.id.editItem){
				Intent i = new Intent("todolist.wireandlights.EDITITEMSACTIVITY");
				Bundle b = new Bundle();
				String[]value = new String[5];
				value[0]=item.getShortDescription();
				if(item.getDate()!=null)
					value[1]= item.getDate().toString();
				if(item.getType()!=null)
					value[2] = item.getType();
				if(item.getLongDescription()!=null)
					value[3] = item.getLongDescription();
				value[4]=chosenUser;
				b.putStringArray("ItemInfo", value);
				i.putExtras(b);
				context.startActivity(i);
			}else if(v.getId()==R.id.mapButton){
				Intent i = new Intent("todolist.wireandlights.GOOGLEMAPACTIVITY");
				Bundle bundle = new Bundle();
				bundle.putString("currentuser", chosenUser);
				bundle.putString("Focus", item.getShortDescription());
				i.putExtras(bundle);
				context.startActivity(i);
			}
			else{
				facade.remove(item);
				items.remove(item);
				try {
					facade.saveBinary();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				facade.setUser(chosenUser);
				notifyDataSetChanged();
			}

		}

	}

	private class MyTouchHandler implements View.OnTouchListener {
		private Item item;
		private float x;
		private float x2;

		public MyTouchHandler(Item item) {
			this.item = item;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				x2 = x;
				return true;
			case MotionEvent.ACTION_UP:
				x2 = event.getX();
				break;

			}
			if (x2 - x > 100|| x-x2 > 100) {
				//item.setCompleted();
				facade.setCompleted(item);
				try {
					facade.saveBinary();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				facade.setUser(chosenUser);
				notifyDataSetChanged();
				return true;
			}
			return true;

		}

	}

}

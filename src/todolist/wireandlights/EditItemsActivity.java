package todolist.wireandlights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class EditItemsActivity extends Activity implements View.OnClickListener {
	private MyApp facade;
	private EditText shortDescript,longDescript,location,category;
	private Spinner categoryList;
	private CheckBox categorySelector;
	private DatePicker datepicker;
	private TimePicker timepicker;
	private Item item;
	private ArrayList<Item> items;
	private Button cancel,save;
	private String[] iteminfo;
	private String chosenUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.todolistedititems);
		facade = MyApp.getInstance();
		
		try {
			facade.loadBinary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iteminfo =  getIntent().getExtras().getStringArray("ItemInfo");
		Log.d("String Passed",iteminfo[0]);
		chosenUser = iteminfo[4];
		facade.setUser(chosenUser);
		ArrayList<Item> items = facade.getItemList();
		for(int i = 0; i < items.size();i++){
			if(iteminfo[0].equals(items.get(i).getShortDescription())){
				item = items.get(i);
				break;
			}	
		}
		shortDescript = (EditText) findViewById(R.id.editTextShortDescription);
		if(item.getShortDescription()!=null)
			shortDescript.setText(item.getShortDescription());
		longDescript=(EditText) findViewById(R.id.editTextLongDescription);
		if(item.getLongDescription()!=null||item.getLongDescription().equals(""))
			longDescript.setText(item.getLongDescription());
		location = (EditText) findViewById(R.id.editTextLocation);
		if(item.getLocation()!=null||item.getLocation().equals(""))
			location.setText(item.getLocation());
		datepicker = (DatePicker) findViewById(R.id.datePicker1);
		if(item.getDate()!=null)
			datepicker.updateDate(item.getDate().getYear(), item.getDate().getMonth(), item.getDate().getDate());
		timepicker = (TimePicker) findViewById(R.id.timePicker1);
		timepicker.setIs24HourView(true);
		if(item.getDate()!=null){
			timepicker.setCurrentHour(item.getDate().getHours());
			timepicker.setCurrentMinute(item.getDate().getMinutes());
		}
		cancel = (Button) findViewById(R.id.EditItemsCancel);
		save = (Button) findViewById(R.id.EditItemsSave);
		categorySelector = (CheckBox) findViewById (R.id.EditCheckBox);
		category = (EditText) findViewById(R.id.EditCategory);
		categoryList = (Spinner) findViewById(R.id.EditSpinner);
		ArrayAdapter<String> adap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,facade.getCategories());
		adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categoryList.setAdapter(adap);
		categorySelector.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					categoryList.setVisibility(View.INVISIBLE);
					category.setVisibility(View.VISIBLE);
				}else{
					category.setVisibility(View.INVISIBLE);
					categoryList.setVisibility(View.VISIBLE);
				}
					
				// TODO Auto-generated method stub
				
			}
			
		});
		
		cancel.setOnClickListener(this);
		save.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()){
		case R.id.EditItemsCancel:
			finish();
			break;
		case R.id.EditItemsSave:
			Log.d("EditItems","Before Stuff");
			shortDescript.getText().toString();
			Log.d("EditItems","Attempting to load a few things");
			item.setShortDescription(shortDescript.getText().toString());
			Log.d("EditItems","Short Description");
			item.setLongDescription(longDescript.getText().toString());
			Log.d("EditItems","Long Description");
			item.setLocation(location.getText().toString());
			Log.d("EditItems","Location");
			item.setDate(new Date(datepicker.getYear(),datepicker.getMonth(),datepicker.getDayOfMonth(),timepicker.getCurrentHour(),timepicker.getCurrentMinute()));
			Log.d("EditItems","Date");
			if(categorySelector.isChecked()){
				item.setType(category.getText().toString());
				Log.d("EditItems","Is Checked");
				facade.addCategory(category.getText().toString());
			}else{
				item.setType(categoryList.getSelectedItem().toString());
				Log.d("EditItems","Not Checked");
			}
			try {
				facade.saveBinary();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();
			
			
			break;
		}
		// TODO Auto-generated method stub
		
	}

}

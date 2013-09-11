package todolist.wireandlights;

/*
 * The lack of comments in this program is pretty lame... We should work on that.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

public class ToDoList extends Activity implements View.OnClickListener {
	private MyApp facade;
	private String chosenUser;
	private ViewSwitcher switcher;
	private ExpandableListView list;
	private AppExpandableListAdapter adap;
	private Button add, filterAll, filterCompleted, filterUnCompleted,
			filterDate, filterCategory;
	private Spinner categories;
	private DatePicker filterDatePicker;
	private TimePicker filterTime;
	private boolean switched;
	private EditText descript;
	private String[] thing = { "Boop", "Boop2", "Boop3", "Boop4" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todolist);
		switcher = (ViewSwitcher) findViewById(R.id.filterViewSwitcher);
		switched = false;
		chosenUser = getIntent().getExtras().getString("chosenUser");
		list = (ExpandableListView) findViewById(R.id.itemListView);
		add = (Button) findViewById(R.id.bAddItem);
		descript = (EditText) findViewById(R.id.eShortDescription);

		filterAll = (Button) findViewById(R.id.bNoFilter);
		filterAll.setOnClickListener(this);
		filterCompleted = (Button) findViewById(R.id.bCompletedFilter);
		filterCompleted.setOnClickListener(this);
		filterUnCompleted = (Button) findViewById(R.id.bUnCompletedFilter);
		filterUnCompleted.setOnClickListener(this);
		filterDate = (Button) findViewById(R.id.bDateFilter);
		filterDate.setOnClickListener(this);
		filterCategory = (Button) findViewById(R.id.bCategoryFilter);
		filterCategory.setOnClickListener(this);
		filterDatePicker = (DatePicker) findViewById(R.id.filterDatePicker);
		filterTime = (TimePicker) findViewById(R.id.filterTimePicker);
		filterTime.setIs24HourView(true);
		categories = (Spinner) findViewById(R.id.filterSpinner);

		adap = new AppExpandableListAdapter(this, chosenUser);
		list.setAdapter(adap);
		// list.setOnTouchListener(l);
		add.setOnClickListener(this);
		ArrayAdapter<String> adap2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, adap.returnCategories());
		adap2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categories.setAdapter(adap2);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			if (!switched) {
				AlertDialog alert = new AlertDialog.Builder(this).create();
				alert.setMessage("Choose Option");
				alert.setButton(DialogInterface.BUTTON_POSITIVE, "Map",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent i = new Intent("todolist.wireandlights.GOOGLEMAPACTIVITY");
								Bundle b = new Bundle();
								b.putString("currentuser", chosenUser);
								i.putExtras(b);
								startActivity(i);
							}
						});
				alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Filter",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								switched = true;
								switcher.showNext();
							}
						});
				alert.show();
				return true;
			}
			break;
		case KeyEvent.KEYCODE_BACK:
			if (switched) {
				switcher.showPrevious();
				switched = false;
				return true;
			}
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		switched=false;
		adap.resetAdapter();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bAddItem:
			if (descript.getText() != null) {
				Item i = new Item(descript.getText().toString());
				if (adap.setList(i) == false) {
					descript.setText("");
					descript.setHint("Item Already Exists");
				} else {
					descript.setText("");
					descript.setHint("Item Added");
				}
			}
			break;
		case R.id.bNoFilter:
			switched = false;
			adap.noFilter();
			switcher.showPrevious();
			break;
		case R.id.bCompletedFilter:
			switched = false;
			adap.filterCompleted();
			switcher.showPrevious();
			break;
		case R.id.bUnCompletedFilter:
			switched = false;
			adap.filterUnCompleted();
			switcher.showPrevious();
			break;
		case R.id.bCategoryFilter:
			switched = false;
			adap.filterCategory(categories.getSelectedItem().toString());
			switcher.showPrevious();
			break;
		case R.id.bDateFilter:
			switched = false;
			Date date = new Date(filterDatePicker.getYear(),
					filterDatePicker.getMonth(),
					filterDatePicker.getDayOfMonth(),
					filterTime.getCurrentHour(), filterTime.getCurrentMinute());
			adap.filterDate(date);
			switcher.showPrevious();
			break;
		}

	}

}

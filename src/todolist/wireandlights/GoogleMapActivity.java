package todolist.wireandlights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GoogleMapActivity extends MapActivity {
	/** Called when the activity is first created. */
	private GeoPoint point;
	private Geocoder geocoder;
	private TextView tView;
	private MyApp facade;
	private ArrayList<Item> items;
	private OverlayItem overlay;
	double lat = 0, longi = 0;
	private MapController controller;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		facade = MyApp.getInstance();
		try {
			facade.loadBinary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		facade.setUser(getIntent().getExtras().getString("currentuser"));
		items = facade.getItemList();
		// write forloop for creating Overlay Items and adding them to the
		// overlay
		// get address for each one that actually has a location
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		controller = mapView.getController();
		controller.setZoom(6);
		geocoder = new Geocoder(this);
		if(getIntent().getExtras().getString("Focus")!=null){
			String s = getIntent().getExtras().getString("Focus");
			Item item = null;
			for(int i = 0; i < items.size();i++){
				if (items.get(i).getShortDescription().equals(s)){
					item = items.get(i);
					i = items.size();
				}
			}
			boolean tem = false;
			while(!tem){
				try {
					if (item.getLocation() != null
							&& !(item.getLocation().equals("No Location"))) {
						List<Address> list = geocoder.getFromLocationName(item
								.getLocation(), 1);
						if (list.size() != 0) {
							controller.setCenter(new GeoPoint((int) (list.get(0)
									.getLatitude() * 1E6), (int) (list.get(0)
									.getLongitude() * 1E6)));
							controller.setZoom(16);
							Log.d("Lat and Long", ""
									+ list.get(0).getLatitude() + "   "
									+ list.get(0).getLongitude());
							tem = true;
						} else {
							Log.d("Size", item.getShortDescription());
						}
					} else
						tem = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		tView = (TextView) findViewById(R.id.tvView);
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.androidmarker);
		HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(
				drawable, this);

		LocationManager locationMgr = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationLnr = new LocationListener() {

			@Override
			public void onLocationChanged(Location loca) {
				// TODO Auto-generated method stub
				makeUseOfNewLocation(loca);

			}

			@Override
			public void onProviderDisabled(String arg0) {

			}

			@Override
			public void onProviderEnabled(String arg0) {

			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

			}

		};
		locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locationLnr);
		for (int i = 0; i < items.size(); i++) {
			Log.d("Thing", items.get(i).getShortDescription());
			boolean temp = false;
			while (!temp) {
				try {
					if (items.get(i).getLocation() != null
							&& !(items.get(i).getLocation().equals("Default"))) {
						List<Address> list = geocoder.getFromLocationName(items
								.get(i).getLocation(), 1);
						if (list.size() != 0) {
							Log.d("Lat and Long", ""
									+ list.get(0).getLatitude() + "   "
									+ list.get(0).getLongitude());
							point = new GeoPoint((int) (list.get(0)
									.getLatitude() * 1E6), (int) (list.get(0)
									.getLongitude() * 1E6));
							overlay = new OverlayItem(point, items.get(i)
									.getShortDescription(), items.get(i)
									.getLongDescription());
							itemizedoverlay.addOverlay(overlay);
							temp = true;
						} else {
							Log.d("Size", items.get(i).getShortDescription());
						}
					} else
						temp = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		mapOverlays.add(itemizedoverlay);
	}

	private void makeUseOfNewLocation(Location location) {
		tView.setText("the location is: Lat"
				+ Double.toString(location.getLatitude()) + " long"
				+ Double.toString(location.getLongitude()) + ".");
		lat = (location.getLatitude());
		longi = location.getLongitude();

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
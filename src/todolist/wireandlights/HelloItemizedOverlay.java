package todolist.wireandlights;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class HelloItemizedOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> mOverlays =new ArrayList<OverlayItem>();
	 Context mContext;
	public HelloItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
		mContext=context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}

	public void addOverlay (OverlayItem overlay){
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		OverlayItem item=mOverlays.get(index);
		AlertDialog.Builder dialog =new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		
		return true;
	}

	
}
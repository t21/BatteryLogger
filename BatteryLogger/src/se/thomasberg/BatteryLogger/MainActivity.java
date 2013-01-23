package se.thomasberg.BatteryLogger;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		startService(new Intent(this, BatteryService.class));		

		TextView tv1 = (TextView) findViewById(R.id.textView2);
		tv1.setText(getAverageFullChargingTime());

	}

	private String getAverageFullChargingTime() {

		String[] columns = { MyApplication._ID, MyApplication.TYPE, MyApplication.TIME, MyApplication.CAPACITY, MyApplication.PLUGGED, MyApplication.POWER_ONOFF};
		//					String where = BattLogContentProvider.TIME + " >=? AND " + BattLogContentProvider.TIME + " <=?";
		//			String[] args = { Long.toString(startCal.getTimeInMillis()), Long.toString(startCal.getTimeInMillis() + fullScreenTimeInMillis) };
		String where = MyApplication.TYPE + " = ?";
		String[] args = { "" + MyApplication.ONOFF_DATA };
		Cursor c = this.getContentResolver().query(MyApplication.BATTERY_CONTENT_URI, columns, where, args, null);
		Log.d(MyApplication.TAG, "" + c.getCount());

		boolean lastEventWasOn = false;

		if (c.getCount() > 0) {
			c.moveToFirst();
			do{
				if (c.getInt(c.getColumnIndex(MyApplication.POWER_ONOFF)) == 1) {
					if (lastEventWasOn) {
						String[] columns2 = { MyApplication._ID, MyApplication.TYPE, MyApplication.TIME, MyApplication.CAPACITY, MyApplication.PLUGGED, MyApplication.POWER_ONOFF};
						String where2 = MyApplication._ID + " = ?";
						String[] args2 = { "" + (c.getInt(c.getColumnIndex(MyApplication._ID)) - 1) };
						Cursor d = this.getContentResolver().query(MyApplication.BATTERY_CONTENT_URI, columns2, where2, args2, null);
						Log.d(MyApplication.TAG, "" + d.getCount());
						if (d.getCount() > 0) {
							d.moveToFirst();
							Log.v(MyApplication.TAG, "Id:" + d.getInt(d.getColumnIndex(MyApplication._ID))); 
							Log.v(MyApplication.TAG, "Type:" + d.getInt(d.getColumnIndex(MyApplication.TYPE))); 
							Log.v(MyApplication.TAG, "OnOff:" + d.getInt(d.getColumnIndex(MyApplication.POWER_ONOFF))); 
							Log.v(MyApplication.TAG, "Plugged:" + d.getInt(d.getColumnIndex(MyApplication.PLUGGED))); 
							Log.v(MyApplication.TAG, "Time:" + dateTimeInMillisToString(d.getLong(d.getColumnIndex(MyApplication.TIME)))); 
							Log.v(MyApplication.TAG, "Capcity:" + d.getInt(d.getColumnIndex(MyApplication.CAPACITY))); 
							Log.v(MyApplication.TAG, "-----------------------------------"); 
						}
						d.close();
					}
					lastEventWasOn = true;
				} else {
					lastEventWasOn = false;
				}
				if (MyApplication.DEBUG_MainActivity) {
					Log.v(MyApplication.TAG, "Id:" + c.getInt(c.getColumnIndex(MyApplication._ID))); 
					Log.v(MyApplication.TAG, "Type:" + c.getInt(c.getColumnIndex(MyApplication.TYPE))); 
					Log.v(MyApplication.TAG, "OnOff:" + c.getInt(c.getColumnIndex(MyApplication.POWER_ONOFF))); 
					Log.v(MyApplication.TAG, "Plugged:" + c.getInt(c.getColumnIndex(MyApplication.PLUGGED))); 
					Log.v(MyApplication.TAG, "Time:" + dateTimeInMillisToString(c.getLong(c.getColumnIndex(MyApplication.TIME)))); 
					Log.v(MyApplication.TAG, "Capcity:" + c.getInt(c.getColumnIndex(MyApplication.CAPACITY))); 
					Log.v(MyApplication.TAG, "-----------------------------------"); 
				}
				//				}
			} while (c.moveToNext());
		}
		c.close();


		return "under development";
	}


	private String dateTimeInMillisToString(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		String timeStr = (String) DateFormat.format("dd MMM yyyy - k:mm:ss", cal);
		return timeStr;
	}

}
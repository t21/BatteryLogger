package se.thomasberg.BatteryLogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BatteryReceiver2 extends BroadcastReceiver {

	private Long startTime;

	@Override
	public void onReceive(Context context, Intent intent) {

		if (MyApplication.DEBUG_BatteryBrodcastReceiver) {
			startTime = System.currentTimeMillis();
			Log.v(MyApplication.TAG, "BatteryBroadcastReceiver: onReceive begin");
		}

		Intent serviceIntent = new Intent(context, BatteryService.class);
		serviceIntent.putExtras(intent.getExtras());
		serviceIntent.putExtra(MyApplication.BROADCAST_EVENT, intent.getAction());
		context.startService(serviceIntent);			

		//		BatteryBundle batteryBundle = new BatteryBundle(intent.getExtras());
		//
		//		if (batteryBundle.isValid()) {
		//			if (batteryBundle.getBatteryCapacity() != lastCapacity) {
		//				ContentValues values = new ContentValues();
		//				values.put(MyApplication.TYPE, MyApplication.BATT_DATA);
		//				values.put(MyApplication.TIME, System.currentTimeMillis());
		//				values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
		//				values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
		//				values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
		//				values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
		//				values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
		//				values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
		//				context.getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
		//				batteryLoggerNotification.update(context, batteryBundle.getBundle());
		//				lastCapacity = batteryBundle.getBatteryCapacity();
		//			}
		//		}

		if (MyApplication.DEBUG_BatteryBrodcastReceiver) {
			Log.v(MyApplication.TAG, "BatteryBroadcastReceiver: onReceive end " + (System.currentTimeMillis() - startTime) + "ms");
		}

	}

}

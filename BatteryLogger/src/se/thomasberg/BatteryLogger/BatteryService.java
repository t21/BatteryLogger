package se.thomasberg.BatteryLogger;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class BatteryService extends Service {

	private int lastCapacity;
	private BatteryLoggerNotification batteryLoggerNotification;

	private BatteryReceiver batteryReceiver; 
	private PowerReceiver powerReceiver;
	private ScreenBroadcastReceiver screenBroadcastReceiver;
	private ShutdownBroadcastReceiver shutdownBroadcastReceiver;
//	private Builder notification;

	@Override
	public void onCreate() {

		Log.d("se.thomasberg", "onCreate");
		
		batteryReceiver = new BatteryReceiver(); 
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		//		intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
		//		intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
		//		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		//		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		//		intentFilter.addAction(Intent.ACTION_SHUTDOWN);
		registerReceiver(batteryReceiver, intentFilter);

		powerReceiver = new PowerReceiver(); 
		intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
		intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
		registerReceiver(powerReceiver, intentFilter);

		screenBroadcastReceiver = new ScreenBroadcastReceiver(); 
		intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(screenBroadcastReceiver, intentFilter);

		shutdownBroadcastReceiver = new ShutdownBroadcastReceiver(); 
		intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SHUTDOWN);
		registerReceiver(shutdownBroadcastReceiver, intentFilter);

		batteryLoggerNotification = new BatteryLoggerNotification(this);
		
		startForeground(batteryLoggerNotification.getId(), batteryLoggerNotification.getNotification());

		super.onCreate();

	}


	@Override
	public void onDestroy() {
		Log.d("se.thomasberg", "onDestroy");
		unregisterReceiver(batteryReceiver);
		unregisterReceiver(powerReceiver);
		unregisterReceiver(screenBroadcastReceiver);
		unregisterReceiver(shutdownBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

//		Log.d("se.thomas", "-Intent:" + intent);
		if (intent != null && Intent.ACTION_BOOT_COMPLETED.equals(intent.getStringExtra(MyApplication.BROADCAST_EVENT))) {
//			String event = intent.getStringExtra(MyApplication.BROADCAST_EVENT);
//
//			if (Intent.ACTION_BOOT_COMPLETED.equals(event)) {

				IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
				Intent batteryStatus = registerReceiver(null, ifilter);
				BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());

				int onoff = 1;
				ContentValues values = new ContentValues();
				values.put(MyApplication.TYPE, MyApplication.ONOFF_DATA);
				values.put(MyApplication.TIME, System.currentTimeMillis());
				values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
				values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
				values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
				values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
				values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
				values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
				values.put(MyApplication.POWER_ONOFF, onoff);
				getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);

//			}
		}
			

		//		if (Intent.ACTION_BATTERY_CHANGED.equals(event)) {
		//			storeBatteryEvent(intent);
		//		} else if (Intent.ACTION_SCREEN_OFF.equals(event)) {
		//			storeScreenEvent(intent, 0);
		//		} else if (Intent.ACTION_SCREEN_ON.equals(event)) {
		//			storeScreenEvent(intent, 1);
		//		} else if (Intent.ACTION_POWER_DISCONNECTED.equals(event)) {
		//			storePowerEvent(intent, 0);
		//		} else if (Intent.ACTION_POWER_CONNECTED.equals(event)) {
		//			storePowerEvent(intent, 1);
		//		} else if (Intent.ACTION_BOOT_COMPLETED.equals(event)) {
		//			storeBootEvent();
		//
		//			//			IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		//			//			Intent batteryStatus = registerReceiver(null, ifilter);
		//			//			BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());
		//			//
		//			//			int onoff = 1;
		//			//			ContentValues values = new ContentValues();
		//			//			values.put(MyApplication.TYPE, MyApplication.ONOFF_DATA);
		//			//			values.put(MyApplication.TIME, System.currentTimeMillis());
		//			//			values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
		//			//			values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
		//			//			values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
		//			//			values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
		//			//			values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
		//			//			values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
		//			//			values.put(MyApplication.POWER_ONOFF, onoff);
		//			//			getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
		//		} else if (Intent.ACTION_SHUTDOWN.equals(event)) {
		//			storeShutdownEvent();
		//		} 

//		return super.onStartCommand(intent, flags, startId);
				return START_STICKY;
	}


//	private void storeBatteryEvent(Intent intent) {
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
//				this.getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
//				batteryLoggerNotification.update(this, batteryBundle.getBundle());
//				lastCapacity = batteryBundle.getBatteryCapacity();
//			}
//		}
//	}


//	private void storeScreenEvent(Intent intent, int type) {
//
//		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//		Intent batteryStatus = registerReceiver(null, ifilter);
//		BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());
//
//		//		String event = intent.getStringExtra(MyApplication.BROADCAST_EVENT);
//
//		if (type == 1) {
//			Log.v(MyApplication.TAG, "SCREEN ON");
//			ContentValues values = new ContentValues();
//			values.put(MyApplication.TYPE, MyApplication.SCREEN_DATA);
//			values.put(MyApplication.TIME, System.currentTimeMillis());
//			values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
//			values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
//			values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
//			values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
//			values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
//			values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
//			values.put(MyApplication.SCREEN_ONOFF, 1);
//			getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
//		} else if (type == 0) {
//			Log.v(MyApplication.TAG, "SCREEN OFF");
//			ContentValues values = new ContentValues();
//			values.put(MyApplication.TYPE, MyApplication.SCREEN_DATA);
//			values.put(MyApplication.TIME, System.currentTimeMillis());
//			values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
//			values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
//			values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
//			values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
//			values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
//			values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
//			values.put(MyApplication.SCREEN_ONOFF, 0);
//			getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
//		}
//	}


//	private void storePowerEvent(Intent intent, int type) {
//		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//		Intent batteryStatus = registerReceiver(null, ifilter);
//		BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());
//
//		//		String event = intent.getStringExtra(MyApplication.BROADCAST_EVENT);
//
//		if (type == 1) {
//			ContentValues values = new ContentValues();
//			values.put(MyApplication.TYPE, MyApplication.POWER_DATA);
//			values.put(MyApplication.TIME, System.currentTimeMillis());
//			values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
//			values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
//			values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
//			values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
//			values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
//			values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
//			getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
//		} else if (type == 0) {
//			ContentValues values = new ContentValues();
//			values.put(MyApplication.TYPE, MyApplication.POWER_DATA);
//			values.put(MyApplication.TIME, System.currentTimeMillis());
//			values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
//			values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
//			values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
//			values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
//			values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
//			values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
//			getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
//		}
//	}


//	private void storeBootEvent() {
//		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//		Intent batteryStatus = registerReceiver(null, ifilter);
//		BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());
//
//		int onoff = 1;
//		ContentValues values = new ContentValues();
//		values.put(MyApplication.TYPE, MyApplication.ONOFF_DATA);
//		values.put(MyApplication.TIME, System.currentTimeMillis());
//		values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
//		values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
//		values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
//		values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
//		values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
//		values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
//		values.put(MyApplication.POWER_ONOFF, onoff);
//		getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
//	}


//	private void storeShutdownEvent() {
//		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//		Intent batteryStatus = registerReceiver(null, ifilter);
//		BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());
//
//		int onoff = 0;
//		ContentValues values = new ContentValues();
//		values.put(MyApplication.TYPE, MyApplication.ONOFF_DATA);
//		values.put(MyApplication.TIME, System.currentTimeMillis());
//		values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
//		values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
//		values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
//		values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
//		values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
//		values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
//		values.put(MyApplication.POWER_ONOFF, onoff);
//		getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
//	}


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}




	//	if (Intent.ACTION_BATTERY_CHANGED.equals(event)) {
	//		storeBatteryEvent(intent);
	//	} else if (Intent.ACTION_SCREEN_OFF.equals(event)) {
	//		storeScreenEvent(intent, 0);
	//	} else if (Intent.ACTION_SCREEN_ON.equals(event)) {
	//		storeScreenEvent(intent, 1);
	//	} else if (Intent.ACTION_POWER_DISCONNECTED.equals(event)) {
	//		storePowerEvent(intent, 0);
	//	} else if (Intent.ACTION_POWER_CONNECTED.equals(event)) {
	//		storePowerEvent(intent, 1);
	//	} else if (Intent.ACTION_BOOT_COMPLETED.equals(event)) {
	//		storeBootEvent();
	//
	//		//			IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	//		//			Intent batteryStatus = registerReceiver(null, ifilter);
	//		//			BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());
	//		//
	//		//			int onoff = 1;
	//		//			ContentValues values = new ContentValues();
	//		//			values.put(MyApplication.TYPE, MyApplication.ONOFF_DATA);
	//		//			values.put(MyApplication.TIME, System.currentTimeMillis());
	//		//			values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
	//		//			values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
	//		//			values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
	//		//			values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
	//		//			values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
	//		//			values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
	//		//			values.put(MyApplication.POWER_ONOFF, onoff);
	//		//			getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
	//	} else if (Intent.ACTION_SHUTDOWN.equals(event)) {
	//		storeShutdownEvent();
	//	} 
	//

	private class BatteryReceiver extends BroadcastReceiver {

		private Long startTime;
		//		private BatteryLoggerNotification batteryLoggerNotification;

		@Override
		public void onReceive(Context context, Intent intent) {

			if (MyApplication.DEBUG_BatteryBrodcastReceiver) {
				startTime = System.currentTimeMillis();
				Log.v(MyApplication.TAG, "BatteryBroadcastReceiver: onReceive begin");
			}

			//			Intent serviceIntent = new Intent(context, BatteryService.class);
			//			serviceIntent.putExtras(intent.getExtras());
			//			//		serviceIntent.putExtra(BatteryService.EVENT_NAME, intent.getAction());
			//			context.startService(serviceIntent);			

			//			batteryLoggerNotification = new BatteryLoggerNotification();
			BatteryBundle batteryBundle = new BatteryBundle(intent.getExtras());

			if (batteryBundle.isValid()) {
				if (batteryBundle.getBatteryCapacity() != lastCapacity) {
					ContentValues values = new ContentValues();
					values.put(MyApplication.TYPE, MyApplication.BATT_DATA);
					values.put(MyApplication.TIME, System.currentTimeMillis());
					values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
					values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
					values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
					values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
					values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
					values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
					context.getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
					batteryLoggerNotification.update(context, batteryBundle.getBundle());
					lastCapacity = batteryBundle.getBatteryCapacity();
				}
			}

			if (MyApplication.DEBUG_BatteryBrodcastReceiver) {
				Log.v(MyApplication.TAG, "BatteryBroadcastReceiver: onReceive end " + (System.currentTimeMillis() - startTime) + "ms");
			}

		}

	}


	private class PowerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//			Intent serviceIntent = new Intent(context, PowerService.class);
			//			serviceIntent.putExtra(MyApplication.BROADCAST_EVENT, intent.getAction());
			//			context.startService(serviceIntent);			
			IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			Intent batteryStatus = registerReceiver(null, ifilter);
			BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());

			String event = intent.getStringExtra(MyApplication.BROADCAST_EVENT);

			if (Intent.ACTION_POWER_CONNECTED.equals(event)) {
				ContentValues values = new ContentValues();
				values.put(MyApplication.TYPE, MyApplication.POWER_DATA);
				values.put(MyApplication.TIME, System.currentTimeMillis());
				values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
				values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
				values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
				values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
				values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
				values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
				getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
			} else if (Intent.ACTION_POWER_DISCONNECTED.equals(event)) {
				ContentValues values = new ContentValues();
				values.put(MyApplication.TYPE, MyApplication.POWER_DATA);
				values.put(MyApplication.TIME, System.currentTimeMillis());
				values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
				values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
				values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
				values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
				values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
				values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
				getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
			}
		}

	}


	private class ScreenBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//			Intent serviceIntent = new Intent(context, ScreenService.class);
			//			serviceIntent.putExtra(MyApplication.BROADCAST_EVENT, intent.getAction());
			//			context.startService(serviceIntent);			
			IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			Intent batteryStatus = registerReceiver(null, ifilter);
			BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());

			String event = intent.getStringExtra(MyApplication.BROADCAST_EVENT);

			if (Intent.ACTION_SCREEN_ON.equals(event)) {
				Log.v(MyApplication.TAG, "SCREEN ON");
				ContentValues values = new ContentValues();
				values.put(MyApplication.TYPE, MyApplication.SCREEN_DATA);
				values.put(MyApplication.TIME, System.currentTimeMillis());
				values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
				values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
				values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
				values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
				values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
				values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
				values.put(MyApplication.SCREEN_ONOFF, 1);
				getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
			} else if (Intent.ACTION_SCREEN_OFF.equals(event)) {
				Log.v(MyApplication.TAG, "SCREEN OFF");
				ContentValues values = new ContentValues();
				values.put(MyApplication.TYPE, MyApplication.SCREEN_DATA);
				values.put(MyApplication.TIME, System.currentTimeMillis());
				values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
				values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
				values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
				values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
				values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
				values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
				values.put(MyApplication.SCREEN_ONOFF, 0);
				getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
			}
		}

	}


	private class ShutdownBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//			Intent serviceIntent = new Intent(context, BatteryService.class);
			//			serviceIntent.putExtra(MyApplication.BROADCAST_EVENT, intent.getAction());
			//			context.startService(serviceIntent);			

			//			 else if (Intent.ACTION_SHUTDOWN.equals(event)) {
			IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			Intent batteryStatus = registerReceiver(null, ifilter);
			BatteryBundle batteryBundle = new BatteryBundle(batteryStatus.getExtras());

			int onoff = 0;
			ContentValues values = new ContentValues();
			values.put(MyApplication.TYPE, MyApplication.ONOFF_DATA);
			values.put(MyApplication.TIME, System.currentTimeMillis());
			values.put(MyApplication.CAPACITY, batteryBundle.getBatteryCapacity());
			values.put(MyApplication.VOLTAGE, batteryBundle.getBatteryVoltage());
			values.put(MyApplication.TEMPERATURE, batteryBundle.getBatteryTemperature());
			values.put(MyApplication.STATUS, batteryBundle.getBatteryStatus());
			values.put(MyApplication.HEALTH, batteryBundle.getBatteryHealth());
			values.put(MyApplication.PLUGGED, batteryBundle.getBatteryPlugged());
			values.put(MyApplication.POWER_ONOFF, onoff);
			getContentResolver().insert(MyApplication.BATTERY_CONTENT_URI, values);
		}

	}

}

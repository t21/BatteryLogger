package se.thomasberg.BatteryLogger;

import android.app.Application;
import android.net.Uri;

public class MyApplication extends Application {
	
	public static int lastCapacity = -1;

	/** TAG that is shown in logs */
	public static final String TAG = "se.thomasberg.BatteryLogger";
	
	/** enable/disable specific parts of the logs */
	public static final boolean DEBUG_BatteryBrodcastReceiver = true;
	public static final boolean DEBUG_BatteryLoggerContentProvider = false;
	public static final boolean DEBUG_MainActivity = false;
	public static final boolean DEBUG_DiagramView = true;
	
	/** Name of the Content provider */
	public static final String PROVIDER_NAME = "se.thomasberg.provider.BatteryLogger";

	/** URI of the content provider */
	public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME);
	public static final Uri BATTERY_CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/battery");
//	public static final Uri CHARGER_CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/charger");
//	public static final Uri ONOFF_CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/onoff");
//	public static final Uri SCREEN_CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/screen");

	/** Content provider table columns */
	public static String _ID = "_id";
	public static final String TYPE = "type";
	public static final String TIME = "time";
	public static final String CAPACITY = "capacity";
	public static final String VOLTAGE = "voltage";
	public static final String TEMPERATURE = "temperature";
	public static final String STATUS = "status";
	public static final String PLUGGED = "plugged";
	public static final String HEALTH = "health";
	public static final String POWER_ONOFF = "poweronoff";
	public static final String SCREEN_ONOFF = "screenonoff";

	/** Content provider tables */
	public static final String BATTERY_DATA_TABLE_NAME = "BatteryData";
//	public static final String CHARGER_DATA_TABLE_NAME = "ChargerData";
//	public static final String ONOFF_DATA_TABLE_NAME = "OnOffData";
//	public static final String SCREEN_DATA_TABLE_NAME = "ScreenData";

	public static final String BROADCAST_EVENT = "event";

	public static final int BATT_DATA = 0;
	public static final int POWER_DATA = 1;
	public static final int ONOFF_DATA = 2;
	public static final int SCREEN_DATA = 3;


}

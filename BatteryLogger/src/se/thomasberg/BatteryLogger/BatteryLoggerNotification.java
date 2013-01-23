package se.thomasberg.BatteryLogger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.RemoteViews;

public class BatteryLoggerNotification extends Notification.Builder {

	private static final int NOTIFICATION_VIEW_ID = 3;
//	Notification notification;

	public BatteryLoggerNotification(Context context) {
		super(context);
	}

	public void update(Context context, Bundle extras) {
		NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		this.setSmallIcon(getBattIcon(getBatteryCapacity(extras)));
		this.setTicker("Battery logging started");
		this.setWhen(System.currentTimeMillis());
		this.setWhen(1);
//		notification = new Notification(getBattIcon(getBatteryCapacity(extras)), "Battery logging started", System.currentTimeMillis());

		RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
//						contentView.setTextViewText(R.id.active_value, myModel.getTotalAverageRemainingUsageTime());
//				contentView.setTextViewText(R.id.active_value, myModel.getScreenOnAverage());
//				contentView.setTextViewText(R.id.total_value, myModel.getTotalAverage());
//				contentView.setTextViewText(R.id.standby_value, myModel.getScreenOffAverage());

		//		notification.contentView = contentView;
		this.setContent(contentView);

//		Intent notificationIntent = new Intent(context, MainActivity.class);
		Intent notificationIntent = new Intent(context, DiagramActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		this.setContentIntent(contentIntent);
//		notification.contentIntent = contentIntent;

		this.setAutoCancel(false);
//		notification.flags |= Notification.FLAG_NO_CLEAR;
		this.setOngoing(true);
//		notification.flags |= Notification.FLAG_ONGOING_EVENT;
//		mNotificationManager.notify(NOTIFICATION_VIEW_ID , notification);
		mNotificationManager.notify(NOTIFICATION_VIEW_ID , this.getNotification());
	}

	//	public Notification getNotification() {
	//		return notification;
	//	}

	public int getId() {
		return NOTIFICATION_VIEW_ID;
	}

	private int getBatteryCapacity(Bundle bundle) {
		return (bundle.getInt(BatteryManager.EXTRA_LEVEL, -1) * 100) / bundle.getInt(BatteryManager.EXTRA_SCALE, -1);
	}

	//	private float getBatteryVoltage(Bundle bundle) {
	//		return (float) bundle.getInt(BatteryManager.EXTRA_VOLTAGE, -1) / 1000;
	//	}
	//
	//	private float getBatteryTemperature(Bundle bundle) {
	//		return (float) bundle.getInt(BatteryManager.EXTRA_TEMPERATURE, -1) / 10;
	//	}
	//
	//	private int getBatteryStatus(Bundle bundle) {
	//		return bundle.getInt(BatteryManager.EXTRA_STATUS, -1);
	//	}
	//
	//	private int getBatteryHealth(Bundle bundle) {
	//		return bundle.getInt(BatteryManager.EXTRA_HEALTH, -1);
	//	}
	//
	//	private int getBatteryPlugged(Bundle bundle) {
	//		return bundle.getInt(BatteryManager.EXTRA_PLUGGED, -1);
	//	}

	private int getBattIcon(int level) {
		int temp = -1;
		switch (level) {
		case 0: temp = R.drawable.stat_sys_battery_0; break;
		case 1: temp = R.drawable.stat_sys_battery_1; break;
		case 2: temp = R.drawable.stat_sys_battery_2; break;
		case 3: temp = R.drawable.stat_sys_battery_3; break;
		case 4: temp = R.drawable.stat_sys_battery_4; break;
		case 5: temp = R.drawable.stat_sys_battery_5; break;
		case 6: temp = R.drawable.stat_sys_battery_6; break;
		case 7: temp = R.drawable.stat_sys_battery_7; break;
		case 8: temp = R.drawable.stat_sys_battery_8; break;
		case 9: temp = R.drawable.stat_sys_battery_9; break;
		case 10: temp = R.drawable.stat_sys_battery_10; break;
		case 11: temp = R.drawable.stat_sys_battery_11; break;
		case 12: temp = R.drawable.stat_sys_battery_12; break;
		case 13: temp = R.drawable.stat_sys_battery_13; break;
		case 14: temp = R.drawable.stat_sys_battery_14; break;
		case 15: temp = R.drawable.stat_sys_battery_15; break;
		case 16: temp = R.drawable.stat_sys_battery_16; break;
		case 17: temp = R.drawable.stat_sys_battery_17; break;
		case 18: temp = R.drawable.stat_sys_battery_18; break;
		case 19: temp = R.drawable.stat_sys_battery_19; break;
		case 20: temp = R.drawable.stat_sys_battery_20; break;
		case 21: temp = R.drawable.stat_sys_battery_21; break;
		case 22: temp = R.drawable.stat_sys_battery_22; break;
		case 23: temp = R.drawable.stat_sys_battery_23; break;
		case 24: temp = R.drawable.stat_sys_battery_24; break;
		case 25: temp = R.drawable.stat_sys_battery_25; break;
		case 26: temp = R.drawable.stat_sys_battery_26; break;
		case 27: temp = R.drawable.stat_sys_battery_27; break;
		case 28: temp = R.drawable.stat_sys_battery_28; break;
		case 29: temp = R.drawable.stat_sys_battery_29; break;
		case 30: temp = R.drawable.stat_sys_battery_30; break;
		case 31: temp = R.drawable.stat_sys_battery_31; break;
		case 32: temp = R.drawable.stat_sys_battery_32; break;
		case 33: temp = R.drawable.stat_sys_battery_33; break;
		case 34: temp = R.drawable.stat_sys_battery_34; break;
		case 35: temp = R.drawable.stat_sys_battery_35; break;
		case 36: temp = R.drawable.stat_sys_battery_36; break;
		case 37: temp = R.drawable.stat_sys_battery_37; break;
		case 38: temp = R.drawable.stat_sys_battery_38; break;
		case 39: temp = R.drawable.stat_sys_battery_39; break;
		case 40: temp = R.drawable.stat_sys_battery_40; break;
		case 41: temp = R.drawable.stat_sys_battery_41; break;
		case 42: temp = R.drawable.stat_sys_battery_42; break;
		case 43: temp = R.drawable.stat_sys_battery_43; break;
		case 44: temp = R.drawable.stat_sys_battery_44; break;
		case 45: temp = R.drawable.stat_sys_battery_45; break;
		case 46: temp = R.drawable.stat_sys_battery_46; break;
		case 47: temp = R.drawable.stat_sys_battery_47; break;
		case 48: temp = R.drawable.stat_sys_battery_48; break;
		case 49: temp = R.drawable.stat_sys_battery_49; break;
		case 50: temp = R.drawable.stat_sys_battery_50; break;
		case 51: temp = R.drawable.stat_sys_battery_51; break;
		case 52: temp = R.drawable.stat_sys_battery_52; break;
		case 53: temp = R.drawable.stat_sys_battery_53; break;
		case 54: temp = R.drawable.stat_sys_battery_54; break;
		case 55: temp = R.drawable.stat_sys_battery_55; break;
		case 56: temp = R.drawable.stat_sys_battery_56; break;
		case 57: temp = R.drawable.stat_sys_battery_57; break;
		case 58: temp = R.drawable.stat_sys_battery_58; break;
		case 59: temp = R.drawable.stat_sys_battery_59; break;
		case 60: temp = R.drawable.stat_sys_battery_60; break;
		case 61: temp = R.drawable.stat_sys_battery_61; break;
		case 62: temp = R.drawable.stat_sys_battery_62; break;
		case 63: temp = R.drawable.stat_sys_battery_63; break;
		case 64: temp = R.drawable.stat_sys_battery_64; break;
		case 65: temp = R.drawable.stat_sys_battery_65; break;
		case 66: temp = R.drawable.stat_sys_battery_66; break;
		case 67: temp = R.drawable.stat_sys_battery_67; break;
		case 68: temp = R.drawable.stat_sys_battery_68; break;
		case 69: temp = R.drawable.stat_sys_battery_69; break;
		case 70: temp = R.drawable.stat_sys_battery_70; break;
		case 71: temp = R.drawable.stat_sys_battery_71; break;
		case 72: temp = R.drawable.stat_sys_battery_72; break;
		case 73: temp = R.drawable.stat_sys_battery_73; break;
		case 74: temp = R.drawable.stat_sys_battery_74; break;
		case 75: temp = R.drawable.stat_sys_battery_75; break;
		case 76: temp = R.drawable.stat_sys_battery_76; break;
		case 77: temp = R.drawable.stat_sys_battery_77; break;
		case 78: temp = R.drawable.stat_sys_battery_78; break;
		case 79: temp = R.drawable.stat_sys_battery_79; break;
		case 80: temp = R.drawable.stat_sys_battery_80; break;
		case 81: temp = R.drawable.stat_sys_battery_81; break;
		case 82: temp = R.drawable.stat_sys_battery_82; break;
		case 83: temp = R.drawable.stat_sys_battery_83; break;
		case 84: temp = R.drawable.stat_sys_battery_84; break;
		case 85: temp = R.drawable.stat_sys_battery_85; break;
		case 86: temp = R.drawable.stat_sys_battery_86; break;
		case 87: temp = R.drawable.stat_sys_battery_87; break;
		case 88: temp = R.drawable.stat_sys_battery_88; break;
		case 89: temp = R.drawable.stat_sys_battery_89; break;
		case 90: temp = R.drawable.stat_sys_battery_90; break;
		case 91: temp = R.drawable.stat_sys_battery_91; break;
		case 92: temp = R.drawable.stat_sys_battery_92; break;
		case 93: temp = R.drawable.stat_sys_battery_93; break;
		case 94: temp = R.drawable.stat_sys_battery_94; break;
		case 95: temp = R.drawable.stat_sys_battery_95; break;
		case 96: temp = R.drawable.stat_sys_battery_96; break;
		case 97: temp = R.drawable.stat_sys_battery_97; break;
		case 98: temp = R.drawable.stat_sys_battery_98; break;
		case 99: temp = R.drawable.stat_sys_battery_99; break;
		case 100: temp = R.drawable.stat_sys_battery_100; break;
		}
		return temp;
	}

}

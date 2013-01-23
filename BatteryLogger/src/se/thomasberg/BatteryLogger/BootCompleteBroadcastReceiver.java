package se.thomasberg.BatteryLogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
//		Log.v(MyApplication.TAG, "BootCompleteReceiver -> Broadcast received: ACTION_BOOT_COMPLETED");
//		Log.v(MyApplication.TAG, "BootCompleteReceiver -> Intent action: " + intent.getAction());
		Intent serviceIntent = new Intent(context, BatteryService.class);
		serviceIntent.putExtra(MyApplication.BROADCAST_EVENT, intent.getAction());
		context.startService(serviceIntent);			
	}

}

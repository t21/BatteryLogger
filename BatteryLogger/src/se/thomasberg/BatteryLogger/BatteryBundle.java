package se.thomasberg.BatteryLogger;

import android.os.BatteryManager;
import android.os.Bundle;

public class BatteryBundle {

	private Bundle bundle;

	BatteryBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public int getBatteryCapacity() {
		//		if (bundle == null) {
		//			return -1;
		//		} else {
		return (bundle.getInt(BatteryManager.EXTRA_LEVEL, -1) * 100) / bundle.getInt(BatteryManager.EXTRA_SCALE, -1);
		//		}
	}

	public Bundle getBundle() {
		return bundle;
	}

	public boolean isValid() {
		if (bundle == null) {
			return false;
		} else if (getBatteryCapacity() >= 0 && getBatteryCapacity() <= 100) {
			return true;
		} else {
			return false;
		}
	}

	public float getBatteryVoltage() {
		return (float) bundle.getInt(BatteryManager.EXTRA_VOLTAGE, -1) / 1000;
	}

	public float getBatteryTemperature() {
		return (float) bundle.getInt(BatteryManager.EXTRA_TEMPERATURE, -1) / 10;
	}

	public int getBatteryStatus() {
		return bundle.getInt(BatteryManager.EXTRA_STATUS, -1);
	}

	public int getBatteryHealth() {
		return bundle.getInt(BatteryManager.EXTRA_HEALTH, -1);
	}

	public int getBatteryPlugged() {
		return bundle.getInt(BatteryManager.EXTRA_PLUGGED, -1);
	}

}

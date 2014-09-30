package org.arachnidium.model.mobile.android;

import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.HasAppStrings;
import io.appium.java_client.android.HasNetworkConnection;
import io.appium.java_client.android.StartsActivity;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.model.mobile.MobileApplication;

/**
 * It is the same as {@link MobileApplication} with some capabilities of
 * Android. It works only with {@link AndroidDriver}
 * 
 * @see MobileApplication
 */
public abstract class AndroidApp extends MobileApplication implements
		AndroidDeviceActionShortcuts, HasAppStrings, HasNetworkConnection,
		StartsActivity, IHasActivity {

	protected AndroidApp(MobileScreen context) {
		super(context);
	}

	@Override
	public void sendKeyEvent(int key, Integer metastate) {
		((AndroidDriver) getWrappedDriver()).sendKeyEvent(key, metastate);
	}

	@Override
	public String getAppStrings() {
		return ((AndroidDriver) getWrappedDriver()).getAppStrings();
	}

	@Override
	public String getAppStrings(String language) {
		return ((AndroidDriver) getWrappedDriver()).getAppStrings(language);
	}

	@Override
	public NetworkConnectionSetting getNetworkConnection() {
		return ((AndroidDriver) getWrappedDriver()).getNetworkConnection();
	}

	@Override
	public void setNetworkConnection(NetworkConnectionSetting connection) {
		((AndroidDriver) getWrappedDriver()).setNetworkConnection(connection);
	}

	@Override
	public void startActivity(String appPackage, String appActivity,
			String appWaitPackage, String appWaitActivity)
			throws IllegalArgumentException {
		((AndroidDriver) getWrappedDriver()).startActivity(appPackage,
				appActivity, appWaitPackage, appWaitActivity);
	}

	@Override
	public void startActivity(String appPackage, String appActivity)
			throws IllegalArgumentException {
		((AndroidDriver) getWrappedDriver()).startActivity(appPackage,
				appActivity);
	}

	/**
	 * Shows currently focused activity
	 */
	@Override
	public String currentActivity() {
		return ((AndroidDriver) getWrappedDriver()).currentActivity();
	}

}

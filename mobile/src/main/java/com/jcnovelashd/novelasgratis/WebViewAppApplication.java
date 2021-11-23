package com.jcnovelashd.novelasgratis;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jcnovelashd.novelasgratis.ads.AdMobUtility;
import com.jcnovelashd.novelasgratis.fcm.OneSignalNotificationOpenedHandler;
import com.jcnovelashd.novelasgratis.utility.Preferences;
import com.onesignal.OneSignal;
import com.robotemplates.kozuza.BaseApplication;
import com.robotemplates.kozuza.Kozuza;

import org.alfonz.utility.Logcat;

public class WebViewAppApplication extends BaseApplication {
	@Override
	public void onCreate() {
		super.onCreate();

		// init logcat
		Logcat.init(WebViewAppConfig.LOGS, "JCPlayTv");

		// init analytics
		FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(!WebViewAppConfig.DEV_ENVIRONMENT);

		// init AdMob
		MobileAds.initialize(this);
		MobileAds.setRequestConfiguration(AdMobUtility.createRequestConfiguration());

		// init OneSignal
		initOneSignal(getString(R.string.onesignal_app_id));
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public String getPurchaseCode() {
		return WebViewAppConfig.PURCHASE_CODE;
	}

	@Override
	public String getProduct() {
		return Kozuza.PRODUCT_WEBVIEWAPP;
	}

	private void initOneSignal(String oneSignalAppId) {
		if (!oneSignalAppId.equals("")) {
			OneSignal.initWithContext(this);
			OneSignal.setAppId(oneSignalAppId);
			OneSignal.setNotificationOpenedHandler(new OneSignalNotificationOpenedHandler());
			OneSignal.addSubscriptionObserver(stateChanges -> {
				if (stateChanges.getTo().isSubscribed()) {
					String userId = stateChanges.getTo().getUserId();
					saveOneSignalUserId(userId);
				}
			});
			saveOneSignalUserId(OneSignal.getDeviceState().getUserId());
		}
	}

	private void saveOneSignalUserId(String userId) {
		if (userId != null) {
			Logcat.d("userId = " + userId);
			Preferences preferences = new Preferences();
			preferences.setOneSignalUserId(userId);
		}
	}
}

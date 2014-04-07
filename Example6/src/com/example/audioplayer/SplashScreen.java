package com.example.audioplayer;

import com.example.example6.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

	private static final int SPLASH_SCREEN_DELAY = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		if (savedInstanceState == null) {
			startHomeScreenActivity();
		}
	}

	private void startHomeScreenActivity() {

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				SplashScreen.this.finish();
				Intent intent = new Intent(SplashScreen.this,
						AudioPlayerActivity.class);
				startActivity(intent);

			}
		}, SPLASH_SCREEN_DELAY);
	}

}

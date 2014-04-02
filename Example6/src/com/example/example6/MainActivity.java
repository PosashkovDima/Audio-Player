package com.example.example6;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private static boolean isPlay = false;
	private MediaPlayer mPlayer;
	private int currPosition;
	private float currVolume = 1.0f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		} else {
			setTrackStatus();
		}
	}

	/**
	 * Start playing the track.
	 */
	private void playTrack() {

		mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
		mPlayer.start();
		if (currPosition != 0) {
			mPlayer.seekTo(currPosition);
		}
		isPlay = true;
	}

	/**
	 * Track put on pause.
	 */
	private void pauseTrack() {
		if (mPlayer != null) {
			mPlayer.pause();
			currPosition = mPlayer.getCurrentPosition();
			isPlay = false;
		}
	}

	/**
	 * Set status to action button and status label.
	 */
	private void setTrackStatus() {
		Button actionbutton = (Button) findViewById(R.id.actionButton);
		TextView trackStatus = (TextView) findViewById(R.id.statusLabel);
		if (!isPlay) {
			trackStatus.setText("Status: Playing");
			actionbutton.setText("Pause");
		} else {
			trackStatus.setText("Status: Paused");
			actionbutton.setText("Play");
		}
	}

	/**
	 * Each call to lowers the volume to 10%
	 * 
	 * @param v
	 */
	public void volumeLower(View v) {
		if (currVolume != 0.0f) {
			currVolume -= 0.1f;
		}
		mPlayer.setVolume(--currVolume, currVolume);

	}

	/**
	 * Each call to higher the volume to 10%
	 * 
	 * @param v
	 */
	public void volumeHigher(View v) {
		if (currVolume != 1.0f) {
			currVolume += 0.1f;
		}
		mPlayer.setVolume(currVolume, currVolume);
	}

	/**
	 * Called if click action button. Calls setTrackStatus() and play or pause
	 * track.
	 * 
	 * @param v
	 */
	public void actionButtonOnClick(View v) {
		setTrackStatus();
		if (!isPlay) {
			playTrack();
		} else {
			pauseTrack();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}

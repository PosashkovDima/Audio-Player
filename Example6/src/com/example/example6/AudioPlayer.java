package com.example.example6;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioPlayer extends Activity {
 
	private MediaPlayer mPlayer;
	private int currPosition;
	private float currVolume = 1.0f;
	private static AudioPlayer sharedVolumeControl = null;

	public static synchronized AudioPlayer sharedAudioPlayer() {
		if (sharedVolumeControl == null) {
			sharedVolumeControl = new AudioPlayer();
		}

		return sharedVolumeControl;
	}

	public boolean isPlaying() {

		return mPlayer.isPlaying();
	}

	public void playTrack() {
		mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
		mPlayer.start();
		if (currPosition != 0) {
			mPlayer.seekTo(currPosition);
		}
	}

	public void pauseTrack() {
		if (mPlayer != null && mPlayer.isPlaying()) {
			mPlayer.pause();
			currPosition = mPlayer.getCurrentPosition();
		}
	}

	/**
	 * Set status to action button and status label.
	 */
	public void setTrackStatus() {
		Button actionbutton = (Button) findViewById(R.id.actionButton);
		TextView trackStatus = (TextView) findViewById(R.id.statusLabel);
		if (mPlayer.isPlaying()) {
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
		mPlayer.setVolume(currVolume, currVolume);

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

}

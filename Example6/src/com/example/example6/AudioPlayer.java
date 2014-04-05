package com.example.example6;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;

public class AudioPlayer extends Activity {

	private MediaPlayer mPlayer;
	private int currentPosition;
	private float currVolume = 1.0f;

	public AudioPlayer(MediaPlayer mPlayer) {
		this.mPlayer = mPlayer;
	}

	public void setTimePassed(int timePassed) {
		this.currentPosition = timePassed;
	}

	public int getTimePassed() {
		return mPlayer.getCurrentPosition();
	}

	public void playTrack() {
		mPlayer.seekTo(currentPosition);
		mPlayer.start();
	}

	public void pauseTrack() {
		if (mPlayer != null && mPlayer.isPlaying()) {
			mPlayer.pause();
			currentPosition = mPlayer.getCurrentPosition();
		}
	}

	public void stopTrack() {
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}

	public boolean isPlaying() {

		return mPlayer.isPlaying();
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

	// adjustVolume !!
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

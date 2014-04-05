package com.example.example6;

import android.app.Activity;
import android.media.MediaPlayer;

public class AudioPlayer extends Activity {

	private MediaPlayer mPlayer;
	private int currentPosition;

	public AudioPlayer(MediaPlayer mPlayer) {
		this.mPlayer = mPlayer;
	}

	/**
	 * Save current position of track.
	 * 
	 * @param timePassed
	 */
	public void setCurrentPosition(int timePassed) {
		this.currentPosition = timePassed;
	}

	/**
	 * Get current position of track.
	 * 
	 * @return current position type of int
	 */
	public int getCurrentPosition() {
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

}

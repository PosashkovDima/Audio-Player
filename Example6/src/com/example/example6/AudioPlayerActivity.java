package com.example.example6;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity {
	private AudioPlayer player;
	private AudioManager audioManager;
	private Button actionButton;
	private TextView trackStatus;
	private boolean wasPaused = false;
	private static final String CURRENT_POSITION_KEY = "currentPositionKey";
	private static final String WAS_PLAYING_KEY = "wasPlayingKey";
	private static final String WAS_PAUSED_KEY = "wasPausedKey";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		actionButton = (Button) findViewById(R.id.actionButton);
		trackStatus = (TextView) findViewById(R.id.statusLabel);

		player = new AudioPlayer(MediaPlayer.create(getApplicationContext(),
				R.raw.song));

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		savedInstanceStateAnalyzes(savedInstanceState);
	}

	/**
	 * Analyzes the restored state and change status to playing\paused\idle.
	 * 
	 * @param savedInstanceState
	 *            type of Bundle
	 */
	private void savedInstanceStateAnalyzes(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			player.setCurrentPosition(savedInstanceState
					.getInt(CURRENT_POSITION_KEY));
			if (savedInstanceState.getBoolean(WAS_PLAYING_KEY)) {
				player.playTrack();
				setStatusPlaying();
			} else if (savedInstanceState.getBoolean(WAS_PAUSED_KEY)) {
				setStatusPaused();
			} else {
				setStatudIdle();
			}
		}
	}

	/**
	 * Called if click on actionButton. Calls setTrackStatus() and play or pause
	 * track.
	 * 
	 * @param v
	 */
	public void actionButtonOnClick(View v) {
		if (!player.isPlaying()) {
			player.playTrack();
			setStatusPlaying();
			wasPaused = true;
		} else {
			player.pauseTrack();
			setStatusPaused();
		}
	}

	/**
	 * Increase button if clicked on increaseButton
	 */
	public void increaseButtonOnClick(View v) {
		audioManager.adjustVolume(AudioManager.ADJUST_RAISE,
				AudioManager.FLAG_SHOW_UI);
	}

	/**
	 * Decrease button if clicked on decreaseButton
	 */
	public void decreaseButtonOnClick(View v) {
		audioManager.adjustVolume(AudioManager.ADJUST_LOWER,
				AudioManager.FLAG_SHOW_UI);
	}

	/**
	 * Set status "Paused" to status label action and "Play" to button.
	 */
	public void setStatusPaused() {
		trackStatus.setText("Status: Paused");
		actionButton.setText("Play");
	}

	/**
	 * Set status "Playing" to status label action and "Pause" to button.
	 */
	private void setStatusPlaying() {
		trackStatus.setText("Status: Playing");
		actionButton.setText("Pause");
	}

	/**
	 * Set status "Idle" to status label action and "Play" to button.
	 */
	private void setStatudIdle() {
		trackStatus.setText("Status: Idle");
		actionButton.setText("Play");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(CURRENT_POSITION_KEY, player.getCurrentPosition());
		savedInstanceState.putBoolean(WAS_PLAYING_KEY, player.isPlaying());
		savedInstanceState.putBoolean(WAS_PAUSED_KEY, wasPaused);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		player.stopTrack();
		super.onDestroy();
	}

}

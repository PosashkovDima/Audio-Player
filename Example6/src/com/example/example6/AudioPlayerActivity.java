package com.example.example6;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity {
	private AudioPlayer player;
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
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		player = new AudioPlayer(MediaPlayer.create(getApplicationContext(),
				R.raw.song));
		
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
			player.setTimePassed(savedInstanceState
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

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(CURRENT_POSITION_KEY, player.getTimePassed());
		savedInstanceState.putBoolean(WAS_PLAYING_KEY, player.isPlaying());
		savedInstanceState.putBoolean(WAS_PAUSED_KEY, wasPaused);
		super.onSaveInstanceState(savedInstanceState);
	}

	// onAudioFocusedChangedListener
	@Override
	protected void onDestroy() {
		player.stopTrack();
		super.onDestroy();
	}

	/**
	 * Called if click action button. Calls setTrackStatus() and play or pause.
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
	 * Set status "Paused" to status label action and "Play" to button.
	 */
	public void setStatusPaused() {
		actionButton = (Button) findViewById(R.id.actionButton);
		trackStatus = (TextView) findViewById(R.id.statusLabel);
		trackStatus.setText("Status: Paused");
		actionButton.setText("Play");
	}

	/**
	 * Set status "Playing" to status label action and "Pause" to button.
	 */
	private void setStatusPlaying() {
		actionButton = (Button) findViewById(R.id.actionButton);
		trackStatus = (TextView) findViewById(R.id.statusLabel);
		trackStatus.setText("Status: Playing");
		actionButton.setText("Pause");
	}

	/**
	 * Set status "Idle" to status label action and "Play" to button.
	 */
	private void setStatudIdle() {
		actionButton = (Button) findViewById(R.id.actionButton);
		trackStatus = (TextView) findViewById(R.id.statusLabel);
		trackStatus.setText("Status: Idle");
		actionButton.setText("Play");
	}
}

package com.example.audioplayer;

import com.example.example6.R;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity {
	private AudioPlayer audioPlayer;
	private AudioManager audioManager;
	private Button actionButton;
	private MediaPlayer mediaPlayer;
	private TextView trackStatus;
	private boolean wasPaused = false;
	private static final String CURRENT_POSITION_KEY = "currentPositionKey";
	private static final String WAS_PLAYING_KEY = "wasPlayingKey";
	private static final String WAS_PAUSED_KEY = "wasPausedKey";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio_player_main);
		actionButton = (Button) findViewById(R.id.actionButton);
		trackStatus = (TextView) findViewById(R.id.statusLabel);
		mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
		audioPlayer = new AudioPlayer(mediaPlayer);

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		restoreInstanceState(savedInstanceState);

		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer arg0) {
				setStatusIdle();
				audioPlayer.setCurrentPosition(0);
			}
		});
	}

	/**
	 * Analyzes the restored state and change status to playing\paused\idle.
	 * 
	 * @param savedInstanceState
	 *            type of Bundle
	 */
	private void restoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			audioPlayer.setCurrentPosition(savedInstanceState
					.getInt(CURRENT_POSITION_KEY));
			if (savedInstanceState.getBoolean(WAS_PLAYING_KEY)) {
				audioPlayer.playTrack();
				setStatusPlaying();
			} else if (savedInstanceState.getBoolean(WAS_PAUSED_KEY)) {
				setStatusPaused();
			} else {
				setStatusIdle();
			}
		}
	}

	public void actionButtonOnClick(View v) {
		if (!audioPlayer.isPlaying()) {
			audioPlayer.playTrack();
			setStatusPlaying();
		} else {
			audioPlayer.pauseTrack();
			setStatusPaused();
			wasPaused = true;
		}
	}

	/**
	 * Increase volume if clicked on increaseButton
	 */
	public void increaseButtonOnClick(View v) {
		audioManager.adjustVolume(AudioManager.ADJUST_RAISE,
				AudioManager.FLAG_SHOW_UI);
	}

	/**
	 * Decrease volume if clicked on decreaseButton
	 */
	public void decreaseButtonOnClick(View v) {
		audioManager.adjustVolume(AudioManager.ADJUST_LOWER,
				AudioManager.FLAG_SHOW_UI);
	}

	public void setStatusPaused() {
		trackStatus.setText("Status: Paused");
		actionButton.setText("Play");
	}

	private void setStatusPlaying() {
		trackStatus.setText("Status: Playing");
		actionButton.setText("Pause");
	}

	private void setStatusIdle() {
		trackStatus.setText("Status: Idle");
		actionButton.setText("Play");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(CURRENT_POSITION_KEY,
				audioPlayer.getCurrentPosition());
		savedInstanceState.putBoolean(WAS_PLAYING_KEY, audioPlayer.isPlaying());
		savedInstanceState.putBoolean(WAS_PAUSED_KEY, wasPaused);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		audioPlayer.stopTrack();
		super.onDestroy();
	}

}

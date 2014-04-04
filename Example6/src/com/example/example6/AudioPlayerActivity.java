package com.example.example6;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class AudioPlayerActivity extends ActionBarActivity {
	private AudioPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		if (savedInstanceState == null) {
			player = AudioPlayer.sharedAudioPlayer();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		} else {
		}
	}

	/**
	 * Called if click action button. Calls setTrackStatus() and play or pause
	 * track.
	 * 
	 * @param v
	 */
	public void actionButtonOnClick(View v) {

		if (!player.isPlaying()) {
			player.playTrack();
		} else {
			player.pauseTrack();
		}
		player.setTrackStatus();
	}

}

package com.asyn.lumos;

import com.asyn.player.Media;
import com.asyn.player.PlayMedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
	
	public PlayMedia mpSwear;
	
	private static final int DURATION = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		mpSwear = new PlayMedia(this, Media.SWEAR);
		mpSwear.startPlayableMedia();
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(Splash.this, Main.class);
				startActivity(intent);
				Splash.this.finish();
			}
		}, DURATION);
		
	}

}

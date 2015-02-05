package com.asyn.lumos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.asyn.player.Media;
import com.asyn.player.PlayMedia;
import com.asyn.tools.AdsLoader;
import com.asyn.tools.FlashActivator;
import com.asyn.tools.ShakeListener;
import com.asyn.tools.ShakeListener.OnShakeListener;
import com.google.android.gms.ads.AdView;

public class Main extends Activity {
	
	private ShakeListener shaker;	private boolean torchIsOn;
	private PlayMedia introLoop;	private PlayMedia lumosNox;
	
	private FlashActivator torch;	private SurfaceView view;
	
	private Vibrator vibrator;
	
	private RelativeLayout layout;	
	private AdView adView;			private AdsLoader adLoader;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        view = (SurfaceView) findViewById(R.id.surfaceView);
        adView = (AdView) findViewById(R.id.adView);
		layout = (RelativeLayout) findViewById(R.id.rel); // setting view
		/* We need this to change it's image when the flash is ON/OFF */
		
		introLoop = new PlayMedia(this);
		introLoop.setPlayRepeatMedia(Media.BACKGROUND, true);
		
		lumosNox = new PlayMedia(this);
		lumosNox.setPlayableMedia(Media.LUMOS);
		
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        
        torchIsOn = false;
        
                
        
		torch = new FlashActivator(this, view);
	    
	    /* ************** ADVERTISING CODE **************** */
		//new AdsLoader(adView);
		/* ************************************************ */
        
        shaker = new ShakeListener(this);
        shaker.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake() {
				if(torchIsOn) {
					lumosNox.setAndPlayMedia(Media.NOXX);
					torch.off();
					vibrate(50);
					torchIsOn = false;
					layout.setBackgroundResource(Media.FLASH_OFF); // off
				}
				else {
					lumosNox.setAndPlayMedia(Media.LUMOS);
					torch.on();
					vibrate(100);
					torchIsOn = true;
					layout.setBackgroundResource(Media.FLASH_ON);
				}
			}

			@SuppressLint("NewApi")
			private void vibrate(int duration) {
				try {
					if (vibrator.hasVibrator()) {
						vibrator.vibrate(duration);
					}
				} catch (Exception e) {
					
				} // end try/catch
			}
		});
        
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	shaker.pause();
    	introLoop.stopPlayableMedia();
    	introLoop.setAndPlayMedia(Media.MICHIEF);
    	vibrator.cancel();
    	torch.destroy();
    } // end method onDestroy 
    
    @Override
    protected void onPause() {
    	super.onPause();
    	torch.destroy();
    	introLoop.pausePlayableMedia();
    } // end method onPause
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if(adLoader == null)
    		adLoader = new AdsLoader(adView);
    	
    	torch.reInstateCamera();
    	if(torchIsOn){
    		layout.setBackgroundResource(Media.FLASH_OFF);
    		torchIsOn = false;
    	}
    	
    	introLoop.startPlayableMedia();
    } // end method onResume

    
}

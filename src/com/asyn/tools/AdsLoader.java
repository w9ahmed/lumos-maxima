package com.asyn.tools;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdsLoader {
		
	private AdView adView;
	private AdRequest adRequest;
	
	public AdsLoader(AdView view) {
		adView = view;
		adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	} // end constructor

} // end class AdLoader

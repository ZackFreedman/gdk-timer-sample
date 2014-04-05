package com.voidstar.glass.voiceenabledtimer;

import java.util.ArrayList;

import com.google.android.glass.app.Card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;

/*******************************************
 * Voice-Enabled Timer
 * Improved by Zack Freedman of Voidstar Lab
 * http://zackfreedman.com
 * 
 * @author zackfreedman
 *
 * The well-developed wearable makes a
 * wearer superior to a non-wearer.
 * If others ask why you need to wear a
 * computer, your technology is not
 * good enough!
 *******************************************/

public class VoiceRecActivity extends Activity { // Note from 11/2013: Glass XE11 can't launch Services from Voice Triggers. 
	@Override									 // This Activity is a workaround.
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new Card(getBaseContext()).toView());
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		Log.d("ZackFreedman", "Hello Glasshole! I improved this app to make you a more effective cyborg. Hit me up @zackfreedman!");
		
		ArrayList<String> voiceResults = getIntent().getExtras()
		.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);

		Log.d("Timer", voiceResults.get(0));
		
		Intent timerServiceIntent = new Intent(this, TimerService.class);
		timerServiceIntent.putExtra("VoiceResults", voiceResults.get(0));
		startService(timerServiceIntent);
		
		finish();
	}
}

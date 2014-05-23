package com.voidstar.glass.voiceenabledtimer;

import java.io.Serializable;
import java.util.Calendar;

import android.util.Log;

public class TimerTombstone implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// !!
	// These fields are highly relevant.
	// Changing their names or types will cause unrecoverable exceptions.
	private long mLastTimerEndMillis;
	private int mLastTimerFlags;
	
	public TimerTombstone(long durationMillis, int flags) {
		mLastTimerEndMillis = Calendar.getInstance().getTimeInMillis() + durationMillis;
		mLastTimerFlags = flags;
		
		Log.d("TimerTombstone", "Captured timer tombstone with endmillis " + String.valueOf(mLastTimerEndMillis) + 
				" and flags " + String.valueOf(flags));
	}
	
	public long lastTimerEndMillis() {
		return mLastTimerEndMillis;
	}
	
	public int lastTimerFlags() {
		return mLastTimerFlags;
	}
	
//	public boolean isStillActive() {
//		if (mLastTimerEndMillis == 0) {
//			Log.e("TimerTombstone", "Can't be active - mLastTimerEndMillis is null");
//			return false;
//		}
//		
//		long millis = Calendar.getInstance().getTimeInMillis();
//		
//		Log.d("TimerTombstone", "Comparing tombstoned endmillis " + String.valueOf(mLastTimerEndMillis) + 
//				" to current millis " + String.valueOf(millis));
//		
//		return mLastTimerEndMillis > millis;
//	}
}

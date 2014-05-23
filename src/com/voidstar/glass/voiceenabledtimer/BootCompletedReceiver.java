package com.voidstar.glass.voiceenabledtimer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
	private static long FLAG_LOUDLY = 2;
	
	@Override
	public void onReceive(Context context, Intent intent) {

		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			Log.d("Timer BootReceiver", "Received BOOT_COMPLETED broadcast, checking timer tombstone");
			
			FileInputStream fis;
			ObjectInputStream ois;
			TimerTombstone tombstone;
			
			try {
				fis = context.openFileInput("timertombstone");
			} catch (FileNotFoundException e) {
				Log.d("Timer BootReceiver", "No tombstone found. Not regenerating Service or Live Card.");
				return;
			}
			
			try {
				ois = new ObjectInputStream(fis);
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
				Log.d("Timer BootReceiver", "Tombstone corrupted. Not regenerating Service or Live Card.");
				close(fis);
				return;
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("Timer BootReceiver", "Tombstone IO error. Not regenerating Service or Live Card.");
				close(fis);
				return;
			}
			
			try {
				tombstone = (TimerTombstone)ois.readObject();
			} catch (OptionalDataException e) {
				e.printStackTrace();
				close(fis);
				close(ois);
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				close(fis);
				close(ois);
				return;
			} catch (IOException e) {
				e.printStackTrace();
				close(fis);
				close(ois);
				return;
			}
			
			close(fis);
			close(ois);
			
			if (tombstone == null) {
				Log.d("Timer BootReceiver", "Null tombstone loaded. Not regenerating Service or Live Card.");
				return;
			}
			
			Log.d("Timer BootReceiver", "Regenerating app with endmillis " + String.valueOf(tombstone.lastTimerEndMillis() +
					" and flags " + String.valueOf(tombstone.lastTimerFlags())));
			
            Intent serviceIntent = new Intent(context, TimerService.class);
            serviceIntent.putExtra("regenerate", true);
            serviceIntent.putExtra("endmillis", tombstone.lastTimerEndMillis());
            serviceIntent.putExtra("loudly", tombstone.lastTimerFlags() & FLAG_LOUDLY);
            context.startService(serviceIntent);    
		}
	}
	
	void close(FileInputStream fis) {
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void close(ObjectInputStream ois) {
		try {
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

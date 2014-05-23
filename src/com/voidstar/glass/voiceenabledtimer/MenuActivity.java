/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.voidstar.glass.voiceenabledtimer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Activity showing the options menu.
 */
public class MenuActivity extends Activity {

    /** Request code for setting the timer. */
    private static final int SET_TIMER = 100;

    private final Handler mVamanosMuchachoHandler = new Handler();
    private boolean mAttachedToWindow;
    private boolean mOptionsMenuOpen;
    
    private Timer mTimer;
    private boolean mResumed;
    private boolean mSettingTimer;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof TimerService.TimerBinder) {
                mTimer = ((TimerService.TimerBinder) service).getTimer();
                openOptionsMenu();
            }
            // No need to keep the service bound.
            unbindService(this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // Nothing to do here.
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService(new Intent(this, TimerService.class), mConnection, 0);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAttachedToWindow = true;
        openOptionsMenu();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAttachedToWindow = false;
    }

    @Override
    public void openOptionsMenu() {
        if (!mOptionsMenuOpen && mAttachedToWindow && mTimer != null) {
        	mOptionsMenuOpen = true;
            super.openOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timer, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final boolean timeSet = mTimer.getDurationMillis() != 0;

        menu.setGroupVisible(R.id.no_time_set, !timeSet);
        menu.setGroupVisible(R.id.time_set, timeSet);
        if (timeSet) {
            menu.findItem(R.id.start).setVisible(!mTimer.isRunning() && !mTimer.isStarted());
            menu.findItem(R.id.resume).setVisible(!mTimer.isRunning() && mTimer.isStarted());
            menu.findItem(R.id.pause).setVisible(
                    mTimer.isRunning() && mTimer.getRemainingTimeMillis() > 0);
            menu.findItem(R.id.reset).setVisible(mTimer.isStarted());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.change_timer:
            case R.id.set_timer:
                mTimer.reset();
                setTimer();
                return true;
            case R.id.start:
            case R.id.resume:
                mTimer.start();
                try {
    				FileOutputStream fos = openFileOutput("timertombstone", Context.MODE_PRIVATE);
    				ObjectOutputStream oos = new ObjectOutputStream(fos);
    				TimerTombstone tombstone = new TimerTombstone(mTimer.getRemainingTimeMillis(), 2);
    				oos.writeObject(tombstone);
    				oos.close();
    				fos.close();
    			} catch (FileNotFoundException e) {
    				Log.d("Timer", "This error should never occur.");
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
                return true;
            case R.id.pause:
                mTimer.pause();
                deleteFile("timertombstone");
                return true;
            case R.id.reset:
                mTimer.reset();
                deleteFile("timertombstone");
                return true;
            case R.id.stop:
                deleteFile("timertombstone");

            	mVamanosMuchachoHandler.post(new Runnable() {
            		@Override
            		public void run() {
                        stopService(new Intent(MenuActivity.this, TimerService.class));
            		}
            	});
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
    	super.onOptionsMenuClosed(menu);
    	mOptionsMenuOpen = false;
        if (!mSettingTimer) {
            // Nothing else to do, closing the Activity.
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SET_TIMER) {
            mTimer.setDurationMillis(data.getLongExtra(SetTimerActivity.EXTRA_DURATION_MILLIS, 0));
        }
        finish();
    }

    private void setTimer() {
        Intent setTimerIntent = new Intent(this, SetTimerActivity.class);

        setTimerIntent.putExtra(SetTimerActivity.EXTRA_DURATION_MILLIS, mTimer.getDurationMillis());
        startActivityForResult(setTimerIntent, SET_TIMER);
        mSettingTimer = true;
    }
}

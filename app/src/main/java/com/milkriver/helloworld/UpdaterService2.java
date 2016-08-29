package com.milkriver.helloworld;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import winterwell.jtwitter.Status;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

public class UpdaterService2 extends Service {
    private boolean runFlag = false;
    private static final String TAG = "UpdaterService";
    static final int DELAY = 600;
    private Updater updater;
    private YambaApplication yamba;


    public UpdaterService2() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.updater = new Updater();
        this.yamba = (YambaApplication) getApplication();
        Log.d(TAG,"onCreated");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        this.runFlag = true;
        this.updater.start();
        this.yamba.setServiceRunning(true);
        Log.d(TAG,"onStarted");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.runFlag = false;
        this.updater.interrupt();
        this.updater = null;
        this.yamba.setServiceRunning(false);
        Log.d(TAG, "onDestroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    private class Updater extends Thread {
        List<Status> timeline;

        public Updater() {
            super("UpdaterService-Updater");
        }

        @Override
        public void run() {
            UpdaterService2 updaterService = UpdaterService2.this;
            while(updaterService.runFlag) {
                Log.d(TAG, "Updater running");
                try{
                    try{
                        timeline = yamba.getTwitter().getFriendsTimeline();
                    } catch(TwitterException e) {
                        Log.e(TAG, "Failed to connect to Twitter Service", e);
                    }

                    for(Status status : timeline) {
                        Log.d(TAG, String.format("%s: %s", status.user.name, status.text));
                    }
                    Log.d(TAG,"Updater ran");
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    updaterService.runFlag = false;
                }
            }
        }
    }
}

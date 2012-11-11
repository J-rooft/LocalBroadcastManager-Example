package com.nocturnaldev.localbroadcastmanager_example;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.nocturnaldev.localbroadcastmanager_example.R;

public class BroadcastSenderIntentService extends IntentService {
    
    public BroadcastSenderIntentService() {
        super("BroadcastSenderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = getString(R.string.lbl_running);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MainActivity.MESSAGE_SENT_ACTION);
        broadcastIntent.putExtra(MainActivity.MESSAGE_EXTRA, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        message = getString(R.string.lbl_finished);
        broadcastIntent = new Intent();
        broadcastIntent.setAction(MainActivity.MESSAGE_SENT_ACTION);
        broadcastIntent.putExtra(MainActivity.MESSAGE_EXTRA, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

}

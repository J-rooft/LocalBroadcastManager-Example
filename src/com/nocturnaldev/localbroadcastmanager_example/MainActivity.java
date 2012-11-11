package com.nocturnaldev.localbroadcastmanager_example;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.nocturnaldev.localbroadcastmanager_example.R;

public class MainActivity extends Activity {
    
    public static final String MESSAGE_SENT_ACTION = "com.nocturnaldev.MESSAGE_RECEIVED_ACTION";
    public static final String MESSAGE_EXTRA = "com.nocturnaldev.MESSAGE_EXTRA";
    
    private static final String MESSAGE_FROM_SERVICE_KEY = "com.nocturnaldev.MESSAGE_FROM_SERVICE_KEY";
    
    private BroadcastReceiver receiver;
    private TextView serviceMessageView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        serviceMessageView = (TextView) findViewById(R.id.service_message_textview);
        
        if (savedInstanceState != null) {
            serviceMessageView.setText(savedInstanceState.getString(MESSAGE_FROM_SERVICE_KEY));
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        receiver = new BroadcastReceiver() {
            
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                String message = bundle.getString(MESSAGE_EXTRA);
                
                serviceMessageView.setText(message);
            }
        };
        
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(MESSAGE_SENT_ACTION));
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        if (receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
            receiver = null;
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MESSAGE_FROM_SERVICE_KEY, serviceMessageView.getText().toString());
    }
    
    public void onStartServiceClicked(View view) {
        startService(new Intent(this, BroadcastSenderIntentService.class));
    }
    
}

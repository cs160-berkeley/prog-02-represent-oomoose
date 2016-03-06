package com.example.jenny.represent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by Jenny on 3/5/2016 for testing!
 */
public class PhoneListenerService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        if( messageEvent.getPath().equalsIgnoreCase("/PERSON") ) {
            String person = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, more.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("whichrep", person);
            startActivity(intent);
            Log.d("T", "about to start watch MainActivity with ZIPCODE");
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}

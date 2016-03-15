package com.example.jenny.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by Jenny on 3/5/2016.
 */
public class WatchListener extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        if( messageEvent.getPath().equalsIgnoreCase("/ZIPCODE") ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, candidates.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("type", 1 );
            intent.putExtra("ZIPCODE", value);
            startActivity(intent);
            Log.d("T", "about to start watch MainActivity with ZIPCODE");
        } else if (messageEvent.getPath().equalsIgnoreCase("/LOCATION")) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String[] latlong = value.split("\n");
            Intent intent = new Intent(this, candidates.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("type", 0);
            intent.putExtra("latitude", Double.parseDouble(latlong[0]));
            intent.putExtra("longitude", Double.parseDouble(latlong[1]));
            startActivity(intent);
            Log.d("T", "about to start watch MainActivity with POSITION");

        }else {
            super.onMessageReceived( messageEvent );
        }

    }
}

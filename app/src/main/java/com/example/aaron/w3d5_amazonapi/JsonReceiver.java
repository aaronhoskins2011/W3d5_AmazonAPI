package com.example.aaron.w3d5_amazonapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class JsonReceiver extends BroadcastReceiver {
    public static final String TAG = "JSON_RECEIVER";

    @Override
    public void onReceive(Context context, Intent recievedIntent) {
        String json = recievedIntent.getBundleExtra("amazon_json").getString("amazon_json_string");
        Log.d(TAG, "onReceive: >>>>> RECIEVED JSON = " + json);
        Intent sendIntent = new Intent(context, MainActivity.class);
        Bundle sendBundle = new Bundle();
        sendBundle.putString("sent_json", json);
        sendIntent.putExtra("sent_bundle", sendBundle);
        sendIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }
}

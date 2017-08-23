/***********************************************************
 * *****                                               *****
 * *****    AmzIntentService                           *****
 * *****                                               *****
 * *********************************************************
 * ********************************************************/

package com.example.aaron.w3d5_amazonapi;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AmzIntentService extends IntentService {
    public static final String TAG = "_AMAZON_INT_SERV";
    public static final String AMAZON_URL = "http://de-coding-test.s3.amazonaws.com/books.json";
    static String jsonReturn = "";
    public AmzIntentService() {
        super("name");
    }

    public AmzIntentService(String name) {
        super(name);
    }

    /*********************************
     *    onHandleEvent              *
     ********************************/
    @Override
    protected void onHandleIntent(Intent intent) {
        Intent broadcastIntent = new Intent(getApplicationContext(),JsonReceiver.class);
        Log.d(TAG, "startAmazonIntentService: >>>>>   Amazon Intent Service Started ");
        try {
            getJson();
            InternalStorage.writeString(getApplicationContext(),"JSONBRD", jsonReturn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString("amazon_json_string", "JSONBRD");
        broadcastIntent.putExtra("amazon_json", bundle);
        Log.d(TAG, "onHandleIntent: >>>>>     Sending Broadcast");
        sendBroadcast(broadcastIntent);
    }

    private void getJson() throws IOException {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(AMAZON_URL).build();

        jsonReturn = client.newCall(request).execute().body().string();

    }


}

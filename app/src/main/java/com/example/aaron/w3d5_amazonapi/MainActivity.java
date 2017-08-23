/***********************************************************
 * *****                                               *****
 * *****    MAIN ACTIVITY                              *****
 * *****                                               *****
 * *********************************************************
 * ********************************************************/
package com.example.aaron.w3d5_amazonapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.aaron.w3d5_amazonapi.model.AmazonProfile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "_MAIN_ACTIVITY";
    public static final String KEY = "JSON";
    public static final String EMPTY_STRING = "";
    public static final String NOT_RECIEVED = " NOT RECIEVED ";
    String json = EMPTY_STRING;
    List<AmazonProfile>  amazonList;
    RecyclerView rvAmazonList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    AmazonRVAdaptor amazonRVAdaptor;
    /*************************
     *   onCreate()          *
     ************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startJsonCache();
        amazonList =  getAmazonArrayList();
        initRV();
    }
    
    public void startAmazonIntentService(){
        Intent amazonIntent = new Intent(this,AmzIntentService.class);
        amazonIntent.setAction("rest_amazon_service");
        startService(amazonIntent);
    }

    public void handleJsonCache(){
        try {
            if(InternalStorage.fileExistance(json, this) && !(getIntent().getFlags() == FLAG_ACTIVITY_NEW_TASK)){
                deleteFile("JSONBRD");
                Log.d(TAG, "onCreate: >>>>>     Starting Amazon Intent Service     ");
                startAmazonIntentService();
            }
            if(!InternalStorage.fileExistance(json, this)){
                Log.d(TAG, "onCreate: >>>>>     Starting Amazon Intent Service     ");
                startAmazonIntentService();
            }
            if(getIntent().getFlags() == FLAG_ACTIVITY_NEW_TASK) {
                json = InternalStorage.readString(this, "JSONBRD");
            }
            if (json.equals(EMPTY_STRING)) {
                startAmazonIntentService();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: <<<<<     JSON Received on MAIN ACTIVITY == " + json);
    }

    public void startJsonCache(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run () {
                handleJsonCache();
            }
        };
        timer.schedule (task, 0l, 1000*30*60);   // 1000*10*60 every 10 minut
    }
    public List<AmazonProfile> getAmazonArrayList(){
        Type list = new TypeToken<List<AmazonProfile>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, list);
    }
    private void initRV(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                /*****************************************
                 *     Recycle View initialization       *
                 * **************************************/
                rvAmazonList = (RecyclerView)findViewById(R.id.rvAmazonLIst);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                itemAnimator = new DefaultItemAnimator();
                rvAmazonList.setLayoutManager(layoutManager);
                rvAmazonList.setItemAnimator(itemAnimator);
                //Adaptor instantiated
                amazonRVAdaptor = new AmazonRVAdaptor(amazonList,getApplicationContext());
                rvAmazonList.setAdapter(amazonRVAdaptor);
                amazonRVAdaptor.notifyDataSetChanged();
            }
        });
    }

}

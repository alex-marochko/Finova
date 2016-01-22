package com.example.sdn.finova;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by sdn on 1/19/16.
 */
public class FinovaTracksService extends IntentService{

    static final String LOG_TAG = "marinfo";

    static final String SERVICE_NAME = "FinovaTracksIntentService";

    ArrayList<TrackJSON> tracks;



    public FinovaTracksService() {
        super(SERVICE_NAME);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(LOG_TAG, "onHandleIntent(..) start");

        int requestId = intent.getIntExtra("requestId", 1);
        int perPage = intent.getIntExtra("perPage", 2);
        int tracksPerRequest = intent.getIntExtra("tracksPerRequest", 5);
        String accessToken = intent.getStringExtra("accessToken");
        String serverUri = intent.getStringExtra("serverUri");

        PendingIntent pendingIntent = intent.getParcelableExtra("pendingIntent");

        FinovaModel finovaModel = new FinovaModel(serverUri, accessToken, tracksPerRequest, perPage);

        tracks = finovaModel.getTracksOnRequest(requestId);

        Gson gson = new GsonBuilder()
                .serializeSpecialFloatingPointValues()
                .create();

        String json = gson.toJson(tracks);

        Intent tracksDataIntent = new Intent().putExtra("tracks", json)
                .putExtra("tracksCount", tracks.size());

        try {
            pendingIntent.send(FinovaTracksService.this, TracksActivity.REQUEST_PARAM_TRACKS, tracksDataIntent);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "onHandleIntent(..) end");


    }

    public void onDestroy() {
        super.onDestroy();
    }

}

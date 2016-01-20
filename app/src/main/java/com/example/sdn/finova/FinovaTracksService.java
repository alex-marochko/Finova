package com.example.sdn.finova;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by sdn on 1/19/16.
 */
public class FinovaTracksService extends IntentService{

    static final String SERVICE_NAME = "FinovaTracksIntentService";

    static final String ACCESS_TOKEN_PARAM = "access-token";
    static final String PAGE_PARAM = "page";
    static final String PER_PAGE_PARAM = "per-page";
    static final String DATE1_PARAM = "date1";
    static final String DATE2_PARAM = "date2";

    static final short NOT_AUTHORIZED_RESPONSE = 401;
    static final short SUCCESS_RESPONSE = 200;
    static final short INTERNAL_SERVER_ERROR_RESPONSE = 500;

    ArrayList<Track> tracks;


    String accessToken;
    int page;
    int perPage;
    long dateFilterFrom;
    long dateFilterTo;

    public FinovaTracksService(String accessToken) {
        super(SERVICE_NAME);
        this.accessToken = accessToken;
        tracks = new ArrayList<>();
    }


    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public void onDestroy() {
        super.onDestroy();
    }

}

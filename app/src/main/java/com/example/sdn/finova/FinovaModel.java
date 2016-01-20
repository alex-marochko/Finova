package com.example.sdn.finova;

import java.util.ArrayList;

/**
 * Created by sdn on 1/20/16.
 */

public class FinovaModel {

    static final String ACCESS_TOKEN_PARAM = "access-token";
    static final String PAGE_PARAM = "page";
    static final String PER_PAGE_PARAM = "per-page";
    static final String DATE1_PARAM = "date1";
    static final String DATE2_PARAM = "date2";

    static final short NOT_AUTHORIZED_RESPONSE = 401;
    static final short SUCCESS_RESPONSE = 200;
    static final short INTERNAL_SERVER_ERROR_RESPONSE = 500;

    ArrayList<Track> tracks;


    String serverURI;
    String accessToken;
    int page;
    int perPage;
    long dateFilterFrom;
    long dateFilterTo;

    public FinovaModel(String serverURI, String accessToken) {
        this.serverURI = serverURI;
        this.accessToken = accessToken;
        tracks = new ArrayList<>();
    }

    public void getData(){

        

    }
}

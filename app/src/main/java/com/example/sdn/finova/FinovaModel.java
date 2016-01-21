package com.example.sdn.finova;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sdn on 1/20/16.
 */

public class FinovaModel {

    static final String LOG_TAG = "marinfo";

    static final String ACCESS_TOKEN_PARAM = "access-token";
    static final String PAGE_PARAM = "page";
    static final String PER_PAGE_PARAM = "per-page";
    static final String DATE1_PARAM = "date1";
    static final String DATE2_PARAM = "date2";

    static final String TRACKS_SECTION_NAME = "tracks?";

    static final short NOT_AUTHORIZED_RESPONSE = 401;
    static final short SUCCESS_RESPONSE = 200;
    static final short INTERNAL_SERVER_ERROR_RESPONSE = 500;

    ArrayList<TrackJSON> tracks;


    String serverURI;
    String accessToken;
    int page;
    int perPage;
    long dateFilterFrom;
    long dateFilterTo;

    int tracksPerRequest;

    public FinovaModel(String serverURI, String accessToken, int tracksPerRequest, int perPage) {
        this.serverURI = serverURI;
        this.accessToken = accessToken;
        this.tracksPerRequest = tracksPerRequest;
    }



    public String getTracksDataFromPage(int pageNumber) throws IOException { //numbers begin at 1, not 0

        URL url = new URL(serverURI + TRACKS_SECTION_NAME + ACCESS_TOKEN_PARAM
        + "=" + accessToken + "&" + PAGE_PARAM + "=" + pageNumber);

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try{

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer))>0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toString().replaceAll("\"\\d+\":", ""); //using regex to replace unnecessary tracks numeration

        }finally {
            connection.disconnect();
        }

    }
}

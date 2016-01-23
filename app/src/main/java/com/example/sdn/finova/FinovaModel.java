package com.example.sdn.finova;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sdn on 1/20/16.
 *
 * TODO: server returns incorrect JSON format for pages with number >1 (see replaceAll with RegEx)
 * TODO: server doesn't give an empty array (or other sign) when there's no more tracks
 * TODO: check all methods to work properly when there's no tracks at all, or just 1 or 2
 * TODO: checking server response code
 *
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




    String serverURI;
    String accessToken;
    int perPage;
    long dateFilterFrom;
    long dateFilterTo;

    int tracksPerRequest;

    public FinovaModel(String serverURI, String accessToken, int tracksPerRequest, int perPage) {
        this.serverURI = serverURI;
        this.accessToken = accessToken;
        this.tracksPerRequest = tracksPerRequest;
        this.perPage = perPage;
    }



    public TrackJSON[] getTracksDataFromPage(int pageNumber) throws IOException { //numbers begin at 1, not 0

        URL url = new URL(serverURI + TRACKS_SECTION_NAME + ACCESS_TOKEN_PARAM
        + "=" + accessToken + "&" + PAGE_PARAM + "=" + pageNumber);

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        String s;

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
            s = out.toString().replaceAll("\"\\d+\":\\{", "{")
                    .replaceAll("\\{\\{","[{")
                    .replaceAll("\\}\\}", "}]"); //using regex to replace unnecessary tracks numeration and wrong brackets

        }finally {
            connection.disconnect();
        }

        Gson gson = new Gson();
        TrackJSON[] tracks;
        tracks = gson.fromJson(s, TrackJSON[].class);

        return tracks;
    }

    public ArrayList<TrackJSON> getCertainTracks(int trackFrom, int trackTo){

//        The point is to have opportunity to select certain TRACKS, not pages.

        ArrayList<TrackJSON> tracks = new ArrayList<>();

        int pageFrom;

        pageFrom = trackFrom/perPage;
        if((trackFrom%perPage)>0)pageFrom++;

        int pageCounter = pageFrom;

        int deleteFirst = trackFrom - (pageFrom-1)*perPage - 1;

        TrackJSON[] trackArray;
        int trackArrayLength;
        int i;

        do {

            try {
                trackArray = getTracksDataFromPage(pageCounter);
                trackArrayLength = trackArray.length;
                for (i=0;i<trackArrayLength;i++) {

                    if(deleteFirst>0)deleteFirst--;
                        else if(tracks.size()<(trackTo-trackFrom+1))tracks.add(trackArray[i]);
                }

            } catch (IOException e) {
                Log.d(LOG_TAG, e.getMessage());
            }

            pageCounter++;

        }while(tracks.size()<(trackTo-trackFrom+1));


        return tracks;
    }

    public ArrayList<TrackJSON> getTracksOnRequest(int requestIndex){
//        we will manipulate with requests, it's easier

        Log.d(LOG_TAG, "tracksPerRequest = " + tracksPerRequest);
        Log.d(LOG_TAG, "requestIndex = " + requestIndex);

        Log.d(LOG_TAG, "from = " + (1 + tracksPerRequest*(requestIndex - 1)));
        Log.d(LOG_TAG, "to = " + requestIndex*tracksPerRequest);

        return getCertainTracks(1 + tracksPerRequest*(requestIndex - 1), requestIndex*tracksPerRequest);

    }

}

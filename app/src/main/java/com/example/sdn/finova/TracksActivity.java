package com.example.sdn.finova;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TracksActivity extends AppCompatActivity {

    static final String LOG_TAG = "marinfo";

    static final String ACCESS_TOKEN = "63Rqp1-c72sYPuLao3BYpLRv-358SHer";
    static final String SERVER_URI = "http://api.connect.finova.ua/app/";
    static final int TRACKS_PER_PAGE_SERVER = 2;
    static final int TRACKS_PER_REQUEST_CLIENT = 5;

    static final int REQUEST_PARAM_TRACKS = 1;

    private int requestId = 1;

    private ArrayList<TrackJSON> tracks = new ArrayList<>();
    private TrackJSON[] tracksArray;

    ListView listViewTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewTracks = (ListView)findViewById(R.id.listViewTracks);


        loadTracksData();
        loadTracksData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadTracksData(){

        Log.d(LOG_TAG, "loadTracksData() start");

        Intent dummyIntent = new Intent();

        PendingIntent pendingIntent = createPendingResult(REQUEST_PARAM_TRACKS, dummyIntent, 0);

        Intent intent = new Intent(this, FinovaTracksService.class);

        intent.putExtra("requestId", requestId)
                .putExtra("accessToken", ACCESS_TOKEN)
                .putExtra("serverUri", SERVER_URI)
                .putExtra("tracksPerRequest", TRACKS_PER_REQUEST_CLIENT)
                .putExtra("perPage", TRACKS_PER_PAGE_SERVER)
                .putExtra("pendingIntent", pendingIntent);

        startService(intent);


        requestId++;
        Log.d(LOG_TAG, "loadTracksData() end");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(LOG_TAG, "onActivityResult(..) start");

        if (data == null) return;

        switch (requestCode) {
            case REQUEST_PARAM_TRACKS:
                Log.d(LOG_TAG, "case REQUEST_PARAM_TRACKS start");
                String tracksString = data.getStringExtra("tracks");
                int tracksCount = data.getIntExtra("tracksCount", 5);
                Gson gson = new Gson();

//                Type type = new TypeToken<ArrayList<TrackJSON>>(){}.getType();
//                tracks.addAll(gson.fromJson(tracksString, type));

                JsonParser parser = new JsonParser();
                JsonArray array = parser.parse(tracksString).getAsJsonArray();
                for(int i=0; i<tracksCount; i++ )
                    tracks.add( gson.fromJson(array.get(i), TrackJSON.class) );

                fillViews();
//
//  Log.d(LOG_TAG, "size = " + tracks.size());
                Log.d(LOG_TAG, "case REQUEST_PARAM_TRACKS end");
                break;
        }

        Log.d(LOG_TAG, "onActivityResult(..) end");

    }

    public void fillViews(){
/*
        Log.d(LOG_TAG, "size = " + tracks.size());
        for (int i = 0; i < tracks.size(); i++) {
            Log.d(LOG_TAG, "track = " + tracks.get(i).getId());
        }
*/

        ArrayList<Map<String, Object>> listData = new ArrayList<Map<String, Object>>(
                tracks.size());

        Map<String, Object> m;

        for (int i = 0; i < tracks.size(); i++) {
            m = new HashMap<String, Object>();
            m.put(R.id.textViewDistance + "", tracks.get(i).getLength());
            m.put(R.id.textViewDuration +"", tracks.get(i).getTime_track());
            m.put(R.id.textViewFrom + "", tracks.get(i).getAddressStart());
            m.put(R.id.textViewTimeFrom + "", tracks.get(i).getTime_track_start());
            m.put(R.id.textViewTimeTo + "", tracks.get(i).getTime_track_stop());
            m.put(R.id.textViewTo + "", tracks.get(i).getAddressEnd());

            listData.add(m);
        }

        String[] from = {R.id.textViewDistance + "", R.id.textViewDuration +"",
                R.id.textViewFrom + "", R.id.textViewTimeFrom + "", R.id.textViewTimeTo + "",
                R.id.textViewTo + ""};

        int[] to = {R.id.textViewDistance, R.id.textViewDuration,
                R.id.textViewFrom, R.id.textViewTimeFrom, R.id.textViewTimeTo,
                R.id.textViewTo};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listData, R.layout.track_list_item,
                from, to);

        listViewTracks.setAdapter(simpleAdapter);

    }

    public void onDummyButtonClick(View v){

        Toast.makeText(getBaseContext(), "Coming soon!", Toast.LENGTH_SHORT).show();

    }



/*
        Picasso.with(getBaseContext())
                .load("https://newevolutiondesigns.com/images/freebies/space-wallpaper-29.jpg").into(imageViewTest);
*/

}

package com.example.sdn.finova;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

public class TracksActivity extends AppCompatActivity {

    static final String LOG_TAG = "marinfo";
    static final String ACCESS_TOKEN = "63Rqp1-c72sYPuLao3BYpLRv-358SHer";
    static final String SERVER_URI = "http://api.connect.finova.ua/app/";
    static final int TRACKS_PER_PAGE_SERVER = 2;
    static final int TRACKS_PER_REQUEST_CLIENT = 5;

    Button buttonTest;
    ImageView imageViewTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonTest = (Button)findViewById(R.id.buttonTest);
        imageViewTest = (ImageView)findViewById(R.id.imageViewTest);

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

    public void onButtonTestClick(View v) throws IOException {

        class GetDataTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    temp();
                }catch (IOException e){
                    Log.d(LOG_TAG, e.getMessage());
                }


                return null;
            }



            protected void temp() throws IOException{

                FinovaModel finovaModel = new FinovaModel(SERVER_URI, ACCESS_TOKEN,
                        TRACKS_PER_REQUEST_CLIENT, TRACKS_PER_PAGE_SERVER);

                ArrayList<TrackJSON> tracks = new ArrayList<>();

                tracks = finovaModel.getCertainTracks(3, 6);

                Log.d(LOG_TAG, "size = " + tracks.size());
                Log.d(LOG_TAG, "track = " + tracks.get(0).getImg());


/*
                TrackJSON[] tracks = finovaModel.getTracksDataFromPage(2);


                Log.d(LOG_TAG, tracks[0].getAddressStart());
                Log.d(LOG_TAG, tracks[0].toString());
                Log.d(LOG_TAG, tracks[1].getImg());
*/

//                Picasso.with(getBaseContext()).load(tracks[1].getImg()).into(imageViewTest);

//                    imageViewTest.setContentDescription("123");


            }

        }

        new GetDataTask().execute();
/*
        Picasso.with(getBaseContext())
                .load("https://newevolutiondesigns.com/images/freebies/space-wallpaper-29.jpg").into(imageViewTest);
*/



    }


}

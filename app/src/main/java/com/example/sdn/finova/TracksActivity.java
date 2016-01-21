package com.example.sdn.finova;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

public class TracksActivity extends AppCompatActivity {

    static final String LOG_TAG = "marinfo";

    Button buttonTest;
    ImageView imageViewTest;
    ImageView imageViewTest2;
    ImageView imageViewTest3;
    ImageView imageViewTest4;
    ImageView imageViewTest5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonTest = (Button)findViewById(R.id.buttonTest);
        imageViewTest = (ImageView)findViewById(R.id.imageViewTest);
        imageViewTest2 = (ImageView)findViewById(R.id.imageViewTest2);
        imageViewTest3 = (ImageView)findViewById(R.id.imageViewTest3);
        imageViewTest4 = (ImageView)findViewById(R.id.imageViewTest4);
        imageViewTest5 = (ImageView)findViewById(R.id.imageViewTest5);

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

                String s = new FinovaModel("http://api.connect.finova.ua/app/tracks?access-token=63Rqp1-c72sYPuLao3BYpLRv-358SHer&page=1",
                        "63Rqp1-c72sYPuLao3BYpLRv-358SHer&page=1").getData();

                Log.d(LOG_TAG, s);

                TrackJSON[] tracks;

                Gson gson = new Gson();
                tracks = gson.fromJson(s, TrackJSON[].class);

                Log.d(LOG_TAG, tracks[0].getAddressStart());
                Log.d(LOG_TAG, tracks[1].getImg());

//                Picasso.with(getBaseContext()).load(tracks[1].getImg()).into(imageViewTest);

//                    imageViewTest.setContentDescription("123");


            }



        }

        new GetDataTask().execute();
        Picasso.with(getBaseContext())
                .load("https://newevolutiondesigns.com/images/freebies/space-wallpaper-29.jpg").into(imageViewTest);

        Picasso.with(getBaseContext())
                .load("http://wallpapersdesk.net/wp-content/uploads/2015/03/4399_space.jpg").into(imageViewTest2);

        Picasso.with(getBaseContext())
                .load("http://cdni.wired.co.uk/1240x826/s_v/space_11.jpg").into(imageViewTest3);

        Picasso.with(getBaseContext())
                .load("https://i.ytimg.com/vi/Un5SEJ8MyPc/maxresdefault.jpg").into(imageViewTest4);


        Picasso.with(getBaseContext())
                .load("http://hdwallpapersfit.com/wp-content/uploads/2015/02/space-hd-wallpapers.jpg").into(imageViewTest5);


    }


}

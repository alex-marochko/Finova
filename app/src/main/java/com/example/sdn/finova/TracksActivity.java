package com.example.sdn.finova;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class TracksActivity extends AppCompatActivity {

    static final String LOG_TAG = "marinfo";

    static final String ACCESS_TOKEN = "63Rqp1-c72sYPuLao3BYpLRv-358SHer";
    static final String SERVER_URI = "http://api.connect.finova.ua/app/";
    static final int TRACKS_PER_PAGE_SERVER = 2;
    static final int TRACKS_PER_REQUEST_CLIENT = 20;

    static final int REQUEST_PARAM_TRACKS = 1;

    private int requestId = 1;

    private ArrayList<TrackJSON> tracks = new ArrayList<>();

    ListView listViewTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewTracks = (ListView)findViewById(R.id.listViewTracks);


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
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        for (int i = 0; i < tracks.size(); i++) {
            m = new HashMap<String, Object>();
            m.put(R.id.textViewDistance + "", tracks.get(i).getLength() + ".0 км");

            String s = "";
            if(tracks.get(i).getTime_track()>=3600) s = getTimeFormatted(tracks.get(i).getTime_track(), "H ч ");
            m.put(R.id.textViewDuration +"", s + getTimeFormatted(tracks.get(i).getTime_track(), "m мин"));
            m.put(R.id.textViewFrom + "", tracks.get(i).getAddressStart());
            m.put(R.id.textViewTimeFrom + "", getTimeFormatted(tracks.get(i).getTime_track_start(), "hh:mm"));
            m.put(R.id.textViewTimeTo + "", getTimeFormatted(tracks.get(i).getTime_track_stop(), "hh:mm"));
            m.put(R.id.textViewTo + "", tracks.get(i).getAddressEnd());
            m.put(R.id.imageViewMap + "", R.drawable.map_dummy);
//            m.put(R.id.textViewDateDivider + "", tracks.get(i).getTime_track_start());

//            we should have visible date divider
//            only when it's first in list, or when date changes:

//            TODO: check algorithm, there's something wrong with dates

            calendar2.setTimeInMillis(tracks.get(i).getTime_track_start() * 1000);

            if(i>0) { //not first in list
                calendar.setTimeInMillis(tracks.get(i - 1).getTime_track_start() * 1000);


                if (calendar.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)) //day's not changed
                    m.put(R.id.textViewDateDivider + "", "");
                    else m.put(R.id.textViewDateDivider + "", getDateFormatted(calendar2));
//                        calendar2.getDisplayName(Calendar.DATE, Calendar.SHORT, getResources().getConfiguration().locale));
            }else m.put(R.id.textViewDateDivider + "", getDateFormatted(calendar2));


                    listData.add(m);
        }

        String[] from = {R.id.textViewDistance + "", R.id.textViewDuration +"",
                R.id.textViewFrom + "", R.id.textViewTimeFrom + "", R.id.textViewTimeTo + "",
                R.id.textViewTo + "", R.id.imageViewMap + ""};

        int[] to = {R.id.textViewDistance, R.id.textViewDuration,
                R.id.textViewFrom, R.id.textViewTimeFrom, R.id.textViewTimeTo,
                R.id.textViewTo, R.id.imageViewMap};

        FinovaSimpleAdapter simpleAdapter = new FinovaSimpleAdapter(this, listData, R.layout.track_list_item,
                from, to);

        listViewTracks.setAdapter(simpleAdapter);



        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        listViewTracks.setVisibility(View.VISIBLE);

    }

    public String getTimeFormatted(long seconds, String pattern){

        if(seconds > 0) {

//            incredible, but without this line all local datetime conversations
//            are considering timezone difference automatically:
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

            Date date = new Date(seconds*1000);


            Log.d(LOG_TAG, "seconds = " + seconds + " date = " + date.toString());

            SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());


//            Log.d(LOG_TAG, "after formatting ("+  Locale.UK.toString() + ") " + formatter.format(date));

            return formatter.format(date);
        } else return "";
    }

    public String getDateFormatted(Calendar calendar){

        Date date = calendar.getTime();  //calendar.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM", Locale.getDefault());

        String result;

        formatter.setCalendar(calendar);

        result = formatter.format(calendar.getTime());

        Calendar currentDate = Calendar.getInstance();

        if(calendar.get(Calendar.YEAR)!=currentDate.get(Calendar.YEAR)){

            result = result + " " + calendar.get(Calendar.YEAR);
        }


        //checking difference between track date and current date:
        Calendar checkCalendar = Calendar.getInstance();
        checkCalendar.setTimeInMillis(currentDate.getTime().getTime() - calendar.getTime().getTime());
//        currentDate.add(Calendar.MILLISECOND, -calendar.get(Calendar.MILLISECOND));

        Log.d(LOG_TAG, "date.toString(): " + currentDate.getTime().toString() );




        if(checkCalendar.get(Calendar.YEAR)==1970){
            switch (currentDate.get(Calendar.DAY_OF_YEAR)-calendar.get(Calendar.DAY_OF_YEAR)){

                case 0:
                    result = "Сегодня, " + result;
                    break;
                case 1:
                    result = "Вчера, " + result;
            }
        }

        return result;

    }

    public void onDummyButtonClick(View v){

        Toast.makeText(getBaseContext(), "Coming soon!", Toast.LENGTH_SHORT).show();

    }


    private class FinovaSimpleAdapter extends SimpleAdapter{
        private Context context;
        private LayoutInflater inflater;
        private List<? extends Map<String, ?>> data;
        int[] to;
        String[] from;


        public FinovaSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);

            this.context = context;
            inflater = LayoutInflater.from(context);
            this.data = data;
            this.to = to;
            this.from = from;
        }

/*
        @Override
        public void setViewImage(ImageView v, int value){

//            Log.d(LOG_TAG, "setViewImage, value = " + value);

//            Log.d(LOG_TAG, " CLASSS = " + v.getParent().getClass().toString());

            Picasso
                    .with(getBaseContext())
                    .load("http://media.tumblr.com/1a82d8359973115a13349f0b5bd3eb90/tumblr_inline_mw9bpi2oeI1rw02zn.png")//tracks.get(value).getImg())
                    .into(v);

            super.setViewImage(v, value);
        }
*/

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.track_list_item, parent, false);
            }

//           ----------====== PICASSO =======-----------------------

            if(tracks.get(position).getImg().length()>0) {
                Picasso
                        .with(context)
                        .setIndicatorsEnabled(true);
                Picasso.with(context)
                        .load(tracks.get(position).getImg())

                        .resize(1000, 800)

                                .centerInside()
//                        .resize(500, 400)
//                        .fit()
//                        .noFade()
                        .error(R.drawable.map_error)
                        .placeholder(R.drawable.map_loading)
                        .priority(picassoLoadingPriority(position))
                        .into((ImageView) convertView.findViewById(R.id.imageViewMap));
            }

//           ----------====== GLIDE =======-----------------------
/*
            Glide.with(context)
                    .load(tracks.get(position).getImg())
//                    .centerCrop()
//                    .placeholder(R.drawable.loading_spinner)
                    .crossFade()
                    .into((ImageView) convertView.findViewById(R.id.imageViewMap));
*/

//          hiding textViewDateDivider when it's not first and date is not changing

            /*if(data.get(position).get(R.id.textViewDateDivider+"").toString().equals(""))
                (convertView.findViewById(R.id.textViewDateDivider))
                        .setVisibility(View.GONE);
            else */((TextView)convertView.findViewById(R.id.textViewDateDivider))
                    .setText(data.get(position).get(R.id.textViewDateDivider+"").toString());

//          filling time textViews

            ((TextView)convertView.findViewById(R.id.textViewTimeFrom))
                    .setText(data.get(position).get(R.id.textViewTimeFrom+"").toString());

            ((TextView)convertView.findViewById(R.id.textViewTimeTo))
                    .setText(data.get(position).get(R.id.textViewTimeTo+"").toString());

//          filling route points textViews

            ((TextView)convertView.findViewById(R.id.textViewFrom))
                    .setText(data.get(position).get(R.id.textViewFrom+"").toString());

            ((TextView)convertView.findViewById(R.id.textViewTo))
                    .setText(data.get(position).get(R.id.textViewTo+"").toString());

//            textViewDistance

            ((TextView)convertView.findViewById(R.id.textViewDistance))
                    .setText(data.get(position).get(R.id.textViewDistance+"").toString());

//            textViewDuration

            ((TextView)convertView.findViewById(R.id.textViewDuration))
                    .setText(data.get(position).get(R.id.textViewDuration+"").toString());



//            Log.d(LOG_TAG, "getView, position = " + position);
            return convertView;
        }

        private Picasso.Priority picassoLoadingPriority(int index){
            if(index < data.size()/2) return Picasso.Priority.HIGH;
            else return Picasso.Priority.LOW;

        }

/*
        @Override
        public void setViewText(TextView v, String text) {

            super.setViewText(v, text);

            Log.d(LOG_TAG, "setViewText");

            if (v.getId() == R.id.textViewDateDivider) {

            }
        }
*/

    }


}

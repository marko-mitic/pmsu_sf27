package com.example.mitke.pmsu_sf27.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.activities.RestourantActivity;
import com.example.mitke.pmsu_sf27.adapters.RestourantListAdapter;
import com.example.mitke.pmsu_sf27.model.Restourant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mitke on 04-Jan-17.
 */

public class RestourantsFragment extends ListFragment implements AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Context context;
    private RestourantListAdapter mAdapter;
    private ArrayList<Restourant> mRestList;
    private double prefRadius;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double mCurrentLatitude;
    double mCurrentLongitude;
    double mDistance;
    int fromHour;
    int fromMin;
    TextView timeText;
    Button timeFilterBtn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }



    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.restourants_fragment, container, false);
        timeText = (TextView) view.findViewById(R.id.time_filter_text);
                timeFilterBtn =(Button) view.findViewById(R.id.filter_time_btn);
        timeFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterTime();
            }
        });
        return view;
    }
    public void filterTime(){
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog fromTimePicker = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        fromHour = hourOfDay;
                        fromMin = minute;

                        mRestList = Restourant.filterByWorkTime(fromHour,fromMin);
                        mAdapter = new RestourantListAdapter(getContext(),mRestList);
                        setListAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        String timeString = "Your time";
                        if(fromHour<10){
                            timeString+="0"+fromHour;
                        }else{
                            timeString += ""+fromHour;
                        }
                        if (fromMin<10){
                            timeString+=":0"+fromHour;
                        }else{
                            timeString += ":"+fromHour;
                        }
                        timeText.setText(timeString);
                    }
                }, mHour, mMinute, true);
        fromTimePicker.setTitle("Chose start time");
        fromTimePicker.show();
        
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(context, "Location Connected", Toast.LENGTH_SHORT).show();
            mCurrentLatitude = mLastLocation.getLatitude();
            mCurrentLongitude = mLastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        mDistance = Double.parseDouble(pref.getString("pref_radius","1"));
        if (mLastLocation != null){
            mRestList = Restourant.filterByDistance((ArrayList<Restourant>)Restourant.getAllRestourants(),mCurrentLatitude,mCurrentLongitude,mDistance);
        }else {
            mRestList = (ArrayList<Restourant>) Restourant.getAllRestourants();
        }
        mAdapter = new RestourantListAdapter(getContext(),mRestList);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(context, "onItemClick item:"+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, RestourantActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}

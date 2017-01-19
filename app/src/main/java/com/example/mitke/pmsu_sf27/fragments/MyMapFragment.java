package com.example.mitke.pmsu_sf27.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mitke.pmsu_sf27.activities.MealActivity;
import com.example.mitke.pmsu_sf27.services.OrderService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.example.mitke.pmsu_sf27.activities.RestourantActivity;
import com.example.mitke.pmsu_sf27.model.Restourant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import com.example.mitke.pmsu_sf27.R;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.example.mitke.pmsu_sf27.R.string.map;


public class MyMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    MapView mMapView;
    private GoogleMap googleMap;
    ArrayList<Restourant> mRestourantList;
    Context context;
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    Button mLocationBtn;
    Location mLastLocation;
    double mDistance;
    public MyMapFragment() {
        // Required empty public constructor
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();

            }
            return false;
        }
        return true;
    }
        @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mRestourantList =(ArrayList<Restourant>)Restourant.getAllRestourants();
        mMapView.onResume();// needed to get the map to display immediately
        context = getContext();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                LatLng place = new LatLng(1,1);
                for(Restourant r: mRestourantList){
                    place = new LatLng(r.getLocationLat(), r.getLocationLong());
                    googleMap.addMarker(new MarkerOptions().position(place).title(r.getName())).showInfoWindow();
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 12.0f));
                googleMap.setOnMarkerClickListener(MyMapFragment.this);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.my_map_fragment, container,
                false);
        mLocationBtn = (Button) v.findViewById(R.id.locationBtn);
        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocation();

            }
        });

        return v;

    }

    private void getLocation() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        mLastLocation = LocationServices.FusedLocationApi
                                .getLastLocation(mGoogleApiClient);
                        if (mLastLocation != null) {
                            mDistance = Double.parseDouble(PreferenceManager.getDefaultSharedPreferences(context).getString("pref_radius","1000"));
                            double latitude = mLastLocation.getLatitude();
                            double longitude = mLastLocation.getLongitude();
                            LatLng nowLocation = new LatLng(latitude,longitude);
                            googleMap.clear();
                            googleMap.addMarker(new MarkerOptions().position(nowLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            CircleOptions circleOptions = new CircleOptions()
                                    .center(nowLocation)
                                    .radius(mDistance);
                            Circle circle = googleMap.addCircle(circleOptions);
                            LatLng place = new LatLng(1,1);
                            for(Restourant r: Restourant.filterByDistance((ArrayList<Restourant>)Restourant.getAllRestourants(),latitude,longitude,mDistance)){
                                place = new LatLng(r.getLocationLat(), r.getLocationLong());
                                googleMap.addMarker(new MarkerOptions().position(place).title(r.getName())).showInfoWindow();
                            }
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 12.0f));
                            googleMap.setOnMarkerClickListener(MyMapFragment.this);
                            Toast.makeText(context, "Map filtered by location", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
        builder.setMessage("Filter your location?\n (You can change your search radious in settings)").setPositiveButton("Filter", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        checkPlayServices();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("MAOS", "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }
    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildGoogleApiClient();

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        String name= marker.getTitle();

        for(Restourant r : mRestourantList){
            if(name.equals(r.getName())){
                Intent intent = new Intent(context, RestourantActivity.class);
                intent.putExtra("position", mRestourantList.indexOf(r));
                startActivity(intent);
                return true;

            }
        }
        return false;
    }
}

package com.example.mitke.pmsu_sf27.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mitke.pmsu_sf27.activities.RestourantActivity;
import com.example.mitke.pmsu_sf27.model.Restourant;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MyMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    ArrayList<Restourant> mRestourantList;
    Context context;
    public MyMapFragment() {
        // Required empty public constructor
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
                    googleMap.addMarker(new MarkerOptions().position(place).title(r.getName()));
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


        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
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
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

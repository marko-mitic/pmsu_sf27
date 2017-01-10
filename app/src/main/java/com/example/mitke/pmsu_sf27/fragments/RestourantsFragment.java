package com.example.mitke.pmsu_sf27.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.activities.RestourantActivity;
import com.example.mitke.pmsu_sf27.adapters.RestourantListAdapter;
import com.example.mitke.pmsu_sf27.model.Restourant;

import java.util.ArrayList;

/**
 * Created by mitke on 04-Jan-17.
 */

public class RestourantsFragment extends ListFragment implements AdapterView.OnItemClickListener {

    Context context;
    private RestourantListAdapter mAdapter;
    private ArrayList<Restourant> mRestList;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mRestList =(ArrayList<Restourant>) Restourant.getAllRestourants();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.restourants_fragment, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
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

}

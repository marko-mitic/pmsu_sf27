package com.example.mitke.pmsu_sf27.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.model.Restourant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitke on 04-Jan-17.
 */

public class RestourantListAdapter extends ArrayAdapter<Restourant> {
    Context mContext;
    ArrayList<Restourant> mRestourantItems;

    @Override
    public int getCount() {
        return mRestourantItems.size();
    }

    @Override
    public Restourant getItem(int position) {
        return mRestourantItems.get(position);
    }
    @Override
    public long getItemId(int position) {
        return mRestourantItems.get(position).getId();
    }

    protected void populateView(View view, Restourant item) {

    }
    public  RestourantListAdapter(Context context, ArrayList<Restourant> restourants){
        super(context, 0, restourants);
        this.mContext = context;
        this.mRestourantItems = restourants;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        View view;
        Restourant r = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.restourant_list_item, parent, false);
        }
        else{
            view = convertView;
        }
        // Lookup view for data population
        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView descView = (TextView) view.findViewById(R.id.description);
        ImageView imgView = (ImageView) view.findViewById(R.id.restourantImage);
        // Populate the data into the template view using the data object
        imgView.setImageResource(r.getImagePath());

        titleView.setText(mRestourantItems.get(position).getName());
        descView.setText(mRestourantItems.get(position).getDescription());
        // Return the completed view to render on screen
        return view;
    }
}

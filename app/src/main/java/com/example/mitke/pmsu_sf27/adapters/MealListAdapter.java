package com.example.mitke.pmsu_sf27.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.model.Meal;
import com.example.mitke.pmsu_sf27.model.Restourant;

import java.util.ArrayList;

/**
 * Created by mitke on 08-Jan-17.
 */

public class MealListAdapter extends ArrayAdapter<Meal> implements Filterable {
    private Context mContext;
    private ArrayList<Meal> mMealList;
    @Override
    public int getCount() {
        return mMealList.size();
    }
    @Override
    public Meal getItem(int pos){
        return mMealList.get(pos);
    }
    public  MealListAdapter(Context context, ArrayList<Meal> mMealList){
        super(context, 0, mMealList);
        this.mContext = context;
        this.mMealList = mMealList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        View view;
        Meal m = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.meal_list_item, parent, false);
        }
        else{
            view = convertView;
        }
        TextView name = (TextView) view.findViewById(R.id.meal_name);
        TextView price = (TextView) view.findViewById(R.id.meal_price);
        if (m != null) {
            name.setText(m.getName());
        }
        price.setText(String.valueOf(m.getPrice()));


        // Return the completed view to render on screen
        return view;
    }
}

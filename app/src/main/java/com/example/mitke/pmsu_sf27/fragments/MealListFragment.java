package com.example.mitke.pmsu_sf27.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.activities.MealActivity;
import com.example.mitke.pmsu_sf27.activities.RestourantActivity;
import com.example.mitke.pmsu_sf27.adapters.MealListAdapter;
import com.example.mitke.pmsu_sf27.model.Meal;
import com.example.mitke.pmsu_sf27.model.MealToRestourant;
import com.example.mitke.pmsu_sf27.model.Restourant;

import java.util.ArrayList;

/**
 * Created by mitke on 06-Jan-17.
 */

public class MealListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private Context context;
    MealListAdapter mAdapter;
    ArrayList<Meal> mMealList;
    Restourant mRestourant;
    private int pos;
    public MealListFragment(){
        super();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.meal_list_fragment, container, false);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            pos = bundle.getInt("position", 0);
        }else {
            pos = 1;
        }
        mMealList = (ArrayList<Meal>) Restourant.getById(pos).getMealsFromDb();
        mAdapter = new MealListAdapter(getContext(),mMealList);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(context, "onItemClick item:"+mMealList.get(position).getId(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, MealActivity.class);
        intent.putExtra("position", mMealList.get(position).getId());
        startActivity(intent);

    }


}

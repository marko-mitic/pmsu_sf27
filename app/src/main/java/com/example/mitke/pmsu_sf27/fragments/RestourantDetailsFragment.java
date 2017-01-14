package com.example.mitke.pmsu_sf27.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.activities.RestourantActivity;
import com.example.mitke.pmsu_sf27.model.Restourant;


/**
 * Created by mitke on 06-Jan-17.
 */

public class RestourantDetailsFragment extends Fragment {

    Context context;
    private Restourant restourant;
    private int pos;
    View view;

    public RestourantDetailsFragment(){
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            pos = bundle.getInt("position", 0);
        }else {
            pos = 1;
        }
        restourant = Restourant.getById(pos);
        view = inflater.inflate(R.layout.restourant_details_fragment, container, false);

        TextView title =(TextView) view.findViewById(R.id.title2);
        TextView desc = (TextView) view.findViewById(R.id.description2);
        ImageView img = (ImageView) view.findViewById(R.id.restourantImage2);
        TextView numberSms = (TextView) view.findViewById(R.id.sms_text);
        TextView numberCall = (TextView) view.findViewById(R.id.call_text);
        TextView website = (TextView) view.findViewById(R.id.website_text);
        TextView workingTime = (TextView) view.findViewById(R.id.working_time_text);

        title.setText(restourant.getName());
        desc.setText(restourant.getDescription());
        img.setImageResource(restourant.getImagePath());
        numberSms.setText(restourant.getPhone());
        numberCall.setText(restourant.getPhone());
        website.setText(restourant.getSiteString());
        workingTime.setText(restourant.getWorkTimeString());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restourant = Restourant.getById(pos);
    }
    }



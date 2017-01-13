package com.example.mitke.pmsu_sf27.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.model.Tag;

import java.util.ArrayList;

/**
 * Created by mitke on 10-Jan-17.
 */

public class TagAdapter extends ArrayAdapter<Tag> {
    private Context mContext;
    private ArrayList<Tag> mTags;
    private ArrayList<Tag> selectedTags = new ArrayList<>();

    private int mCount;
    public  TagAdapter(Context context, ArrayList<Tag> tags){
        super(context, 0, tags);
        this.mContext = context;
        this.mTags = tags;
    }

    public ArrayList<Tag> getSelectedTags() {
        return selectedTags;
    }

    @Override
    public int getCount(){
        return mTags.size();
    }
    @Override
    public Tag getItem(int pos){
        return mTags.get(pos);
    }
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View view;
        Tag t = getItem(position);
        view = LayoutInflater.from(getContext()).inflate(R.layout.filter_item, parent, false);
        CheckBox text1 = (CheckBox) view.findViewById(R.id.spiner_text);
        if (t != null) {
            text1.setText(t.getName());
        }
        text1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(Tag.getByName(buttonView.getText().toString()));
                }else{
                    selectedTags.remove(Tag.getByName(buttonView.getText().toString()));
                }

            }
        });
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }



}

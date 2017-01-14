package com.example.mitke.pmsu_sf27.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.activities.MealActivity;
import com.example.mitke.pmsu_sf27.adapters.MealListAdapter;
import com.example.mitke.pmsu_sf27.adapters.TagAdapter;
import com.example.mitke.pmsu_sf27.model.Meal;
import com.example.mitke.pmsu_sf27.model.Restourant;
import com.example.mitke.pmsu_sf27.model.Tag;

import java.util.ArrayList;

/**
 * Created by mitke on 06-Jan-17.
 */

public class MealListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private Context context;
    MealListAdapter mAdapter;
    ArrayList<Meal> mMealList;
    ArrayList<Tag> mTagList;
    Button filter_button;
    ListView filterList;
    PopupWindow popup;
    LinearLayout filterLayout;
    TagAdapter tagAdapter;
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
        mTagList = (ArrayList<Tag>) Tag.getall();
        filter_button = (Button) view.findViewById(R.id.show_filters_btn);
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterList(filter_button);
            }
        });

        return view;
    }
    public void showFilterList(View anchor){
        popup = new PopupWindow(getContext());
        tagAdapter = new TagAdapter(getContext(), mTagList);
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        filterLayout =(LinearLayout) inflater.inflate(R.layout.filter_list_layout,null);
        filterList = (ListView) filterLayout.findViewById(R.id.filter_list);
        filterList.setAdapter(tagAdapter);
        Button btn = (Button) filterLayout.findViewById(R.id.filter_meals_btn);
        btn.setOnClickListener(filterButtonClick());
        popup.setFocusable(true);
        popup.setWidth(R.dimen.popup_width);
        popup.setBackgroundDrawable(getResources().getDrawable(R.drawable.abc_dialog_material_background_light,null));
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setContentView(filterLayout);
        popup.showAsDropDown(anchor,0,0);

    }

    private View.OnClickListener filterButtonClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Tag> filterTags = tagAdapter.getSelectedTags();
                if(filterTags.size()<1){
                    mAdapter = new MealListAdapter(getContext(),(ArrayList<Meal>)mRestourant.getMeals());
                    setListAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    popup.dismiss();
                }else{
                    mMealList = (ArrayList<Meal>) Meal.getFilteredByTags(filterTags,(ArrayList<Meal>) mRestourant.getMeals());
                    mAdapter = new MealListAdapter(getContext(),mMealList);
                    setListAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    popup.dismiss();
                }
            }
        };
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
        mRestourant = Restourant.getById(pos);
        mMealList = (ArrayList<Meal>) mRestourant.getMealsFromDb();
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


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}

package com.example.mitke.pmsu_sf27.pagers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mitke.pmsu_sf27.fragments.MealListFragment;
import com.example.mitke.pmsu_sf27.fragments.RestourantDetailsFragment;
import com.example.mitke.pmsu_sf27.model.Restourant;

/**
 * Created by mitke on 06-Jan-17.
 */

public class RestourantPager extends FragmentStatePagerAdapter {
    private int tabCount;
    private int mPosition;
    public RestourantPager(FragmentManager fm, int tabCount, int mPosition){
        super(fm);
        this.tabCount = tabCount;
        this.mPosition = mPosition;
    }

    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                RestourantDetailsFragment fg = new RestourantDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", mPosition);
                fg.setArguments(bundle);
                return fg;
            case 1:
                MealListFragment mfg = new MealListFragment();
                Bundle bundleMeal = new Bundle();
                bundleMeal.putInt("position", mPosition);
                mfg.setArguments(bundleMeal);
                return mfg;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

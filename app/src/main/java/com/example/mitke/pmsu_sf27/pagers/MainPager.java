package com.example.mitke.pmsu_sf27.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mitke.pmsu_sf27.fragments.MyMapFragment;
import com.example.mitke.pmsu_sf27.fragments.RestourantsFragment;

/**
 * Created by mitke on 05-Jan-17.
 */

public class MainPager extends FragmentStatePagerAdapter {
    private int tabCount;
    public MainPager(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount= tabCount;
    }
    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                RestourantsFragment rf = new RestourantsFragment();
                return rf;
            case 1:
                MyMapFragment mf = new MyMapFragment();
                return mf;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

package com.example.TravelDay;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Calendar;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    public TabPagerAdapter(FragmentManager fm,int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new TabFragment1();
                return fragment;
            case 1:
                fragment = new TabFragment2();
                return fragment;
            case 2:
                fragment = new TabFragment3();
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

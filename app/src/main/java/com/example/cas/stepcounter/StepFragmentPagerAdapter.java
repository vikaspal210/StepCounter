package com.example.cas.stepcounter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by cas on 11/8/2017.
 */

public class StepFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitle[]=new String[] {"Step Counter","Daily Water","BMI"};

    public StepFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new stepFragment();
        } else if(position==1){
            return new WaterFragment();
        }else{
            return new BMIFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}

package com.example.cas.stepcounter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by cas on 11/8/2017.
 */

public class StepFragmentPagerAdapter extends FragmentPagerAdapter {

    //tab titles
    private String tabTitle[] = new String[]{"Activity", "Progress", "Profile"};

    //needed cunstructor
    public StepFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //get item method
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new stepFragment();
        } else if(position==1){
            return new ProfileFragment();
        }else{
            return new ProgressFragment();
        }
    }

    //count no of fragments
    @Override
    public int getCount() {
        return 3;
    }

    //tab title setting
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}

package com.example.cas.stepcounter;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View pager and View pager adapter setting
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        StepFragmentPagerAdapter adapter = new StepFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //code for adding TABS
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

}

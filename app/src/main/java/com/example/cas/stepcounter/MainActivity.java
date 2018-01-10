package com.example.cas.stepcounter;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View pager and View pager adapter setting
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        StepFragmentPagerAdapter adapter = new StepFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //code for adding TABS
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        //code to check if UserInfo have run once if not runs UserInfo.class then MainActivity.class
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            startActivity(new Intent(MainActivity.this, UserInfo.class));
            Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();
    }

    //setting tab icons for images...
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_48dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home_white_48dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_home_white_48dp);
    }
}

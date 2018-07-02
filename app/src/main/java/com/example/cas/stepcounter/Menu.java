package com.example.cas.stepcounter;

import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Calendar;

public class Menu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //upper toolbar
        Toolbar toolbar = findViewById(R.id.menu_toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu_close);
    }

    public void openCalender(View view) {

        //ori builder to open calender
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);
    }

    public void openProfile(View view) {
        /*Fragment profileFragment = ProfileFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.menu_rootLayout, profileFragment);
        transaction.commit();*/
        ((ActivityManager) getBaseContext().getSystemService(ACTIVITY_SERVICE))
                .clearApplicationUserData();

        /*DialogExperiment newFragment = new DialogExperiment();
        newFragment.show(getFragmentManager(), "ProfileDialog");*/

    }

    public void openAboutApps(View view) {
        AboutAppDialog aboutAppDialog = new AboutAppDialog();
        aboutAppDialog.show(getFragmentManager(), "AboutAppDialog");
    }
}

package com.example.cas.stepcounter;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Calendar;
import java.util.Objects;

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

    //to launch calender
    public void launchCalender(View view) {

        //ori builder to open calender
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);
    }

    //to open profile
    public void openProfile(View view) {
        DialogExperiment newFragment = new DialogExperiment();
        newFragment.show(getFragmentManager(), "ProfileDialog");

    }

    //to open About apps
    public void openAboutApps(View view) {
        AboutAppDialog aboutAppDialog = new AboutAppDialog();
        aboutAppDialog.show(getFragmentManager(), "AboutAppDialog");
    }

    //to clear App data
    public void clearData(View view){
        AlertDialog.Builder alertBuilder;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            alertBuilder=new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert);
        }else{
            alertBuilder=new AlertDialog.Builder(getApplicationContext());
        }
        alertBuilder.setTitle("Clear App Data")
        .setMessage("Are you sure you want to clear all data")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //on yes clear application data
                ((ActivityManager) Objects.requireNonNull(getBaseContext().getSystemService(ACTIVITY_SERVICE)))
                        .clearApplicationUserData();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        })
        .show();

    }
}

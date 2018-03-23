package com.example.cas.stepcounter;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    public static Boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //upper toolbar
        Toolbar toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ham_icon);



        //code to check if UserInfo have run once if not runs UserInfo.class then run MainActivity.class
        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (isFirstRun) {
            startActivity(new Intent(MainActivity.this, UserInfo.class));
        }
        //setting up navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_steps:
                                // Action to perform when Home Menu item is selected.
                                selectedFragment = stepFragment.newInstance();
                                break;
                            case R.id.action_progress:
                                // Action to perform when Bag Menu item is selected.
                                selectedFragment = ProgressFragment.newInstance();
                                break;
                            case R.id.action_profile:
                                // Action to perform when Account Menu item is selected.
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.rootLayout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.rootLayout, stepFragment.newInstance());
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(MainActivity.this, com.example.cas.stepcounter.Menu.class));
                Toast.makeText(this, "Clicked hamburger icon", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

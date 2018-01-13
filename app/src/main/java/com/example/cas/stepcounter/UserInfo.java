package com.example.cas.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInfo extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String HEIGHT = "heightKey";
    public static final String WEIGHT = "weightKey";
    public static final String AGE = "ageKey";


    //constants
    SharedPreferences mSharedPreferences;
    EditText nameET, heightET, weightET,ageET;
    Button mdoneButton;

    //setDefault
    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //getDefault
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //initializing edit text, button;
        nameET = (EditText) findViewById(R.id.name_ET);
        heightET = (EditText) findViewById(R.id.height_ET);
        weightET = (EditText) findViewById(R.id.weight_ET);
        ageET = (EditText) findViewById(R.id.age_ET);
        mdoneButton = (Button) findViewById(R.id.done_button);
        //initializing mSharedPreferences
        mSharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mdoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaults(Name, nameET.getText().toString(), getBaseContext());
                setDefaults(HEIGHT, heightET.getText().toString(), getBaseContext());
                setDefaults(WEIGHT, weightET.getText().toString(), getBaseContext());
                setDefaults(AGE, ageET.getText().toString(), getBaseContext());
                Toast.makeText(getBaseContext(), getDefaults(Name, getBaseContext()), Toast.LENGTH_LONG).show();
            }
        });
    }
}

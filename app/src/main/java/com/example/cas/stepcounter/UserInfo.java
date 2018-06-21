package com.example.cas.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
    Button mdoneButton;
    private EditText nameET, heightET, weightET, ageET;

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
        nameET = findViewById(R.id.name_ET);
        heightET = findViewById(R.id.height_ET);
        weightET = findViewById(R.id.weight_ET);
        ageET = findViewById(R.id.age_ET);
        mdoneButton = findViewById(R.id.done_button);
        //initializing mSharedPreferences
        mSharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mdoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logic to check if edit text is empty.
                if (TextUtils.isEmpty(nameET.getText().toString().trim()) ||
                        TextUtils.isEmpty(heightET.getText().toString().trim()) ||
                        TextUtils.isEmpty(weightET.getText().toString().trim()) ||
                        TextUtils.isEmpty(ageET.getText().toString().trim())) {
                    Toast.makeText(getBaseContext(), "Fields can't be empty!", Toast.LENGTH_LONG).show();
                } else {
                    setDefaults(Name, nameET.getText().toString(), getBaseContext());
                setDefaults(HEIGHT, heightET.getText().toString(), getBaseContext());
                setDefaults(WEIGHT, weightET.getText().toString(), getBaseContext());
                setDefaults(AGE, ageET.getText().toString(), getBaseContext());
                    //add preference to check if UserInfo added
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putBoolean("isFirstRun", false).apply();
                    startActivity(new Intent(UserInfo.this, MainActivity.class));
                }
            }
        });
    }
}

package com.example.cas.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    //constants
    SharedPreferences mSharedPreferences;
    EditText nameET, heightET, weightET;
    Button mdoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //initializing edit text, button;
        nameET = (EditText) findViewById(R.id.name_ET);
        heightET = (EditText) findViewById(R.id.height_ET);
        weightET = (EditText) findViewById(R.id.weight_ET);
        mdoneButton = (Button) findViewById(R.id.done_button);
        //initializing mSharedPreferences
        mSharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mdoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(Name, nameET.getText().toString());
                editor.putString(HEIGHT, heightET.getText().toString());
                editor.putString(WEIGHT, weightET.getText().toString());
                editor.commit();

                Toast.makeText(getBaseContext(), mSharedPreferences.getString(Name, "nameKey"), Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.example.cas.stepcounter;


import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class WaterFragment extends Fragment {

    //constants
    private EditText weightEditText;
    private EditText ageEditText;
    private float mWeight;
    private float mAge;
    private double waterIntakeValue;
    private TextView waterTextView;
    private Button calculateButton;

    public WaterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water, container, false);

        //initialise Edit Text, Button, Text View instances
        weightEditText = (EditText) view.findViewById(R.id.et_weight);
        ageEditText = (EditText) view.findViewById(R.id.et_age);
        waterTextView = (TextView) view.findViewById(R.id.water_textView);
        calculateButton = (Button) view.findViewById(R.id.waterButton);

        //listening for Button click
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //if condition to check Empty fields
                if (weightEditText.getText().toString().trim().isEmpty() || ageEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Field can't be EMPTY!!", Toast.LENGTH_SHORT).show();
                } else {
                    mWeight = Float.valueOf(weightEditText.getText().toString());
                    mAge = Integer.valueOf(ageEditText.getText().toString());
                    waterIntakeValue = dailyWaterIntakeCalculator(mWeight, mAge);
                    System.out.print(waterIntakeValue);
                    waterTextView.setText(String.valueOf(String.format("%.2f", waterIntakeValue)) + " Liters");
                }
            }
        });
        return view;
    }

    //daily water intake calculating method
    private double dailyWaterIntakeCalculator(float weight, float age) {

        int ageThreshold;
        mWeight = weight;
        mAge = age;

        if (age < 30) {
            ageThreshold = 40;
        } else if (age >= 30 && age <= 50) {
            ageThreshold = 35;
        } else {
            ageThreshold = 30;
        }
        return (((((weight * 2.205) / 2.2) * ageThreshold) / 28.3) * 0.0296);
    }
}

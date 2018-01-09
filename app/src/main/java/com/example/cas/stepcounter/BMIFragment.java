package com.example.cas.stepcounter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class BMIFragment extends Fragment {

    //constants
    private EditText weightET;
    private EditText heightET;
    private TextView bmiTV;
    private TextView bmiCategoryTV;
    private Button bmiButton;
    private float mWeight;
    private float mHeight;
    private float bmi;

    public BMIFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        //initialise Edit Text, Button, Text View instances
        weightET = (EditText) view.findViewById(R.id.bmi_weight);
        heightET = (EditText) view.findViewById(R.id.bmi_height);
        bmiButton = (Button) view.findViewById(R.id.bmi_button);
        bmiTV = (TextView) view.findViewById(R.id.bmi_textView);
        bmiCategoryTV = (TextView) view.findViewById(R.id.bmi_category_textView);

        //listening for click on Button
        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //if condition for checking Empty fields
                if (weightET.getText().toString().trim().isEmpty() || heightET.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Field can't be EMPTY!!", Toast.LENGTH_SHORT).show();
                } else {
                    //get text for Edit Text, calculate and then Display
                    mWeight = Float.valueOf(weightET.getText().toString());
                    mHeight = Float.valueOf(heightET.getText().toString());
                    bmi = calculateBMI(mWeight, mHeight);
                    bmiTV.setText(String.valueOf(String.format("%.2f", bmi)));
                    if (bmi < 18.5) {
                        bmiCategoryTV.setText("UnderWeight");
                    } else if (18.5 <= bmi && bmi <= 24.9) {
                        bmiCategoryTV.setText("Normal Weight");
                    } else if (25 <= bmi && bmi <= 29.9) {
                        bmiCategoryTV.setText("Overweight");
                    } else {
                        bmiCategoryTV.setText("Obesity");
                    }
                }
            }
        });
        return view;
    }

    //method to calculate BMI
    private float calculateBMI(float weight, float height) {
        mWeight = weight;
        mHeight = height;
        return (weight * weight) / height;
    }
}

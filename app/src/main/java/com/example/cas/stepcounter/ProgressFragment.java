package com.example.cas.stepcounter;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment {

    //constants water
    private float mWeight;
    private float mAge;
    private double waterIntakeValue;
    private TextView waterTextView;
    //constants BMI
    private float mHeight;
    private float bmi;
    private TextView bmiTV;
    private TextView bmiCategoryTV;

    public ProgressFragment() {
        // Required empty public constructor
    }

    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        //initializing Water,BMI Text View
        waterTextView = view.findViewById(R.id.water_textView);
        bmiTV = view.findViewById(R.id.bmi_textView);
        bmiCategoryTV = view.findViewById(R.id.bmi_category_textView);


        if (MainActivity.isFirstRun) {
            Toast.makeText(getActivity().getBaseContext(), "Empty", Toast.LENGTH_SHORT).show();
        } else {
            //initialize weight,height,age.
            mWeight = Float.valueOf(UserInfo.getDefaults(UserInfo.WEIGHT, getActivity().getBaseContext()));
            mAge = Float.valueOf(UserInfo.getDefaults(UserInfo.AGE, getActivity().getBaseContext()));
            mHeight = Float.valueOf(UserInfo.getDefaults(UserInfo.HEIGHT, getActivity().getBaseContext()));

            //calculate water
            waterIntakeValue = dailyWaterIntakeCalculator(mWeight, mAge);
            waterTextView.setText(String.valueOf(String.format("%.2f", waterIntakeValue)) + " Liters");

            //calculating BMI
            bmi = calculateBMI(mWeight, mHeight);
            bmiTV.setText(String.valueOf(String.format("%.2f", bmi)));
            if (bmi < 18.5) {
                bmiCategoryTV.setText("UnderWeight");
            } else if (18.5 <= bmi && bmi <= 24.9) {
                bmiCategoryTV.setText("Normal Weight");
            } else if (25 <= bmi && bmi <= 29.9) {
                bmiCategoryTV.setText("Overweight");
            } else {
                bmiCategoryTV.setText("Obese");
            }
        }
        //TextView variables for graph steps
        TextView stepsMon, stepsTue, stepsWed, stepsThr, stepsFri, stepsSat, stepsSun;
        //initializing steps display TextViews
        stepsMon = view.findViewById(R.id.monSteps);
        stepsTue = view.findViewById(R.id.tueSteps);
        stepsWed = view.findViewById(R.id.wedSteps);
        stepsThr = view.findViewById(R.id.thrSteps);
        stepsFri = view.findViewById(R.id.friSteps);
        stepsSat = view.findViewById(R.id.satSteps);
        stepsSun = view.findViewById(R.id.sunSteps);
        //graph setting text
        stepsMon.setText(UserInfo.getDefaults(UserInfo.STEPSMon, getActivity().getBaseContext()));
        stepsTue.setText(UserInfo.getDefaults(UserInfo.STEPSTue, getActivity().getBaseContext()));
        stepsWed.setText(UserInfo.getDefaults(UserInfo.STEPSWed, getActivity().getBaseContext()));
        stepsThr.setText(UserInfo.getDefaults(UserInfo.STEPSThr, getActivity().getBaseContext()));
        stepsFri.setText(UserInfo.getDefaults(UserInfo.STEPSFri, getActivity().getBaseContext()));
        stepsSat.setText(UserInfo.getDefaults(UserInfo.STEPSSat, getActivity().getBaseContext()));
        stepsSun.setText(UserInfo.getDefaults(UserInfo.STEPSSun, getActivity().getBaseContext()));


        //return view
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

    //method to calculate BMI
    private float calculateBMI(float weight, float height) {
        mWeight = weight;
        mHeight = height;
        return (weight * weight) / height;
    }
}

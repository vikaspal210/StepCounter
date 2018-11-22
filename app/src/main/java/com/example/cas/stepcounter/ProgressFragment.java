package com.example.cas.stepcounter;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


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
        TextView caloriesTue,caloriesWed, caloriesMon,caloriesThr, caloriesFri ,caloriesSat,caloriesSun;
        TextView distanceTue ,distanceWed ,distanceMon ,distanceThr ,distanceFri ,distanceSat ,distanceSun;
        TextView stepsTueTV ,stepsWedTV ,stepsMonTV ,stepsThrTV ,stepsFriTV ,stepsSatTV ,stepsSunTV;
        //initializing steps display TextViews
        caloriesTue = view.findViewById(R.id.tueCalories);
        caloriesWed = view.findViewById(R.id.wedCalories);
        caloriesMon = view.findViewById(R.id.monCalories);
        caloriesThr = view.findViewById(R.id.thrCalories);
        caloriesFri = view.findViewById(R.id.friCalories);
        caloriesSat = view.findViewById(R.id.satCalories);
        caloriesSun = view.findViewById(R.id.sunCalories);

        distanceTue = view.findViewById(R.id.tueDistance);
        distanceWed = view.findViewById(R.id.wedDistance);
        distanceMon = view.findViewById(R.id.monDistance);
        distanceThr = view.findViewById(R.id.thrDistance);
        distanceFri = view.findViewById(R.id.friDistance);
        distanceSat = view.findViewById(R.id.satDistance);
        distanceSun = view.findViewById(R.id.sunDistance);

        stepsTueTV = view.findViewById(R.id.tueSteps);
        stepsWedTV = view.findViewById(R.id.wedSteps);
        stepsMonTV = view.findViewById(R.id.monSteps);
        stepsThrTV = view.findViewById(R.id.thrSteps);
        stepsFriTV = view.findViewById(R.id.friSteps);
        stepsSatTV = view.findViewById(R.id.satSteps);
        stepsSunTV = view.findViewById(R.id.sunSteps);

        double stepsMon= 0;
        double stepsTue= 0;
        double stepsWed= 0;
        double stepsThr= 0;
        double stepsFri= 0;
        double stepsSat= 0;
        double stepsSun= 0;

        for(int i=0;i<=6;i++){
            switch(i){
                case 0:
                    try {
                        stepsMon = Double.parseDouble(UserInfo.getDefaults(UserInfo.STEPSMon, getActivity().getBaseContext()));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        stepsTue = Double.parseDouble(UserInfo.getDefaults(UserInfo.STEPSTue, getActivity().getBaseContext()));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        stepsWed = Double.parseDouble(UserInfo.getDefaults(UserInfo.STEPSWed, getActivity().getBaseContext()));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        stepsThr = Double.parseDouble(UserInfo.getDefaults(UserInfo.STEPSThr, getActivity().getBaseContext()));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        stepsFri = Double.parseDouble(UserInfo.getDefaults(UserInfo.STEPSFri, getActivity().getBaseContext()));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    try {
                        stepsSat = Double.parseDouble(UserInfo.getDefaults(UserInfo.STEPSSat, getActivity().getBaseContext()));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    try {
                        stepsSun = Double.parseDouble(UserInfo.getDefaults(UserInfo.STEPSSun, getActivity().getBaseContext()));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
            }//switch end
        }//for end

        //calculating distance
        double disMon = (double) (stepsMon * 8) / 10000;
        double disTue = (double) (stepsTue * 8) / 10000;
        double disWed = (double) (stepsWed * 8) / 10000;
        double disThr = (double) (stepsThr * 8) / 10000;
        double disFri = (double) (stepsFri * 8) / 10000;
        double disSat = (double) (stepsSat * 8) / 10000;
        double disSun = (double) (stepsSun * 8) / 10000;


        //calculating calories
        int weight = Integer.parseInt(UserInfo.getDefaults(UserInfo.WEIGHT, getActivity().getBaseContext()));
        double calMon = ((0.75 * weight) / 2000) * stepsMon;
        double calTue = ((0.75 * weight) / 2000) * stepsTue;
        double calWed = ((0.75 * weight) / 2000) * stepsWed;
        double calThr = ((0.75 * weight) / 2000) * stepsThr;
        double calFri = ((0.75 * weight) / 2000) * stepsFri;
        double calSat = ((0.75 * weight) / 2000) * stepsSat;
        double calSun = ((0.75 * weight) / 2000) * stepsSun;


        try {
            //graph setting text to steps
            stepsMonTV.setText(Double.toString(stepsMon));
            stepsTueTV.setText(Double.toString(stepsTue));
            stepsWedTV.setText(Double.toString(stepsWed));
            stepsThrTV.setText(Double.toString(stepsThr));
            stepsFriTV.setText(Double.toString(stepsFri));
            stepsSatTV.setText(Double.toString(stepsSat));
            stepsSunTV.setText(Double.toString(stepsSun));
            //graph setting text to distance
            distanceMon.setText(String.format("%.2f", disMon));
            distanceTue.setText(String.format("%.2f", disTue));
            distanceWed.setText(String.format("%.2f", disWed));
            distanceThr.setText(String.format("%.2f", disThr));
            distanceFri.setText(String.format("%.2f", disFri));
            distanceSat.setText(String.format("%.2f", disSat));
            distanceSun.setText(String.format("%.2f", disSun));
            //graph setting text to calories
            caloriesMon.setText(String.format("%.2f", calMon));
            caloriesTue.setText(String.format("%.2f", calTue));
            caloriesWed.setText(String.format("%.2f", calWed));
            caloriesThr.setText(String.format("%.2f", calThr));
            caloriesFri.setText(String.format("%.2f", calFri));
            caloriesSat.setText(String.format("%.2f", calSat));
            caloriesSun.setText(String.format("%.2f", calSun));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


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

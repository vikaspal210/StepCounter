package com.example.cas.stepcounter;

import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.content.Context.SENSOR_SERVICE;


public class stepFragment extends Fragment implements SensorEventListener, StepListener {

    //constants
    private TextView stepTV, calorieTV, distanceTV;
    private ImageView BtnStart;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps, no;
    private double calorie, distance = 0.00;
    private Boolean isOff = true;
    private int[] stepDataBase = new int[7];


    public stepFragment() {
        // Required empty public constructor
    }

    //code for newInstance
    public static stepFragment newInstance() {
        return new stepFragment();
    }

    //onCreateView for inflating step fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //populate a view for fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        //setting background to null for reducing OverDraw
        getActivity().getWindow().setBackgroundDrawable(null);
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            Toast.makeText(getActivity().getBaseContext(), "There was some problem, please try again after closing the application.", Toast.LENGTH_SHORT).show();
        }
        //
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        //initialise Text View and Button instances
        stepTV = view.findViewById(R.id.tv_steps);
        calorieTV = view.findViewById(R.id.tv_calories);
        distanceTV = view.findViewById(R.id.tv_distance);
        BtnStart = view.findViewById(R.id.btn_start);

        //listening for Sensor ON/Off button click
        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //animation
                final Animation buttonBounceAnim = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.bounce);
                // Use bounce interpolator with amplitude 0.2 and frequency 20
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                buttonBounceAnim.setInterpolator(interpolator);
                BtnStart.startAnimation(buttonBounceAnim);

                if (isOff) {
                    Toast.makeText(getActivity().getBaseContext(), "Sensor Started", Toast.LENGTH_SHORT).show();
                    //initialize Number of steps to 0;
                    numSteps = 0;
                    //register step Listener
                    sensorManager.registerListener(stepFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                    //flag isOff false for toggle purposes
                    isOff = false;
                } else {
                    storeData(numSteps);
                    //unregisterListener to release sensor manager
                    sensorManager.unregisterListener(stepFragment.this);
                    isOff = true;
                }
            }
        });
        return view;
    }

    //for Accelerometer Sensor
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    //Overriding step method of StepListener
    @Override
    public void step(long timeNs, String textToShow) {
        //calculating steps
        numSteps++;
        no = numSteps;

        try {
            //calculating distance
            distance = (double) (no * 8) / 10000;

            //calculating calories
            int weight = Integer.parseInt(UserInfo.getDefaults(UserInfo.WEIGHT, getActivity().getBaseContext()));
            calorie = ((0.75 * weight) / 2000) * no;
            //set steps, distance and calories to TextView's
            stepTV.setText(no + "");
            distanceTV.setText(String.format("%.2f", distance) + " km");
            calorieTV.setText(String.format("%.2f", calorie) + "cal");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //storeData method
    private void storeData(int steps) {

        Calendar calendar = Calendar.getInstance();
        //to find what day is today {Monday, Tuesday, Thursday,...,Sunday}
        int todayDay = calendar.get(Calendar.DAY_OF_WEEK);


        //switch to find which day
        switch (todayDay) {
            case 1:
                if (isSameWeek(new Date())) {
                    try {
                        stepDataBase[0] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSSun, getActivity().getBaseContext()));
                    } catch (NumberFormatException mNumberFormatException) {
                        mNumberFormatException.printStackTrace();
                    }
                    stepDataBase[0] = stepDataBase[0] + steps;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                } else {
                    stepDataBase[0] = 0;
                    UserInfo.setDefaults(UserInfo.SavedMilisec, Long.toString(System.currentTimeMillis()), getActivity().getBaseContext());
                }
                break;
            case 2:
                Toast.makeText(getActivity(), Boolean.toString(isSameWeek(new Date())), Toast.LENGTH_SHORT).show();
                if (isSameWeek(new Date())) {
                    try {
                        stepDataBase[1] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSMon, getActivity().getBaseContext()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    stepDataBase[1] = stepDataBase[1] + steps;
                    UserInfo.setDefaults(UserInfo.STEPSMon, Integer.toString(stepDataBase[1]), getActivity().getBaseContext());
                } else {
                    stepDataBase[1] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                }
                break;
            case 3:
                if (isSameWeek(new Date())) {
                    try {
                        stepDataBase[2] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSTue, getActivity().getBaseContext()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    stepDataBase[2] = stepDataBase[2] + steps;
                    UserInfo.setDefaults(UserInfo.STEPSTue, Integer.toString(stepDataBase[2]), getActivity().getBaseContext());
                } else {
                    stepDataBase[2] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                }
                break;
            case 4:
                if (isSameWeek(new Date())) {
                    try {
                        stepDataBase[3] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSWed, getActivity().getBaseContext()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    stepDataBase[3] = stepDataBase[3] + steps;
                    UserInfo.setDefaults(UserInfo.STEPSWed, Integer.toString(stepDataBase[3]), getActivity().getBaseContext());
                } else {
                    stepDataBase[3] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                }
                break;

            case 5:
                if (isSameWeek(new Date())) {
                    try {
                        stepDataBase[4] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSThr, getActivity().getBaseContext()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    stepDataBase[4] = stepDataBase[4] + steps;
                    UserInfo.setDefaults(UserInfo.STEPSThr, Integer.toString(stepDataBase[4]), getActivity().getBaseContext());
                } else {
                    stepDataBase[4] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                }
                break;
            case 6:
                if (isSameWeek(new Date())) {
                    try {
                        stepDataBase[5] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSFri, getActivity().getBaseContext()));
                    } catch (NumberFormatException mNumberFormatException) {
                        mNumberFormatException.printStackTrace();
                    }
                    stepDataBase[5] = stepDataBase[5] + steps;
                    UserInfo.setDefaults(UserInfo.STEPSFri, Integer.toString(stepDataBase[5]), getActivity().getBaseContext());
                } else {
                    stepDataBase[5] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                    storeData(numSteps);
                }

                break;
            case 7:
                //if its same week keep storing steps NO need to reset its Value
                if (isSameWeek(new Date())) {
                    try {
                        //read value from SharedPreferences and save to stepDataBase
                        stepDataBase[6] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSSat, getActivity().getBaseContext()));
                    }
                    //This is done as when SharedPreferences Value of STEPSDay is not initialized "null" is returned
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    //calculate total steps in a day
                    stepDataBase[6] = stepDataBase[6] + steps;
                    //stores SharedPreferences STEPSDay Key's Value
                    UserInfo.setDefaults(UserInfo.STEPSSat, Integer.toString(stepDataBase[6]), getActivity().getBaseContext());
                }
                //as We are in Next Week Clear stored steps data and start fresh
                else {
                    //initializing stepDataBase[x] to 0
                    stepDataBase[6] = 0;
                    //Initializing SharedPreference Value of SavedDate Key's Value to Date Object
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                    //After Initialization we recall storeData(numSteps) to save steps to SharedPreference STEPDay Key to Its Value
                    storeData(numSteps);
                }
                break;
        }//switch END
    }

    //true if we are in same week, false if we are in next week
    public Boolean isSameWeek(Date currentDate) {

        try {
            Date savedDate;
            //if already initialized savedDate, we get a Date Object
            savedDate = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).parse(UserInfo.getDefaults(UserInfo.SavedDate, getActivity().getBaseContext()));
            long diff = savedDate.getTime() - currentDate.getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) <= 6;
        } catch (ParseException | NullPointerException mParseException) {
            return false;
        }
    }


}

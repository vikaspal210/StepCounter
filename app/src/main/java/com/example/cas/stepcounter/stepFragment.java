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
        View view = inflater.inflate(R.layout.fragment_step, container, false);

        //setting background to null for reducing OverDraw
        getActivity().getWindow().setBackgroundDrawable(null);
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        //initialise Text View and Button instances
        stepTV = view.findViewById(R.id.tv_steps);
        calorieTV = view.findViewById(R.id.tv_calories);
        distanceTV = view.findViewById(R.id.tv_distance);
        BtnStart = view.findViewById(R.id.btn_start);

        //listening for button click
        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }


    @Override
    public void step(long timeNs, String textToShow) {
        //calculating steps
        numSteps++;
        no = numSteps;

        //calculating distance
        distance = (double) (no * 8) / 10000;

        //calculating calories
        int weight = Integer.parseInt(UserInfo.getDefaults(UserInfo.WEIGHT, getActivity().getBaseContext()));
        calorie = ((0.75 * weight) / 2000) * no;
        //set steps, distance and calories to TextView's
        stepTV.setText(no + "");
        distanceTV.setText(String.format("%.2f", distance) + " km");
        calorieTV.setText(String.format("%.2f", calorie) + "cal");
    }

    //storing DATA storeData method
    private void storeData(int steps) {

        Calendar calendar = Calendar.getInstance();
        int todayDay = calendar.get(Calendar.DAY_OF_WEEK);


        //switch to find which day
        switch (todayDay) {
            case 1:
                Toast.makeText(getActivity(), Boolean.toString(isSameWeek(new Date())), Toast.LENGTH_SHORT).show();
                if (isSameWeek(new Date())) {
                    Toast.makeText(getActivity(), "Its Sunday", Toast.LENGTH_SHORT).show();
                    try {
                        stepDataBase[0] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSSun, getActivity().getBaseContext()));
                    } catch (NumberFormatException mNumberFormatException) {
                        mNumberFormatException.printStackTrace();
                    }
                    stepDataBase[0] = stepDataBase[0] + steps;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                    Toast.makeText(getActivity(), UserInfo.getDefaults(UserInfo.STEPSSun, getActivity().getBaseContext()), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[0] = 0;
                    UserInfo.setDefaults(UserInfo.SavedMilisec, Long.toString(System.currentTimeMillis()), getActivity().getBaseContext());
                    Toast.makeText(getActivity(), "Next Sunday", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                Toast.makeText(getActivity(), Boolean.toString(isSameWeek(new Date())), Toast.LENGTH_SHORT).show();
                if (isSameWeek(new Date())) {
                    Toast.makeText(getActivity(), "Its Monday", Toast.LENGTH_SHORT).show();
                    try {
                        stepDataBase[1] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSMon, getActivity().getBaseContext()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    stepDataBase[1] = stepDataBase[1] + steps;
                    UserInfo.setDefaults(UserInfo.STEPSMon, Integer.toString(stepDataBase[1]), getActivity().getBaseContext());
                    Toast.makeText(getActivity(), UserInfo.getDefaults(UserInfo.STEPSMon, getActivity().getBaseContext()), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[1] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                    Toast.makeText(getActivity(), "Next Monday", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                Toast.makeText(getActivity(), Boolean.toString(isSameWeek(new Date())), Toast.LENGTH_SHORT).show();
                if (isSameWeek(new Date())) {
                    Toast.makeText(getActivity(), "Its Tuesday", Toast.LENGTH_SHORT).show();
                    try {
                        stepDataBase[2] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSTue, getActivity().getBaseContext()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    stepDataBase[2] = stepDataBase[2] + steps;
                    UserInfo.setDefaults(UserInfo.STEPSTue, Integer.toString(stepDataBase[2]), getActivity().getBaseContext());
                    Toast.makeText(getActivity(), UserInfo.getDefaults(UserInfo.STEPSTue, getActivity().getBaseContext()), Toast.LENGTH_SHORT).show();

                } else {
                    stepDataBase[2] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                    Toast.makeText(getActivity(), "Next Tuesday", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                Toast.makeText(getActivity(), Boolean.toString(isSameWeek(new Date())), Toast.LENGTH_SHORT).show();
                if (isSameWeek(new Date())) {
                    Toast.makeText(getActivity(), "Its Wednesday", Toast.LENGTH_SHORT).show();
                    try {
                        stepDataBase[3] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSWed, getActivity().getBaseContext()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    stepDataBase[3] = stepDataBase[3] + steps;
                    UserInfo.setDefaults(UserInfo.STEPSWed, Integer.toString(stepDataBase[3]), getActivity().getBaseContext());
                    Toast.makeText(getActivity(), UserInfo.getDefaults(UserInfo.STEPSWed, getActivity().getBaseContext()), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[3] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                    Toast.makeText(getActivity(), "Next Wednesday", Toast.LENGTH_SHORT).show();
                }
                break;

            case 5:
                if (isSameWeek(new Date())) {
                    Toast.makeText(getActivity(), "Its Thursday", Toast.LENGTH_SHORT).show();
                    try {
                        stepDataBase[4] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSThr, getActivity().getBaseContext()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    stepDataBase[4] = stepDataBase[4] + steps;
                    UserInfo.setDefaults(UserInfo.STEPSThr, Integer.toString(stepDataBase[4]), getActivity().getBaseContext());
                    Toast.makeText(getActivity(), UserInfo.getDefaults(UserInfo.STEPSThr, getActivity().getBaseContext()), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[4] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                    Toast.makeText(getActivity(), "Next Thrusday", Toast.LENGTH_SHORT).show();
                }
                break;
            case 6:
                if (isSameWeek(new Date())) {
                    Toast.makeText(getActivity(), "Its Friday", Toast.LENGTH_SHORT).show();


                    try {
                        stepDataBase[5] = Integer.parseInt(UserInfo.getDefaults(UserInfo.STEPSFri, getActivity().getBaseContext()));
                    } catch (NumberFormatException mNumberFormatException) {
                        mNumberFormatException.printStackTrace();
                    }
                    Toast.makeText(getActivity().getBaseContext(), "stepDateaBase before=" + Integer.toString(stepDataBase[5]), Toast.LENGTH_SHORT).show();
                    stepDataBase[5] = stepDataBase[5] + steps;
                    Toast.makeText(getActivity().getBaseContext(), "stepDateaBase after=" + Integer.toString(stepDataBase[5]), Toast.LENGTH_SHORT).show();
                    UserInfo.setDefaults(UserInfo.STEPSFri, Integer.toString(stepDataBase[5]), getActivity().getBaseContext());
                } else {
                    stepDataBase[5] = 0;
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                    Toast.makeText(getActivity(), "Next Friday", Toast.LENGTH_SHORT).show();
                    storeData(numSteps);
                }

                break;
            case 7:
                //if its same week keep storing steps NO need to reset its Value
                if (isSameWeek(new Date())) {
                    Toast.makeText(getActivity(), "Its Saturday", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), UserInfo.getDefaults(UserInfo.STEPSSat, getActivity().getBaseContext()), Toast.LENGTH_SHORT).show();
                }
                //as We are in Next Week Clear stored steps data and start fresh
                else {
                    //initializes stepDataBase to 0
                    stepDataBase[6] = 0;
                    //Initializes SharedPreference Value of SavedDate Key's Value to Date Object
                    UserInfo.setDefaults(UserInfo.SavedDate
                            , DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(new Date())
                            , getActivity().getBaseContext());
                    Toast.makeText(getActivity(), "Next Saturday", Toast.LENGTH_SHORT).show();
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
            //when already initialized savedDate we get a Date Object
            savedDate = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).parse(UserInfo.getDefaults(UserInfo.SavedDate, getActivity().getBaseContext()));
            long diff = savedDate.getTime() - currentDate.getTime();
            Toast.makeText(getActivity().getBaseContext(), "difference=" + Long.toString(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)), Toast.LENGTH_SHORT).show();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) <= 6;
        } catch (ParseException mParseException) {
            Toast.makeText(getActivity().getBaseContext(), "ParseException in isSameWeek", Toast.LENGTH_SHORT).show();
            return false;

        } catch (NullPointerException mNullPointerException) {
            //when savedDate not initialized we return false which will that for us
            Toast.makeText(getActivity().getBaseContext(), "NullPointerException in isSameWeek", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}

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

import java.util.Calendar;

import static android.content.Context.SENSOR_SERVICE;


public class stepFragment extends Fragment implements SensorEventListener, StepListener {

    //constants
    private TextView stepTV, calorieTV, distanceTV;
    private ImageView BtnStart;

    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps;
    private double calorie, distance = 0.00;
    private Boolean isOff=true;
    private int[] stepDataBase = new int[7];


    public stepFragment() {
        // Required empty public constructor
    }

    //code for newInstance
    public static stepFragment newInstance() {
        return new stepFragment();
    }
    //end of onCreateView

    //onCreateView for inflating step fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);

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
                if(isOff) {
                    numSteps = 0;
                    sensorManager.registerListener(stepFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                    isOff=false;
                }else{
                    storeData(numSteps);
                    sensorManager.unregisterListener(stepFragment.this);
                isOff=true;}


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
    public void step(long timeNs) {
        //calculating steps
        numSteps++;
        int no = numSteps * 1;

        //calculating distance
        distance = (double) (no * 8) / 10000;

        //calculating calories
        int weight = Integer.parseInt(UserInfo.getDefaults(UserInfo.WEIGHT, getActivity().getBaseContext()));
        calorie = ((0.75 * weight) / 2000) * no;

        //set text to TextView's
        stepTV.setText(no + "");
        distanceTV.setText(String.format("%.2f", distance) + " km");
        calorieTV.setText(String.format("%.2f", calorie) + "cal");
    }


    //storing DATA
    private void storeData(int steps) {
        Calendar calendar = Calendar.getInstance();
        int todayDay = calendar.get(Calendar.DAY_OF_WEEK);

        //saved Milliseconds logic
        long savedMillis = System.currentTimeMillis();

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = calendar.get(Calendar.MINUTE);
        int currentSeconds = calendar.get(Calendar.SECOND);

        int passHour = 23 - currentHour;
        int passMinutes = 60 - currentMinutes;
        int passSeconds = 60 - currentSeconds;

        Boolean isNewDay;
        isNewDay = System.currentTimeMillis() >= savedMillis + passHour * passMinutes * passSeconds * 1000;

        //switch to find which day
        switch (todayDay) {
            case 1:
                Toast.makeText(getActivity(), Long.toString(savedMillis), Toast.LENGTH_SHORT).show();

                if (!isNewDay) {
                    Toast.makeText(getActivity(), "Its Sunday", Toast.LENGTH_SHORT).show();
                    stepDataBase[1] = stepDataBase[1] + steps;
                    Toast.makeText(getActivity(), Integer.toString(stepDataBase[1]), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[1] = 0;
                    Toast.makeText(getActivity(), "Next Day", Toast.LENGTH_SHORT).show();
                }

                break;
            case 2:
                Toast.makeText(getActivity(), Long.toString(savedMillis), Toast.LENGTH_SHORT).show();
                if (!isNewDay) {
                    Toast.makeText(getActivity(), "Its Monday", Toast.LENGTH_SHORT).show();
                    stepDataBase[2] = stepDataBase[2] + steps;
                    Toast.makeText(getActivity(), Integer.toString(stepDataBase[2]), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[2] = 0;
                    Toast.makeText(getActivity(), "Next Day", Toast.LENGTH_SHORT).show();
                }

                break;
            case 3:
                Toast.makeText(getActivity(), Long.toString(savedMillis), Toast.LENGTH_SHORT).show();
                if (!isNewDay) {
                    Toast.makeText(getActivity(), "Its Tuesday", Toast.LENGTH_SHORT).show();
                    stepDataBase[3] = stepDataBase[3] + steps;
                    Toast.makeText(getActivity(), Integer.toString(stepDataBase[3]), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[3] = 0;
                    Toast.makeText(getActivity(), "Next Day", Toast.LENGTH_SHORT).show();
                }

                break;
            case 4:
                Toast.makeText(getActivity(), Long.toString(savedMillis), Toast.LENGTH_SHORT).show();
                if (!isNewDay) {
                    Toast.makeText(getActivity(), "Its Wednesday", Toast.LENGTH_SHORT).show();
                    stepDataBase[4] = stepDataBase[4] + steps;
                    Toast.makeText(getActivity(), Integer.toString(stepDataBase[4]), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[4] = 0;
                    Toast.makeText(getActivity(), "Next Day", Toast.LENGTH_SHORT).show();
                }
                break;


            case 5:
                Toast.makeText(getActivity(), Long.toString(savedMillis), Toast.LENGTH_SHORT).show();
                if (!isNewDay) {
                    Toast.makeText(getActivity(), "Its Thursday", Toast.LENGTH_SHORT).show();
                    stepDataBase[5] = stepDataBase[5] + steps;
                    Toast.makeText(getActivity(), Integer.toString(stepDataBase[5]), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[5] = 0;
                    Toast.makeText(getActivity(), "Next Day", Toast.LENGTH_SHORT).show();
                }
                break;
            case 6:
                Toast.makeText(getActivity(), Long.toString(savedMillis), Toast.LENGTH_SHORT).show();
                if (!isNewDay) {
                    Toast.makeText(getActivity(), "Its Friday", Toast.LENGTH_SHORT).show();
                    stepDataBase[6] = stepDataBase[6] + steps;
                    Toast.makeText(getActivity(), Integer.toString(stepDataBase[6]), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[6] = 0;
                    Toast.makeText(getActivity(), "Next Day", Toast.LENGTH_SHORT).show();
                }

                break;
            case 7:
                Toast.makeText(getActivity(), Long.toString(savedMillis), Toast.LENGTH_SHORT).show();
                if (!isNewDay) {
                    Toast.makeText(getActivity(), "Its Saturday", Toast.LENGTH_SHORT).show();
                    stepDataBase[7] = stepDataBase[7] + steps;
                    Toast.makeText(getActivity(), Integer.toString(stepDataBase[7]), Toast.LENGTH_SHORT).show();
                } else {
                    stepDataBase[7] = 0;
                    Toast.makeText(getActivity(), "Next Day", Toast.LENGTH_SHORT).show();
                }
                break;
        }//switch END
    }



}

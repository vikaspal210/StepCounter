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
    private float distance, calorie;
    private Boolean isOff=true;
    private Boolean isNextDay = false;


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
                storeData();
                if(isOff) {
                    numSteps = 0;
                    sensorManager.registerListener(stepFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                    isOff=false;
                }else{
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
        distance = no * 8 / 10000;
        //set text to TextView's
        stepTV.setText(no + "");
        distanceTV.setText(distance + " km");
    }

    private void storeData() {
        Calendar c = Calendar.getInstance();
        int todayDay = c.get(Calendar.DAY_OF_WEEK);
        switch (todayDay) {
            case 1:
                Toast.makeText(getActivity(), "Its Sunday", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "Its Monday", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getActivity(), "Its Tuesday", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(getActivity(), "Its Wednesday", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(getActivity(), "Its Thursday", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(getActivity(), "Its Friday", Toast.LENGTH_SHORT).show();
                break;
            case 7:
                Toast.makeText(getActivity(), "Its Saturday", Toast.LENGTH_SHORT).show();
                break;
        }//switch END
    }



}

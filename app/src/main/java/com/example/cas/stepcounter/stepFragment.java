package com.example.cas.stepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.SENSOR_SERVICE;


public class stepFragment extends Fragment implements SensorEventListener, StepListener {

    //constants
    private TextView tview;
    private Button BtnStart;

    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps;
    private Boolean isOff=true;


    public stepFragment() {
        // Required empty public constructor
    }


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
        tview = (TextView) view.findViewById(R.id.tv_steps);
        BtnStart = (Button) view.findViewById(R.id.btn_start);

        //listening for button click
        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
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
    //end of onCreateView



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
        numSteps++;
        int no=numSteps*7;
        tview.setText(no + "");
    }

}

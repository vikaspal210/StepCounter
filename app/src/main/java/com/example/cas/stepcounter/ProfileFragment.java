package com.example.cas.stepcounter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements NumberPicker.OnValueChangeListener {

    CardView heightCV, weightCV, ageCV;
    //constants
    private String name, height, weight, age;
    private TextView nameTV, heightTV, weightTV, ageTV;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //initializing name ,height ,weight ,age Text view,cardViews
        nameTV = view.findViewById(R.id.profileNameTV);
        heightTV = view.findViewById(R.id.profile_heightTV);
        weightTV = view.findViewById(R.id.profileWeightTV);
        ageTV = view.findViewById(R.id.profileAgeTV);
        heightCV = view.findViewById(R.id.profileHeightCV);
        weightCV = view.findViewById(R.id.profileWeightCV);
        ageCV = view.findViewById(R.id.profileAgeCV);

        //setting onClickListener on cardView to show NumberPicker DialogFragment.
        heightCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker(v);
            }
        });
        weightCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker(v);
            }
        });
        ageCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker(v);
            }
        });

        //logic for checking null exception
        if (MainActivity.isFirstRun) {
            Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
        } else {
            //gettign values from shared preferences
            name = UserInfo.getDefaults(UserInfo.Name, getContext());
            height = UserInfo.getDefaults(UserInfo.HEIGHT, getContext());
            weight = UserInfo.getDefaults(UserInfo.WEIGHT, getContext());
            age = UserInfo.getDefaults(UserInfo.AGE, getContext());
            //setting text
            nameTV.setText(name);
            heightTV.setText(height);
            weightTV.setText(weight);
            ageTV.setText(age);
        }

        return view;
    }


    //Number Picker onValueChange
    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        Toast.makeText(getContext(), "selected number " + numberPicker.getValue(), Toast.LENGTH_SHORT).show();
    }

    //showNumberPicker
    public void showNumberPicker(View view) {
        NumberDialogFragment newFragment = new NumberDialogFragment();
        newFragment.setValueChangeListener(this);
        newFragment.show(getFragmentManager(), "time picker");
    }
}

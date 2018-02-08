package com.example.cas.stepcounter;


import android.app.Fragment;
import android.os.Bundle;
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

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
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
            Toast.makeText(getActivity().getBaseContext(), "Empty", Toast.LENGTH_SHORT).show();
        } else {
            //getting values from shared preferences
            getTextToLayouts();
            //setting text
            setTextToLayouts();
        }
        return view;
    }

    //Number Picker onValueChange
    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        Toast.makeText(getActivity().getBaseContext(), "selected number " + numberPicker.getValue(), Toast.LENGTH_SHORT).show();
    }

    //showNumberPicker
    public void showNumberPicker(View view) {
        NumberDialogFragment newFragment = new NumberDialogFragment();
        newFragment.setValueChangeListener(this);
    }

    public void setTextToLayouts() {
        nameTV.setText(name);
        heightTV.setText(height);
        weightTV.setText(weight);
        ageTV.setText(age);
    }

    public void getTextToLayouts() {
        name = UserInfo.getDefaults(UserInfo.Name, getActivity().getBaseContext());
        height = UserInfo.getDefaults(UserInfo.HEIGHT, getActivity().getBaseContext());
        weight = UserInfo.getDefaults(UserInfo.WEIGHT, getActivity().getBaseContext());
        age = UserInfo.getDefaults(UserInfo.AGE, getActivity().getBaseContext());
    }
}

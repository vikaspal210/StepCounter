package com.example.cas.stepcounter;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;


public class NumberDialogFragment extends DialogFragment {
    //constants
    private NumberPicker.OnValueChangeListener valueChangeListener;

    public NumberDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //container design
        //Horizontal LinerLayout
        LinearLayout linLayoutH =
                new LinearLayout(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        linLayoutH.setLayoutParams(params);

        //vertical Parent LinearLayout
        LinearLayout linearLayoutV = new LinearLayout(getActivity());
        linearLayoutV.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramsV = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        paramsV.gravity = Gravity.CENTER;
        linearLayoutV.setLayoutParams(paramsV);

        linearLayoutV.addView(linLayoutH);

        //number picker
        final NumberPicker np =
                new NumberPicker(getActivity());
        np.setMaxValue(1000);
        np.setMinValue(1);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.rightMargin = 6;
        np.setLayoutParams(params);
        linLayoutH.addView(np);

        final NumberPicker npUnit =
                new NumberPicker(getActivity());
        npUnit.setMaxValue(2);
        npUnit.setMinValue(1);
        npUnit.setDisplayedValues(new String[]{"kg", "lb"});
        params.gravity = Gravity.CENTER_HORIZONTAL;
        npUnit.setLayoutParams(params);
        linLayoutH.addView(npUnit);


        //Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Weight");
        builder.setNeutralButton("Imperial(Kg)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserInfo.setDefaults(UserInfo.WEIGHT, Integer.toString(np.getValue()), getContext());

                valueChangeListener.onValueChange(npUnit,
                        npUnit.getValue(), npUnit.getValue());
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setView(linearLayoutV);
        return builder.create();
    }

    //Number picker value change listener
    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }
    //setValueChangeListener
    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }


}

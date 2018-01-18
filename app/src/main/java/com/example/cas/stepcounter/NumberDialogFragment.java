package com.example.cas.stepcounter;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberDialogFragment extends DialogFragment {
    //constants
    private NumberPicker.OnValueChangeListener valueChangeListener;

    public NumberDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //container design
        LinearLayout linLayoutH =
                new LinearLayout(getActivity());
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        linLayoutH.setLayoutParams(params);

        //number picker
        final NumberPicker np =
                new NumberPicker(getActivity());
        np.setMaxValue(1000);
        np.setMinValue(1);
        linLayoutH.addView(np);

        final NumberPicker npUnit =
                new NumberPicker(getActivity());
        npUnit.setDisplayedValues(new String[]{"kg", "lb"});
        linLayoutH.addView(npUnit);

        //Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Value");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(np,
                        np.getValue(), np.getValue());
                valueChangeListener.onValueChange(npUnit,
                        npUnit.getValue(), npUnit.getValue());
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setView(linLayoutH);
        return builder.create();
    }

    //Number picker value change listener
    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

}

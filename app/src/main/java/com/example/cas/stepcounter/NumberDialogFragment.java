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


    private DialogInterface.OnDismissListener onDismissListener;
    //constants
    private NumberPicker.OnValueChangeListener valueChangeListener;

    //EMPTY Constructor
    public NumberDialogFragment() {
        // Required empty public constructor
    }

    //setOnDismissListener method
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    //Override onDismiss method
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Constants
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final NumberPicker npUnit =
                new NumberPicker(getActivity());

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

        //Switch for Height ,Weight &Age
        switch (ProfileFragment.counter) {
            case 0:
                //height
                np.setMaxValue(300);
                np.setMinValue(30);
                np.setValue(165);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.rightMargin = 6;
                np.setLayoutParams(params);
                linLayoutH.addView(np);

                npUnit.setMaxValue(2);
                npUnit.setMinValue(1);
                npUnit.setDisplayedValues(new String[]{"kg", "lb"});
                params.gravity = Gravity.CENTER_HORIZONTAL;
                npUnit.setLayoutParams(params);
                linLayoutH.addView(npUnit);

                //Alert Dialog
                builder.setTitle("Height");
                builder.setNeutralButton("Imperial(Feet)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserInfo.setDefaults(UserInfo.HEIGHT, Integer.toString(np.getValue()), getActivity().getBaseContext());

                        valueChangeListener.onValueChange(npUnit,
                                npUnit.getValue(), npUnit.getValue());
                    }
                });


                break;
            case 1:
                // weight
                np.setMaxValue(1000);
                np.setMinValue(30);
                np.setValue(65);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.rightMargin = 6;
                np.setLayoutParams(params);
                linLayoutH.addView(np);

                npUnit.setMaxValue(2);
                npUnit.setMinValue(1);
                npUnit.setDisplayedValues(new String[]{"kg", "lb"});
                params.gravity = Gravity.CENTER_HORIZONTAL;
                npUnit.setLayoutParams(params);
                linLayoutH.addView(npUnit);

                //Alert Dialog
                builder.setTitle("Weight");
                builder.setNeutralButton("Imperial(Kg)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserInfo.setDefaults(UserInfo.WEIGHT, Integer.toString(np.getValue()), getActivity().getBaseContext());

                        valueChangeListener.onValueChange(npUnit,
                                npUnit.getValue(), npUnit.getValue());
                    }
                });
                break;
            case 2:
                // age
                np.setMaxValue(150);
                np.setMinValue(1);
                np.setValue(25);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.rightMargin = 6;
                np.setLayoutParams(params);
                linLayoutH.addView(np);

                //Alert Dialog
                builder.setTitle("Age");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserInfo.setDefaults(UserInfo.AGE, Integer.toString(np.getValue()), getActivity().getBaseContext());
                    }
                });
                break;
        }//Switch END

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

package com.example.cas.stepcounter;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;


public class NumberDialogFragment extends DialogFragment {


    private DialogInterface.OnDismissListener onDismissListener;
    //constants
    private boolean toggleKg = true, toggleCm = true;
    private NumberPicker.OnValueChangeListener valueChangeListener;

    //EMPTY Constructor
    public NumberDialogFragment() {
        // Required empty public constructor
    }

    //setOnDismissListener method
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    //Override onDismiss method to refresh fragment after any change.
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
        //textView
        final TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        para.leftMargin = 48;
        textView.setLayoutParams(para);

        linearLayoutV.addView(textView);
        //number picker
        final NumberPicker np =
                new NumberPicker(getActivity());

        //Switch for Height ,Weight &Age
        switch (ProfileFragment.counter) {
            case 0:
                textView.setText("Centimeters");
                //UNIT TextView on click listener
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (toggleCm) {
                            toggleCm = false;
                            textView.setText("Feet");
                        } else {
                            toggleCm = true;
                            textView.setText("Centimeters");
                        }
                    }
                });
                //height
                np.setMaxValue(300);
                np.setMinValue(30);
                np.setValue(165);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.rightMargin = 6;
                np.setLayoutParams(params);
                linLayoutH.addView(np);

                //Alert Dialog
                builder.setTitle("Height");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserInfo.setDefaults(UserInfo.HEIGHT, Integer.toString(np.getValue()), getActivity().getBaseContext());
                    }
                });


                break;
            case 1:
                textView.setText("Kilogram");

                //UNIT TextView on click listener
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (toggleKg) {
                            toggleKg = false;
                            textView.setText("Lb");
                        } else {
                            toggleKg = true;
                            textView.setText("Kilogram");
                        }

                    }
                });
                // weight
                np.setMaxValue(1000);
                np.setMinValue(30);
                np.setValue(65);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.rightMargin = 6;
                np.setLayoutParams(params);
                linLayoutH.addView(np);
                //Alert Dialog
                builder.setTitle("Weight");
                builder.setIcon(R.drawable.avatar);
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

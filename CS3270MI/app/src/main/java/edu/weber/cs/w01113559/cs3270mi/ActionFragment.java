package edu.weber.cs.w01113559.cs3270mi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionFragment extends Fragment {

    private View root;
    private actionInterface mCallback;
    private int sex;
    private SharedPreferences prefs;

    interface actionInterface {
        /**
         * Passes the calculation info to the activity
         * Passes info to the activity.
         * @param weight BigDecimal: Weight in pounds.
         * @param height BigDecimal: Height in inches.
         * @param age BigInteger: Age
         * @param sex int: 0- Female 1- Male
         */
        void passInfo(BigDecimal weight, BigDecimal height, BigInteger age, int sex);
    }

    public ActionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (actionInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement the actionInterface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_action, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        prefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        root.findViewById(R.id.inputAge);

        RadioButton radio_female = root.findViewById(R.id.radio_female);
        RadioButton radio_male = root.findViewById(R.id.radio_male);

        radio_female.setOnClickListener(onRadioButtonClicked);
        radio_male.setOnClickListener(onRadioButtonClicked);

        TextInputEditText inputBoxWeight = root.findViewById(R.id.inputWeight);
        TextInputEditText inputBoxHeight = root.findViewById(R.id.inputHeight);
        TextInputEditText inputBoxAge = root.findViewById(R.id.inputAge);

        // Get Values from shared preferences
        float fWeight = prefs.getFloat("Weight", 120);
        float fHeight = prefs.getFloat("Height", 66);
        int iAge = prefs.getInt("Age", 24);
        sex = prefs.getInt("Sex", 0);

        inputBoxWeight.setText(Float.toString(fWeight));
        inputBoxHeight.setText(Float.toString(fHeight));
        inputBoxAge.setText(Integer.toString(iAge));

        if (sex == 0) {
            radio_male.setChecked(true);
        } else {
            radio_female.setChecked(true);
        }


        root.findViewById(R.id.btnCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean failFlag = false;

                // Test Weight
                if (inputBoxWeight.getText().toString().isEmpty()) {
                    failFlag = true;
                    inputBoxWeight.setError("Must Supply Weight");
                } else if (new BigDecimal(inputBoxWeight.getText().toString()).compareTo(new BigDecimal(0)) != 1) {    // Invalid Weight
                    failFlag = true;
                    inputBoxWeight.setError("Invalid Weight");
                }

                // Test Height
                if (inputBoxHeight.getText().toString().isEmpty()) {
                    failFlag = true;
                    inputBoxHeight.setError("Must Supply Height");
                } else if (new BigDecimal(inputBoxHeight.getText().toString()).compareTo(new BigDecimal(6)) != 1) {    // Invalid Height
                    failFlag = true;
                    inputBoxHeight.setError("Invalid Height");
                } else if (new BigDecimal(inputBoxHeight.getText().toString()).compareTo(new BigDecimal(120)) == 1) {    // Invalid Height
                    failFlag = true;
                    inputBoxHeight.setError("Invalid Height");
                }

                // Test Age
                if (inputBoxAge.getText().toString().isEmpty()) {
                    failFlag = true;
                    inputBoxAge.setError("Must Supply Age");
                } else if (new BigInteger(inputBoxAge.getText().toString()).compareTo(new BigInteger("0")) != 1) {    // Too Young
                    failFlag = true;
                    inputBoxAge.setError("Too young");
                } else if (new BigInteger(inputBoxAge.getText().toString()).compareTo(new BigInteger("120")) >= 0) { // Too Old
                    failFlag = true;
                    inputBoxAge.setError("Too Old");
                }
                BigDecimal weight = new BigDecimal(inputBoxWeight.getText().toString());
                BigDecimal height = new BigDecimal(inputBoxHeight.getText().toString());
                BigInteger Age = new BigInteger(inputBoxAge.getText().toString());

                if (!failFlag) {
                    mCallback.passInfo(weight, height, Age, sex);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        TextInputEditText inputBoxWeight = root.findViewById(R.id.inputWeight);
        TextInputEditText inputBoxHeight = root.findViewById(R.id.inputHeight);
        TextInputEditText inputBoxAge = root.findViewById(R.id.inputAge);
        TextView tvBMI = root.findViewById(R.id.tvBMI);
        TextView tvBodyFat = root.findViewById(R.id.tvBodyFat);

        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit
                .putFloat("Weight", Float.parseFloat(inputBoxWeight.getText().toString()))
                .putFloat("Height", Float.parseFloat(inputBoxHeight.getText().toString()))
                .putInt("Age", Integer.parseInt(inputBoxAge.getText().toString()))
                .putInt("Sex", sex)
                .apply();
    }

    /**
     * Handles when a radio button is clicked.
     */
    private View.OnClickListener onRadioButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Is the button now checked?
            boolean checked = ((RadioButton) v).isChecked();

            // Check which radio button was clicked
            switch(v.getId()) {
                case R.id.radio_female:
                    if (checked)
                        sex = 1;
                    break;
                case R.id.radio_male:
                    if (checked)
                        sex = 0;
                    break;
            }

        }
    };
}
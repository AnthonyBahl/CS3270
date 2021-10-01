package edu.weber.cs.w01113559.cs3270a5;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeActions extends Fragment {

    private View root;
    private TextView tvCorrectChangeCount;
    private Button btnStartOver;
    private Button btnNewAmount;
    private changeActions mCallBack;
    private SharedPreferences preferences;
    private int iCorrectChangeCount;

    interface changeActions {
        /**
         * Resets the timer and the 'Change so far' values.
         */
        void startOver();
        void newAmount();
    }


    public ChangeActions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_change_actions, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Instantiate Shared Preferences
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        // Instantiate tvCorrectChangeCount
        tvCorrectChangeCount = root.findViewById(R.id.correctChangeCountTextView);

        // Instantiate Buttons
        btnStartOver = root.findViewById(R.id.btn_start_over);
        btnNewAmount = root.findViewById(R.id.btn_new_amount);

        // Create on Click listener for buttons
        btnStartOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.startOver();
            }
        });
        btnNewAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartOver.setEnabled(true);
                mCallBack.newAmount();
            }
        });

        // Set value to Correct Change Count
        setCorrectChangeCount();

    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (changeActions) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException( activity.toString() + " must implement changeActions interface.");
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        // Update Shared Preferences
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt("correct_change_count", iCorrectChangeCount);
        prefsEditor.apply();
    }

    /**
     * Sets the value of Correct Change Count to whatever is in the Shared Preferences, or 0 by default.
     */
    private void setCorrectChangeCount() {

        // Get current value from shared preferences
        int value = preferences.getInt("correct_change_count", 0);

        // Validate value
        if (value < 0) {
            throw new IllegalArgumentException( getActivity().toString() + " 'Correct Change Count' value must be >= 0.");
        } else {
            iCorrectChangeCount = value;
            tvCorrectChangeCount.setText(value + "");
        }
    }

    /**
     * Sets the value of Correct Change Count.
     * @param value int: positive value for correct change count.
     */
    private void setCorrectChangeCount(int value) {
        if (value < 0) {
            throw new IllegalArgumentException( getActivity().toString() + " 'Correct Change Count' value must be >= 0.");
        } else {
            iCorrectChangeCount = value;
            tvCorrectChangeCount.setText(value + "");
        }
    }

    /**
     * Increases the Correct Change Count by 1.
     */
    public void incrementCorrectChangeCount() {
        setCorrectChangeCount(iCorrectChangeCount+1);
    }

    /**
     * Enables and disables the 'Start Over' Button based on value
     * @param value boolean: true - Enabled, false - Disabled
     */
    public void setStartOverButton(boolean value) {
        btnStartOver.setEnabled(value);
    }

    /**
     * Resets the value of CorrectChangeCount to 0.
     */
    public void resetCorrectChangeCount() {
        setCorrectChangeCount(0);
    }
}
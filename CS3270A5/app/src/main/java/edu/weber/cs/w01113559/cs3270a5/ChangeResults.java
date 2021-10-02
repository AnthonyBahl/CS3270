package edu.weber.cs.w01113559.cs3270a5;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeResults extends Fragment {

    // Initialize Variables
    private View root;
    private roundActions mCallback;
    private SharedPreferences preferences;
    private TextView tvChangeToMake;
    private TextView tvChangeSoFar;
    private TextView tvTimeRemaining;
    private NumberFormat nfDollars;
    private NumberFormat nfTimeRemaining;
    private CountDownTimer timer;
    private BigDecimal bdCurrentChangeToMake;
    private BigDecimal bdCurrentChangeSoFar;
    private int iCurrentTimeRemaining;
    /**
     * Determines if there is a round currently active
     */
    private boolean bRoundActive;

    // Interface to be implemented by the activity
    interface roundActions {
        /**
         * Completes the round.
         * @param result int: 1- Time ran out, 2- Went over on change, 3- Successfully made change.
         */
        void roundEnd(int result);
    }

    // Constructor
    public ChangeResults() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        // Verify that the activity is implementing the timerActions interface.
        try {

            mCallback = (roundActions) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException( activity.toString() + " must implement the roundActions interface.");

        }

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_change_results, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get Shared Preferences
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        // Initialize Text View Variables
        tvChangeToMake = root.findViewById(R.id.changeToMakeTextView);
        tvChangeSoFar = root.findViewById(R.id.changeSoFarTextView);
        tvTimeRemaining = root.findViewById(R.id.timeRemainingTextView);

        // Assign Text Watcher for 'Change so Far'
        tvChangeSoFar.addTextChangedListener(changeSoFarWatcher);

        // Initialize Number Formats
        nfDollars = NumberFormat.getCurrencyInstance(Locale.US);
        nfDollars.setMaximumIntegerDigits(5);
        nfDollars.setMaximumFractionDigits(2);
        nfDollars.setMinimumIntegerDigits(1);
        nfDollars.setMinimumFractionDigits(2);
        nfDollars.setRoundingMode(RoundingMode.HALF_UP);

        nfTimeRemaining = NumberFormat.getIntegerInstance(Locale.US);
        nfTimeRemaining.setMaximumIntegerDigits(3);
        nfTimeRemaining.setMaximumFractionDigits(0);
        nfTimeRemaining.setMinimumIntegerDigits(0);
        nfTimeRemaining.setMinimumFractionDigits(0);
        nfTimeRemaining.setRoundingMode(RoundingMode.HALF_UP);

        // Set Values to Text Views based on shared preferences
        updateChangeToMake();
        updateChangeSoFar();
        updateTimeRemaining();

    }

    @Override
    public void onPause() {
        super.onPause();


        // Update Shared Preferences
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putFloat("change_to_make", bdCurrentChangeToMake.floatValue());
        prefsEditor.putFloat("change_so_far", bdCurrentChangeSoFar.floatValue());
        prefsEditor.putInt("time_remaining", iCurrentTimeRemaining);
        prefsEditor.apply();

    }

    /**
     * Watches for updates to the 'Change So Far' field to see if correct change is made.
     */
    private final TextWatcher changeSoFarWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            // Check to see if the user has matched or gone over the 'Change to Make' amount.
            switch (checkValues()) {
                case 0:
                    // User has successfully matched the amounts.
                    endRound(3);
                    break;
                case 1:
                    // User has gone over the 'Change to Make' amount.
                    endRound(2);
                    break;
            }
        }
    };

    /**
     * Updates the "Change to Make" field to whatever is saved in Shared Preferences. Default to 0
     */
    private void updateChangeToMake() {

        // Get current value from shared preferences
        float value = preferences.getFloat("change_to_make", 0);

        // Validate value
        if (value < 0) {

            throw new IllegalArgumentException( getActivity().toString() + " 'Change to Make' value must be >= 0.");

        } else {

            bdCurrentChangeToMake = new BigDecimal(value);

            // Update Text View
            tvChangeToMake.setText(nfDollars.format(value));

        }
    }

    /**
     * Updates the "Change to Make" field.
     * @param value value to put into the "change to make" field.
     */
    private void updateChangeToMake(BigDecimal value) {

        if (value.compareTo(new BigDecimal(0)) < 0) {

            throw new IllegalArgumentException( getActivity().toString() + " 'Change to Make' value must be >= 0.");

        } else {

            bdCurrentChangeToMake = value;

            tvChangeToMake.setText(nfDollars.format(value.floatValue()));

        }
    }

    /**
     * Generates a random number for the 'Change to Make Field' between 0 and the Maximum value setting ( Default 50 ).
     * @return BigDecimal: Random value between 0 and the maximum value.
     */
    private BigDecimal getRandomChangeToMake(){

        // Generate random number between 0 and 1
        BigDecimal bdRandom = BigDecimal.valueOf(Math.random());

        // Get Max Value From Settings
        BigDecimal bdMax = new BigDecimal(preferences.getString("maximum_value", "50"));

        // Generate Random Change Value
        return bdRandom.multiply(bdMax);

    }

    /**
     * Updates the "Change so far" field to whatever is saved in Shared Preferences. Default to 0
     */
    private void updateChangeSoFar() {

        // Get current value from shared preferences
        float value = preferences.getFloat("change_so_far", 0);

        // Validate value
        if (value < 0) {

            throw new IllegalArgumentException( getActivity().toString() + " 'Change so Far' value must be >= 0.");

        } else {

            bdCurrentChangeSoFar = new BigDecimal(value);

            // Update Text View
            tvChangeSoFar.setText(nfDollars.format(value));

        }
    }

    /**
     * Updates the "Change so far" field.
     * @param value value to put into the "Change so far" field.
     */
    public void updateChangeSoFar(BigDecimal value) {

        // Validate value
        if (value.compareTo(new BigDecimal(0)) < 0) {

            throw new IllegalArgumentException( getActivity().toString() + " 'Change to Make' value must be >= 0.");

        } else {

            bdCurrentChangeSoFar = value;

            // Update Text View
            tvChangeSoFar.setText(nfDollars.format(value.floatValue()));

        }
    }

    /**
     * Updates the "Time Remaining" field to whatever is saved in Shared Preferences. Default to 30
     */
    private void updateTimeRemaining() {

        // Get current value from shared preferences
        int value = preferences.getInt("time_remaining", 30);

        // Validate value
        if (value < 0) {

            throw new IllegalArgumentException( getActivity().toString() + " 'Time Remaining' value must be >= 0.");

        } else {

            iCurrentTimeRemaining = value;

            // Update Text View
            tvTimeRemaining.setText(nfTimeRemaining.format(value));

        }
    }

    /**
     * Updates the "Time Remaining" field.
     * @param value BigDecimal - value to put into the "Time Remaining" field.
     */
    private void updateTimeRemaining(int value) {

        // Validate value
        if (value < 0) {

            throw new IllegalArgumentException( getActivity().toString() + " 'Time Remaining' value must be >= 0.");

        } else {

            iCurrentTimeRemaining = value;

            // Update Text View
            tvTimeRemaining.setText(nfTimeRemaining.format(value));

        }
    }

    /**
     * Creates and starts a new CountDownTimer and stores it in 'timer'.
     */
    private void startTimer() {

        // Cancel Timer if there is already a timer active
        if ( timer != null ) {
            timer.cancel();
        }

        // Create new timer
        timer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                // Update the Time Remaining Text Field
                updateTimeRemaining((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {

                // Update the Time Remaining Text Field
                updateTimeRemaining(0);

                // Compare the values
                switch (checkValues()){
                    case -1:
                        // User was under the 'Change to Make' Value'
                        endRound(1);
                    case 0:
                        // User matched the 'Change to Make' value.
                        endRound(3);
                        break;
                    case 1:
                        // User went over the 'Change to Make' value.
                        endRound(2);
                        break;
                }

            }
        }.start();
    }

    /**
     * Checks if the value of 'Change so far' matches 'Change to Make'.
     * @return int: -1 if 'Change so far' is below 'Change to Make', 0 if they match, and 1 if 'Change so far' is above 'Change to Make'
     */
    private int checkValues() {
        // Make sure 'Change to Make' is > 0 (there's an active round)
        if (bdCurrentChangeToMake.equals(new BigDecimal(0))) {
            return -1;
        }

        // Round Variables to 2 decimal places
        BigDecimal target = bdCurrentChangeToMake.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal current = bdCurrentChangeSoFar.setScale(2, BigDecimal.ROUND_HALF_UP);

        return current.compareTo(target);
    }

    /**
     * Clears current values for 'Change to Make' and 'Change so far', generates a new random value for 'Change to Make', un-locks buttons, and starts the timer.
     */
    public void startRound() {

        // Generate new Random value for "Change to Make"
        BigDecimal randomBigDecimal = getRandomChangeToMake();
        updateChangeToMake(randomBigDecimal);

        // Clear out values for "Change so far"
        updateChangeSoFar(new BigDecimal(0));

        // Start timer
        startTimer();

        // Activate round
        bRoundActive = true;

    }

    /**
     * Clears current values for 'Change so far' and resets timer, but does not change 'Change to Make'.
     */
    public void resetRound() {

        // Clear out values for "Change so far"
        updateChangeSoFar(new BigDecimal(0));

        // Start timer
        startTimer();

        // Activate round
        bRoundActive = true;
    }

    /**
     * Ends the active round if there is one
     */
    public void endRound() {
        endRound(0);
    }

    /**
     * Ends the active round
     * @param result int: 0- Manual Round End, 1- Time ran out, 2- Went over on change, 3- Successfully made change
     */
    private void endRound(int result) {
        if (bRoundActive) {

            // Deactivate round
            bRoundActive = false;

            // Turn off Timer
            timer.cancel();

            // Check to see if round manually ended
            if (result > 0) {
                // Call Activity to end round
                mCallback.roundEnd(result);
            }
        }
    }

    /**
     * Adds change to the 'Change so Far' value
     * @param change BigDecimal: change to add to the 'Change so Far' value.
     */
    public void addChange(BigDecimal change) {

        // Make sure there is an active round
        if (bRoundActive) {

            // Update Change Amount
            updateChangeSoFar(bdCurrentChangeSoFar.add(change));

        }
    }
}
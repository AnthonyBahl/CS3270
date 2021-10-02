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
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class maxChangeFragment extends Fragment {

    private View root;
    private SharedPreferences preferences;
    private NumberFormat nfMaxValue;
    private TextView tvMaxChange;
    private Button btnSave;
    private maxChangeInterface mCallback;

    interface maxChangeInterface {
        void finishMaxChangeScreen();
    }

    public maxChangeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        // Verify that the activity is implementing the timerActions interface.
        try {

            mCallback = (maxChangeInterface) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException( activity.toString() + " must implement the maxChangeInterface interface.");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_max_change, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Instantiate Shared Preferences
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        // Instantiate Max Change Text View
        tvMaxChange = root.findViewById(R.id.tvMaxChange);

        // Set up Number Format
        nfMaxValue = NumberFormat.getIntegerInstance(Locale.US);
        nfMaxValue.setMaximumFractionDigits(0);
        nfMaxValue.setMinimumFractionDigits(0);
        nfMaxValue.setRoundingMode(RoundingMode.HALF_UP);

        // Get Current Max Change from Settings
        BigDecimal currentMaxChange = getCurrentMaxChange();

        // Update Max Change Textbox
        setMaxChangeTextBox(currentMaxChange);

        // Instantiate Save Button
        btnSave = root.findViewById(R.id.action_save_max_change);

        // Set On Click Listener for the Save Button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save new value to preferences
                preferences.edit()
                        .putString("maximum_value", tvMaxChange.getText().toString())
                        .apply();

                // Close Screen
                mCallback.finishMaxChangeScreen();

            }
        });
    }

    /**
     * Get's the current Max Change Value, defaults to 50
     * @return BigDecimal: Max Change
     */
    private BigDecimal getCurrentMaxChange() {

        // Get temp_maximum_value (for if the view was re-created while in max-change screen)
        String sMaxChange = preferences.getString("temp_maximum_value", "-1");

        // If no temp_maximum_value, get the last saved value.
        if (sMaxChange == "-1") {

            sMaxChange = preferences.getString("maximum_value", "50");

        }
        return new BigDecimal(sMaxChange);
    }

    /**
     * Sets the value of the 'Max Change' Text Box.
     * @param currentMaxChange BigDecimal: Max Change Value.
     * @return boolean: true- success, false- failure.
     */
    private boolean setMaxChangeTextBox(BigDecimal currentMaxChange) {
        // Create return object
        Object[] objReturn = new Object[2];

        // Validate currentMaxChange is > 0
        if (currentMaxChange.compareTo(new BigDecimal(0)) > 0) {
            tvMaxChange.setText(nfMaxValue.format(currentMaxChange.doubleValue()));
            return true;
        } else {
            // currentMaxChange <= 0
            objReturn[0] = new Boolean(false);
            objReturn[1] = new String("Value must be > 0");
            return false;
        }
    }
}
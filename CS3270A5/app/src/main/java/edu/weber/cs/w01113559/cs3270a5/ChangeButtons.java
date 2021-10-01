package edu.weber.cs.w01113559.cs3270a5;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeButtons extends Fragment {

    private View root;
    private Button btn50;
    private Button btn20;
    private Button btn10;
    private Button btn5;
    private Button btn1;
    private Button btn050;
    private Button btn025;
    private Button btn010;
    private Button btn005;
    private Button btn001;
    private onButtonPress mCallBack;

    interface onButtonPress {
        /**
         * Passes the value of the button pressed
         * @param value BigDecimal: amount of change to be added.
         */
        void onButtonPress(BigDecimal value);
    }

    public ChangeButtons() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_change_buttons, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        btn50 = root.findViewById(R.id.btn_50);
        btn20 = root.findViewById(R.id.btn_20);
        btn10 = root.findViewById(R.id.btn_10);
        btn5 = root.findViewById(R.id.btn_05);
        btn1 = root.findViewById(R.id.btn_01);
        btn050 = root.findViewById(R.id.btn_0_50);
        btn025 = root.findViewById(R.id.btn_0_25);
        btn010 = root.findViewById(R.id.btn_0_10);
        btn005 = root.findViewById(R.id.btn_0_05);
        btn001 = root.findViewById(R.id.btn_0_01);

        // Set Button Tags
        btn50.setTag("50");
        btn20.setTag("20");
        btn10.setTag("10");
        btn5.setTag("5");
        btn1.setTag("1");
        btn050.setTag("0.50");
        btn025.setTag("0.25");
        btn010.setTag("0.10");
        btn005.setTag("0.05");
        btn001.setTag("0.01");

        // Attach on Button Listener to each button
        btn50.setOnClickListener(buttonListener);
        btn20.setOnClickListener(buttonListener);
        btn10.setOnClickListener(buttonListener);
        btn5.setOnClickListener(buttonListener);
        btn1.setOnClickListener(buttonListener);
        btn050.setOnClickListener(buttonListener);
        btn025.setOnClickListener(buttonListener);
        btn010.setOnClickListener(buttonListener);
        btn005.setOnClickListener(buttonListener);
        btn001.setOnClickListener(buttonListener);

    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        // Verify that they implement the interface
        try {
            mCallBack = (onButtonPress) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException( activity.toString() + " must implement onButtonPress interface.");
        }

    }

    // Button listener for all buttons
    private final View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Get Value from tag
            String value = v.getTag().toString();
            // Call onButtonPress
            mCallBack.onButtonPress(new BigDecimal(value));
        }
    };
}
package edu.weber.cs.w01113559.cs3270a5;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeButtons extends Fragment {

    private View root;
    // ToDo: create variables for each button
    // ToDo: create mCallback

    // ToDo: Create interface

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

        // ToDo: Instantiate variables for buttons

        // ToDo: Attach on Button Listener to each button

    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        // ToDo: Verify that they implement the interface

    }

    // ToDo: Create on Button listener for all buttons (one function)

    // ToDo: Enable Buttons function

    // ToDo: Disable Buttons function
}
package edu.weber.cs.w01113559.cs3270a5;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeActions extends Fragment {

    private View root;
    private TextView tvCorrectChangeCount;
    // ToDo: Create variables for Buttons
    // ToDo: Create interface variable

    // ToDo: Create interface


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

        // ToDo: Instantiate tvCorrectChangeCount

        // ToDo: Instantiate Buttons

        // ToDo: Create on Click listener for buttons

    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        // ToDo: Verify that interface is implemented

    }

    // ToDo: Create setCorrectChangeCount()

    // ToDo: Create setCorrectChangeCount(value)
}
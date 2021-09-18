package edu.weber.cs.w01113559.fragmentcommunication;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonFragment extends Fragment {

    // Create a variable to hold the base view.
    private View root;
    private int count = 0;
    private onButtonListener mCallback;

    // Declare the interface
    public interface onButtonListener{
        void fragButtonPressed(int count);
    }

    public ButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_button, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Find the button that we created in the XML
        Button btn = root.findViewById(R.id.btn);

        // Create a Listener for click actions
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                mCallback.fragButtonPressed(count);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (onButtonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " Must implement onButtonListener");
        }

    }
}
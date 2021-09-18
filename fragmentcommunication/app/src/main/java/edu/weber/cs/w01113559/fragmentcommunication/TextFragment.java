package edu.weber.cs.w01113559.fragmentcommunication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {

    // Create a variable to hold the base view.
    private View root;
    private TextView tv;

    public TextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        tv = root.findViewById(R.id.FragTv);
        updateText(0);

    }

    public void updateText(int count){
        tv.setText("You've pressed the button " + count + " times.");

        Toast toast = Toast.makeText(getActivity(), "updated", Toast.LENGTH_SHORT);
        toast.show();
    }
}
package edu.weber.cs.w01113559.mypracticecalculator;

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
public class buttonsFragment extends Fragment {

    private View root;
    private buttonFragInterface mCallBack;

    interface buttonFragInterface {
        void passButtonTag(String tag);
    }

    public buttonsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        // Make sure that the activity implemented the interface
        try {
            mCallBack = (buttonFragInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement the buttonFragInterface interface");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_buttons, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Add Button Listeners
        root.findViewById(R.id.btn_C).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_percent).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_percent).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_divide).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_7).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_8).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_9).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_multiply).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_4).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_5).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_6).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_minus).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_1).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_2).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_3).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_add).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_pos_neg).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_0).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_decimal).setOnClickListener(buttonListener);
        root.findViewById(R.id.btn_equals).setOnClickListener(buttonListener);
    }

    /**
     * Listener for almost all buttons
     */
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String sTag = v.getTag().toString();
            mCallBack.passButtonTag(sTag);
        }
    };
}
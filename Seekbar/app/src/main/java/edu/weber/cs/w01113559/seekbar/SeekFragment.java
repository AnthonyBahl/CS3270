package edu.weber.cs.w01113559.seekbar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeekFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnSeekChanged mCallback;

    interface OnSeekChanged{
        /**
         * Passes the value new value of the seek bar position.
         * @param value New seek bar position value.
         */
        void onSeekUpdate(int value);
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SeekBar seek;
    private View root;

    public SeekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeekFragment newInstance(String param1, String param2) {
        SeekFragment fragment = new SeekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        seek = root.findViewById(R.id.seekBar);

        // Get a copy of the Activity's preferences
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        int position = prefs.getInt("seek_progress", 15);
        seek.setProgress(position);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCallback.onSeekUpdate(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    // Using the depricated version because the new version is not fully backwards compatible.
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        /**
         * Make sure that the activity is implementing our interface. If not then crash the program.
         */
        try {
            mCallback = (OnSeekChanged) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException( activity.toString() + "must implement the onSeekChanged interface.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_seek, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        /**
         * Editor for the prefs SharedPreferences file.
         */
        SharedPreferences.Editor prefsEditor = prefs.edit();
        // Set the value of "seek_position" to the current position
        prefsEditor.putInt("seek_position", seek.getProgress());
        // save changes to the prefs SharedPreference file.
        prefsEditor.apply();
    }
}
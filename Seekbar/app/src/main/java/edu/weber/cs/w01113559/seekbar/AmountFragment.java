package edu.weber.cs.w01113559.seekbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AmountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View root;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView amount;

    public AmountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AmountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AmountFragment newInstance(String param1, String param2) {
        AmountFragment fragment = new AmountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onResume() {
        super.onResume();
        amount = root.findViewById(R.id.txtAmount);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        int val = prefs.getInt("seek_progress", 15);
        amount.setText(val+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_amount, container, false);
    }

    public void setAmount(int value) {
        Log.d("test", "Seek Bar Value: " + value);
        // Concatinating a string onto the end is an easy way to cast the int to a string.
        amount.setText(value+"");
    }
}
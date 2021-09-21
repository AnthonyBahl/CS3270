package edu.weber.cs.w01113559.cs3270a4;

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
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaxFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SeekBar seek;
    private View root;
    private onSeekChanged mCallback;
    private NumberFormat nfPercentage;
    private TextView tvTaxRate;

    private String mParam1;
    private String mParam2;

    interface onSeekChanged{
        /**
         * Passes the value new value of the seek bar position.
         * @param value New seek bar position value.
         */
        void onSeekUpdate(BigDecimal value);
    }

    public TaxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaxFragment newInstance(String param1, String param2) {
        TaxFragment fragment = new TaxFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_tax, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // add seekBar1 to it's variable
        seek = root.findViewById(R.id.seekBar1);

        // Format for Percentages that always have 2 decimal places
        nfPercentage = NumberFormat.getPercentInstance(Locale.US);
        nfPercentage.setMaximumIntegerDigits(2);
        nfPercentage.setMinimumIntegerDigits(2);
        nfPercentage.setMaximumFractionDigits(2);
        nfPercentage.setMinimumFractionDigits(2);

        // Get the TextView that holds the tax rate
        tvTaxRate =root.findViewById(R.id.txtviewTaxRate);

        // get the seek position if it was saved, otherwise default to 0
        int position = getActivity().getPreferences(Context.MODE_PRIVATE).getInt("seek_progress", 0);
        seek.setProgress(position);
        setTaxRateTextView(convert_ToTaxRate(position));

        // Create listener for the seek bar
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Convert position to tax rate
                BigDecimal taxRate = convert_ToTaxRate(progress);

                // Update text view
                setTaxRateTextView(taxRate);

                // Send to Activity
                mCallback.onSeekUpdate(taxRate);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Updates the tax rate in the text view on screen.
     * @param taxRate
     */
    private void setTaxRateTextView(BigDecimal taxRate) {
        tvTaxRate.setText(nfPercentage.format(taxRate.doubleValue()));
    }

    /**
     * Converts the seek bar progress to the tax rate (betweeen 0 and 25%)
     * @param progress int Progress of seek bar (between 0 and 100)
     * @return BigDecimal Equivilant Tax Rate (between 1 and 1.25)
     */
    private BigDecimal convert_ToTaxRate(int progress) {

        BigDecimal bdProgress = new BigDecimal(progress);
        BigDecimal bdMaxTaxRate = new BigDecimal(.25);
        BigDecimal bdMaxProgress = new BigDecimal(100);

        // Use Ratios to compute the equivilant tax rate based on seekbar progression.
        return bdProgress.multiply(bdMaxTaxRate).divide(bdMaxProgress);
    }

    /**
     * Happens right after something changes on the view, before onStop, onDestroyView, and before we create the new version of view.
     */
    @Override
    public void onPause() {
        super.onPause();

        // Save Seek Bar Position
        getActivity().getPreferences(Context.MODE_PRIVATE)
                .edit()
                .putInt("seek_position", seek.getProgress())
                .apply();
    }

    /**
     * Very first thing to happen in the Fragment lifestyle
     * @param activity Basically the main activity.
     */
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        /**
         * Make sure that the activity is implementing our interface. If not then crash the program.
         */
        try {
            mCallback = (onSeekChanged) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException( activity.toString() + "must implement the onSeekChanged interface.");
        }
    }

    public void updateTaxAmount(BigDecimal ItemCost, BigDecimal TaxRate) {

        //ToDo: Calculate Tax Amount

        //ToDo: Update Tax Amount Field
    }
}
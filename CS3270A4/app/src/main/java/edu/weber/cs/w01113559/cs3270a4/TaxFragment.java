package edu.weber.cs.w01113559.cs3270a4;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class TaxFragment extends Fragment {

    private SeekBar seek;
    private View root;
    private onSeekChanged mCallback;
    private NumberFormat nfPercentage;
    private NumberFormat nfDollars;
    private TextView tvTaxRate;
    private TextView tvTaxAmount;
    private float currentTaxRate;
    private float currentTaxAmount;

    /**
     *
     * @return Current tax rate. -1 for failure.
     */
    public BigDecimal getTaxRate() {
        if (seek != null) {
            return convert_ToTaxRate(seek.getProgress());
        } else {
            return new BigDecimal(-1);
        }
    }

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

        // Format for US Dollars
        nfDollars = NumberFormat.getCurrencyInstance(Locale.US);
        nfDollars.setMaximumFractionDigits(2);
        nfDollars.setMinimumFractionDigits(2);

        // Get the TextView that holds the tax rate
        tvTaxRate =root.findViewById(R.id.txtviewTaxRate);
        tvTaxAmount = root.findViewById(R.id.txtviewTaxAmount);

        // get the seek position if it was saved, otherwise default to 0
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        int position = prefs.getInt("seek_position", 0);
        seek.setProgress(position);
        setTaxRateTextView(convert_ToTaxRate(position));

        float taxAmount = prefs.getFloat("tax_amount", 0);
        setTaxAmountTextView(new BigDecimal(taxAmount));

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
     * @param taxRate BigDecimal - Tax Rate.
     */
    private void setTaxRateTextView(BigDecimal taxRate) {
        currentTaxRate = taxRate.floatValue();
        tvTaxRate.setText(nfPercentage.format(taxRate.doubleValue()));
    }

    /**
     * Updates the tax amount in the text view on screen.
     * @param taxAmount BigDecimal tax Amount
     */
    private void setTaxAmountTextView(BigDecimal taxAmount) {
        currentTaxAmount = taxAmount.floatValue();
        tvTaxAmount.setText(nfDollars.format(taxAmount.doubleValue()));
    }

    /**
     * Converts the seek bar progress to the tax rate (betweeen 0 and 25%)
     * @param progress int Progress of seek bar (between 0 and 100)
     * @return BigDecimal Equivilant Tax Rate (between 1 and 1.25)
     */
    private BigDecimal convert_ToTaxRate(int progress) {

        BigDecimal bdProgress = new BigDecimal(progress);
        BigDecimal bdMaxTaxRate = new BigDecimal(".25");
        BigDecimal bdMaxProgress = new BigDecimal(100);

        // Use Ratios to compute the equivilant tax rate based on seekbar progression.
        return bdProgress.multiply(bdMaxTaxRate).divide(bdMaxProgress, RoundingMode.HALF_UP);
    }

    /**
     * Happens right after something changes on the view, before onStop, onDestroyView, and before we create the new version of view.
     */
    @Override
    public void onPause() {
        super.onPause();

        int currentProgress = seek.getProgress();
        // Save Seek Bar Position
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        prefsEditor.putInt("seek_position", currentProgress);
        prefsEditor.putFloat("tax_amount", currentTaxAmount);
        prefsEditor.apply();
    }

    /**
     * Very first thing to happen in the Fragment lifestyle
     * @param activity Basically the main activity.
     */
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);


        // Make sure that the activity is implementing our interface. If not then crash the program.
        try {
            mCallback = (onSeekChanged) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException( activity.toString() + "must implement the onSeekChanged interface.");
        }
    }

    public void updateTaxAmount(BigDecimal itemTotal, BigDecimal taxRate) {

        BigDecimal taxAmount = itemTotal.multiply(taxRate);

        setTaxAmountTextView(taxAmount);
    }
}
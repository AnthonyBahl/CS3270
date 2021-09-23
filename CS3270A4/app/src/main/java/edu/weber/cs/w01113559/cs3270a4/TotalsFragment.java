package edu.weber.cs.w01113559.cs3270a4;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class TotalsFragment extends Fragment {

    private View root;
    private TextView tvTotal;
    private NumberFormat nfDollars;
    private Float currentTotal;

    public TotalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_totals, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        tvTotal =root.findViewById(R.id.txtviewTotalAmount);

        // Format for US Dollars
        nfDollars = NumberFormat.getCurrencyInstance(Locale.US);
        nfDollars.setMaximumFractionDigits(2);
        nfDollars.setMinimumFractionDigits(2);

        // Check to see if there is a default saved in the preferences.
        float totalAmount = getActivity().getPreferences(Context.MODE_PRIVATE).getFloat("total_amount", 0);
        currentTotal = totalAmount;
        tvTotal.setText(nfDollars.format(totalAmount));
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save current value of Total to the Preferences
        getActivity().getPreferences((Context.MODE_PRIVATE))
                .edit()
                .putFloat("total_amount", currentTotal)
                .apply();

    }

    public void updateTotalAmount(BigDecimal itemTotal, BigDecimal taxRate) {
        BigDecimal taxNum = taxRate.add(new BigDecimal(1));
        BigDecimal totalAmount = itemTotal.multiply(taxNum);
        currentTotal = totalAmount.floatValue();
        tvTotal.setText(nfDollars.format(totalAmount));
    }
}
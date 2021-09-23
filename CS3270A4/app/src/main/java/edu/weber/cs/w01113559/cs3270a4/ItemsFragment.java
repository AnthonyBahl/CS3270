package edu.weber.cs.w01113559.cs3270a4;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ItemsFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private View root;
    private TextInputEditText tilItemAmount1;
    private TextInputEditText tilItemAmount2;
    private TextInputEditText tilItemAmount3;
    private TextInputEditText tilItemAmount4;
    private onItemChange mCallBack;
    private NumberFormat nfDollars;

    /**
     * Listener for text values to change.
     */
    private final TextWatcher itemWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mCallBack.onItemUpdate(getItemTotal());
        }
    };

    /**
     *
     * @return Sum of all items.
     */
    public BigDecimal getItemTotal() {

        BigDecimal total = new BigDecimal(0);

        if (!(tilItemAmount1.getText().toString().isEmpty())) {
            total = total.add(new BigDecimal(tilItemAmount1.getText().toString()));
        }

        if (!(tilItemAmount2.getText().toString().isEmpty())) {
            total = total.add(new BigDecimal(tilItemAmount2.getText().toString()));
        }

        if (!(tilItemAmount3.getText().toString().isEmpty())) {
            total = total.add(new BigDecimal(tilItemAmount3.getText().toString()));
        }

        if (!(tilItemAmount4.getText().toString().isEmpty())) {
            total = total.add(new BigDecimal(tilItemAmount4.getText().toString()));
        }

        return total;


    }

    interface onItemChange{
        /**
         * Passes the new Total of all items whenever any item cost changes.
         * @param newTotal BigDecimal - Sum of all item costs.
         */
        void onItemUpdate(BigDecimal newTotal);
    }

    public ItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Number Format for US Dollars
        nfDollars = NumberFormat.getInstance(Locale.US);
        nfDollars.setMaximumFractionDigits(2);
        nfDollars.setMinimumFractionDigits(2);

        // Find and assign Text Input Layouts
        tilItemAmount1 = root.findViewById(R.id.editText_Item1);
        tilItemAmount2 = root.findViewById(R.id.editText_Item2);
        tilItemAmount3 = root.findViewById(R.id.editText_Item3);
        tilItemAmount4 = root.findViewById(R.id.editText_Item4);

        // Create Shared Preferences
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        // Get Values saved in Shared Preferences (if any)
        float item1 = prefs.getFloat("item_amount_1", -1);
        float item2 = prefs.getFloat("item_amount_2", -1);
        float item3 = prefs.getFloat("item_amount_3", -1);
        float item4 = prefs.getFloat("item_amount_4", -1);

        // Set values if any were found
        if (item1 >= 0) {
            tilItemAmount1.setText(nfDollars.format(item1));
        }
        if (item2 >= 0) {
            tilItemAmount2.setText(nfDollars.format(item2));
        }
        if (item3 >= 0) {
            tilItemAmount3.setText(nfDollars.format(item3));
        }
        if (item4 >= 0) {
            tilItemAmount4.setText(nfDollars.format(item4));
        }

        tilItemAmount1.addTextChangedListener(itemWatcher);
        tilItemAmount2.addTextChangedListener(itemWatcher);
        tilItemAmount3.addTextChangedListener(itemWatcher);
        tilItemAmount4.addTextChangedListener(itemWatcher);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        // Make sure that the activity is implementing our interface. If not then crash the program.
        try {
            mCallBack = (onItemChange) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException( activity.toString() + "must implement the onItemChange interface.");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        if (!(tilItemAmount1.getText().toString().isEmpty())) {
            BigDecimal value = new BigDecimal(tilItemAmount1.getText().toString());
            prefsEditor.putFloat("item_amount_1", value.floatValue());
        }

        if (!(tilItemAmount2.getText().toString().isEmpty())) {
            BigDecimal value = new BigDecimal(tilItemAmount2.getText().toString());
            prefsEditor.putFloat("item_amount_2", value.floatValue());
        }

        if (!(tilItemAmount3.getText().toString().isEmpty())) {
            BigDecimal value = new BigDecimal(tilItemAmount3.getText().toString());
            prefsEditor.putFloat("item_amount_3", value.floatValue());
        }

        if (!(tilItemAmount4.getText().toString().isEmpty())) {
            BigDecimal value = new BigDecimal(tilItemAmount4.getText().toString());
            prefsEditor.putFloat("item_amount_4", value.floatValue());
        }

        prefsEditor.apply();

    }
}
package edu.weber.cs.w01113559.cs3270a4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View root;
    private NumberFormat nfDollars;
    private TextInputEditText tilItemAmount1;
    private TextInputEditText tilItemAmount2;
    private TextInputEditText tilItemAmount3;
    private TextInputEditText tilItemAmount4;
    private onItemChange mCallBack;

    interface onItemChange{
        /**
         * Passes the new Total of all items whenever any item cost changes.
         * @param newTotal BigDecimal - Sum of all item costs.
         */
        void onItemUpdate(BigDecimal newTotal);
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemsFragment newInstance(String param1, String param2) {
        ItemsFragment fragment = new ItemsFragment();
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

        // Get Values saved in Shared Preferences (if any)
        float item1 = getActivity().getPreferences(Context.MODE_PRIVATE).getFloat("item_amount_1", -1);
        float item2 = getActivity().getPreferences(Context.MODE_PRIVATE).getFloat("item_amount_2", -1);
        float item3 = getActivity().getPreferences(Context.MODE_PRIVATE).getFloat("item_amount_3", -1);
        float item4 = getActivity().getPreferences(Context.MODE_PRIVATE).getFloat("item_amount_4", -1);

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
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        /**
         * Make sure that the activity is implementing our interface. If not then crash the program.
         */
        try {
            mCallBack = (onItemChange) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException( activity.toString() + "must implement the onItemChange interface.");
        }
    }
}
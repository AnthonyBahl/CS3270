package edu.weber.cs.w01113559.cs3270mi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {

    private View root;

    public ResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_results, container, false);
    }

    private double calculateBMI(BigDecimal weight, BigDecimal height) {
        BigDecimal constant = new BigDecimal(703);
        BigDecimal heightSquared = height.pow(2);
        double BMIp1 = weight.doubleValue() / heightSquared.doubleValue();
        double BMIp2 = BMIp1 * constant.doubleValue();


        NumberFormat nfBMI = NumberFormat.getNumberInstance(Locale.US);
        nfBMI.setMaximumFractionDigits(2);
        nfBMI.setMinimumIntegerDigits(1);

        TextView tvBMI = root.findViewById(R.id.tvBMI);
        tvBMI.setText(nfBMI.format(BMIp2));
        return BMIp2;
    }

    private void calculateBodyFat(double BMI, int Age, int Sex) {

        NumberFormat nfBodyFat = NumberFormat.getNumberInstance(Locale.US);
        nfBodyFat.setRoundingMode(RoundingMode.HALF_UP);
        nfBodyFat.setMaximumFractionDigits(2);
        nfBodyFat.setMinimumIntegerDigits(1);

        TextView tvBodyFat = root.findViewById(R.id.tvBodyFat);
        double dBodyFat = (1.20 * BMI) + (0.23 * (double) Age) - (10.8 * (double) Sex) - 5.4;
        tvBodyFat.setText(nfBodyFat.format(dBodyFat));

    }

    /**
     * Calculates BMI and Body Fat % and puts them on the screen.
     * @param weight BigDecimal: Weight in pounds.
     * @param height BigDecimal: Height in inches.
     * @param age BigInteger: Age
     * @param sex int: 0- Female 1- Male
     */
    public void calculate(BigDecimal weight, BigDecimal height, BigInteger age, int sex) {
        double BMI = calculateBMI(weight, height);
        calculateBodyFat(BMI, age.intValue(), sex);
    }
}
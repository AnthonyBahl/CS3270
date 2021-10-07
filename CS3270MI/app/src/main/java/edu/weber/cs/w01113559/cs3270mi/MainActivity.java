package edu.weber.cs.w01113559.cs3270mi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MainActivity extends AppCompatActivity implements ActionFragment.actionInterface {

    private FragmentManager fragmentManager;
    private ResultsFragment resultsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a toolbar variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Instantiate Fragment Manager
        fragmentManager = getSupportFragmentManager();

        // Create Fragments
        fragmentManager.beginTransaction()
                .replace(R.id.actionFragment, new ActionFragment(), "actionFragment")
                .replace(R.id.resultsFragment, new ResultsFragment(), "resultsFragment")
                .commit();
    }

    /**
     * Recieves the info from the action fragment and passes the info to the results fragment for calculations.
     * @param weight BigDecimal: Weight in pounds.
     * @param height BigDecimal: Height in inches.
     * @param age BigInteger: Age
     * @param sex int: 0- Female 1- Male
     */
    @Override
    public void passInfo(BigDecimal weight, BigDecimal height, BigInteger age, int sex) {

        // Verify that resultsFragment has been instantiated
        if (resultsFragment == null) {
            resultsFragment = (ResultsFragment) fragmentManager.findFragmentByTag("resultsFragment");
        }

        resultsFragment.calculate(weight, height, age, sex);

    }
}
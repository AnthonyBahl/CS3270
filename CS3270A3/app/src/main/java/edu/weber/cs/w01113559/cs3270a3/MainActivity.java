package edu.weber.cs.w01113559.cs3270a3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements GameFragment.onButtonListener {

    /**
     * Fragment for the Results portion of the screen
     */
    private ResultsFragment resultsFragment;

    /**
     * First thing that happens in an Activity Lifecycle.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Passes the result to the results fragment.
     * @param result 1-Player Win, 2-Phone Win, 3-Tie Game
     */
    public void fragButtonPressed(int result) {

        // Find fragment and assign to variable if we haven't already
        if ( resultsFragment == null ) {
            FragmentManager fm = getSupportFragmentManager();
            resultsFragment = (ResultsFragment) fm.findFragmentById(R.id.fragmentResults);
        }

        assert resultsFragment != null;
        resultsFragment.updateResults(result);
    }
}
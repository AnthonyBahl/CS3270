package edu.weber.cs.w01113559.cs3270a5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity
        implements ChangeResults.roundActions
        , ChangeButtons.onButtonPress
        , ChangeActions.changeActions
        , roundResultFragment.dialogInterface
        , maxChangeFragment.maxChangeInterface {

    private FragmentManager fragmentManager;
    private ChangeResults changeResultsFragment;
    private ChangeButtons changeButtonsFragment;
    private ChangeActions changeActionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Instantiate Fragment Manager
        fragmentManager = getSupportFragmentManager();

        // Create Fragments
        fragmentManager.beginTransaction()
                .replace(R.id.changeResultsFragment, new ChangeResults(), "changeResultsFrag")
                .replace(R.id.changeButtonsFragment, new ChangeButtons(), "changeButtonsFrag")
                .replace(R.id.changeActionsFragment, new ChangeActions(), "changeActionsFrag")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    /**
     * Makes sure that each of the Fragment variables is not null.
     */
    private void instantiateFragments() {

        // Make sure changeResultsFragment isn't null
        if (changeResultsFragment == null) {
            changeResultsFragment = (ChangeResults) fragmentManager.findFragmentByTag("changeResultsFrag");
        }

        // Make sure changeButtonsFragment isn't null
        if (changeButtonsFragment == null) {
            changeButtonsFragment = (ChangeButtons) fragmentManager.findFragmentByTag("changeButtonsFrag");
        }

        // Make sure changeActionsFragment isn't null
        if (changeActionsFragment == null) {
            changeActionsFragment = (ChangeActions) fragmentManager.findFragmentByTag("changeActionsFrag");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_zero_correct_count:    // "Zero Correct Count"

                // Make sure fragment variables are not null
                instantiateFragments();

                // Zero out the Correct Change Count
                changeActionsFragment.resetCorrectChangeCount();

                return true;

            case R.id.action_set_change_max:    // "Set Change Max"
                setMaxChangeScreen();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Ends the active round.
     * @param result result int: 1- Time ran out, 2- Went over on change, 3- Successfully made change.
     */
    @Override
    public void roundEnd(int result) {

        // Make sure fragment variables are not null
        instantiateFragments();

        // Display Dialog
        roundResultFragment dialog = new roundResultFragment(result);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog");

        // If the user succeeded, increment Correct Change Count
        if (result == 3) {

            // Increment Correct Change Count
            changeActionsFragment.incrementCorrectChangeCount();

            // Disable Start Over Button
            changeActionsFragment.setStartOverButton(false);
        }

    }

    /**
     * Updates the 'Change so Far' amount.
     * @param value BigDecimal: amount of change to be added.
     */
    @Override
    public void onbuttonpress(BigDecimal value) {

        // Make sure fragment variables are not null
        instantiateFragments();

        // Send the value of the change to the results fragment
        changeResultsFragment.addChange(value);
    }

    /**
     * Resets the timer and 'Change so far' value, but not the 'Change to Make' value.
     */
    @Override
    public void startOver() {

        // Make sure fragment variables are not null
        instantiateFragments();

        // Reset the round
        changeResultsFragment.resetRound();

    }

    /**
     * Creates a whole new round with a new 'Change to Make' value
     */
    @Override
    public void newAmount() {

        // Make sure fragment variables are not null
        instantiateFragments();

        // Start a new round
        changeResultsFragment.startRound();

    }

    /**
     * Sets the "change so far" to 0
     */
    @Override
    public void resetChangeSoFar() {

        // Make sure fragment variables are not null
        instantiateFragments();

        // Set 'Change so far' to 0
        changeResultsFragment.updateChangeSoFar(new BigDecimal(0));

    }

    /**
     * Replaces changeResultsFragment with maxChangeFragment and hides changeButtonsFrag and changeActionsFrag
     */
    private void setMaxChangeScreen() {

        // Make sure fragment variables are not null
        instantiateFragments();

        // End current round if there is one.
        changeResultsFragment.endRound();

        // Change to Max Change Screen
        fragmentManager.beginTransaction()
                .replace(R.id.changeResultsFragment, new maxChangeFragment(), "maxChangeFragment")
                .hide(fragmentManager.findFragmentByTag("changeButtonsFrag"))
                .hide(fragmentManager.findFragmentByTag("changeActionsFrag"))
                .commit();


    }

    /**
     * Removes the edit Max Change Screen and puts back the other screens.
     */
    @Override
    public void finishMaxChangeScreen() {

        // Make sure fragment variables are not null
        instantiateFragments();

        fragmentManager.beginTransaction()
                .replace(R.id.changeResultsFragment, new ChangeResults(), "changeResultsFrag")
                .show(fragmentManager.findFragmentByTag("changeButtonsFrag"))
                .show(fragmentManager.findFragmentByTag("changeActionsFrag"))
                .commit();

        // update changeResultsFragment variable
        changeResultsFragment = (ChangeResults) fragmentManager.findFragmentByTag("changeResultsFrag");

    }
}
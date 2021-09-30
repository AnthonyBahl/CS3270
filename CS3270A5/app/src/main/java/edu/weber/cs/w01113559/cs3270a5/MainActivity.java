package edu.weber.cs.w01113559.cs3270a5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements ChangeResults.roundActions, ChangeButtons.onButtonPress, ChangeActions.changeActions {

    private FragmentManager fragmentManager;
    private ChangeResults changeResultsFragment;
    private ChangeButtons changeButtonsFragment;
    private ChangeActions changeActionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate Fragment Manager
        fragmentManager = getSupportFragmentManager();

        // Create Fragments
        fragmentManager.beginTransaction()
                .replace(R.id.changeResultsFragment, new ChangeResults(), "changeResultsFrag")
                .replace(R.id.changeButtonsFragment, new ChangeButtons(), "changeButtonsFrag")
                .replace(R.id.changeActionsFragment, new ChangeActions(), "changeActionsFrag")
                .commit();
    }

    /**
     * Ends the active round.
     * @param result boolean: true - user matched the change, false - user failed to match the change.
     */
    @Override
    public void roundEnd(boolean result) {

        // ToDo: After any of the dialogs have been displayed and dismissed,  the ChangeSoFar should go to zero and the user will retry the ChangeToMake.

        // Make sure changeButtonsFragment isn't null
        if (changeButtonsFragment == null) {
            changeButtonsFragment = (ChangeButtons) fragmentManager.findFragmentByTag("changeButtonsFrag");
        }

        // Make sure changeActionsFragment isn't null
        if (changeActionsFragment == null) {
            changeActionsFragment = (ChangeActions) fragmentManager.findFragmentByTag("changeActionsFrag");
        }


        // Grey out buttons
        changeButtonsFragment.disableButtons();

        // If True, increment Correct Change Count
        if (result) {

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
    public void onButtonPress(BigDecimal value) {

        // Make sure changeResultsFragment isn't null
        if (changeResultsFragment == null) {
            changeResultsFragment = (ChangeResults) fragmentManager.findFragmentByTag("changeResultsFrag");
        }

        // Send the value of the change to the results fragment
        changeResultsFragment.addChange(value);
    }

    /**
     * Resets the timer and 'Change so far' value, but not the 'Change to Make' value.
     */
    @Override
    public void startOver() {

        // Make sure changeButtonsFragment isn't null
        if (changeButtonsFragment == null) {
            changeButtonsFragment = (ChangeButtons) fragmentManager.findFragmentByTag("changeButtonsFrag");
        }

        // Enable Buttons
        changeButtonsFragment.enableButtons();

        // Make sure changeResultsFragment isn't null
        if (changeResultsFragment == null) {
            changeResultsFragment = (ChangeResults) fragmentManager.findFragmentByTag("changeResultsFrag");
        }

        changeResultsFragment.resetRound();

    }

    /**
     * Creates a whole new round with a new 'Change to Make' value
     */
    @Override
    public void newAmount() {

        // Make sure changeButtonsFragment isn't null
        if (changeButtonsFragment == null) {
            changeButtonsFragment = (ChangeButtons) fragmentManager.findFragmentByTag("changeButtonsFrag");
        }

        // Enable Buttons
        changeButtonsFragment.enableButtons();

        // Make sure changeResultsFragment isn't null
        if (changeResultsFragment == null) {
            changeResultsFragment = (ChangeResults) fragmentManager.findFragmentByTag("changeResultsFrag");
        }

        changeResultsFragment.startRound();

    }
}
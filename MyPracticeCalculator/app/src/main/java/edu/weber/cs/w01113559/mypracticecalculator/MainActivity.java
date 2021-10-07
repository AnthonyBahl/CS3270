package edu.weber.cs.w01113559.mypracticecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements buttonsFragment.buttonFragInterface {

    private FragmentManager fragmentManager;
    private displayFragment displayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate fragmentManager
        fragmentManager = getSupportFragmentManager();

        // Replace the fragments in their containers and also assign a tag.
        fragmentManager.beginTransaction()
                .replace(R.id.displayFragmentContainer, new displayFragment(), "displayFragment")
                .replace(R.id.buttonsFragmentContainer, new buttonsFragment(), "buttonsFragment")
                .commit();
    }

    /**
     * Recieves the tag of a button that was pressed in buttonsFragment and passes that info to the displayFragment.
     * @param tag String: tag of the button pressed.
     */
    @Override
    public void passButtonTag(String tag) {

        // Make Sure displayFragment has been instantiated
        if (displayFragment == null) {
            displayFragment = (displayFragment) fragmentManager.findFragmentByTag("displayFragment");
        }

        displayFragment.processInput(tag);

    }
}
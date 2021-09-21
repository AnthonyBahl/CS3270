package edu.weber.cs.w01113559.cs3270a4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements TaxFragment.onSeekChanged {

    // Fragment Manager to be used throughout the app
    private FragmentManager fragManager;
    private TotalsFragment totalsFragment;
    private ItemsFragment itemsFragment;
    private TaxFragment taxFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragManager = getSupportFragmentManager();
        fragManager.beginTransaction()
                .replace(R.id.totalsFragment, new TotalsFragment(), "totalsFrag")
                .replace(R.id.TaxFragment, new TaxFragment(), "taxFrag")
                .replace(R.id.ItemsFragment, new ItemsFragment(), "itemsFrag")
                .commit();
    }

    /**
     * Recieves the new Tax % when the seek bar is updated.
     * @param value Value of seek bar (The tax %)
     */
    @Override
    public void onSeekUpdate(BigDecimal value) {

        initializeFragments();

        //ToDo: Get Item Sum from Items Fragment

        //ToDo: updateTaxAmount(ItemCost, TaxRate) (in tax Fragment)
        if (taxFragment != null) {
            // taxFragment.updateTaxAmount();
        }

        //ToDo: updateTotalAmount(ItemCost, TaxRate) (in total Fragment)
    }

    /**
     * Checks to see if fragment variables have been initialized
     */
    private void initializeFragments() {

        if ( totalsFragment == null) {
            totalsFragment = (TotalsFragment) fragManager.findFragmentByTag("totalsFrag");
        }

        if (itemsFragment == null) {
            itemsFragment = (ItemsFragment) fragManager.findFragmentByTag("itemsFrag");
        }

        if (taxFragment == null) {
            taxFragment = (TaxFragment) fragManager.findFragmentByTag("taxFrag");
        }
    }
}
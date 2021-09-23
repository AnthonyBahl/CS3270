package edu.weber.cs.w01113559.cs3270a4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements TaxFragment.onSeekChanged, ItemsFragment.onItemChange {

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
     * @param taxRate Value of seek bar (The tax %)
     */
    @Override
    public void onSeekUpdate(BigDecimal taxRate) {

        // Check to make sure fragments are initialized
        initializeFragments();


        if (itemsFragment != null && taxFragment != null && totalsFragment != null) {

            // Get Item Sum from Items Fragment
            BigDecimal bdItemTotal = itemsFragment.getItemTotal();

            // Update Tax Amount on the tax fragment
            taxFragment.updateTaxAmount(bdItemTotal, taxRate);

            // Update Total Amount on the Totals Fragment
            totalsFragment.updateTotalAmount(bdItemTotal, taxRate);
        }
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

    /**
     * Update the Tax Amount and Total Amount.
     * @param newTotal BigDecimal - Sum of all item costs.
     */
    @Override
    public void onItemUpdate(BigDecimal newTotal) {

        initializeFragments();

        if (itemsFragment != null && taxFragment != null && totalsFragment != null) {

            // Get Item Sum from Items Fragment
            BigDecimal bdTaxRate = taxFragment.getTaxRate();

            // Update Tax Amount on the tax fragment
            taxFragment.updateTaxAmount(newTotal, bdTaxRate);

            // Update Total Amount on the Totals Fragment
            totalsFragment.updateTotalAmount(newTotal, bdTaxRate);
        }
    }
}
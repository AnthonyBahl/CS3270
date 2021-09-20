package edu.weber.cs.w01113559.seekbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SeekFragment.OnSeekChanged {

    private FragmentManager fragmentManager;
    private AmountFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragOne, new SeekFragment(), "seek")
                .replace(R.id.fragTwo, new AmountFragment(), "amount")
                .commit();
    }

    @Override
    public void onSeekUpdate(int value) {

        if ( fragment == null ) {
            fragment = (AmountFragment) fragmentManager.findFragmentByTag("amount");
        }

        if (fragment != null) {
            fragment.setAmount(value);
        }
    }
}
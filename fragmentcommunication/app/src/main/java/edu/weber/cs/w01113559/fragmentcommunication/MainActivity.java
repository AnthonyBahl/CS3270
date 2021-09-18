package edu.weber.cs.w01113559.fragmentcommunication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements ButtonFragment.onButtonListener {

    private TextFragment textFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fragButtonPressed(int count) {
        Log.d("test", "Count: " + count);

        if ( textFragment == null ) {
            FragmentManager fm = getSupportFragmentManager();
            textFragment = (TextFragment) fm.findFragmentById(R.id.fragmentTextView);
        }

        textFragment.updateText(count);
    }
}
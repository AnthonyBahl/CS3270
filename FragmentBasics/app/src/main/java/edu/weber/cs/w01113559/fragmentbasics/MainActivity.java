package edu.weber.cs.w01113559.fragmentbasics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();   // Get reference to the fragment manager

        fm.beginTransaction()
                .replace(R.id.fragContainer1, new BlankFragment(), "frag1")
                .addToBackStack(null)
                .commit();

        Button btnLoad = findViewById(R.id.btn_load_frag_2);    // Create the button

        // Set behavior for when button is clicked
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction()
                        .replace(R.id.fragContainer2, new BlankFragment2(), "frag2")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
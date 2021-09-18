package edu.weber.cs.w01113559.cs3270a2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Variable to hold our fragment manager
    private FragmentManager fm;

    // Variables to manage the switch button
    int container_3 = 0;
    int container_4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();    // Get reference to the fragment manager

        // put Frag A into the frag container 1
        fm.beginTransaction()
                .replace(R.id.fragmentContainer1, new FragmentA(), "fragA")
                .addToBackStack(null)
                .commit();

        // Setup for btn_load_2
        Button btn_load_frag_2 = findViewById(R.id.btn_load_2);
        btn_load_frag_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Put Frag B into frag container 2
                fm.beginTransaction()
                        .replace(R.id.fragmentContainer2, new FragmentB(), "fragB")
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Setup for btn_load_3
        Button btn_load_frag_3 = findViewById(R.id.btn_load_3);
        btn_load_frag_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Put Frag C into frag container 3
                fm.beginTransaction()
                        .replace(R.id.fragmentContainer3, new FragmentC(), "fragC")
                        .addToBackStack(null)
                        .commit();

                container_3 = 1;
            }
        });

        // Setup for btn_load_4
        Button btn_load_frag_4 = findViewById(R.id.btn_load_4);
        btn_load_frag_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                container_4 = 2;

                // Put Frag D into frag container 4
                fm.beginTransaction()
                        .replace(R.id.fragmentContainer4, new FragmentD(), "fragD")
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Setup for btn_switch_3_4
        Button btn_switch = findViewById((R.id.btn_switch_3_4));
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debugger", "the switch button has been pressed");

                // Variable to hold current contents
                int[] currentStates = {container_3, container_4};

                // Remove current contents from container 3
                switch (currentStates[0]){
                    case 1:
                        fm.beginTransaction()
                                .remove(fm.findFragmentByTag("fragC"))
                                .addToBackStack(null)
                                .commit();

                        container_3 = 0;
                        break;
                    case 2:
                        fm.beginTransaction()
                                .remove(fm.findFragmentByTag("fragD"))
                                .addToBackStack(null)
                                .commit();

                        container_3 = 0;
                        break;
                    default:
                        break;
                }

                // Remove current contents from container 4
                switch (currentStates[1]){
                    case 1:
                        fm.beginTransaction()
                                .remove(fm.findFragmentByTag("fragC"))
                                .addToBackStack(null)
                                .commit();

                        container_4 = 0;
                        break;
                    case 2:
                        fm.beginTransaction()
                                .remove(fm.findFragmentByTag("fragD"))
                                .addToBackStack(null)
                                .commit();

                        container_4 = 0;
                        break;
                    default:
                        break;
                }

                // Add fragment to container 3
                switch (currentStates[1]){
                    case 1:
                        fm.beginTransaction()
                                .replace(R.id.fragmentContainer3, new FragmentC(), "fragC")
                                .addToBackStack(null)
                                .commit();

                        container_3 = 1;
                        break;
                    case 2:
                        fm.beginTransaction()
                                .replace(R.id.fragmentContainer3, new FragmentD(), "fragD")
                                .addToBackStack(null)
                                .commit();

                        container_3 = 2;
                        break;
                    default:
                        break;
                }

                // Add fragment to container 4
                switch (currentStates[0]){
                    case 1:
                        fm.beginTransaction()
                                .replace(R.id.fragmentContainer4, new FragmentC(), "fragC")
                                .addToBackStack(null)
                                .commit();

                        container_4 = 1;
                        break;
                    case 2:
                        fm.beginTransaction()
                                .replace(R.id.fragmentContainer4, new FragmentD(), "fragD")
                                .addToBackStack(null)
                                .commit();

                        container_4 = 2;
                        break;
                    default:
                        break;
                }

            }
        });
    }
}
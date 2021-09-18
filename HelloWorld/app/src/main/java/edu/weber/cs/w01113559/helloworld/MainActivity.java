package edu.weber.cs.w01113559.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Lifecycles", "onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycles", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycles", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycles", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycles", "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Lifecycles", "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycles", "onDestroy()");
    }
}
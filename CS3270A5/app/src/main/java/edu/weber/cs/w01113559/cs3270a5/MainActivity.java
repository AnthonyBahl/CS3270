package edu.weber.cs.w01113559.cs3270a5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ChangeResults.roundActions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Ends the active round.
     * @param result boolean: true - user matched the change, false - user failed to match the change.
     */
    @Override
    public void roundEnd(boolean result) {
        // ToDo: Grey out buttons

        // ToDo: If True, increment Correct Change Count

    }
}
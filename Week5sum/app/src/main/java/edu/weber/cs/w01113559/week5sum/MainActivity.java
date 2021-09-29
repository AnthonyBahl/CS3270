package edu.weber.cs.w01113559.week5sum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteConfirmationDialogFragment dialog = new DeleteConfirmationDialogFragment();
                dialog.setCancelable(false);
                dialog.show(getSupportFragmentManager(), "delete_dialog");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_done:
                //USER PRESSED DONE
                action_done();
                return true;
            case R.id.action_settings:
                //USER PRESSED SETTINGS
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Creates a toast stating that the user pressed done.
     */
    private void action_done() {
        Toast toast = Toast.makeText(this, "Done Pressed", Toast.LENGTH_SHORT);
        toast.show();
    }
}
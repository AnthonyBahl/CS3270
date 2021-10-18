package edu.weber.cs.w01113559;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import edu.weber.cs.w01113559.db.AppDatabase;
import edu.weber.cs.w01113559.db.User;
import edu.weber.cs.w01113559.db.UserDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Start thread so that data call doesn't freeze app.
        new Thread(new Runnable() {
            @Override
            public void run() {

                AppDatabase db = AppDatabase.getInstance(getApplicationContext());

                List<User> courseList;

                UserDAO dao = db.userDAO();

                courseList = dao.getAll();

                if (courseList.size() > 0) {
                    for (User c:
                            courseList) {
                        Log.d("Course List", c.toString());
                    }
                }
            }
        }).start();
    }
}
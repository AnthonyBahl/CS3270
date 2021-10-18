package edu.weber.cs.w01113559.cs3270a7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import edu.weber.cs.w01113559.cs3270a7.db.AppDatabase;
import edu.weber.cs.w01113559.cs3270a7.db.Course;
import edu.weber.cs.w01113559.cs3270a7.db.CourseDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn_add_course);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CourseEditFragment dialog = new CourseEditFragment();
                dialog.show(fm, "add_course_dialog");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        // Start thread so that data call doesn't freeze app.
        new Thread(new Runnable() {
            @Override
            public void run() {

                AppDatabase db = AppDatabase.getInstance(getApplicationContext());

                List<Course> courseList;

                CourseDAO dao = db.courseDAO();

                courseList = dao.getAll();

                if (courseList.size() > 0) {
                    for (Course c:
                            courseList) {
                        Log.d("Course List", c.toString());
                    }
                }
            }
        }).start();

    }


}
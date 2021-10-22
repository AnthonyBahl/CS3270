package edu.weber.cs.w01113559.cs3270a8;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import edu.weber.cs.w01113559.cs3270a8.databinding.ActivityMainBinding;
import edu.weber.cs.w01113559.cs3270a8.db.Course;

public class MainActivity extends AppCompatActivity implements CourseListFragment.onCourseClickListener {

    private FragmentManager fragmentManager;
    private CourseEditFragment courseEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        edu.weber.cs.w01113559.cs3270a8.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        binding.fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Make sure CourseEditFragment isn't null
                if (courseEditFragment == null) {
                    courseEditFragment = (CourseEditFragment) fragmentManager.findFragmentByTag("courseEditFrag");
                }

                courseEditFragment.saveCourse();
            }
        });
    }

    @Override
    public void courseClicked(Course course) {
        fragmentManager.beginTransaction()
                .replace(R.id.mainContentFragment, new CourseEditFragment(course), "courseEditFrag")
                .commit();
    }
}
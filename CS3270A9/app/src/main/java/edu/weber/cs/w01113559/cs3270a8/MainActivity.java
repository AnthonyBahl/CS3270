package edu.weber.cs.w01113559.cs3270a8;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Objects;

import edu.weber.cs.w01113559.cs3270a8.databinding.ActivityMainBinding;
import edu.weber.cs.w01113559.cs3270a8.db.AppDatabase;
import edu.weber.cs.w01113559.cs3270a8.db.Course;
import edu.weber.cs.w01113559.cs3270a8.db.CourseDAO;

public class MainActivity extends AppCompatActivity implements CourseListFragment.onCourseClickListener, CourseEditFragment.navigationInterface {

    private FragmentManager fragmentManager;
    private CourseEditFragment courseEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        // Create the list fragment
        fragmentManager.beginTransaction()
                .replace(R.id.mainContentFragment, new CourseListFragment(), "listFrag")
                .commit();

        // I'm sure this is important for something... I guess i'll leave it.
        // I think this actually is the code that was added because we assigned this as our main activity. SO it's binding to like.... the activity or the context or something manually
        // and it maybe wasn't there last time because it was done behind the scenes somewhere... But since this is not a standard "main activity" it has to do it manually
        edu.weber.cs.w01113559.cs3270a8.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Assign Toolbar
        setSupportActionBar(binding.toolbar);

        // 'Add' Button
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create Course Edit Page
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContentFragment, new CourseEditFragment(new Course(), "add"), "courseEditFrag")
                        .commit();
            }
        });

        // 'Save' Button
        binding.fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Make sure CourseEditFragment isn't null
                if (courseEditFragment == null) {
                    courseEditFragment = (CourseEditFragment) fragmentManager.findFragmentByTag("courseEditFrag");
                }

                // Save the course
                Objects.requireNonNull(courseEditFragment).saveCourse();
            }
        });
    }

    /**
     * Opens the course view page.
     * @param course Course: courseViewHolder for the course that was clicked.
     */
    @Override
    public void courseClicked(Course course) {
        // Create Course View Page
        fragmentManager.beginTransaction()
                .replace(R.id.mainContentFragment, new CourseEditFragment(course, "view"), "courseEditFrag")
                .commit();
    }

    /**
     * Opens course edit view.
     * @param course Course: course to edit
     */
    @Override
    public void swapToEdit(Course course) {
        // Create Course Edit Page
        fragmentManager.beginTransaction()
                .replace(R.id.mainContentFragment, new CourseEditFragment(course, "edit"), "courseEditFrag")
                .commit();
    }

    /**
     * Returns to the main screen of the program.
     */
    @Override
    public void returnToList() {
        // Create the list fragment
        fragmentManager.beginTransaction()
                .replace(R.id.mainContentFragment, new CourseListFragment(), "listFrag")
                .commit();
    }
}
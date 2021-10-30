package edu.weber.cs.w01113559.cs3270a9;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.view.View;

import java.util.Objects;

import edu.weber.cs.w01113559.cs3270a9.databinding.ActivityMainBinding;
import edu.weber.cs.w01113559.cs3270a9.db.Course;

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
        edu.weber.cs.w01113559.cs3270a9.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
     * Opens an assignment list page.
     * @param course Course: course that was long clicked
     */
    @Override
    public void courseLongClicked(Course course) {
        fragmentManager.beginTransaction()
                .replace(R.id.mainContentFragment, new AssignmentListFragment(course), "AssignmentListFrag")
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
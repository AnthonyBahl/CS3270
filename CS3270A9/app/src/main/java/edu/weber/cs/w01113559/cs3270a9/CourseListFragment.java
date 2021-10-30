package edu.weber.cs.w01113559.cs3270a9;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.weber.cs.w01113559.cs3270a9.db.Course;

public class CourseListFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private CourseRecyclerAdapter adapter;
    private onCourseClickListener mCallback;
    private GetCanvasCourses getCoursesTask;

    public interface onCourseClickListener{
        /**
         * Passes the course that is clicked.
         * @param course Course: course that was clicked
         */
        void courseClicked(Course course);
    }

    public CourseListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (onCourseClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onCourseClickListener interface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabAdd = requireActivity().findViewById(R.id.fabAdd);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);            // Remove back button
        toolbar.getMenu().clear();                  // Clear any existing menu
        toolbar.inflateMenu(R.menu.main_menu);      // Set main menu
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_import_courses:

                        getCoursesTask = new GetCanvasCourses();

                        getCoursesTask.setOnCourseListComplete(new GetCanvasCourses.OnCourseListComplete() {
                            @Override
                            public void processCourseList(Course[] courses) {

                                if(courses != null) {

                                    ArrayList<Course> coursesList = new ArrayList<>(Arrays.asList(courses));

                                    adapter.clear();

                                    adapter.setCourseList(coursesList);
                                }
                            }
                        });

                        getCoursesTask.execute("");

                        return true;
                    default:
                        return false;
                }
            }
        });

        fabAdd.show(); // Show Add Button
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();

        Context context = getContext();
        recyclerView = root.findViewById(R.id.courseRV);
        adapter = new CourseRecyclerAdapter(new ArrayList<>(), mCallback);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        new ViewModelProvider(this)
                .get(AllCoursesViewModel.class)
                .getCourseList(context)
                .observe(this, new Observer<List<Course>>() {
                    @Override
                    public void onChanged(@Nullable List<Course> courses) {
                        if (courses != null) {
                            adapter.setCourseList(courses);
                        }
                    }
                });
    }
}
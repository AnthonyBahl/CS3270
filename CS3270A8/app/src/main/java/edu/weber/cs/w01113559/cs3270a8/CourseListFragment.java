package edu.weber.cs.w01113559.cs3270a8;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.weber.cs.w01113559.cs3270a8.db.AppDatabase;
import edu.weber.cs.w01113559.cs3270a8.db.Course;

public class CourseListFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private CourseRecyclerAdapter adapter;
    private onCourseClickListener mCallback;

    public interface onCourseClickListener{
        /**
         * Passes the course that is clicked.
         * @param course Course: courseViewHolder for the course that was clicked.
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
    public void onResume() {
        super.onResume();

        Context context = getContext();
        recyclerView = root.findViewById(R.id.courseRV);
        adapter = new CourseRecyclerAdapter(new ArrayList<Course>(), mCallback);

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
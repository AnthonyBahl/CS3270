package edu.weber.cs.w01113559.cs3270a9;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.weber.cs.w01113559.cs3270a9.db.Course;

public class AssignmentListFragment extends Fragment {

    private View root;
    private final Course course;
    private CourseEditFragment.navigationInterface mCallback;

    public AssignmentListFragment(Course course) {
        this.course = course;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (CourseEditFragment.navigationInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement the CourseEditFragment.courseViewInterface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_assignment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Add Back Button to toolbar
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();  // Clear any existing menu
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_round_arrow_back_24, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.returnToList();
            }
        });

        // Get Assignments
        GetCourseAssignments getCourseAssignments = new GetCourseAssignments();
        getCourseAssignments.setmCallback(new GetCourseAssignments.onAssignmentListComplete() {
            @Override
            public void processAssignmentList(Assignment[] assignments) {
                ArrayAdapter<Assignment> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, assignments);
                ListView assignmentListView = root.findViewById(R.id.assignmentListView);
                assignmentListView.setAdapter(adapter);
            }
        });
        getCourseAssignments.execute(course.getId());
    }
}
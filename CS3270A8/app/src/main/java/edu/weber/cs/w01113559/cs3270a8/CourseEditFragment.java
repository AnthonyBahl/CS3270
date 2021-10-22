package edu.weber.cs.w01113559.cs3270a8;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import edu.weber.cs.w01113559.cs3270a8.db.Course;

public class CourseEditFragment extends Fragment {

    private View root;
    private Course course;
    private TextInputLayout tvID,tvName,tvCourseCode,tvStartAt,tvEndAt;

    public CourseEditFragment(Course course) {
        this.course = course;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_course_edit, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        //ToDo: When I leave this page I need to inflate a new toolbard that doesn't have these buttons
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.edit_delete_menu);

        // Get Fields
        tvID = root.findViewById(R.id.inputID);
        tvName = root.findViewById(R.id.inputName);
        tvCourseCode = root.findViewById(R.id.inputCourseCode);
        tvStartAt = root.findViewById(R.id.inputStartAt);
        tvEndAt = root.findViewById(R.id.inputEndAt);

        // Make Fields not editable
        Objects.requireNonNull(tvID.getEditText()).setEnabled(false);
        Objects.requireNonNull(tvName.getEditText()).setEnabled(false);
        Objects.requireNonNull(tvCourseCode.getEditText()).setEnabled(false);
        Objects.requireNonNull(tvStartAt.getEditText()).setEnabled(false);
        Objects.requireNonNull(tvEndAt.getEditText()).setEnabled(false);

        // Populate Fields
        tvID.getEditText().setText(course.getId());
        tvName.getEditText().setText(course.getName());
        tvCourseCode.getEditText().setText(course.getCourse_code());
        tvStartAt.getEditText().setText(course.getStart_at());
        tvEndAt.getEditText().setText(course.getEnd_at());

    }
}
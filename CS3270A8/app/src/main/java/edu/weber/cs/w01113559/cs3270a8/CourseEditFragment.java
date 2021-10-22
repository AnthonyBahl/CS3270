package edu.weber.cs.w01113559.cs3270a8;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import edu.weber.cs.w01113559.cs3270a8.db.Course;

public class CourseEditFragment extends Fragment {

    private View root;
    private Course course;
    private TextInputLayout tvID,tvName,tvCourseCode,tvStartAt,tvEndAt;
    private FloatingActionButton fabAdd, fabSave;
    private String mode;

    /**
     * Constructor for course view/edit mode
     * @param course Course: the course to view.
     */
    public CourseEditFragment(Course course) {
        this.course = course;
        mode = "view";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_course_edit, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_delete_menu, menu);
    }

    @Override
    public void onResume() {
        super.onResume();

        switch (this.mode){
            case "view":
                //ToDo: When I leave this page I need to inflate a new toolbard that doesn't have these buttons

                // Get Fields
                tvID = root.findViewById(R.id.inputID);
                tvName = root.findViewById(R.id.inputName);
                tvCourseCode = root.findViewById(R.id.inputCourseCode);
                tvStartAt = root.findViewById(R.id.inputStartAt);
                tvEndAt = root.findViewById(R.id.inputEndAt);
                fabAdd = getActivity().findViewById(R.id.fabAdd);
                fabSave = getActivity().findViewById(R.id.fabSave);

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

                // Disable the fab button in View mode
                fabAdd.hide();
                fabSave.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_edit_course:

                mode = "edit";

                // Make Fields editable
                Objects.requireNonNull(tvID.getEditText()).setEnabled(true);
                Objects.requireNonNull(tvName.getEditText()).setEnabled(true);
                Objects.requireNonNull(tvCourseCode.getEditText()).setEnabled(true);
                Objects.requireNonNull(tvStartAt.getEditText()).setEnabled(true);
                Objects.requireNonNull(tvEndAt.getEditText()).setEnabled(true);

                // ToDo: Change fab button to a save button and enable again

                return true;
            case R.id.action_delete_course:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void saveCourse(){
        switch (this.mode){
            case "edit":
                // ToDo: Validate Data

                // ToDo: Save the course

                fabSave.hide();
                fabAdd.show();

                // ToDo: Return to list view

                break;
            case "add":
                // ToDo: Validate Data

                // ToDo: Add the course

                fabSave.hide();
                fabAdd.show();

                // ToDo: Return to list view

                break;
        }
    }
}
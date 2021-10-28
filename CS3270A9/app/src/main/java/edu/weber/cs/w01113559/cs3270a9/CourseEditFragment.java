package edu.weber.cs.w01113559.cs3270a9;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import edu.weber.cs.w01113559.cs3270a9.db.AppDatabase;
import edu.weber.cs.w01113559.cs3270a9.db.Course;

public class CourseEditFragment extends Fragment implements DeleteConfirmationDialogFragment.deleteDialogInterface {

    private View root;
    private final Course course;
    private TextInputLayout tvID,tvName,tvCourseCode,tvStartAt,tvEndAt;
    private final String mode;
    private navigationInterface mCallback;

    interface navigationInterface {
        /**
         * Swaps to edit mode.
         * @param course Course: course to edit
         */
        void swapToEdit(Course course);

        /**
         * Returns to the main screen of the program.
         */
        void returnToList();
    }

    /**
     * Constructor for course view/edit page (view, edit, or add)
     * @param course Course: the course to view.
     * @param mode String: view, edit, or add.
     */
    public CourseEditFragment(Course course, String mode) {
        this.course = course;
        this.mode = mode;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (navigationInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement the courseViewInterface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_course_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        // Add Back Button
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_round_arrow_back_24, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.returnToList();
            }
        });

        // Clear any existing menu
        toolbar.getMenu().clear();

        // Add menu
        if (!mode.equals("add")) {
            // Add Edit & Delete Buttons
            toolbar.inflateMenu(R.menu.edit_delete_menu);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){

                        case R.id.action_edit_course:   // Edit Clicked
                            mCallback.swapToEdit(course);
                            return true;

                        case R.id.action_delete_course: // Delete Clicked

                            DeleteConfirmationDialogFragment dialog = new DeleteConfirmationDialogFragment();
                            dialog.setCancelable(true);
                            dialog.show(requireActivity().getSupportFragmentManager(), getString(R.string.delete_dialog));

                            return true;

                        default:
                            return false;
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get Fields
        tvID = root.findViewById(R.id.inputID);
        tvName = root.findViewById(R.id.inputName);
        tvCourseCode = root.findViewById(R.id.inputCourseCode);
        tvStartAt = root.findViewById(R.id.inputStartAt);
        tvEndAt = root.findViewById(R.id.inputEndAt);

        // Set Listeners
        Objects.requireNonNull(tvID.getEditText()).setOnFocusChangeListener(inputListener);
        Objects.requireNonNull(tvName.getEditText()).setOnFocusChangeListener(inputListener);
        Objects.requireNonNull(tvCourseCode.getEditText()).setOnFocusChangeListener(inputListener);
        Objects.requireNonNull(tvStartAt.getEditText()).setOnFocusChangeListener(inputListener);
        Objects.requireNonNull(tvEndAt.getEditText()).setOnFocusChangeListener(inputListener);

        // Get Fab buttons that are stored in Main Activity because it's a CoordinatorLayout, this allows them to move with the keyboard.
        FloatingActionButton fabAdd = requireActivity().findViewById(R.id.fabAdd);
        FloatingActionButton fabSave = requireActivity().findViewById(R.id.fabSave);

        // Disable the add fab button
        fabAdd.hide();

        // Cater to which mode we're in
        switch (this.mode){

            case "view":

                populateFields(this.course);

                // Make Fields not editable
                Objects.requireNonNull(tvID.getEditText()).setEnabled(false);
                Objects.requireNonNull(tvName.getEditText()).setEnabled(false);
                Objects.requireNonNull(tvCourseCode.getEditText()).setEnabled(false);
                Objects.requireNonNull(tvStartAt.getEditText()).setEnabled(false);
                Objects.requireNonNull(tvEndAt.getEditText()).setEnabled(false);

                // Add grey tint to imply not editable
                tvID.setBoxBackgroundColor(getResources().getColor(R.color.grey_84));
                tvName.setBoxBackgroundColor(getResources().getColor(R.color.grey_84));
                tvCourseCode.setBoxBackgroundColor(getResources().getColor(R.color.grey_84));
                tvCourseCode.setBoxBackgroundColor(getResources().getColor(R.color.grey_84));
                tvStartAt.setBoxBackgroundColor(getResources().getColor(R.color.grey_84));
                tvEndAt.setBoxBackgroundColor(getResources().getColor(R.color.grey_84));

                // Hide save button (Even though it should already be)
                fabSave.hide();

                break;

            case "edit":

                populateFields(this.course);

                // Show save button
                fabSave.show();

                break;

            case "add":

                // Show save button
                fabSave.show();

                break;
        }
    }

    /**
     * Watch for when the user leaves the text field, then validate the data.
     * If data is valid, update course.
     */
    private final View.OnFocusChangeListener inputListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) { validateData((EditText) v); }
        }
    };

    /**
     * Submits the changes/additions to the database.
     */
    public void saveCourse(){
        if (validateData(tvID.getEditText(), tvName.getEditText(), tvCourseCode.getEditText(), tvStartAt.getEditText(), tvEndAt.getEditText())) {

                    course.setId(Objects.requireNonNull(tvID.getEditText()).getText().toString());
                    course.setName(Objects.requireNonNull(tvName.getEditText()).getText().toString());
                    course.setCourse_code(Objects.requireNonNull(tvCourseCode.getEditText()).getText().toString());
                    course.setStart_at(Objects.requireNonNull(tvStartAt.getEditText()).getText().toString());
                    course.setEnd_at(Objects.requireNonNull(tvEndAt.getEditText()).getText().toString());

            switch (mode) {

                case "edit":
                    // Update record
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase db = AppDatabase.getInstance(getContext());

                            db.courseDAO().updateCourses(course);
                        }
                    }).start();
                    break;

                case "add":
                    // Insert record
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase db = AppDatabase.getInstance(getContext());

                            db.courseDAO().insertAll(course);
                        }
                    }).start();
                    break;
            }
            mCallback.returnToList();
        }
    }

    /**
     * Validates the text field to make sure it isn't blank
     * @param textView TextLayoutEditText: field to check.
     * @return boolean: true- Valid Text, false- Invalid Text
     */
    private boolean validateData(EditText ...textView) {
        for (EditText tv: textView) {
            if (tv.length() < 1) {
                tv.setError(tv.getTag().toString() + " can not be blank.");
                return false;
            }
        }
        return true;
    }

    /**
     * Populates the text fields with an existing courses information.
     * @param course Course: course with data to populate fields with.
     */
    private void populateFields(Course course) {
        Objects.requireNonNull(tvID.getEditText()).setText(course.getId());
        Objects.requireNonNull(tvName.getEditText()).setText(course.getName());
        Objects.requireNonNull(tvCourseCode.getEditText()).setText(course.getCourse_code());
        Objects.requireNonNull(tvStartAt.getEditText()).setText(course.getStart_at());
        Objects.requireNonNull(tvEndAt.getEditText()).setText(course.getEnd_at());
    }

    /**
     * Deletes this course.
     */
    @Override
    public void confirmDelete() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getContext());

                db.courseDAO().deleteCourse(course);
            }
        }).start();

        mCallback.returnToList();
    }
}
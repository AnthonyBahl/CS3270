package edu.weber.cs.w01113559.cs3270a7;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import edu.weber.cs.w01113559.cs3270a7.db.AppDatabase;
import edu.weber.cs.w01113559.cs3270a7.db.Course;
import edu.weber.cs.w01113559.cs3270a7.db.CourseDAO;

public class CourseEditFragment extends DialogFragment {

    private TextInputLayout input_id, input_name, input_course_code, input_start, input_end;

    public CourseEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_CS3270A7_Dialog_FullScreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set Animation
        requireDialog().getWindow().setWindowAnimations(R.style.Theme_CS3270A7_DialogAnimation);

        // Instantiate variables
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        input_id = view.findViewById(R.id.input_id);
        input_name = view.findViewById(R.id.input_name);
        input_course_code = view.findViewById(R.id.input_course_code);
        input_start = view.findViewById(R.id.input_start);
        input_end = view.findViewById(R.id.input_end);

        // Set Listener for Close Button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        toolbar.inflateMenu(R.menu.newcourse_dialog);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.save:

                        final String id = input_id.getEditText().getText().toString();
                        final String name = input_name.getEditText().getText().toString();
                        final String course_code = input_course_code.getEditText().getText().toString();
                        final String start = input_start.getEditText().getText().toString();
                        final String end = input_end.getEditText().getText().toString();

                        // Submit the course to the database
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase db = AppDatabase.getInstance(getContext());

                                db.courseDAO().insertAll(new Course(id, name, course_code, start, end));

                            }
                        }).start();

                        // Clear form
                        input_id.getEditText().setText("");
                        input_name.getEditText().setText("");
                        input_course_code.getEditText().setText("");
                        input_start.getEditText().setText("");
                        input_end.getEditText().setText("");

                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().setWindowAnimations(R.style.Theme_CS3270A7_DialogAnimation);

        return dialog;
    }
}
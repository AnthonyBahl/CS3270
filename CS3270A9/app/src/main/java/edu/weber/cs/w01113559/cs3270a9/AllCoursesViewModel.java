package edu.weber.cs.w01113559.cs3270a9;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.weber.cs.w01113559.cs3270a9.db.AppDatabase;
import edu.weber.cs.w01113559.cs3270a9.db.Course;

public class AllCoursesViewModel extends ViewModel {
    private LiveData<List<Course>> courseList;

    public LiveData<List<Course>> getCourseList(Context context) {

        AppDatabase db = AppDatabase.getInstance(context);

        courseList = db.courseDAO().getAll();

        return courseList;
    }
}

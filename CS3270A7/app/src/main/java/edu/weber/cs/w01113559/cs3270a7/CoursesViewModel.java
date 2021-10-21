package edu.weber.cs.w01113559.cs3270a7;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import edu.weber.cs.w01113559.cs3270a7.db.Course;

public class CoursesViewModel extends ViewModel {

    private LiveData<Course> allCourses;

}

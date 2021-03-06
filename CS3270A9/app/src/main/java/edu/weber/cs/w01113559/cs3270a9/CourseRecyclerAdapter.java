package edu.weber.cs.w01113559.cs3270a9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.weber.cs.w01113559.cs3270a9.db.Course;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder> {

    private List<Course> courseList;
    private CourseListFragment.onCourseClickListener mCallback;

    public CourseRecyclerAdapter(List<Course> courseList, CourseListFragment.onCourseClickListener mCallback) {
        this.courseList = courseList;
        this.mCallback = mCallback;
    }

    public void setCourseList(List<Course> list){
        courseList.clear();
        courseList.addAll(list);
        notifyDataSetChanged();
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    /**
     * Takes the data for a single row and builds up the template for that row to be bound to.
     * @param parent
     * @param viewType
     * @return CourseViewHolder: View to hold an entry in the list.
     */
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Create the view for the data entry
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_course_view, parent, false);

        return new CourseViewHolder(view);
    }

    /**
     * Takes the row template that we created in onCreateViewHolder and binds a single entry of data from the data source.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        Course course = courseList.get(position);

        if (course != null){
            holder.course = course;
            holder.tvCourseName.setText(course.getName());

            holder.itemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Pass out the course that was clicked.
                    mCallback.courseClicked(course);
                }
            });

            holder.itemRoot.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Pass out the course that was clicked.
                    mCallback.courseLongClicked(course);
                    return true;
                }
            });

        }

    }

    /**
     * Gets how many items are in the data source.
     * @return int: number of data entries.
     */
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    /**
     * Clears course list.
     */
    public void clear() {
        this.courseList.clear();
        notifyDataSetChanged();
    }

    /**
     * Holds the UI of an individual item in the list.
     */
    public class CourseViewHolder extends RecyclerView.ViewHolder {

        public View itemRoot;
        public TextView tvCourseName;
        public Course course;


        public CourseViewHolder(@NonNull View view) {
            super(view);

            this.itemRoot = view;
            this.tvCourseName = itemRoot.findViewById(R.id.tvCourseName);
        }
    }
}

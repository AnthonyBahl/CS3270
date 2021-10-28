package edu.weber.cs.w01113559.cs3270a9.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    int _id;
    protected String id;
    protected String name;
    protected String course_code;
    protected String start_at;
    protected String end_at;

    @Ignore
    public Course() {
    }

    @Ignore
    public Course(String id, String name, String course_code, String start_at, String end_at) {
        this.id = id;
        this.name = name;
        this.course_code = course_code;
        this.start_at = start_at;
        this.end_at = end_at;
    }

    public Course(int _id, String id, String name, String course_code, String start_at, String end_at) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.course_code = course_code;
        this.start_at = start_at;
        this.end_at = end_at;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "_id=" + _id +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", course_code='" + course_code + '\'' +
                ", start_at='" + start_at + '\'' +
                ", end_at='" + end_at + '\'' +
                '}';
    }
}

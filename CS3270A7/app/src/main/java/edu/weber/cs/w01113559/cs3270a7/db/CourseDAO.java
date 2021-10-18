package edu.weber.cs.w01113559.cs3270a7.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert
    void insertAll(Course... courses);

    @Update
    void updateCourses(Course... courses);

    @Delete
    void deleteCourse(Course course);

    /**
     * Retrieves a list of all courses.
     * @return List<Course>: list of all courses.
     */
    @Query("Select * from course")
    List<Course> getAll();

    /**
     * Retrieve the details of a selected course by _id
     * @param cID int: the databases ID
     * @return Course with the matching _id.
     */
    @Query("Select * FROM course WHERE _id=:cID")
    Course getCourseByID(int cID);

    /**
     * Retrieve the details of a selected course by id
     * @param id String: the courses id.
     * @return Course with matching id.
     */
    @Query("Select * FROM course WHERE id=:id")
    Course getCourseByID(String id);

    /**
     * Retrieve the details of a selected course by course name
     * @param name String: Course name.
     * @return Course with matching id.
     */
    @Query("Select * from Course WHERE name LIKE :name LIMIT 1")
    Course getCourseByName(String name);

    /**
     * Retrieve the details of a selected course by course code
     * @param code String: Course Code.
     * @return Course with matching course code.
     */
    @Query("Select * From course WHERE course_code LIKE :code LIMIT 1")
    Course getCourseByCode(String code);



}

package edu.weber.cs.w01113559.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("Select * from user")
    LiveData<List<User>> getAll();

    @Query("Select * from user where uid=:uid LIMIT 1")
    User getUserByID(int uid);

    @Query("Select * from user where first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void deleteUser(User user);
}

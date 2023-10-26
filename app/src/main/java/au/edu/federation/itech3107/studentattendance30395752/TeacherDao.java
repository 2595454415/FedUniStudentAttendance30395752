package au.edu.federation.itech3107.studentattendance30395752;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TeacherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Teacher teacher);


    @Update
    int update(Teacher teacher);

    @Delete
    void delete(Teacher teacher);

    @Query("SELECT * FROM teachers")
    List<Teacher> getAllTeachers();

    @Query("SELECT * FROM teachers WHERE username = :username")
    Teacher getTeacherById(String username);
}

package au.edu.federation.itech3107.studentattendance30395752;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Course course);

    @Update
    int update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE course_id = :id")
    Course getCourseById(int id);

    @Query("DELETE FROM courses WHERE course_id = :courseId")
    int deleteByCourseId(int courseId);

    // 新增的方法，用于获取前12个课程的起始日期并按日期排序
    @Query("SELECT  start_date FROM courses ORDER BY  start_date ASC LIMIT 12")
    List<String> getCourseStartDatesLimited();
}

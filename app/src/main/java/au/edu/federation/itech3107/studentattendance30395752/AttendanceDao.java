package au.edu.federation.itech3107.studentattendance30395752;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AttendanceDao {
    @Insert
    long insert(Attendance attendance);

    @Update
    int update(Attendance attendance);

    @Delete
    void delete(Attendance attendance);

    @Query("SELECT * FROM attendance")
    List<Attendance> getAllAttendances();

    @Query("SELECT * FROM attendance WHERE _id = :id")
    Attendance getAttendanceById(int id);


    @Query("SELECT * FROM attendance WHERE attendance_date = :date")
    List<Attendance> getAttendanceByDate(String date);

    @Query("SELECT COUNT(*) FROM student_course WHERE student_id = :studentId")
    int studentCourseExists(int studentId);

}

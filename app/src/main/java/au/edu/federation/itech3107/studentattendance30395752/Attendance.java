package au.edu.federation.itech3107.studentattendance30395752;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "attendance",
        foreignKeys = {
                @ForeignKey(
                        entity = Course.class,
                        parentColumns = "course_id",
                        childColumns = "course_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = StudentCourse.class,
                        parentColumns = "student_id",
                        childColumns = "student_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("course_id"), @Index("student_id")}
)
public class Attendance {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    @ColumnInfo(name = "attendance_date")
    private String attendanceDate;

    @ColumnInfo(name = "student_id")
    private int studentId;

    @ColumnInfo(name = "is_present")
    private boolean isPresent;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @ColumnInfo(name = "course_id")
    private int courseId;
}

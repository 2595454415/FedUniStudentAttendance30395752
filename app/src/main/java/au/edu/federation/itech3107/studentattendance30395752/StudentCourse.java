package au.edu.federation.itech3107.studentattendance30395752;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_course",
        foreignKeys = {
                @ForeignKey(entity = Course.class,
                        parentColumns = "course_id",
                        childColumns = "course_id",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index(value = "student_id", unique = true), @Index("course_id")}
)
public class StudentCourse {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "student_id")
    private int studentId;

    @ColumnInfo(name = "student_name")
    private String studentName;

    @ColumnInfo(name = "course_id")
    private int courseId;

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for studentId
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Getter and Setter for studentName
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Getter and Setter for courseId
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}

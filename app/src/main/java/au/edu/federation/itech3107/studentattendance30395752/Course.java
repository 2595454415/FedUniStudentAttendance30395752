package au.edu.federation.itech3107.studentattendance30395752;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey
    @ColumnInfo(name = "course_id")
    private int courseId;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    @ColumnInfo(name = "course_name")
    private String courseName;

    @ColumnInfo(name = "start_date")
    private String startDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    public List<String> getListData() {
        return listData;
    }

    public void setListData(List<String> listData) {
        this.listData = listData;
    }

    @ColumnInfo(name = "list_date")
    private List<String> listData;

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", listData=" + listData +
                '}';
    }
}

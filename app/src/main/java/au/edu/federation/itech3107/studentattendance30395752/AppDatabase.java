package au.edu.federation.itech3107.studentattendance30395752;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {Teacher.class, Course.class, StudentCourse.class, Attendance.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TeacherDao teacherDao();
    public abstract CourseDao courseDao();
    public abstract StudentCourseDao studentCourseDao();
    public abstract AttendanceDao attendanceDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "attendance.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

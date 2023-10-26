package au.edu.federation.itech3107.studentattendance30395752.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395752.AppDatabase;
import au.edu.federation.itech3107.studentattendance30395752.Attendance;
import au.edu.federation.itech3107.studentattendance30395752.Course;
import au.edu.federation.itech3107.studentattendance30395752.CourseDao;
import au.edu.federation.itech3107.studentattendance30395752.R;
import au.edu.federation.itech3107.studentattendance30395752.StudentCourse;
import au.edu.federation.itech3107.studentattendance30395752.StudentCourseDao;


public class AttendanceAdapter extends BaseAdapter {
    private final CourseDao courseDao;
    private Context context;
    private List<Attendance> attendances;
    private StudentCourseDao studentDao;

    public AttendanceAdapter(Context context, List<Attendance> attendances) {
        this.context = context;
        this.attendances = attendances;

        AppDatabase database = AppDatabase.getDatabase(context);
        studentDao = database.studentCourseDao();
        courseDao = database.courseDao();
    }

    @Override
    public int getCount() {
        return attendances.size();
    }

    @Override
    public Object getItem(int position) {
        return attendances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return attendances.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.attendance_item, parent, false);
        } else {
            view = convertView;
        }

        TextView studentNameTextView = view.findViewById(R.id.studentNameTextView);
        TextView attendanceStatusTextView = view.findViewById(R.id.attendanceStatusTextView);
        TextView attendanceName = view.findViewById(R.id.attendanceName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Attendance currentAttendance = attendances.get(position);
                int studentId = currentAttendance.getStudentId();

                StudentCourse studentCourse = studentDao.getStudentCourseById(studentId);
                if (studentCourse!=null){
                    Course courseById = courseDao.getCourseById(studentCourse.getCourseId());
                    new Handler(Looper.getMainLooper()).post(() -> {
                        if (studentCourse != null) {
                            String studentName = studentCourse.getStudentName();
                            studentNameTextView.setText(studentName != null ? studentName : "Unknown Student");
                            attendanceStatusTextView.setText(currentAttendance.isPresent() ? "alreadyOnDuty" : "absence");
                            attendanceName.setText(courseById.getCourseName());

                        }
                    });
                }


            }
        }).start();

        return view;
    }
}

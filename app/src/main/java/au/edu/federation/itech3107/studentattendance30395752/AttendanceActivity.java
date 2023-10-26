package au.edu.federation.itech3107.studentattendance30395752;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.necer.calendar.BaseCalendar;
import com.necer.calendar.WeekCalendar;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;

import org.joda.time.LocalDate;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395752.adapter.CustomAdapter;
import au.edu.federation.itech3107.studentattendance30395752.adapter.StudentAttendanceAdapter;
import au.edu.federation.itech3107.studentattendance30395752.adapter.xqAdapter;


public class AttendanceActivity extends AppCompatActivity {
    private int currentWeek = 1; // start with "1week"

    private LocalDate localDates;
    private Spinner dateSpinner;
    private RecyclerView studentsRecyclerView;
    private StudentAttendanceAdapter adapter;
    private AppDatabase appDatabase;
    private List<String> courseStartDatesLimited;
    private int courseId1;
    private int courseId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        //Receive the passed course id
        // -1 is the default value if not found.
        courseId = getIntent().getIntExtra("COURSE_ID", -1);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        WeekCalendar weekCalendar =  findViewById(R.id.weekCalendar);
        appDatabase = AppDatabase.getDatabase(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Course courseByBean = appDatabase.courseDao().getCourseById(courseId);
                Log.e("获取当前课程的信息：",courseByBean.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> listData = courseByBean.getListData();
                        int courseId1 = courseByBean.getCourseId();
                        xqAdapter adapter = new xqAdapter(AttendanceActivity.this, listData );
                        recyclerView.setLayoutManager(new LinearLayoutManager(AttendanceActivity.this));
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener((position, studentName) -> {
                            Log.e("获取当前studentName信息：",courseId1+"");
                            Intent intent = new Intent(AttendanceActivity.this, SaveAttend.class);
                            intent.putExtra("studentName", studentName);

                            startActivity(intent);
                        });
                    }
                });
            }
        }).start();
        dateSpinner = findViewById(R.id.dateSpinner);
        studentsRecyclerView = findViewById(R.id.studentsRecyclerView);
        Button saveAttendanceButton = findViewById(R.id.saveAttendanceButton);

        Button buttonlast = findViewById(R.id.toLastPager);
        Button buttonnext = findViewById(R.id.toNextPager);
        TextView txNextPager = findViewById(R.id.tx_pager);

        buttonlast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentWeek>1){
                    currentWeek --;
                    txNextPager.setText("第" + currentWeek + "周");
                    weekCalendar.toLastPager();
                }

            }
        });

        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekCalendar.toNextPager();
                currentWeek ++;
                txNextPager.setText("第" + currentWeek + "周");
            }
        });




        new Thread(new Runnable() {
            @Override
            public void run() {


                StudentCourseDao studentCourseDao = appDatabase.studentCourseDao();

                Course courseByBean = appDatabase.courseDao().getCourseById(courseId);
                int courseId1 = courseByBean.getCourseId();
                Log.e("获取courseId1信息：",courseId1+"");
                List<StudentCourse> students = getStudentsFromDatabase(courseId1);
                Log.e("获取courseByBean信息：",courseByBean.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter = new StudentAttendanceAdapter(AttendanceActivity.this, students,studentCourseDao);
                        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(AttendanceActivity.this));
                        studentsRecyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();

        saveAttendanceButton.setOnClickListener(v -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    saveAttendance();
                }
            }).start();


        });




        new Thread(new Runnable() {
            @Override
            public void run() {
                courseStartDatesLimited = appDatabase.courseDao().getCourseStartDatesLimited();
                Log.e("格式的日期", courseStartDatesLimited.toString());
                Course courseByBean = appDatabase.courseDao().getCourseById(courseId);
                Log.e("获取当前课程的信息：",courseByBean.toString());
                //yyyy-MM-dd  [2023-10-21]
               // weekCalendar.jumpDate(courseStartDatesLimited.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> listData = courseByBean.getListData();
                        CustomAdapter customAdapter = new CustomAdapter(AttendanceActivity.this, listData);
                        dateSpinner.setAdapter(customAdapter);

//                        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(AttendanceActivity.this, android.R.layout.simple_spinner_item, listData);
//                        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        dateSpinner.setAdapter(dateAdapter);
                    }
                });
            }
        }).start();
    }

    private List<StudentCourse> getStudentsFromDatabase(int courseId1) {

        return appDatabase.studentCourseDao().getStudentByCourseId(courseId1);
    }

    private void saveAttendance() {
        String selectedDate = dateSpinner.getSelectedItem().toString();
        SparseBooleanArray attendance = adapter.getAttendanceState();

        for (int i = 0; i < attendance.size(); i++) {
            int studentId = attendance.keyAt(i);
            boolean isPresent = attendance.valueAt(i);

            // Before trying to save attendance information, check if the studentId exists in the StudentCourse table.
            if (appDatabase.attendanceDao().studentCourseExists(studentId) > 0) {
                Log.e("courseStartDatesLimited", selectedDate.toString());
                Attendance newAttendance = new Attendance();
                newAttendance.setAttendanceDate(selectedDate);
                newAttendance.setStudentId(studentId);
                newAttendance.setPresent(isPresent);
                newAttendance.setCourseId(courseId);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.attendanceDao().insert(newAttendance);
                    }
                }).start();
                runOnUiThread(() -> Toast.makeText(this, "Attendance Saved", Toast.LENGTH_SHORT).show());

            } else {

                runOnUiThread(() -> Toast.makeText(this, "Invalid student ID: " + studentId, Toast.LENGTH_SHORT).show());
            }
        }
    }

}

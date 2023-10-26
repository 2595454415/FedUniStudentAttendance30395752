package au.edu.federation.itech3107.studentattendance30395752;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395752.adapter.StudentAttendanceAdapter;

public class SaveAttend extends AppCompatActivity {
    private StudentAttendanceAdapter adapter;
    private AppDatabase appDatabase;
    private String studentData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveattend);


        // -1 is the default value if not found.
        studentData = getIntent().getStringExtra("studentName");

        Log.e("获取当前studentData信息：",studentData.toString());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button saveAttendanceButton = findViewById(R.id.saveAttendanceButton);
        appDatabase = AppDatabase.getDatabase(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<StudentCourse> students = getStudentsFromDatabase();

                StudentCourseDao studentCourseDao = appDatabase.studentCourseDao();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new StudentAttendanceAdapter(SaveAttend.this, students,studentCourseDao);
                        recyclerView.setLayoutManager(new LinearLayoutManager(SaveAttend.this));
                        recyclerView.setAdapter(adapter);
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
    }

    private List<StudentCourse> getStudentsFromDatabase() {

        return appDatabase.studentCourseDao().getAllStudentCourses();
    }


    private void saveAttendance() {

        SparseBooleanArray attendance = adapter.getAttendanceState();

        for (int i = 0; i < attendance.size(); i++) {
            int studentId = attendance.keyAt(i);
            boolean isPresent = attendance.valueAt(i);

            // Before trying to save attendance information, check if the studentId exists in the StudentCourse table.
            if (appDatabase.attendanceDao().studentCourseExists(studentId) > 0) {

                Attendance newAttendance = new Attendance();
                newAttendance.setAttendanceDate(studentData);
                newAttendance.setStudentId(studentId);
                newAttendance.setPresent(isPresent);

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

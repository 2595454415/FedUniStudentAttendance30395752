package au.edu.federation.itech3107.studentattendance30395752;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395752.adapter.CourseAdapter;


public class MainActivity extends AppCompatActivity {
    private HandlerThread handlerThread;
    private Handler backgroundHandler;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private AppDatabase database;
    private CourseDao courseDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        handlerThread = new HandlerThread("DatabaseBackgroundThread");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());


        database = AppDatabase.getDatabase(this);
        courseDao = database.courseDao();

         Button btnStudent = findViewById(R.id.btnStudent);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnAttendance = findViewById(R.id.btn_attendance);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backgroundHandler.post(() -> {
            List<Course> courses = courseDao.getAllCourses();
            mainHandler.post(() -> {
                // Update UI on the main thread
                CourseAdapter adapter = new CourseAdapter(courses, MainActivity.this, course -> {
                    Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
                    intent.putExtra("COURSE_ID", course.getCourseId());
                    startActivity(intent);
                });

                recyclerView.setAdapter(adapter);
            });
        });


        btnStudent.setOnClickListener(view -> {
            Log.e("点击事件", "btnStudent");
            Intent intent = new Intent(this, StudentActivity.class);
            startActivity(intent);

        });
        btnAdd.setOnClickListener(view -> {
            Log.e("点击事件", "btnStudent");
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
            Log.e("点击事件", "btnAdd");
        });

        btnAttendance.setOnClickListener(view -> {
            Log.e("点击事件", "btnStudent");
            Intent intent = new Intent(this, AttendanceActivity2.class);
            startActivity(intent);
            Log.e("点击事件", "btnAttendance");
        });
    }
}

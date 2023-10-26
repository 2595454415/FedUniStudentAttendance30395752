package au.edu.federation.itech3107.studentattendance30395752;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395752.adapter.StudentsAdapter;


public class StudentActivity extends AppCompatActivity {
    private EditText edtStudentName, edtStudentId;
    private Spinner spinnerCourses;
    private Button btnAddStudentToCourse;
    private AppDatabase database;
    private StudentCourseDao studentDao;
    private CourseDao courseDao;
    private List<String> courseNames = new ArrayList<>();
    private List<Integer> courseIds = new ArrayList<>();  // Note that this is changed to Integer

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        database = AppDatabase.getDatabase(this);
        studentDao = database.studentCourseDao();
        courseDao = database.courseDao();

        edtStudentId = findViewById(R.id.edtStudentId);
        edtStudentName = findViewById(R.id.edtStudentName);
        spinnerCourses = findViewById(R.id.spinnerCourses);
        btnAddStudentToCourse = findViewById(R.id.btnAddStudentToCourse);

        new AsyncTask<Void, Void, List<Course>>() {
            @Override
            protected List<Course> doInBackground(Void... voids) {
                return courseDao.getAllCourses();  // Execute database operation in background thread
            }

            @Override
            protected void onPostExecute(List<Course> courses) {
                // Now back on main thread
                for (Course course : courses) {
                    courseIds.add(course.getCourseId());
                    courseNames.add(course.getCourseName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_spinner_item, courseNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCourses.setAdapter(adapter);
            }
        }.execute();


        btnAddStudentToCourse.setOnClickListener(v -> {
            String studentIdStr = edtStudentId.getText().toString().trim();
            String studentName = edtStudentName.getText().toString().trim();
            int selectedCoursePosition = spinnerCourses.getSelectedItemPosition();
            if (selectedCoursePosition >= 0 && selectedCoursePosition < courseIds.size()) {
                int courseId = courseIds.get(selectedCoursePosition);

                StudentCourse student = new StudentCourse();
                student.setStudentId(Integer.parseInt(studentIdStr));
                student.setStudentName(studentName);
                student.setCourseId(courseId);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long insertedId = studentDao.insert(student);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (insertedId != -1) {
                                    Toast.makeText(StudentActivity.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                                    edtStudentName.setText("");
                                    finish();
                                } else {
                                    Toast.makeText(StudentActivity.this, "Error Adding Student", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new Thread(() -> {
            List<StudentCourse> studentList = studentDao.getAllStudentCourses();
            StudentsAdapter adapters = new StudentsAdapter(this, studentList);
            recyclerView.setAdapter(adapters);
        }).start();

    }
}

package au.edu.federation.itech3107.studentattendance30395752;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.MonthCalendar;
import com.necer.calendar.WeekCalendar;
import com.necer.entity.CalendarDate;
import com.necer.enumeration.CheckModel;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.painter.CalendarPainter;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private int currentWeek = 1; // start with "1week"

    private String UpClassDate;
    private TextInputEditText edtCourseId, edtCourseName;
    private TextView tvStartDate , tvEndDate;
    private CourseDao courseDao;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);


        edtCourseId = findViewById(R.id.edtCourseId);
        edtCourseName = findViewById(R.id.edtCourseName);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);

         Button btnSave = findViewById(R.id.btnSave);


        AppDatabase database = AppDatabase.getDatabase(this);
        courseDao = database.courseDao();



        WeekCalendar weekUpclassDate = findViewById(R.id.upclassDate);

        WeekCalendar weekCalendar = findViewById(R.id.btnPickStartDate);
        weekCalendar.setCheckMode(CheckModel.SINGLE_DEFAULT_CHECKED);
        weekCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, org.joda.time.LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                tvStartDate.setText(localDate.toString());

            }
        });
        weekUpclassDate.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, org.joda.time.LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                UpClassDate = localDate.toString();
            }
        });
        Button buttonlast = findViewById(R.id.toLastPager);
        Button buttonnext = findViewById(R.id.toNextPager);
        TextView txNextPager = findViewById(R.id.tx_pager);

        buttonlast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentWeek>1){
                    currentWeek --;
                    txNextPager.setText(currentWeek + "week");
                    weekCalendar.toLastPager();
                }

            }
        });

        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekCalendar.toNextPager();
                currentWeek ++;
                txNextPager.setText( currentWeek + "week");
            }
        });


        WeekCalendar weekCalendar2 = findViewById(R.id.btnPickEndDate);
        weekCalendar2.setCheckMode(CheckModel.SINGLE_DEFAULT_CHECKED);
        weekCalendar2.setOnCalendarChangedListener((baseCalendar, year, month, localDate, dateChangeBehavior) ->
                tvEndDate.setText(localDate.toString()));

        btnSave.setOnClickListener(v -> {
            String courseId = edtCourseId.getText().toString().trim();
            String courseName = edtCourseName.getText().toString().trim();
            String startDate = tvStartDate.getText().toString().trim();
            String endDate = tvEndDate.getText().toString().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 根据您的日期格式进行调整
            LocalDate currentStartDate = LocalDate.parse(UpClassDate, formatter);

            List<String> datesList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                datesList.add(currentStartDate.format(formatter));
                currentStartDate = currentStartDate.plusDays(7);
            }

            Course course = new Course();
            course.setCourseId(Integer.parseInt(courseId));
            course.setCourseName(courseName);
            course.setStartDate(startDate);
            course.setEndDate(UpClassDate);
            course.setListData(datesList);


            new Thread(() -> {
                long result = courseDao.insert(course);


                runOnUiThread(() -> {
                    if (result != -1) {
                        Toast.makeText(CourseActivity.this, "Course Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(CourseActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CourseActivity.this, "Error Adding Course", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();

        });
    }


}

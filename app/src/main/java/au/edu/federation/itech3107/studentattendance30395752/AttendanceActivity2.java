package au.edu.federation.itech3107.studentattendance30395752;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.hdev.calendar.bean.DateInfo;
import com.hdev.calendar.view.SingleCalendarView;
import com.necer.calendar.MonthCalendar;
import com.necer.calendar.WeekCalendar;
import com.necer.enumeration.CheckModel;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395752.adapter.AttendanceAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class AttendanceActivity2 extends AppCompatActivity {

    private ListView attendanceListView;
    private AttendanceDao attendanceDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance2);


         attendanceListView = findViewById(R.id.attendanceListView);

        AppDatabase database = AppDatabase.getDatabase(this);
        attendanceDao = database.attendanceDao();
        SingleCalendarView weekCalendar = findViewById(R.id.weekCalendar);
        DateInfo startDate = new DateInfo(2023,1,15);
        DateInfo endDate = new DateInfo(2025,1,15);
        DateInfo  selectedDates = new DateInfo(2023,10,25);

        weekCalendar.setSelectedDate(selectedDates);
        weekCalendar.setDateRange(startDate.timeInMillis(),endDate.timeInMillis(),selectedDates.timeInMillis());
        weekCalendar.setOnSingleDateSelectedListener((singleCalendarView, dateInfo) -> {
            String selectedDate = String.format("%d-%02d-%02d", dateInfo.getYear(), dateInfo.getMonth(), dateInfo.getDay());
            Log.e("获取当前日期：",selectedDate.toString());
            displayAttendance(selectedDate);
            return null;
        });
//        weekCalendar.setOnCalendarChangedListener((baseCalendar, year, month, localDate, dateChangeBehavior) -> {
//
//            String selectedDate = localDate.toString();
//            displayAttendance(selectedDate);
//
//        });

    }

    private void displayAttendance(String date) {
        new Thread(() -> {
            List<Attendance> attendanceList = attendanceDao.getAttendanceByDate(date);

                new Handler(Looper.getMainLooper()).post(() -> {

                    AttendanceAdapter adapter = new AttendanceAdapter(this, attendanceList);
                    attendanceListView.setAdapter(adapter);
                });

        }).start();

    }

}

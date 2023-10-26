package au.edu.federation.itech3107.studentattendance30395752.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395752.R;
import au.edu.federation.itech3107.studentattendance30395752.StudentCourse;
import au.edu.federation.itech3107.studentattendance30395752.StudentCourseDao;


public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.ViewHolder> {
    private Context context;
    private List<StudentCourse> students;
    private SparseBooleanArray attendanceState;
    private StudentCourseDao studentDao;

    public StudentAttendanceAdapter(Context context, List<StudentCourse> students, StudentCourseDao studentDao) {
        this.context = context;
        this.students = students;
        this.studentDao = studentDao;
        this.attendanceState = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentCourse student = students.get(position);
        holder.studentNameTextView.setText(student.getStudentName());

        holder.attendanceCheckbox.setChecked(attendanceState.get(student.getStudentId(), false));
        holder.attendanceCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> attendanceState.put(student.getStudentId(), isChecked));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public SparseBooleanArray getAttendanceState() {
        return attendanceState;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentNameTextView;
        CheckBox attendanceCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.studentNameTextView);
            attendanceCheckbox = itemView.findViewById(R.id.attendanceCheckbox);
        }
    }
}

package au.edu.federation.itech3107.studentattendance30395752.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import au.edu.federation.itech3107.studentattendance30395752.AppDatabase;
import au.edu.federation.itech3107.studentattendance30395752.Course;
import au.edu.federation.itech3107.studentattendance30395752.CourseDao;
import au.edu.federation.itech3107.studentattendance30395752.R;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> courses;
    private Context context;
    private OnCourseClickListener clickListener;
    private CourseDao courseDao; // Add a CourseDao variable.

    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }

    public CourseAdapter(List<Course> courses, Context context, OnCourseClickListener clickListener) {
        this.courses = courses;
        this.context = context;
        this.clickListener = clickListener;
        

        AppDatabase database = AppDatabase.getDatabase(context);
        courseDao = database.courseDao();
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.tvCourseName.setText(course.getCourseName());

        LocalDate date = LocalDate.parse(course.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        holder.tvClassDate.setText(dayOfWeek);

        holder.btnDelete.setOnClickListener(v -> {
            new Thread(() -> {
                int currentPos = holder.getAdapterPosition();
                if (currentPos == RecyclerView.NO_POSITION) {
                    // Handle the case where the position is invalid.
                    return;
                }

                if (deleteCourse(course.getCourseId())) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        if (currentPos >= 0 && currentPos < courses.size()) {
                            courses.remove(currentPos);
                            notifyItemRemoved(currentPos);
                        } else {
                            // Handle the invalid position if needed
                        }
                    });
                }
            }).start();
        });


        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onCourseClick(course);
            }
        });
    }
    private boolean deleteCourse(int courseId) {
        int deletedRows = courseDao.deleteByCourseId(courseId);
        return deletedRows > 0;
    }
    @Override
    public int getItemCount() {
        return courses.size();
    }
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName;
        Button btnDelete;

        TextView tvClassDate;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvClassDate =itemView.findViewById(R.id.tv_up_class_date);
        }
    }
}

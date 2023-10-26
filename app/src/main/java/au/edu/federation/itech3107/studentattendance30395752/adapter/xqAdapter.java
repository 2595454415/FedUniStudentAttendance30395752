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


public class xqAdapter extends RecyclerView.Adapter<xqAdapter.ViewHolder> {
    private Context context;
    private List<String> students;

    public interface OnItemClickListener {
        void onItemClick(int position, String studentName);
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public xqAdapter(Context context, List<String> students ) {
        this.context = context;
        this.students = students;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xq, parent, false);
        return new ViewHolder(view, listener, students);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.studentNameTextView.setText(students.get(position).toString());


    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentNameTextView;
        private OnItemClickListener itemListener;
        private List<String> studentList;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener, List<String> students) {
            super(itemView);
            this.itemListener = listener;
            this.studentList = students;

            studentNameTextView = itemView.findViewById(R.id.studentNameTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemListener.onItemClick(position, studentList.get(position));

                        }
                    }
                }
            });
        }
    }

}

package com.example.studymate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studymate.R;
import com.example.studymate.database.DatabaseHelper;
import com.example.studymate.models.StudyTask;
import java.util.List;

public class StudyPlannerAdapter extends RecyclerView.Adapter<StudyPlannerAdapter.ViewHolder> {

    private final Context context;
    private final List<StudyTask> taskList;
    private final DatabaseHelper dbHelper;

    public StudyPlannerAdapter(Context context, List<StudyTask> taskList) {
        this.context = context;
        this.taskList = taskList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_study_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudyTask task = taskList.get(position);
        holder.tvPlanTime.setText(task.getTimeSlot());
        holder.tvPlanSubject.setText(task.getSubject());
        holder.tvPlanTopic.setText("Topic: " + task.getTopic());

        holder.btnDeletePlan.setOnClickListener(v -> {
            dbHelper.deleteStudyTask(task.getId());
            taskList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlanTime, tvPlanSubject, tvPlanTopic;
        ImageButton btnDeletePlan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlanTime = itemView.findViewById(R.id.tvPlanTime);
            tvPlanSubject = itemView.findViewById(R.id.tvPlanSubject);
            tvPlanTopic = itemView.findViewById(R.id.tvPlanTopic);
            btnDeletePlan = itemView.findViewById(R.id.btnDeletePlan);
        }
    }
}
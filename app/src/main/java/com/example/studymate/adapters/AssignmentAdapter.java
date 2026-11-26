package com.example.studymate.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studymate.AddAssignmentActivity;
import com.example.studymate.R;
import com.example.studymate.database.DatabaseHelper;
import com.example.studymate.models.Assignment;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    private final Context context;
    private final List<Assignment> assignmentList;
    private final DatabaseHelper dbHelper;

    public AssignmentAdapter(Context context, List<Assignment> assignmentList) {
        this.context = context;
        this.assignmentList = assignmentList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Assignment item = assignmentList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvSubject.setText(item.getSubject());
        holder.tvDueDate.setText("Due: " + item.getDueDate());

        String priority = item.getPriority() != null ? item.getPriority() : "Medium";
        holder.tvPriorityBadge.setText(priority.toUpperCase());
        if (priority.equalsIgnoreCase("High")) {
            holder.tvPriorityBadge.setBackgroundColor(android.graphics.Color.parseColor("#E53935"));
        } else if (priority.equalsIgnoreCase("Low")) {
            holder.tvPriorityBadge.setBackgroundColor(android.graphics.Color.parseColor("#43A047"));
        } else {
            holder.tvPriorityBadge.setBackgroundColor(android.graphics.Color.parseColor("#FB8C00"));
        }

        holder.cbCompleted.setOnCheckedChangeListener(null);
        holder.cbCompleted.setChecked(item.getIsCompleted() == 1);

        if (item.getIsCompleted() == 1) {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int status = isChecked ? 1 : 0;
            dbHelper.updateStatus(item.getId(), status);
            item.setIsCompleted(status);
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddAssignmentActivity.class);
            intent.putExtra("IS_EDIT", true);
            intent.putExtra("PRIORITY", item.getPriority());
            intent.putExtra("ID", item.getId());
            intent.putExtra("TITLE", item.getTitle());
            intent.putExtra("SUBJECT", item.getSubject());
            intent.putExtra("DUE_DATE", item.getDueDate());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            dbHelper.deleteAssignment(item.getId());
            assignmentList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubject, tvDueDate;
        CheckBox cbCompleted;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvDueDate = itemView.findViewById(R.id.tvDueDate);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
package com.example.studymate;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studymate.database.DatabaseHelper;

public class ProgressReportActivity extends AppCompatActivity {

    private TextView tvPercentage, tvReportDetails, tvMotivation;
    private ProgressBar progressBar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report);

        dbHelper = new DatabaseHelper(this);

        tvPercentage = findViewById(R.id.tvPercentage);
        tvReportDetails = findViewById(R.id.tvReportDetails);
        tvMotivation = findViewById(R.id.tvMotivation);
        progressBar = findViewById(R.id.progressBar);

        loadReport();
    }

    private void loadReport() {
        int total = dbHelper.getTotalAssignmentsCount();
        int completed = dbHelper.getCompletedAssignmentsCount();
        int pending = total - completed;
        int studySessions = dbHelper.getAllStudyTasks().size();
        int subjectsCount = dbHelper.getAllSubjects().size();

        int percent = (total > 0) ? (completed * 100) / total : 0;

        progressBar.setProgress(percent);
        tvPercentage.setText(percent + "% Completed");

        String summary = "Total Subjects: " + subjectsCount + "\n" +
                "Total Assignments: " + total + "\n" +
                "Completed Assignments: " + completed + "\n" +
                "Pending Assignments: " + pending + "\n" +
                "Study Planner Sessions: " + studySessions;

        tvReportDetails.setText(summary);

        if (percent == 100 && total > 0) {
            tvMotivation.setText("Excellent! You have completed all your assignments!");
        } else if (percent >= 50) {
            tvMotivation.setText("Great progress! Over half of your work is done.");
        } else {
            tvMotivation.setText("Stay focused! Start completing your pending tasks.");
        }
    }
}
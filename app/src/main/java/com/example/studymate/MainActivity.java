package com.example.studymate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studymate.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalCount, tvPendingCount, tvCompletedCount;
    private Button btnModuleAssignments, btnModuleSubjects, btnModulePlanner, btnModuleCalendar, btnModuleReports;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvPendingCount = findViewById(R.id.tvPendingCount);
        tvCompletedCount = findViewById(R.id.tvCompletedCount);

        btnModuleAssignments = findViewById(R.id.btnModuleAssignments);
        btnModuleSubjects = findViewById(R.id.btnModuleSubjects);
        btnModulePlanner = findViewById(R.id.btnModulePlanner);
        btnModuleCalendar = findViewById(R.id.btnModuleCalendar);
        btnModuleReports = findViewById(R.id.btnModuleReports);

        btnModuleAssignments.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AssignmentListActivity.class)));

        btnModuleSubjects.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SubjectManagerActivity.class)));

        btnModulePlanner.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, StudyPlannerActivity.class)));

        btnModuleCalendar.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CalendarActivity.class)));

        btnModuleReports.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ProgressReportActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDashboardCounts();
    }

    private void updateDashboardCounts() {
        int total = dbHelper.getTotalAssignmentsCount();
        int completed = dbHelper.getCompletedAssignmentsCount();
        int pending = total - completed;

        tvTotalCount.setText(String.valueOf(total));
        tvCompletedCount.setText(String.valueOf(completed));
        tvPendingCount.setText(String.valueOf(pending));
    }
}
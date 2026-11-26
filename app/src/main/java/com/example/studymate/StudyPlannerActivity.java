package com.example.studymate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studymate.adapters.StudyPlannerAdapter;
import com.example.studymate.database.DatabaseHelper;
import com.example.studymate.models.StudyTask;

public class StudyPlannerActivity extends AppCompatActivity {

    private EditText etPlanSubject, etPlanTopic, etPlanTime;
    private Button btnAddSchedule;
    private RecyclerView rvStudyPlanner;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_planner);

        dbHelper = new DatabaseHelper(this);
        etPlanSubject = findViewById(R.id.etPlanSubject);
        etPlanTopic = findViewById(R.id.etPlanTopic);
        etPlanTime = findViewById(R.id.etPlanTime);
        btnAddSchedule = findViewById(R.id.btnAddSchedule);
        rvStudyPlanner = findViewById(R.id.rvStudyPlanner);

        rvStudyPlanner.setLayoutManager(new LinearLayoutManager(this));
        loadSchedule();

        btnAddSchedule.setOnClickListener(v -> {
            String sub = etPlanSubject.getText().toString().trim();
            String topic = etPlanTopic.getText().toString().trim();
            String time = etPlanTime.getText().toString().trim();

            if (sub.isEmpty() || topic.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Fill all schedule fields", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.insertStudyTask(new StudyTask(sub, topic, time));
            etPlanSubject.setText("");
            etPlanTopic.setText("");
            etPlanTime.setText("");
            loadSchedule();
            Toast.makeText(this, "Schedule Added!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadSchedule() {
        rvStudyPlanner.setAdapter(new StudyPlannerAdapter(this, dbHelper.getAllStudyTasks()));
    }
}
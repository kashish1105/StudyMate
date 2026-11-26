package com.example.studymate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studymate.adapters.SubjectAdapter;
import com.example.studymate.database.DatabaseHelper;
import com.example.studymate.models.Subject;

public class SubjectManagerActivity extends AppCompatActivity {

    private EditText etSubjectName, etTeacherName;
    private Button btnAddSubject;
    private RecyclerView rvSubjects;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_manager);

        dbHelper = new DatabaseHelper(this);
        etSubjectName = findViewById(R.id.etSubjectName);
        etTeacherName = findViewById(R.id.etTeacherName);
        btnAddSubject = findViewById(R.id.btnAddSubject);
        rvSubjects = findViewById(R.id.rvSubjects);

        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
        loadSubjects();

        btnAddSubject.setOnClickListener(v -> {
            String subName = etSubjectName.getText().toString().trim();
            String teacher = etTeacherName.getText().toString().trim();

            if (subName.isEmpty()) {
                Toast.makeText(this, "Enter subject name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (teacher.isEmpty()) teacher = "Not Assigned";

            dbHelper.insertSubject(new Subject(subName, teacher));
            etSubjectName.setText("");
            etTeacherName.setText("");
            loadSubjects();
            Toast.makeText(this, "Subject Added!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadSubjects() {
        rvSubjects.setAdapter(new SubjectAdapter(this, dbHelper.getAllSubjects()));
    }
}
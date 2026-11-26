package com.example.studymate;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studymate.adapters.AssignmentAdapter;
import com.example.studymate.database.DatabaseHelper;
import com.example.studymate.models.Assignment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class AssignmentListActivity extends AppCompatActivity {

    private RecyclerView rvAssignments;
    private FloatingActionButton fabAddAssignment;
    private DatabaseHelper dbHelper;
    private String filterSubject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);

        dbHelper = new DatabaseHelper(this);
        rvAssignments = findViewById(R.id.rvAssignments);
        fabAddAssignment = findViewById(R.id.fabAddAssignment);

        rvAssignments.setLayoutManager(new LinearLayoutManager(this));

        // Agar Subject Manager se kisi specific subject par click karke aaye hain
        filterSubject = getIntent().getStringExtra("FILTER_SUBJECT");

        fabAddAssignment.setOnClickListener(v -> {
            startActivity(new Intent(this, AddAssignmentActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Assignment> list;
        if (filterSubject != null) {
            list = dbHelper.getAssignmentsBySubject(filterSubject);
        } else {
            list = dbHelper.getAllAssignments();
        }
        rvAssignments.setAdapter(new AssignmentAdapter(this, list));
    }
}
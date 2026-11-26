package com.example.studymate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studymate.database.DatabaseHelper;
import com.example.studymate.models.Assignment;
import com.example.studymate.models.Subject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAssignmentActivity extends AppCompatActivity {

    private EditText etTitle;
    private Spinner spinnerSubject, spinnerPriority;
    private TextView tvSelectedDate, tvFormHeader;
    private Button btnPickDate, btnSave;
    private String selectedDate = "";
    private DatabaseHelper dbHelper;

    private boolean isEdit = false;
    private int editId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        dbHelper = new DatabaseHelper(this);

        tvFormHeader = findViewById(R.id.tvFormHeader);
        etTitle = findViewById(R.id.etTitle);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnSave = findViewById(R.id.btnSave);

        // Load Subjects into Spinner
        List<String> subjectNames = new ArrayList<>();
        for (Subject s : dbHelper.getAllSubjects()) {
            subjectNames.add(s.getName());
        }
        if (subjectNames.isEmpty()) {
            subjectNames.add("General");
            subjectNames.add("Java Programming");
            subjectNames.add("DBMS");
            subjectNames.add("Mobile Computing");
        }
        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectNames);
        spinnerSubject.setAdapter(subAdapter);

        // Load Priorities into Spinner
        String[] priorities = {"High", "Medium", "Low"};
        ArrayAdapter<String> prioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
        spinnerPriority.setAdapter(prioAdapter);

        // Edit Mode Setup
        if (getIntent().getBooleanExtra("IS_EDIT", false)) {
            isEdit = true;
            editId = getIntent().getIntExtra("ID", -1);
            etTitle.setText(getIntent().getStringExtra("TITLE"));
            selectedDate = getIntent().getStringExtra("DUE_DATE");
            tvSelectedDate.setText("Due Date: " + selectedDate);
            tvFormHeader.setText("Edit Assignment");
            btnSave.setText("Update Assignment");

            String oldSub = getIntent().getStringExtra("SUBJECT");
            int subPos = subAdapter.getPosition(oldSub);
            if (subPos >= 0) spinnerSubject.setSelection(subPos);

            String oldPrio = getIntent().getStringExtra("PRIORITY");
            int prioPos = prioAdapter.getPosition(oldPrio);
            if (prioPos >= 0) spinnerPriority.setSelection(prioPos);
        }

        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) -> {
                selectedDate = d + "/" + (m + 1) + "/" + y;
                tvSelectedDate.setText("Due Date: " + selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String subject = spinnerSubject.getSelectedItem().toString();
            String priority = spinnerPriority.getSelectedItem().toString();

            if (title.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Please enter title and due date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEdit) {
                dbHelper.updateAssignment(editId, title, subject, selectedDate, priority);
                Toast.makeText(this, "Assignment Updated!", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.insertAssignment(new Assignment(title, subject, selectedDate, 0, priority));
                Toast.makeText(this, "Assignment Saved!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}
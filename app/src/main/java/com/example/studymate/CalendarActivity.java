package com.example.studymate;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studymate.adapters.AssignmentAdapter;
import com.example.studymate.database.DatabaseHelper;
import com.example.studymate.models.Assignment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView tvSelectedDateHeader;
    private RecyclerView rvCalendarTasks;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dbHelper = new DatabaseHelper(this);
        calendarView = findViewById(R.id.calendarView);
        tvSelectedDateHeader = findViewById(R.id.tvSelectedDateHeader);
        rvCalendarTasks = findViewById(R.id.rvCalendarTasks);

        rvCalendarTasks.setLayoutManager(new LinearLayoutManager(this));

        // Aaj ki date default load
        Calendar cal = Calendar.getInstance();
        String today = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
        filterAssignmentsByDate(today);

        // Date change hone par filter
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String pickedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            filterAssignmentsByDate(pickedDate);
        });
    }

    private void filterAssignmentsByDate(String date) {
        tvSelectedDateHeader.setText("Due on " + date + ":");
        List<Assignment> all = dbHelper.getAllAssignments();
        List<Assignment> filtered = new ArrayList<>();

        for (Assignment a : all) {
            if (a.getDueDate().equals(date)) {
                filtered.add(a);
            }
        }

        if (filtered.isEmpty()) {
            Toast.makeText(this, "No assignments due on " + date, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Reminder: " + filtered.size() + " task(s) due!", Toast.LENGTH_SHORT).show();
        }

        rvCalendarTasks.setAdapter(new AssignmentAdapter(this, filtered));
    }
}
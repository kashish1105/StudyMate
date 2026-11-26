package com.example.studymate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.studymate.models.Assignment;
import com.example.studymate.models.StudyTask;
import com.example.studymate.models.Subject;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "studymate_full.db";
    private static final int DATABASE_VERSION = 2;
    public static final String COL_PRIORITY = "priority";

    // Table 1: Assignments
    public static final String TABLE_ASSIGNMENTS = "assignments";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_SUBJECT = "subject";
    public static final String COL_DUE_DATE = "due_date";
    public static final String COL_COMPLETED = "is_completed";

    // Table 2: Subjects
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String COL_SUB_ID = "sub_id";
    public static final String COL_SUB_NAME = "sub_name";
    public static final String COL_TEACHER = "teacher_name";

    // Table 3: Study Planner
    public static final String TABLE_PLANNER = "study_planner";
    public static final String COL_PLAN_ID = "plan_id";
    public static final String COL_PLAN_SUBJECT = "plan_subject";
    public static final String COL_PLAN_TOPIC = "plan_topic";
    public static final String COL_PLAN_TIME = "plan_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAssignments = "CREATE TABLE " + TABLE_ASSIGNMENTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_SUBJECT + " TEXT, " +
                COL_DUE_DATE + " TEXT, " +
                COL_COMPLETED + " INTEGER, " +
                COL_PRIORITY + " TEXT)";

        String createSubjects = "CREATE TABLE " + TABLE_SUBJECTS + " (" +
                COL_SUB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SUB_NAME + " TEXT, " +
                COL_TEACHER + " TEXT)";

        String createPlanner = "CREATE TABLE " + TABLE_PLANNER + " (" +
                COL_PLAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PLAN_SUBJECT + " TEXT, " +
                COL_PLAN_TOPIC + " TEXT, " +
                COL_PLAN_TIME + " TEXT)";

        db.execSQL(createAssignments);
        db.execSQL(createSubjects);
        db.execSQL(createPlanner);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANNER);
        onCreate(db);
    }

    // ================= 1. ASSIGNMENTS CRUD =================
    public boolean insertAssignment(Assignment a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, a.getTitle());
        cv.put(COL_SUBJECT, a.getSubject());
        cv.put(COL_DUE_DATE, a.getDueDate());
        cv.put(COL_COMPLETED, a.getIsCompleted());
        cv.put(COL_PRIORITY, a.getPriority());
        return db.insert(TABLE_ASSIGNMENTS, null, cv) != -1;
    }

    public boolean updateAssignment(int id, String title, String subject, String dueDate, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, title);
        cv.put(COL_SUBJECT, subject);
        cv.put(COL_DUE_DATE, dueDate);
        cv.put(COL_PRIORITY, priority);
        return db.update(TABLE_ASSIGNMENTS, cv, COL_ID + " = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public List<Assignment> getAllAssignments() {
        List<Assignment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ASSIGNMENTS + " ORDER BY " + COL_ID + " DESC", null);
        if (c.moveToFirst()) {
            do {
                list.add(new Assignment(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4), c.getString(5)));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public List<Assignment> getAssignmentsBySubject(String subjectName) {
        List<Assignment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ASSIGNMENTS + " WHERE " + COL_SUBJECT + " = ?", new String[]{subjectName});
        if (c.moveToFirst()) {
            do {
                list.add(new Assignment(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4), c.getString(5)));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
    public void updateStatus(int id, int isCompleted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_COMPLETED, isCompleted);
        db.update(TABLE_ASSIGNMENTS, cv, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAssignment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSIGNMENTS, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // ================= 2. DASHBOARD & REPORTS COUNTS =================
    public int getTotalAssignmentsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ASSIGNMENTS, null);
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        return count;
    }

    public int getCompletedAssignmentsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ASSIGNMENTS + " WHERE " + COL_COMPLETED + " = 1", null);
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        return count;
    }

    // ================= 3. SUBJECTS CRUD =================
    public boolean insertSubject(Subject s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_SUB_NAME, s.getName());
        cv.put(COL_TEACHER, s.getTeacherName());
        return db.insert(TABLE_SUBJECTS, null, cv) != -1;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_SUBJECTS, null);
        if (c.moveToFirst()) {
            do {
                list.add(new Subject(c.getInt(0), c.getString(1), c.getString(2)));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // ================= 4. STUDY PLANNER CRUD =================
    public boolean insertStudyTask(StudyTask t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PLAN_SUBJECT, t.getSubject());
        cv.put(COL_PLAN_TOPIC, t.getTopic());
        cv.put(COL_PLAN_TIME, t.getTimeSlot());
        return db.insert(TABLE_PLANNER, null, cv) != -1;
    }

    public List<StudyTask> getAllStudyTasks() {
        List<StudyTask> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PLANNER, null);
        if (c.moveToFirst()) {
            do {
                list.add(new StudyTask(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public void deleteStudyTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANNER, COL_PLAN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
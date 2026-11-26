package com.example.studymate.models;

public class Subject {
    private int id;
    private String name;
    private String teacherName;

    public Subject(int id, String name, String teacherName) {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
    }

    public Subject(String name, String teacherName) {
        this.name = name;
        this.teacherName = teacherName;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getTeacherName() { return teacherName; }
}
package com.example.studymate.models;

public class Assignment {
    private int id;
    private String title;
    private String subject;
    private String dueDate;
    private int isCompleted;
    private String priority;

    public Assignment(int id, String title, String subject, String dueDate, int isCompleted, String priority) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.priority = priority;
    }

    public Assignment(String title, String subject, String dueDate, int isCompleted, String priority) {
        this.title = title;
        this.subject = subject;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.priority = priority;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public String getDueDate() { return dueDate; }
    public int getIsCompleted() { return isCompleted; }
    public void setIsCompleted(int isCompleted) { this.isCompleted = isCompleted; }
    public String getPriority() { return priority; }
}
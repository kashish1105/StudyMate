package com.example.studymate.models;

public class StudyTask {
    private int id;
    private String subject;
    private String topic;
    private String timeSlot;

    public StudyTask(int id, String subject, String topic, String timeSlot) {
        this.id = id;
        this.subject = subject;
        this.topic = topic;
        this.timeSlot = timeSlot;
    }

    public StudyTask(String subject, String topic, String timeSlot) {
        this.subject = subject;
        this.topic = topic;
        this.timeSlot = timeSlot;
    }

    public int getId() { return id; }
    public String getSubject() { return subject; }
    public String getTopic() { return topic; }
    public String getTimeSlot() { return timeSlot; }
}
package com.abc123.iro;

public class Notifications {
    private int id;
    private int type;
    private String subject;
    private String content;
    private String date;

    public Notifications(int id, int type, String subject, String content, String date) {
        this.id = id;
        this.type = type;
        this.subject = subject;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
